package at.srfg.iot.aas.service.basys.event;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.basyx.aas.metamodel.api.parts.IConceptDictionary;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.metamodel.map.parts.ConceptDictionary;

/**
 * Event for processing the an AssetAdministrationShell stereotype
 * @author dglachs
 *
 */
public interface SetAssetAdministrationShell extends AssetAdministrationShellEvent, IdentifiableEvent, ReferableEvent {
	default Asset getBasyxAsset() {
		return (Asset) getBasyxAssetAdministrationShell().getAsset();
	}
	default Collection<ConceptDictionary> getBasyxConceptDictionary() {
		Collection<ConceptDictionary> coll = new ArrayList<ConceptDictionary>();
		for (IConceptDictionary dict : getBasyxAssetAdministrationShell().getConceptDictionary()) {
			coll.add((ConceptDictionary) dict);
		}
		return coll;
	}
}
