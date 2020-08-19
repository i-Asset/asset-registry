package at.srfg.iot.aas.service.registry.event;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basic.Asset;
import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.dictionary.ConceptDescription;
import at.srfg.iot.aas.modeling.SubmodelElement;
import at.srfg.iot.aas.repository.registry.SubmodelRepository;
import at.srfg.iot.aas.service.registry.event.handler.AssetEventObject;
import at.srfg.iot.aas.service.registry.event.handler.ConceptDescriptionEventObject;
import at.srfg.iot.aas.service.registry.event.handler.SubmodelElementEventObject;
import at.srfg.iot.aas.service.registry.event.handler.SubmodelEventObject;

@Component
public class RegistryEvent {
	@Autowired
	private SubmodelRepository submodelRepo;
	@Autowired
	ApplicationEventPublisher publisher;
	
	public Asset createAsset(Asset dto) {
		AssetEventObject e = new AssetEventObject(this, dto);
		publisher.publishEvent(e);
		//
		return e.getEntity();
	}
	public Submodel createSubmodel(String shellId, Submodel dto) {
		// create an event - the result submodel will be created on the fly
		SubmodelEventObject e = new SubmodelEventObject(this, shellId, dto);
		publisher.publishEvent(e);
		Submodel changed = e.getEntity();
		// save the submodel
		Submodel updated = submodelRepo.save(changed);
		return updated;
	}
	public Submodel createSubmodel(Submodel dto, AssetAdministrationShell theShell) {
		// verify dto when theShell is null
		if ( theShell == null ) {
			// the dto must contain a Reference to the shell
		}
		return null;
	}
	public Submodel createSubmodel(AssetAdministrationShell theShell, Submodel dto) {
		SubmodelEventObject e = new SubmodelEventObject(this, new Submodel(dto.getIdentification(), theShell));
		publisher.publishEvent(e);
		// the event object holds the changed element
		Submodel changed = e.getEntity();
		// save the submodel
		Submodel updated = submodelRepo.save(changed);
		return updated;
	}
	public Submodel setSubmodel(Submodel dto) {
		Optional<Submodel> entity = submodelRepo.findByIdentification(dto.getIdentification());
		if ( entity.isPresent()) {
			SubmodelEventObject e = new SubmodelEventObject(this, entity.get(), dto);
			publisher.publishEvent(e);
			Submodel changed = e.getEntity();
			// save the submodel
			Submodel updated = submodelRepo.save(changed);
			return updated;
		}
		else {
			SubmodelEventObject e = new SubmodelEventObject(this, dto);
			publisher.publishEvent(e);
			Submodel changed = e.getEntity();
			// save the submodel
			Submodel updated = submodelRepo.save(changed);
			return updated;
		}
		//
	}
	public ConceptDescription createConceptDescription(ConceptDescription dto) {
		ConceptDescriptionEventObject e = new ConceptDescriptionEventObject(this, dto);
		publisher.publishEvent(e);
		return e.getEntity();
	}

	public SubmodelElement createSubmodelElement(SubmodelElement dto) {
		SubmodelElementEventObject e = new SubmodelElementEventObject(this, dto);
		publisher.publishEvent(e);
		return e.getEntity();
	}

}
