package at.srfg.iot.aas.model.dictionary;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.srfg.iot.aas.api.HasDataSpecification;
import at.srfg.iot.aas.api.Identifiable;
import at.srfg.iot.aas.model.GlobalReference;
import at.srfg.iot.aas.model.IdentifiableElement;
import at.srfg.iot.aas.model.ReferableElement;

@Entity
@Table(name="concept_description")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("ConceptDescription")
@PrimaryKeyJoinColumn(name="model_element_id")
public class ConceptDescription extends IdentifiableElement implements Identifiable, HasDataSpecification {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Data Specification refers to a collection of {@link ReferableElement}s
	 */
	@ManyToMany
	@JoinTable(name = "concept_description_data_spec", 
		joinColumns = {			@JoinColumn(name = "model_element_id") }, 
		inverseJoinColumns = { 	@JoinColumn(name = "spec_element_id") })
	private List<ReferableElement> dataSpecification;
	/**
	 * A concept description may point to (external) references
	 */
	@ManyToMany
	@JoinTable(name = "concept_description_case_of", 
		joinColumns = {			@JoinColumn(name = "model_element_id") }, 
		inverseJoinColumns = { 	@JoinColumn(name = "caseof_element_id") })
	private List<GlobalReference> caseOf;
	

	/**
	 * getter for the data specification
	 * @see HasDataSpecification
	 */
	@Override
	public List<ReferableElement> getDataSpecification() {
		return dataSpecification;
	}

	@Override
	public void setDataSpecification(List<ReferableElement> dataSpecElement) {
		this.dataSpecification = dataSpecElement;
	}

}
