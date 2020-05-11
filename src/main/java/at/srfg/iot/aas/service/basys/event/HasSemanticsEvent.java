package at.srfg.iot.aas.service.basys.event;

import at.srfg.iot.aas.common.HasSemantics;

public interface HasSemanticsEvent extends BasyxEvent {
	HasSemantics getLocal();
	
	default org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics getBasyxHasSemantics() {
		return org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics.createAsFacade(getBasyxMap());
	}
}
