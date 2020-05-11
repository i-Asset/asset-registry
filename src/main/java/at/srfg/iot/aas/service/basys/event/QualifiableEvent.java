package at.srfg.iot.aas.service.basys.event;

import at.srfg.iot.aas.common.Qualifiable;

public interface QualifiableEvent extends BasyxEvent {
	public Qualifiable getLocal();
	
	default org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable getBasyxQualifiable() {
		return org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable.createAsFacade(getBasyxMap());
	}
	
}
