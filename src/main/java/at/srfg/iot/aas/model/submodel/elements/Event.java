package at.srfg.iot.aas.model.submodel.elements;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public abstract class Event extends SubmodelElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
