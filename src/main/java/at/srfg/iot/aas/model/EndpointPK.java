package at.srfg.iot.aas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The primary key class for the endpoints database table.
 * 
 */
@Embeddable
public class EndpointPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="model_element_id", referencedColumnName = "model_element_id")
	private AssetAdministrationShell modelElement;
	
	@Column(name="index", length = 2)
	private Integer index;

	public EndpointPK() {
	}
	public EndpointPK(AssetAdministrationShell element_id, Integer index) {
		this.modelElement = element_id;
		this.index = index;
	}
	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((modelElement == null) ? 0 : modelElement.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EndpointPK other = (EndpointPK) obj;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (modelElement == null) {
			if (other.modelElement != null)
				return false;
		} else if (!modelElement.equals(other.modelElement))
			return false;
		return true;
	}




}