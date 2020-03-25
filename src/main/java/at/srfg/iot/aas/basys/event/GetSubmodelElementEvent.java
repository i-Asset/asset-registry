package at.srfg.iot.aas.basys.event;

import at.srfg.iot.aas.model.submodel.elements.SubmodelElement;

public class GetSubmodelElementEvent extends GetElementEvent<SubmodelElement, org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement> 
	implements GetHasKind, GetReferable, GetHasSemantics, GetQualifiable {

	public GetSubmodelElementEvent(SubmodelElement referable) {
		super(newSubmodelElement(referable), referable);
	}
}
