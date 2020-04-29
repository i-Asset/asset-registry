package at.srfg.iot.aas.service.basys.event;

import java.util.Map;

import at.srfg.iot.aas.modeling.SubmodelElement;

public class SetSubmodelElementEvent extends SetElementEvent<SubmodelElement> 
	implements SetHasKind, SetReferable, SetHasSemantics, SetQualifiable {

	public SetSubmodelElementEvent(Map<String, Object> map, SubmodelElement referable) {
		super(map, referable);
	}
}
