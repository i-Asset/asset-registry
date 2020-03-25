package at.srfg.iot.aas.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Endpoint {
	@JsonIgnore
	@EmbeddedId
	private EndpointPK id;
	@Column(name="address",nullable = false)
	private String address;
	@Column(name="type", nullable = false)
	private String type;
	
	public Endpoint() {
		
	}
	protected Endpoint(AssetAdministrationShell registration, Integer  index, String address, String type) {
		this.id = new EndpointPK(registration, index);
		this.address = address;
		this.type =  type;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
