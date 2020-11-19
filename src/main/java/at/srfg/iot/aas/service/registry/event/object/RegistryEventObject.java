package at.srfg.iot.aas.service.registry.event.object;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import at.srfg.iot.aas.common.referencing.ReferableElement;
/**
 * Basic Event object used for creating/updating elements in the i-Asset Registry 
 * @author dglachs
 *
 * @param <T> The type of the entity element
 * @param <DTO> The type of the corresponding DTO element
 */
public abstract class RegistryEventObject<T extends ReferableElement, DTO> extends ApplicationEvent {
	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The registry element already stored & persisted, must not be <code>null</code>
	 */
	private final T entity;
	/**
	 * The corresponding element provided via API - must not be <code>null</code> 
	 */
	private final DTO dto;
	
	protected final List<ApplicationEvent> events = new ArrayList<ApplicationEvent>();

	/**
	 * Constructor for the {@link RegistryEventObject}, both the persisted entity and the data transfer object
	 * must not be null!
	 * @param source
	 * @param stored
	 * @param api
	 */
	public RegistryEventObject(Object source, T stored, DTO api) {
		super(source);
		this.dto = api;
		this.entity = stored;
	}
	public RegistryEventObject(Object source, DTO api) {
		this(source, null, api);
	}
	/**
	 * Obtain the entity element
	 * @return
	 */
	public T getEntity() {
		return entity;
	}
	public DTO getDTO() {
		return dto;
	}

	//public abstract void processDependencies(ApplicationEventPublisher publisher);
	
}
