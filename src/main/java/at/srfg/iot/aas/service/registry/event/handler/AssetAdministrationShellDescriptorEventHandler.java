package at.srfg.iot.aas.service.registry.event.handler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.service.registry.RegistryService;
import at.srfg.iot.aas.service.registry.RegistryWorker;
import at.srfg.iot.aas.service.registry.event.AssetAdministrationShellDescriptorEvent;
import at.srfg.iot.aas.service.registry.event.SubmodelDescriptorEvent;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Endpoint;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.SubmodelDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdType;
import at.srfg.iot.common.datamodel.asset.api.ISubmodel;

@Component
public class AssetAdministrationShellDescriptorEventHandler {
	@Autowired
	ApplicationEventPublisher publisher;
	@Autowired
	RegistryWorker worker;
	@Autowired
	private RegistryService registry;
	@EventListener
	public void onSubmodelDescriptorEvent(SubmodelDescriptorEvent event) {
		Submodel entity = event.getEntity();
		SubmodelDescriptor dto = event.getDTO();
		if ( dto.getEndpoints()!= null) {
			for (Integer index : dto.getEndpoints().keySet() ) {
				Endpoint ep = dto.getEndpoint(index);
				entity.setEndpoint(index, ep.getAddress(), ep.getType());
			}
		}
	}
	@EventListener
	public void onAssetAdministrationShellEvent(AssetAdministrationShellDescriptorEvent event) {
		
		AssetAdministrationShell entity = event.getEntity();
		AssetAdministrationShellDescriptor dto = event.getDTO();
		// 
		Optional<AssetAdministrationShell> type = Optional.empty();
		if ( dto.getDerivedFrom() != null ) {
			type = registry.resolveReference(dto.getDerivedFrom(), AssetAdministrationShell.class);
			AssetAdministrationShell parentType = type.get();
			entity.setDerivedFromElement(parentType);
		}
		
		for (Integer index : dto.getEndpoints().keySet() ) {
			Endpoint ep = dto.getEndpoint(index);
			entity.setEndpoint(index, ep.getAddress(), ep.getType());
		}
		// save the entity before processing dendencies
		registry.saveAssetAdministrationShell(entity);
		// 
		for ( SubmodelDescriptor sub : dto.getSubmodels()) {
			// the submodel might have an identifier "idShort", so need to find the model based on identifier
			if ( sub.getIdType().equals(IdType.IdShort)) {
				// retrieve the submodel via idShort
				Optional<ISubmodel> existing = entity.getSubmodel(sub.getIdShort());
				processSubmodel(entity,  existing.orElse(null),  sub);
			}
			else {
				// the identifier is global, read the model from the registry
				Optional<Submodel> existing = registry.getSubmodel(sub.getIdentification());
				processSubmodel(entity,  existing.orElse(null),  sub);
			}
		}
		

	}
	private void processSubmodel(AssetAdministrationShell shell, ISubmodel existing, SubmodelDescriptor dto) {
		if (existing != null) {
			throw new IllegalArgumentException(String.format("Submodel [%s] already exists and is not part of the the AssetAdministrationShell [%s]!",
					existing.getIdentification().toString(),
					shell.getIdentification().toString()
					));
		}
		else {
			Submodel newSubmodel = new Submodel(dto.getIdentification(), shell);
			worker.registerSubmodel(newSubmodel, dto);
		}
		
	}
}
