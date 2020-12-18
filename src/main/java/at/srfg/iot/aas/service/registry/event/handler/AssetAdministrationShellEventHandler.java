package at.srfg.iot.aas.service.registry.event.handler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.service.registry.RegistryService;
import at.srfg.iot.aas.service.registry.RegistryWorker;
import at.srfg.iot.aas.service.registry.event.AssetAdministrationShellEvent;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdType;

@Component
public class AssetAdministrationShellEventHandler {
	@Autowired
	ApplicationEventPublisher publisher;
	@Autowired
	RegistryWorker worker;
	@Autowired
	private RegistryService registry;
	@EventListener
	public void onAssetAdministrationShellEvent(AssetAdministrationShellEvent event) {
		
		AssetAdministrationShell entity = event.getEntity();
		AssetAdministrationShell dto = event.getDTO();

		if (! event.isDerivedFromResolved()) {
			// need to resolve the reference in DTO
			Optional<AssetAdministrationShell> parent = registry.resolveReference(dto.getDerivedFrom(), AssetAdministrationShell.class);
			if ( parent.isPresent() ) {
				entity.setDerivedFromElement(parent.get());
			}
		}
		registry.saveAssetAdministrationShell(entity);
		// do the post-processing
		for ( Submodel sub : dto.getChildElements(Submodel.class)) {
			// the submodel might have an identifier "idShort", so need to find the model based on identifier
			if ( sub.getIdType().equals(IdType.IdShort)) {
				// retrieve the submodel via idShort
				Optional<Submodel> existing = entity.getChildElement(sub.getIdShort(), Submodel.class);
				processSubmodel(entity,  existing,  sub);
			}
			else {
				// the identifier is global, read the model from the registry
				Optional<Submodel> existing = registry.getSubmodel(sub.getIdentification());
				processSubmodel(entity,  existing,  sub);
			}
		}
	}
	private void processSubmodel(AssetAdministrationShell shell, Optional<Submodel> existing, Submodel dto) {
		if (existing.isPresent()) {
			Submodel e = existing.get();
			// update onlywhen shell's are equal
			if ( e.getAssetAdministrationShell().equals(shell)) {
				worker.setSubmodel(existing.get(), dto);
			}
			else {
				throw new IllegalArgumentException(String.format("Submodel [%s] already exists and is not part of the the AssetAdministrationShell [%s]!",
						e.getIdentification().toString(),
						shell.getIdentification().toString()
						));
			}
		}
		else {
			Submodel newSubmodel = new Submodel(dto.getIdentification(), shell);
			worker.setSubmodel(newSubmodel, dto);
		}
		
	}
}
