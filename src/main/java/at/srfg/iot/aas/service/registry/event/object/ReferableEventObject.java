package at.srfg.iot.aas.service.registry.event.object;

import at.srfg.iot.aas.common.Referable;

public class ReferableEventObject<T extends Referable, DTO> {
	private final T entity;
	private final DTO dto;
	
	public ReferableEventObject(T entity, DTO dto) {
		this.entity = entity;
		this.dto = dto;
	}
	public T getEntity() {
		return this.entity;
	}
	public DTO getDTO() {
		return this.dto;
	}
}
