package at.srfg.iot.aas.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="global_reference")
@DiscriminatorValue("GlobalReference")
@PrimaryKeyJoinColumn(name="model_element_id")
public class GlobalReference extends IdentifiableElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
