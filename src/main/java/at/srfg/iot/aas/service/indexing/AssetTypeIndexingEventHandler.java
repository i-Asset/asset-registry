package at.srfg.iot.aas.service.indexing;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.dependency.AssetIndexer;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableDescription;
import at.srfg.iot.common.solr.model.model.asset.AssetType;
import at.srfg.iot.common.solr.model.model.asset.SubmodelType;

@Component
public class AssetTypeIndexingEventHandler {
	@Autowired
	private AssetIndexer indexer;
	
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
			// deal with submodels
			for (Submodel model : shell.getChildElements(Submodel.class)) {
				SubmodelType index = new SubmodelType();
				
//				SubmodelType submodeltype = assetType.getSubmodelMap().get(model.getId());
			}
			indexer.setAssetType(assetType);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public void visitElements(SubmodelType index, Referable element) {
		
	}
}
