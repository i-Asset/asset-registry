package at.srfg.iot.aas.service.basys.event;

import at.srfg.iot.api.ISubmodelElement;

public class GetSubmodelElementEvent extends GetElementEvent<ISubmodelElement, org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement> 
	implements GetHasKind, GetReferable, GetHasSemantics, GetQualifiable {

	public GetSubmodelElementEvent(ISubmodelElement referable) {
		super(newSubmodelElement(referable), referable);
	}
}
