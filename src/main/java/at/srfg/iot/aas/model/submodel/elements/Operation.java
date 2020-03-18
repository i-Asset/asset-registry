package at.srfg.iot.aas.model.submodel.elements;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

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
