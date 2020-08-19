package at.srfg.iot.aas.service.registry.event.handler;

import org.springframework.context.ApplicationEvent;

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
	 * The registry element already stored & persisted, <code>null</code> when a new data is provided
	 */
	private T entity;
	/**
	 * The corresponding element provided via API - must not be <code>null</code> 
	 */
	private final DTO dto;
	public RegistryEventObject(Object source, T stored, DTO api) {
		super(source);
		this.dto = api;
		this.entity = stored;
	}
	public RegistryEventObject(Object source, DTO api) {
		super(source);
		this.dto = api;
		this.entity = null;
	}
	public T getEntity() {
		if ( entity == null) {
			entity = newEntity();
		}
		return entity;
	}
	public void setEntity(T toStore) {
		this.entity = toStore;
	}
	public DTO getDTO() {
		return dto;
	}
	
	protected abstract T newEntity();
	
}
