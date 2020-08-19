package at.srfg.iot.aas.service.registry.event.handler;

import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.basic.Submodel;

public class SubmodelEventObject extends RegistryEventObject<Submodel, Submodel> implements SubmodelEvent {
	private final Identifier aasIdentifier;
	/**
	 * default serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	public SubmodelEventObject(Object source, Submodel entity, Submodel dto) {
		this(source, null, entity, dto);
	}
	public SubmodelEventObject(Object source, String aasId, Submodel entity, Submodel dto) {
		super(source, entity, dto);
		// keep the identifier (when required)
		this.aasIdentifier = new Identifier(aasId);
	}
	public SubmodelEventObject(Object source, String aasId, Submodel dto) {
		this(source, aasId, null, dto);
	}

	public SubmodelEventObject(Object source, Submodel dto) {
		this(source, null, null, dto);
	}
	public Identifier getAssetAdministrationShellIdentifier() {
		return aasIdentifier;
	}
	@Override
	protected Submodel newEntity() {
		return new Submodel();
	}
	


}
