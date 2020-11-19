package at.srfg.iot.aas.service.basys.event;

import java.util.Map;

import at.srfg.iot.api.ISubmodelElement;

public class SetSubmodelElementEvent extends SetElementEvent<ISubmodelElement> 
	implements SetHasKind, SetReferable, SetHasSemantics, SetQualifiable {

	public SetSubmodelElementEvent(Map<String, Object> map, ISubmodelElement referable) {
		super(map, referable);
	}
}
