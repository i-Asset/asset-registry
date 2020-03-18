package at.srfg.iot.aas.model.submodel.elements;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.srfg.iot.aas.model.ReferableElement;

@Entity
@Table(name="reference_element")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class ReferenceElement extends DataElement<ReferableElement> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * String based value of the property
	 */
	@ManyToOne
	@JoinColumn(name="reference_element_id")
	private ReferableElement value;

	/**
	 * @return the value
	 */
	public ReferableElement getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(ReferableElement value) {
		this.value = value;
	}

	

}
