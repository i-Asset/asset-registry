package at.srfg.iot.aas.service.registry.event.handler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.aas.basic.directory.SubmodelDescriptor;
import at.srfg.iot.aas.common.referencing.IdType;
import at.srfg.iot.aas.service.registry.RegistryService;
import at.srfg.iot.aas.service.registry.RegistryWorker;
import at.srfg.iot.aas.service.registry.event.AssetAdministrationShellDescriptorEvent;

@Component
public class AssetAdministrationShellDescriptorEventHandler {
	@Autowired
	ApplicationEventPublisher publisher;
	@Autowired
	RegistryWorker worker;
	@Autowired
	private RegistryService registry;
	@EventListener
	public void onAssetAdministrationShellEvent(AssetAdministrationShellDescriptorEvent event) {
		
		AssetAdministrationShell entity = event.getEntity();
		AssetAdministrationShellDescriptor dto = event.getDTO();

		for ( SubmodelDescriptor sub : dto.getSubmodels()) {
			// the submodel might have an identifier "idShort", so need to find the model based on identifier
			if ( sub.getIdType().equals(IdType.IdShort)) {
				// retrieve the submodel via idShort
				Optional<Submodel> existing = entity.getSubmodel(sub.getIdShort());
				processSubmodel(entity,  existing,  sub);
			}
			else {
				// the identifier is global, read the model from the registry
				Optional<Submodel> existing = registry.getSubmodel(sub.getIdentification());
				processSubmodel(entity,  existing,  sub);
			}
		}
	}
	private void processSubmodel(AssetAdministrationShell shell, Optional<Submodel> existing, SubmodelDescriptor dto) {
		if (existing.isPresent()) {
			Submodel e = existing.get();
			throw new IllegalArgumentException(String.format("Submodel [%s] already exists and is not part of the the AssetAdministrationShell [%s]!",
					e.getIdentification().toString(),
					shell.getIdentification().toString()
					));
			// update only when shell's are equal
//			if ( e.getAssetAdministrationShell().equals(shell)) {
//			}
//			else {
//				worker.registerSubmodel(existing.get(), dto);
//			}
		}
		else {
			Submodel newSubmodel = new Submodel(dto.getIdentification(), shell);
			worker.registerSubmodel(newSubmodel, dto);
		}
		
	}
}
