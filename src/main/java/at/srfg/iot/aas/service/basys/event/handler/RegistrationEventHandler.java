package at.srfg.iot.aas.service.basys.event.handler;

import java.util.Map;
import java.util.Optional;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.referencing.KeyElementsEnum;
import at.srfg.iot.aas.repository.registry.AssetAdministrationShellRepository;
import at.srfg.iot.aas.repository.registry.IdentifiableRepository;
import at.srfg.iot.aas.service.basys.event.RegistrationEvent;
import at.srfg.iot.aas.service.basys.event.handler.util.MappingHelper;
import at.srfg.iot.aas.service.basys.event.publisher.MappingEventPublisher;
import at.srfg.iot.api.ISubmodel;

@Component
public class RegistrationEventHandler {
	@Autowired
	private AssetAdministrationShellRepository aasRepo;
	@Autowired
	private IdentifiableRepository<Submodel> subModelRepo;

	@Autowired
	private MappingEventPublisher mappingEvent;

	@EventListener
	@Async
	public void onRegistrationEvent(RegistrationEvent event) {
		Object object = event.getElementProxy().getModelPropertyValue("aas");
		
		if (object instanceof Map<?,?>) {
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String,Object>) object;
			KeyElementsEnum type = MappingHelper.getModelType(map);
			switch (type) {
			case AssetAdministrationShell:
				registerAssetAdministrationShell(event, map);
				break;
//			case Submodel:
//				SubModel remoteSubModel = SubModel.createAsFacade(map);
//				break;
			default:
				
			}
			
		}
	}
	private void registerAssetAdministrationShell(RegistrationEvent event, Map<String, Object> map) {
		AssetAdministrationShell remoteShell = AssetAdministrationShell.createAsFacade(map);
		Optional<Identifier> remoteId = MappingHelper.getIdentifier(remoteShell.getIdentification());
		if ( remoteId.isPresent() ) {
			//
			Optional<at.srfg.iot.aas.basic.AssetAdministrationShell> local = aasRepo.findByIdentification(remoteId.get());
			// localShell must be present
			if ( local.isPresent()) {
				//
				at.srfg.iot.aas.basic.AssetAdministrationShell localShell = local.get();
				//
				// perform necessary mapping
				mappingEvent.handleAssetAdministrationShell(remoteShell, localShell);
				//
				// 
				aasRepo.save(localShell);
				//
				for (ISubModel desc : remoteShell.getSubModels().values() ) {
					String idShort = desc.getIdShort();
					Object subObject = event.getElementProxy().getModelPropertyValue("aas/submodels/"+idShort);
					if ( subObject instanceof Map<?,?> ) {
						@SuppressWarnings("unchecked")
						Map<String,Object> subMap = (Map<String,Object>) subObject;
						ISubmodel localSubmodel = null;
						SubModel subModel = SubModel.createAsFacade(subMap);
						//
						
						Optional<Identifier>  subModelId = MappingHelper.getIdentifier(subModel.getIdentification());
						if ( subModelId.isPresent()) {
							localSubmodel = subModelRepo.findByIdentification(subModelId.get()).orElse(new Submodel(subModelId.get(), localShell));
						}
						else {
							localSubmodel = localShell.getSubmodel(subModel.getIdShort()).orElse(new Submodel());
						}
						// handle the submodel (stereotypes)
						mappingEvent.handleSubmodel(subModel, localSubmodel);
						// store the submodel
						subModelRepo.save((Submodel)localSubmodel);
					}
				}
				
			}
		}
		
	}
}
