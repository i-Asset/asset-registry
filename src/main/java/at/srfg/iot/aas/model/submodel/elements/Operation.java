package at.srfg.iot.aas.model.submodel.elements;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.srfg.iot.aas.model.submodel.Submodel;

@Entity
@Table(name="operation")
@Inheritance(strategy = InheritanceType.JOINED) 
@PrimaryKeyJoinColumn(name="model_element_id")
public class Operation extends SubmodelElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "operationIn")
	private List<OperationVariable> in;
	@OneToMany(mappedBy = "operationOut")
	private List<OperationVariable> out;
	
	public Operation() {
		// default
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link Operation} as
	 * a direct child element to the provided {@link Submodel}.
	 * @param idShort
	 * @param submodel
	 */
	public Operation(String idShort, Submodel submodel) {
		super(idShort, submodel);
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link Operation} as
	 * a direct child element to the provided {@link SubmodelElementCollection}.
	 * @param idShort
	 * @param collection
	 */
	public Operation(String idShort, SubmodelElementCollection collection) {
		super(idShort, collection);
	}

	/**
	 * @return the in
	 */
	public List<OperationVariable> getIn() {
		return in;
	}
	/**
	 * @param in the in to set
	 */
	public void setIn(List<OperationVariable> in) {
		this.in = in;
	}
	/**
	 * @return the out
	 */
	public List<OperationVariable> getOut() {
		return out;
	}
	/**
	 * @param out the out to set
	 */
	public void setOut(List<OperationVariable> out) {
		this.out = out;
	}
	
	
	

}
