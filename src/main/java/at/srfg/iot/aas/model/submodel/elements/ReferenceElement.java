package at.srfg.iot.aas.model.submodel.elements;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.srfg.iot.aas.model.ReferableElement;
import at.srfg.iot.aas.model.submodel.Submodel;

@Entity
@Table(name="reference_element")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class ReferenceElement extends DataElement<ReferableElement> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ReferenceElement() {
		// default
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link ReferenceElement} as
	 * a direct child element to the provided {@link Submodel}.
	 * @param idShort
	 * @param submodel
	 */
	public ReferenceElement(String idShort, Submodel submodel) {
		super(idShort, submodel);
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link ReferenceElement} as
	 * a direct child element to the provided {@link SubmodelElementCollection}.
	 * @param idShort
	 * @param collection
	 */
	public ReferenceElement(String idShort, SubmodelElementCollection collection) {
		super(idShort, collection);
	}
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
