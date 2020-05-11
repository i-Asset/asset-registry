package at.srfg.iot.aas.service.basys.event;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.vab.factory.java.ModelProxyFactory;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;

public class RegistrationEvent {
	private final AASDescriptor descriptor;
	private final VABElementProxy element;
	public RegistrationEvent(AASDescriptor descriptor) {
		this.descriptor = descriptor;
		//
		this.element = new ModelProxyFactory(new HTTPConnectorProvider()).createProxy(this.descriptor.getFirstEndpoint());
	}
	
	public String getEndpoint() {
		return this.descriptor.getFirstEndpoint();
	}
	public VABElementProxy getElementProxy() {
		return element;
	}
}
