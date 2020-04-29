package at.srfg.iot.aas.service.basys.event.handler;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.vab.factory.java.ModelProxyFactory;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnector;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.service.basys.event.RegistrationEvent;

@Component
public class RegistrationEventHandler {

	@EventListener
	@Async
	public void onRegistrationEvent(RegistrationEvent event) {
		String endpoint = event.getEndpoint();
		// we use http for accessing
		HTTPConnector httpConnector = new HTTPConnector(endpoint);
		httpConnector.getModelPropertyValue("aas");
		ModelProxyFactory proxyFactory = new ModelProxyFactory(new HTTPConnectorProvider());
		
		VABElementProxy proxy = proxyFactory.createProxy(endpoint);
		// 
		Object object = proxy.getModelPropertyValue("");
		
		if (object instanceof Map<?,?>) {
			Map<String,Object> map = (Map<String,Object>) object;
			
			AssetAdministrationShell remoteShell = AssetAdministrationShell.createAsFacade(map);
		}
	}
}
