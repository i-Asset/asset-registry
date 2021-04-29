package at.srfg.iot.aas.service.indexing;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.dependency.AssetIndexer;
import at.srfg.iot.aas.dependency.SubmodelIndexer;
import at.srfg.iot.common.datamodel.asset.aas.basic.Asset;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableDescription;
import at.srfg.iot.common.datamodel.asset.aas.modeling.SubmodelElement;
import at.srfg.iot.common.solr.model.model.asset.AssetType;
import at.srfg.iot.common.solr.model.model.asset.SubmodelType;

@Component
public class AssetTypeIndexingEventHandler {
	@Autowired
	private AssetIndexer indexer;
	@Autowired
	private SubmodelIndexer sIndexer;
	
	@EventListener
	@Async
	public void onAssetAdministrationShellEvent(AssetTypeIndexingEvent event) {
		AssetAdministrationShell shell = event.getEventElement();
		
		try {
			Optional<AssetType> indexed = indexer.getAssetType(shell.getId());
			
			AssetType assetType = indexed.orElse(new AssetType());
			assetType.setUri(shell.getId());
			assetType.setCode(shell.getIdShort());
			assetType.setLocalName(shell.getIdShort());
			for (ReferableDescription desc : shell.getDescription()) {
				// store each label
				assetType.setLabel(desc.getLanguage(), desc.getDescription());
			}
			if ( shell.getDerivedFromElement()!= null) {
				assetType.addProperty(shell.getDerivedFromElement().getId(), "derivedFrom");
			}
			Asset asset = shell.getAsset();
			
			// deal with submodels
//			for (Submodel model : shell.getChildElements(Submodel.class)) {
//				SubmodelType index = new SubmodelType();
//				index.setAsset(shell.getId());
//				index.setCode(model.getIdShort());
//				//
//				assetType.addSubmodel(index);
//				
////				SubmodelType submodeltype = assetType.getSubmodelMap().get(model.getId());
//			}
			indexer.setAssetType(assetType);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public void visitElements(SubmodelType index, Referable element) {
		
	}
	@EventListener
	@Async
	public void onSubmodelEvent(SubmodelIndexingEvent event) {
		Submodel sub = event.getEventElement();
		try {
			// be sure to use the global id of the submodel
			Optional<SubmodelType> indexed = sIndexer.getSubmodelType(sub.getId());
			SubmodelType submodelType = indexed.orElse(new SubmodelType());
			// TODO: create proper uri consisting of 
			//  - shell id & submodel.idShort when idType == idShort
			
			submodelType.setUri(sub.getId());
			submodelType.setAsset(sub.getAssetAdministrationShell().getId());
			submodelType.setCode(sub.getIdShort());
			submodelType.setLocalName(sub.getIdShort());
			for (ReferableDescription desc : sub.getDescription()) {
				// store each label
				submodelType.setLabel(desc.getLanguage(), desc.getDescription());
			}
			if (sub.getSemanticElement().isPresent()) {
				// must be a class type in semantic lookup!!
				// the model should have
				
			}
			collectSubmodelElements(submodelType, sub);
			
			sIndexer.setSubmodelType(submodelType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void collectSubmodelElements(SubmodelType type, SubmodelElementContainer container) {
		for ( SubmodelElement element : container.getChildElements(SubmodelElement.class)) {
			if ( SubmodelElementContainer.class.isInstance(element)) {
				collectSubmodelElements(type, SubmodelElementContainer.class.cast(element));
			}
			else {
				type.addProperty(element.toString(), element.asReference().getPath());
			}
			
		}
		
	}
	
}
