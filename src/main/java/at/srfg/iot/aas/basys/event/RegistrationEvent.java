package at.srfg.iot.aas.basys.event;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;

public class RegistrationEvent {
	private final AASDescriptor descriptor;
	
	public RegistrationEvent(AASDescriptor descriptor) {
		this.descriptor = descriptor;
	}
	
	public String getEndpoint() {
		return this.descriptor.getFirstEndpoint();
	}
}
