package at.srfg.iot.aas.model.submodel.elements;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.srfg.iot.aas.model.Kind;
import at.srfg.iot.aas.model.submodel.Submodel;

@Entity
@Table(name="operation_variable")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class OperationVariable extends SubmodelElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="operation_in")
	private Operation operationIn;
	@ManyToOne
	@JoinColumn(name="operation_out")
	private Operation operationOut;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="operation_value")
	private SubmodelElement value;

	public OperationVariable() {
		// default
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link OperationVariable} as
	 * a direct child element to the provided {@link Submodel}.
	 * @param idShort
	 * @param submodel
	 */
	public OperationVariable(String idShort, Submodel submodel) {
		super(idShort, submodel);
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link OperationVariable} as
	 * a direct child element to the provided {@link SubmodelElementCollection}.
	 * @param idShort
	 * @param collection
	 */
	public OperationVariable(String idShort, SubmodelElementCollection collection) {
		super(idShort, collection);
	}
	

	/**
	 * @return the operationIn
	 */
	public Operation getOperationIn() {
		return operationIn;
	}

	/**
	 * @param operationIn the operationIn to set
	 */
	public void setOperationIn(Operation operationIn) {
		this.operationIn = operationIn;
	}

	/**
	 * @return the operationOut
	 */
	public Operation getOperationOut() {
		return operationOut;
	}

	/**
	 * @param operationOut the operationOut to set
	 */
	public void setOperationOut(Operation operationOut) {
		this.operationOut = operationOut;
	}

	/**
	 * @return the value
	 */
	public SubmodelElement getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(SubmodelElement value) {
		if ( value.getKind()!=Kind.Type ) {
			throw new IllegalArgumentException("Provided element shall be of kind TYPE!");
		}
		this.value = value;
	}

}
