package at.srfg.iot.aas.service.basys.event;

import java.util.Map;

import at.srfg.iot.aas.common.Identifiable;

public abstract class SetIdentifiableElement<T extends Identifiable> extends SetElementEvent<T> {

	public SetIdentifiableElement(Map<String, Object> map, T referable) {
		super(map, referable);
	}


}
