package at.srfg.iot.aas.service.basys.event;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;

import at.srfg.iot.aas.basic.Submodel;

/**
 * Event for mapping {@link Submodel}
 * @author dglachs
 *
 */
public class SetSubmodelEvent extends SetIdentifiableElement<Submodel> implements SetSubmodel, SetReferable, SetHasKind, SetHasSemantics, SetQualifiable, SetIdentifiable {

	public SetSubmodelEvent(Map<String, Object> map, Submodel referable) {
		super(map, referable);
	}
	public SetSubmodelEvent(ISubModel map, Submodel referable) {
		super((SubModel)map, referable);
	}

}
