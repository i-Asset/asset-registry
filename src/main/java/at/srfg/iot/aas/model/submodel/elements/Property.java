package at.srfg.iot.aas.model.submodel.elements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.srfg.iot.aas.model.submodel.Submodel;

@Entity
@Table(name="property")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class Property extends DataElement<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Default constructor
	 */
	public Property() {
		
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link Property} as
	 * a direct child element to the provided {@link Submodel}.
	 * @param idShort
	 * @param submodel
	 */
	public Property(String idShort, Submodel submodel) {
		super(idShort, submodel);
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link Property} as
	 * a direct child element to the provided {@link SubmodelElementCollection}.
	 * @param idShort
	 * @param collection
	 */
	public Property(String idShort, SubmodelElementCollection collection) {
		super(idShort, collection);
	}
	/**
	 * String based value of the property
	 */
	@Column(name="property_value")
	private String value;
	/**
	 * Denotes the type of the property value, e.g. STRING, DOUBLE, REAL_MEASURE, QUANTITY
	 */
	@Column(name="property_value_qualifier")
	private String valueQualifier;
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the valueQualifier
	 */
	public String getValueQualifier() {
		return valueQualifier;
	}
	/**
	 * @param valueQualifier the valueQualifier to set
	 */
	public void setValueQualifier(String valueQualifier) {
		this.valueQualifier = valueQualifier;
	}
	
	//@TODO: add value Id Reference
	

}
