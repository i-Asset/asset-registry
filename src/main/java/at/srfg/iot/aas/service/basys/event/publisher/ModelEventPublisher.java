package at.srfg.iot.aas.service.basys.event.publisher;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.service.basys.event.RegistrationEvent;

@Component
public class ModelEventPublisher {
	@Autowired
	private ApplicationEventPublisher publisher;
	
	public void processRegistration(AASDescriptor descriptor) {
		// 
		publisher.publishEvent(new RegistrationEvent(descriptor));
	}
}
