package at.srfg.iot.aas.basys.event;

import java.util.Map;

import at.srfg.iot.aas.model.submodel.elements.SubmodelElement;

public class SetSubmodelElementEvent extends SetElementEvent<SubmodelElement> 
	implements SetHasKind, SetReferable, SetHasSemantics, SetQualifiable {

	public SetSubmodelElementEvent(Map<String, Object> map, SubmodelElement referable) {
		super(map, referable);
	}
}
