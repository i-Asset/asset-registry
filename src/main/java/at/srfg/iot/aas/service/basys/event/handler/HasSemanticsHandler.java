package at.srfg.iot.aas.service.basys.event.handler;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basic.GlobalReference;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.HasSemantics;
import at.srfg.iot.aas.common.Referable;
import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.common.referencing.IdType;
import at.srfg.iot.aas.common.referencing.Key;
import at.srfg.iot.aas.common.referencing.KeyElementsEnum;
import at.srfg.iot.aas.dictionary.ConceptDescription;
import at.srfg.iot.aas.repository.registry.IdentifiableRepository;
import at.srfg.iot.aas.service.basys.event.GetHasSemantics;
import at.srfg.iot.aas.service.basys.event.SetHasSemantics;
import at.srfg.iot.aas.service.basys.event.handler.util.MappingHelper;
import at.srfg.iot.aas.service.basys.event.handler.util.ReferenceHelper;
import at.srfg.iot.api.ISubmodelElement;

@Component
public class HasSemanticsHandler {
	private static final Logger logger = LoggerFactory.getLogger(HasSemanticsHandler.class);
	
	private final String SEMANTIC_ID = org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics.SEMANTICID;
	private final String KEYS = org.eclipse.basyx.submodel.metamodel.map.reference.Reference.KEY;
	@Autowired
	private IdentifiableRepository<GlobalReference> globalReferenceRepo;
	
	@Autowired
	private IdentifiableRepository<ConceptDescription> conceptDescriptionRepo;
	@Autowired
	private IdentifiableRepository<Submodel> submodelRepo;
//	
//	@Autowired
//	private SemanticLookup eclass;
//	
	@EventListener
	public void onHasSemanticsGet(GetHasSemantics event) {
		logger.info("Get HasSemantics Handling for {} ", event.getLocal());
		HasSemantics local = event.getLocal();
		if ( local.getSemanticElement() != null) {
			// 
			logger.info("Resolve Semantic Element: {}", local.getSemanticElement());
			org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics facade = org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics.createAsFacade(event.getBasyxMap());
//			local.get
			IReference ref = ReferenceHelper.toIReference(local.getSemanticElement());
			
			facade.setSemanticID(ref);
		}
	}
	@EventListener
	public void onHasSemanticsSet(SetHasSemantics event) {
		logger.info("Set HasSemantics Handling for {} ", event.getLocal());
		
//		Collection<Map<String,Object>> keys = MappingHelper.getElementAsCollection(event.getBasyxMap(), SEMANTIC_ID, "keys");
		List<IKey> keys = MappingHelper.getAsCollection(event.getBasyxMap(),
			// the mapping function for type safety
			new Function<Map<String,Object>, IKey>() {
				@Override
				public IKey apply(Map<String, Object> t) {
					return org.eclipse.basyx.submodel.metamodel.map.reference.Key.createAsFacade(t);
				}
			}, 
			// the elements chain to access the map (must be the last parameter)
			SEMANTIC_ID, KEYS);
		if (!keys.isEmpty()) {
			//
			resolveReference(keys, event.getLocal());
		}
	}
	private void resolveReference(List<IKey> keys, HasSemantics local) {
		List<Key> localKeys = mapKeys(keys);
		
		if ( localKeys.size() == 1) {
			Key theKey = localKeys.get(0);
			switch(theKey.getType()) {
			case GlobalReference:
				// when not found create it
				Optional<GlobalReference> ref = globalReferenceRepo.findByIdentification(theKey.asIdentifier());
				GlobalReference globalReference = ref.orElse(new GlobalReference(theKey.asIdentifier()));
				//  
				
				globalReferenceRepo.save(globalReference);
				// relate local element with global reference
				local.setSemanticElement(globalReference);
				break;
			case ConceptDescription:
				
				
				Optional<ConceptDescription> desc = conceptDescriptionRepo.findByIdentification(theKey.asIdentifier());
				// when not found create it
				ConceptDescription conceptDescription = desc.orElse(new ConceptDescription(theKey.asIdentifier()));
				// 
//				Optional<ClassificationClass> classificationClass = eclass.getClass(theKey.getValue());
//				
				conceptDescriptionRepo.save(conceptDescription);
				// relate local element with concept descripton
				local.setSemanticElement(conceptDescription);
				break;
			default:
				break;
			}
		}
		else {
			Key theKey = localKeys.remove(0);
			switch(theKey.getType()) {
			case Submodel:
				Optional<Submodel> submodel = submodelRepo.findByIdentification(theKey.asIdentifier());
				if ( submodel.isPresent()) {
					Submodel sub = submodel.get();
					
					Referable element = resolvePath(sub, localKeys);
					if ( element != null) {
						local.setSemanticElement(element);
					}
				}
			case SubmodelElement:
			default:
			}
		}
	}
	private Referable resolvePath(SubmodelElementContainer container, List<Key>keys) {
		if ( keys == null ) {
			throw new IllegalArgumentException("Key must be provided");
		}
		if ( keys.size() > 1 ) {
			// either submodel or submodel element collection
			Key current = keys.remove(0);
			if ( current.getIdType().equals(IdType.IdShort)) {
				switch(current.getType()) {
				case SubmodelElementCollection:
					Optional<ISubmodelElement> coll = container.getSubmodelElement(current.getValue());
					if (coll.isPresent()) {
						ISubmodelElement collection = coll.get();
						if ( collection instanceof SubmodelElementContainer ) {
							return resolvePath((SubmodelElementContainer)collection, keys);
							
						}
						else {
							// wrong path
							return null;
						}
					}
				default:
					// when further keys present, they must point to a collection
					throw new IllegalArgumentException("wrong path");
				}
				
			}
		}
		if ( keys.size() == 1 ) {
			Key current = keys.get(0);
			if ( current.getIdType().equals(IdType.IdShort)) {
				switch(current.getType()) {
				case SubmodelElementCollection:
					// wrong path
					throw new IllegalArgumentException("wrong path");
				default:
					Optional<ISubmodelElement> elem = container.getSubmodelElement(current.getValue());
					return elem.orElse(null);
				}
				
			}
		}
 		return null;
	}
	/**
	 * Map the provided keys to internal {@link Key}
	 * @param fromKeys
	 * @return
	 */
	private List<Key> mapKeys(List<IKey> fromKeys) {
		return fromKeys.stream()
			.map(new Function<IKey, Key>() {

				@Override
				public Key apply(IKey t) {
					KeyElementsEnum type = KeyElementsEnum.valueOf(t.getType().getStandardizedLiteral());
					IdType.getType(t.getValue());
					Key k = Key.of(t.getValue(), type);
//					Key k = Key.of(type.name(), t.getValue(), IdType.getType(t.getValue()));
					return k;
				}
			})
			.collect(Collectors.toList());
	}
}