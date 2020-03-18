package at.srfg.iot.aas.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import at.srfg.iot.aas.api.Identifiable;
/**
 * Embeddable element storing the administrative 
 * information for {@link Identifiable} elements.
 *
 */
@Embeddable
public class Identifier {
	@Column(name="identifier")
	private String id;

	@Column(name="identifier_type")
	private IdType idType;

	public Identifier() {
		
	}
	public Identifier(String id) {
		// determine the id type
		this(IdType.getType(id), id);			
	
	}
	public Identifier(IdType idType, String id) {
		this.idType = idType;
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	public void setIdType(IdType idType) {
		this.idType = idType;
	}
}
