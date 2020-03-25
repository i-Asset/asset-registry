package at.srfg.iot.aas.basys.event;

import java.util.Map;

import at.srfg.iot.aas.model.IdentifiableElement;

public abstract class SetIdentifiableElement<T extends IdentifiableElement> extends SetElementEvent<T> {

	public SetIdentifiableElement(Map<String, Object> map, T referable) {
		super(map, referable);
	}


}
