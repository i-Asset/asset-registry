package at.srfg.iot.aas.basys.event;

import java.util.Map;

import at.srfg.iot.aas.basic.Submodel;

/**
 * Event for mapping {@link Submodel}
 * @author dglachs
 *
 */
public class SetSubmodelEvent extends SetIdentifiableElement<Submodel> implements SetReferable, SetHasKind, SetHasSemantics, SetQualifiable, SetIdentifiable {

	public SetSubmodelEvent(Map<String, Object> map, Submodel referable) {
		super(map, referable);
	}



}
