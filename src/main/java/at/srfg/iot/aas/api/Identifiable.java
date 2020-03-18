package at.srfg.iot.aas.api;

import at.srfg.iot.aas.model.AdministrativeInformation;
import at.srfg.iot.aas.model.IdType;
import at.srfg.iot.aas.model.Identifier;

public interface Identifiable extends Referable {
	public AdministrativeInformation getAdministration();
	public void setAdministration(AdministrativeInformation administration);
	public void setIdentification(Identifier identification);
	public Identifier getIdentification();
	default IdType getIdType() {
		if (getIdentification() != null) { 
			return getIdentification().getIdType();
		}
		return IdType.IdShort;
	}
	
	default String getId() {
		if (getIdentification() != null) {
			return getIdentification().getId();
		}
		return "";
	}
	default void setId(String id) {
		if ( getIdentification() == null) {
			setIdentification(new Identifier(id));
		}
		else {
			// detect the type
			getIdentification().setIdType(IdType.getType(id));
			// set the id
			getIdentification().setId(id);
		}
	}
}
