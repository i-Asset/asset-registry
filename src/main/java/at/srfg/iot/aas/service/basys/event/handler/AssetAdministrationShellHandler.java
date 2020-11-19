package at.srfg.iot.aas.service.basys.event.handler;

import java.util.Collection;
import java.util.Optional;

import org.eclipse.basyx.aas.metamodel.api.parts.IConceptDictionary;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basic.Asset;
import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.dictionary.ConceptDictionary;
import at.srfg.iot.aas.repository.registry.IdentifiableRepository;
import at.srfg.iot.aas.service.basys.event.GetAssetAdministrationShellEvent;
import at.srfg.iot.aas.service.basys.event.SetAssetAdministrationShell;
import at.srfg.iot.aas.service.basys.event.publisher.MappingEventPublisher;

@Component
public class AssetAdministrationShellHandler {
	@Autowired
	private IdentifiableRepository<Asset> assetRepo;
	@Autowired
	private IdentifiableRepository<Submodel> submodelRepo;

	@Autowired
	private MappingEventPublisher mappingEventPublisher;
	
	private static final Logger logger = LoggerFactory.getLogger(AssetAdministrationShellHandler.class);

	/**
	 * Transforming {@link AssetAdministrationShell} into Basyx-Maps,
	 * e.g. getting data from {@link AssetAdministrationShell}
	 * @param readEvent
	 */
	@EventListener
	public void onAssetAdministrationShellGet(GetAssetAdministrationShellEvent readEvent) {
		logger.info("Start reading the shell from persistent storage:  {}", readEvent.getLocal());
		// get the local entity
		AssetAdministrationShell local = readEvent.getLocal();
		
		// construct the aas facade
		org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell facade = org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell.createAsFacade(readEvent.getBasyxMap());
		// asset is only relevant when
		if (! readEvent.getDescriptorOnly() ) {
			// map the asset to its map representation
			org.eclipse.basyx.aas.metamodel.map.parts.Asset basyxAsset = mappingEventPublisher.getFromAsset(local.getAsset());
			facade.setAsset(basyxAsset);
		}
		// process the submodels 
		for ( Submodel model : local.getChildElements(Submodel.class)) {
//			String endpoint = local.getFirstEndpoint().getAddress()+"/submodels";
			SubModel descriptor = mappingEventPublisher.getFromSubmodel(model);
//				SubModel sub = mappingEventPublisher.getFromSubmodel(model);
//				SubmodelDescriptor descriptor = new SubmodelDescriptor(sub, endpoint);
			facade.addSubModel(descriptor);
//			if (readEvent.getDescriptorOnly()) {
//			}
//			else {
//				SubModel sub = mappingEventPublisher.getFromSubmodel(model);
//				SubmodelDescriptor descriptor = new SubmodelDescriptor(sub, endpoint);
//				facade.addSubModel(descriptor);
//			}
		}
		for (ConceptDictionary dict : local.getConceptDictionary() ) {
			// TODO: implement 
			mappingEventPublisher.getFromConceptDictionary(dict);
		}
	}
	/**
	 * Setting Basyx-Map to {@link AssetAdministrationShell}
	 * @param event
	 */
	@EventListener
	public void onAssetAdministrationShellSet(SetAssetAdministrationShell event) {
		logger.info("Start persisting the shell to storage:  {}", event.getLocal());
		// access the asset	
		if ( event.getBasyxAsset()!=null) {
			// access the local asset, create it if not present
			Asset asset = event.getLocal().getAsset();
			if ( asset == null) {
				asset = new Asset();
				asset.setParentElement(event.getLocal());
				asset.setAssetAdministrationShell(event.getLocal());
				event.getLocal().setAsset(asset);
			}
			// handle the asset, e.g. let event listeners fill in the asset
			mappingEventPublisher.handleAsset(event.getBasyxAsset(), asset);
			// save the asset
			assetRepo.save(asset);
			
		}
		// check the dictionary 
		Collection<IConceptDictionary> dicts = event.getBasyxAssetAdministrationShell().getConceptDictionary();
		for (IConceptDictionary iDict : dicts) {
			String idShort = iDict.getIdShort();
			Optional<ConceptDictionary> dict = event.getLocal().getConceptDictionary(idShort);
			// when not present, create the dictionary within the shell
			ConceptDictionary cd = dict.orElse(new ConceptDictionary(idShort, event.getLocal()));
			mappingEventPublisher.handleConceptDictionary(iDict, cd);
		}
//		IReference derivedFrom = event.getBasyxAssetAdministrationShell().getDerivedFrom();
//		// TODO: work with derived from!!!
//
//		
//		for (SubmodelDescriptor smDesc : event.getBasyxAssetAdministrationShell().getSubModelDescriptors() ) {
//			//
//			// is there a shortId?
//			String idShort = smDesc.getIdShort();
//			
//			
//		}
//		// check for contained SubmodelDescriptors
//		for (ISubModel sub : event.getBasyxAssetAdministrationShell().getSubModels()) {
//			
//		}
//		Collection<Map<String,Object>> submodels = MappingHelper.getAsCollection(event.getBasyxMap(), SUBMODELS);
//		for (Map<String,Object> subModel : submodels) {
//			// 
//			
//			Submodel sm = null;
//			Optional<Identifier> subIdentifier = MappingHelper.getIdentifier(subModel);
//			if ( subIdentifier.isPresent()) {
////			Map<String,Object> idMap = getElementAsMap(subModel,IDENTIFICATION, ID);
//				// search for the contained & connected submodel
//				Identifier identifier = subIdentifier.get();
//				Optional<Submodel> sub = event.getLocal().getSubmodel(identifier);
//				if ( sub.isPresent()) {
//					sm = sub.get();
//				}
//				else {
//					// TODO: consider checking for existing, but disconnected submodel
//					sm = new Submodel(identifier, event.getLocal());
//				}
//			}
//			else {
//				// check the idShort
//				String idShort = MappingHelper.getIdShort(subModel);
//				// 
//				Identifier subId = new Identifier(idShort);
//				Optional<Submodel> subModelOpt = event.getLocal().getSubmodel(idShort);
//				if ( subModelOpt.isPresent()) {
//					sm = subModelOpt.get();
//				}
//				else {
//					sm = new Submodel(subId, event.getLocal());
//				}
//			}
//			mappingEventPublisher.handleSubmodel(subModel, sm);
//			// handle the submodel, let event listeners fill in the submodel
//			// save the submodel
//			submodelRepo.save(sm);
//		}
		System.out.println("End of AssetAdministrationShell Handling: " + event.getLocal().getId());

	}
}
