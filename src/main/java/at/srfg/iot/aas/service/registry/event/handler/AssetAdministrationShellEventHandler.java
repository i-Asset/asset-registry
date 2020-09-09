package at.srfg.iot.aas.service.registry.event.handler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.referencing.IdType;
import at.srfg.iot.aas.service.registry.RegistryService;
import at.srfg.iot.aas.service.registry.RegistryWorker;
import at.srfg.iot.aas.service.registry.event.AssetAdministrationShellEvent;

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
		for ( Submodel sub : dto.getSubModel()) {
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
	private void processSubmodel(AssetAdministrationShell shell, Optional<Submodel> existing, Submodel dto) {
		if (existing.isPresent()) {
			Submodel e = existing.get();
			// update only when shell's are equal
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
