package at.srfg.iot.aas.service.indexing;

import org.springframework.context.ApplicationEvent;

import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;

public class IndexingEvent<T extends Identifiable> extends ApplicationEvent {
	private final T identifiable;
	public IndexingEvent(Object source, T identifiable) {
		super(source);
		this.identifiable = identifiable;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public T getEventElement() {
		return identifiable;
	}

}
