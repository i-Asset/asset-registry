package at.srfg.iot.aas.basys.event;

import org.eclipse.basyx.submodel.metamodel.map.SubModel;

import at.srfg.iot.aas.model.submodel.Submodel;

/**
 * Event for mapping {@link SubModel} with the asset registry
 * @author dglachs
 *
 */
public class GetSubmodelEvent extends GetIdentifiableElementEvent<Submodel, SubModel> 
	implements GetReferable, GetHasKind, GetHasSemantics, GetQualifiable, GetIdentifiable{

	private final boolean descriptorOnly;
	
	public GetSubmodelEvent(Submodel referable) {
		this(referable, false);
	}
	public GetSubmodelEvent(Submodel referable, boolean descriptorOnly) {
		super(newSubModel(), referable);
		this.descriptorOnly = descriptorOnly;
	}
	
	public boolean isDescriptorOnly() {
		return descriptorOnly;
	}

}
