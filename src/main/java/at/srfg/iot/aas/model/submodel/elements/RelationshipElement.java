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
@Table(name="relationship_element")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class RelationshipElement extends SubmodelElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="subject_element_id", nullable = false)
	private ReferableElement first;
	@ManyToOne
	@JoinColumn(name="object_element_id", nullable =false)
	private ReferableElement second;

	public RelationshipElement() {
		// default
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link RelationshipElement} as
	 * a direct child element to the provided {@link Submodel}.
	 * @param idShort
	 * @param submodel
	 */
	public RelationshipElement(String idShort, Submodel submodel) {
		super(idShort, submodel);
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link RelationshipElement} as
	 * a direct child element to the provided {@link SubmodelElementCollection}.
	 * @param idShort
	 * @param collection
	 */
	public RelationshipElement(String idShort, SubmodelElementCollection collection) {
		super(idShort, collection);
	}	
	/**
	 * @return the first
	 */
	public ReferableElement getFirst() {
		return first;
	}
	/**
	 * @param first the first to set
	 */
	public void setFirst(ReferableElement first) {
		this.first = first;
	}
	/**
	 * @return the second
	 */
	public ReferableElement getSecond() {
		return second;
	}
	/**
	 * @param second the second to set
	 */
	public void setSecond(ReferableElement second) {
		this.second = second;
	}
}
