package at.srfg.iot.aas.basys.event.handler;

import static org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell.ASSET;
import static org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell.CONCEPTDICTIONARY;
import static org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell.DERIVEDFROM;
import static org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell.SUBMODELS;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basic.Asset;
import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.basys.event.GetAssetAdministrationShellEvent;
import at.srfg.iot.aas.basys.event.SetAssetAdministrationShellEvent;
import at.srfg.iot.aas.basys.event.handler.util.MappingHelper;
import at.srfg.iot.aas.basys.event.publisher.MappingEventPublisher;
import at.srfg.iot.aas.dictionary.ConceptDictionary;
import at.srfg.iot.aas.repository.IdentifiableRepository;

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
		for ( Submodel model : local.getSubModel()) {
			String endpoint = local.getFirstEndpoint().getAddress()+"/submodels";
			if (readEvent.getDescriptorOnly()) {
				SubmodelDescriptor descriptor = mappingEventPublisher.getDescriptorFromSubmodel(model);
//				SubModel sub = mappingEventPublisher.getFromSubmodel(model);
//				SubmodelDescriptor descriptor = new SubmodelDescriptor(sub, endpoint);
				facade.addSubModel(descriptor);
			}
			else {
				SubModel sub = mappingEventPublisher.getFromSubmodel(model);
				SubmodelDescriptor descriptor = new SubmodelDescriptor(sub, endpoint);
				facade.addSubModel(descriptor);
			}
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
	public void onAssetAdministrationShellSet(SetAssetAdministrationShellEvent  event) {
		logger.info("Start persisting the shell to storage:  {}", event.getLocal());
		
		Map<String,Object> assetMap = MappingHelper.getElementAsMap(event.getBasyxMap(), ASSET);
		if ( assetMap!=null) {
			Asset asset = event.getLocal().getAsset();
			if ( asset == null) {
				asset = new Asset();
				asset.setParentElement(event.getLocal());
				asset.setAssetAdministrationShell(event.getLocal());
				event.getLocal().setAsset(asset);
			}
			// handle the asset, e.g. let event listeners fill in the asset
			mappingEventPublisher.handleAsset(assetMap, asset);
			// save the asset
			assetRepo.save(asset);
			
		}
		Collection<Map<String,Object>> dictionaries = MappingHelper.getAsCollection(event.getBasyxMap(), CONCEPTDICTIONARY);
		// 
		for (Map<String,Object> dictMap : dictionaries) {
			String idShort = MappingHelper.getIdShort(dictMap);
			
			Optional<ConceptDictionary> dict = event.getLocal().getConceptDictionary(idShort);
			// when not present, create the dictionary within the shell
			ConceptDictionary cd = dict.orElse(new ConceptDictionary(idShort, event.getLocal()));
			mappingEventPublisher.handleConceptDictionary(dictMap, cd);
			
		}
		List<IReference> derivedFrom = MappingHelper.getAsCollection(event.getBasyxMap(), 
				new Function<Map<String,Object>, IReference>() {
					@Override
					public IReference apply(Map<String, Object> t) {
						return Reference.createAsFacade(t);
					}
				
				}, 
				DERIVEDFROM);
		if (derivedFrom.size()> 0) {
			// for a reference to another aas - only on key  is re
		}
		// check for contained SubmodelDescriptors
		Collection<Map<String,Object>> submodels = MappingHelper.getAsCollection(event.getBasyxMap(), SUBMODELS);
		for (Map<String,Object> subModel : submodels) {
			// 
			Identifier identifier = MappingHelper.getIdentifier(subModel);
//			Map<String,Object> idMap = getElementAsMap(subModel,IDENTIFICATION, ID);
			// search for the contained & connected submodel
			Optional<Submodel> sub = event.getLocal().getSubmodel(identifier);
			Submodel sm = null;
			if ( sub.isPresent()) {
				sm = sub.get();
			}
			else {
				// TODO: consider checking for existing, but disconnected submodel
				sm = new Submodel(identifier, event.getLocal());
			}
			// handle the submodel, let event listeners fill in the submodel
			mappingEventPublisher.handleSubmodel(subModel, sm);
			// save the submodel
			submodelRepo.save(sm);
		}
		System.out.println("End of AssetAdministrationShell Handling: " + event.getLocal().getId());

	}
}
