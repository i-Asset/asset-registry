package at.srfg.iot.aas.service.basys.event;

import org.eclipse.basyx.submodel.metamodel.map.SubModel;

import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.HasDataSpecification;
/**
 * Event for setting {@link HasDataSpecification} 
 * 
 * @author dglachs
 *
 */
public interface SubmodelEvent extends BasyxEvent {
	public Submodel getLocal();
	
	default SubModel getBasyxSubModel() {
		return SubModel.createAsFacade(getBasyxMap());
	}
	
}
