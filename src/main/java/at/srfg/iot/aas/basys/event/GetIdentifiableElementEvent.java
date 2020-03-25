package at.srfg.iot.aas.basys.event;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.parts.ConceptDescription;

import at.srfg.iot.aas.model.IdentifiableElement;

public abstract class GetIdentifiableElementEvent<T extends IdentifiableElement, M extends Map<String,Object>> extends GetElementEvent<T,M> {

	public GetIdentifiableElementEvent(M map, T referable) {
		super(map, referable);
	}

	protected static AssetAdministrationShell newAAS() {
		return new AssetAdministrationShell();
	}
	protected static SubModel newSubModel() {
		return new SubModel();
	}
	protected static Asset newAsset() {
		return new Asset();
	}
	protected static ConceptDescription newConceptDescription() {
		return new ConceptDescription();
	}
}