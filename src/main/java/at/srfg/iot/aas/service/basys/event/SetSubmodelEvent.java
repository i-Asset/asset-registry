package at.srfg.iot.aas.service.basys.event;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;

import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.api.ISubmodel;

/**
 * Event for mapping {@link Submodel}
 * @author dglachs
 *
 */
public class SetSubmodelEvent extends SetIdentifiableElement<ISubmodel> implements SetSubmodel, SetReferable, SetHasKind, SetHasSemantics, SetQualifiable, SetIdentifiable {

	public SetSubmodelEvent(Map<String, Object> map, ISubmodel referable) {
		super(map, referable);
	}
	public SetSubmodelEvent(ISubModel map, ISubmodel referable) {
		super((SubModel)map, referable);
	}

}
