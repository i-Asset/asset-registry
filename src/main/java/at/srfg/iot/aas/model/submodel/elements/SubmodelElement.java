package at.srfg.iot.aas.model.submodel.elements;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import at.srfg.iot.aas.api.HasDataSpecification;
import at.srfg.iot.aas.api.HasKind;
import at.srfg.iot.aas.api.HasSemantics;
import at.srfg.iot.aas.api.Qualifiable;
import at.srfg.iot.aas.api.Referable;
import at.srfg.iot.aas.model.Constraint;
import at.srfg.iot.aas.model.Identifier;
import at.srfg.iot.aas.model.Kind;
import at.srfg.iot.aas.model.ReferableElement;
import at.srfg.iot.aas.model.submodel.Submodel;
@JsonInclude(value = Include.NON_NULL)
@Entity
@Table(name="submodel_element")
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "model_element_id")
public abstract class SubmodelElement extends ReferableElement implements Referable, HasSemantics, HasKind, HasDataSpecification, Qualifiable {
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;

	@Column(name="kind")
	private Kind kind;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "semantic_id")
	private ReferableElement semanticElement;
	
	@Embedded
	private Identifier semanticIdentification;
	
	@ManyToMany
	@JoinTable(
		name="submodel_element_qualifier", 
		joinColumns={@JoinColumn(name="model_element_id")}, 
		inverseJoinColumns={@JoinColumn(name="constraint_id")})
	private List<Constraint> qualifier;
	
	@ManyToMany
	@JoinTable(
		name = "submodel_element_data_spec", 
		joinColumns = {	@JoinColumn(name = "model_element_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "spec_element_id") })
	private List<ReferableElement> dataSpecification;

	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "submodel_element_id", referencedColumnName = "model_element_id")
	private Submodel submodel;
	
	public SubmodelElement() {
		// default constructor
	}
	/**
	 * Constructor for creating a {@link SubmodelElement} as
	 * child element of a {@link Submodel}.
	 * @param submodel
	 */
	public SubmodelElement(String idShort, Submodel submodel) {
		//
		setIdShort(idShort);
		setSubmodel(submodel);
		submodel.addChildElement(this);
		
	}
	public SubmodelElement(String idShort, SubmodelElementCollection submodelCollection) {
		setIdShort(idShort);
		if ( submodelCollection.getSubmodel() == null) {
			throw new IllegalStateException("Collection must be part of a submodel to add child elements");
		}
		// each submodel element must be assigned to the submodel
		setSubmodel(submodelCollection.getSubmodel());
		// add the element to the collection's child list
		setParentElement(submodelCollection);
		submodelCollection.addChildElement(this);
	}


	@Override
	public Kind getKind() {
		return kind;
	}

	@Override
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	@JsonIgnore
	public ReferableElement getSemanticElement() {
		return semanticElement;
	}

	public void setSemanticElement(ReferableElement semanticId) {
		this.semanticElement = semanticId;
	}

	/**
	 * @return the qualifier
	 */
	public List<Constraint> getQualifier() {
		return qualifier;
	}

	/**
	 * @param qualifier the qualifier to set
	 */
	public void setQualifier(List<Constraint> qualifier) {
		this.qualifier = qualifier;
	}

	/**
	 * @return the dataSpecification
	 */
	public List<ReferableElement> getDataSpecification() {
		return dataSpecification;
	}

	/**
	 * @param dataSpecification the dataSpecification to set
	 */
	public void setDataSpecification(List<ReferableElement> dataSpecification) {
		this.dataSpecification = dataSpecification;
	}

	@JsonIgnore
	/**
	 * @return the submodel
	 */
	public Submodel getSubmodel() {
		return submodel;
	}

	/**
	 * @param submodel the submodel to set
	 */
	public void setSubmodel(Submodel submodel) {
		this.submodel = submodel;
	}
	/**
	 * 
	 * @param submodel
	 */
	public void setParent(Submodel submodel) {
		super.setParentElement(submodel);
		setSubmodel(submodel);
		submodel.addChildElement(this);
	}
	public void setParent(SubmodelElementCollection collection) {
		super.setParentElement(collection);
		setSubmodel(collection.getSubmodel());
		
	}
	@Override
	public Identifier getSemanticIdentifier() {
		return semanticIdentification;
	}
	public void setSemanticIdentifier(Identifier identifier) {
		this.semanticIdentification = identifier;
	}
	
}
