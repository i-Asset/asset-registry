package at.srfg.iot.aas.model;

import at.srfg.iot.aas.api.Identifiable;
import at.srfg.iot.aas.api.Referable;

public class Key {
	private String value;
	private IdType idType;
	private boolean local;
	private KeyElementsEnum type;
	
	public static Key of(Referable referable) {
		if ( referable instanceof Identifiable) {
			Identifiable i = (Identifiable) referable;
			Key key = new Key();
			key.setLocal(true);
			key.setIdType(i.getIdType());
			key.setValue(i.getId());
			if ( i.getIdType() == IdType.URI && i.getModelType() == KeyElementsEnum.GlobalReference) {
				key.setLocal(false);
			}
			key.setType(referable.getModelType());
			return key;
		}
		else {
			Key key = new Key();
			key.setLocal(true);
			key.setIdType(IdType.IdShort);
//			key.setType(referable.get);
			key.setValue(referable.getIdShort());
			key.setType(referable.getModelType());
			return key;
		}
	}
	public static Key of(String elementType, String id, IdType idType) {
		Key key = new Key();
		Identifier identifier = new Identifier(id);
		key.setValue(identifier.getId());
		key.setIdType(identifier.getIdType());
		
		try {
			key.setType(KeyElementsEnum.valueOf(elementType));
		} catch (Exception e) {
			key.setType(KeyElementsEnum.GlobalReference);
		}
		if ( key.getIdType() == IdType.URI && key.getType() == KeyElementsEnum.GlobalReference) {
			key.setLocal(false);
		}
		else {
			key.setLocal(true);
		}
		return key;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String id) {
		this.value = id;
	}
	/**
	 * @return the idType
	 */
	public IdType getIdType() {
		return idType;
	}
	/**
	 * @param idType the idType to set
	 */
	public void setIdType(IdType keyType) {
		this.idType = keyType;
	}
	/**
	 * @return the local
	 */
	public boolean isLocal() {
		return local;
	}
	/**
	 * @param local the local to set
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}
	/**
	 * @return the type
	 */
	public KeyElementsEnum getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(KeyElementsEnum type) {
		this.type = type;
	}
	public Identifier asIdentifier() {
		return new Identifier(value);
	}
}
