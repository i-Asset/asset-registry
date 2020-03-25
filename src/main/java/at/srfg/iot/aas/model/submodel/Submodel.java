package at.srfg.iot.aas.model.submodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

import at.srfg.iot.aas.api.HasDataSpecification;
import at.srfg.iot.aas.api.HasKind;
import at.srfg.iot.aas.api.HasSemantics;
import at.srfg.iot.aas.api.Identifiable;
import at.srfg.iot.aas.api.Qualifiable;
import at.srfg.iot.aas.model.AssetAdministrationShell;
import at.srfg.iot.aas.model.Constraint;
import at.srfg.iot.aas.model.IdentifiableElement;
import at.srfg.iot.aas.model.Identifier;
import at.srfg.iot.aas.model.Kind;
import at.srfg.iot.aas.model.ReferableElement;
import at.srfg.iot.aas.model.submodel.elements.SubmodelElement;

@Entity
@Table(name = "submodel")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class Submodel extends IdentifiableElement implements Identifiable, HasSemantics, HasKind, HasDataSpecification, Qualifiable, ElementContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the {@link Kind} of the element, either {@link Kind#Type}
	 * or {@link Kind#Instance}
	 */
	@Column(name = "kind")
	private Kind kind = Kind.Type;
	/**
	 * the link to the {@link ReferableElement} specifying 
	 * the semantic description of the current element 
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "semantic_id")
	private ReferableElement semanticElement;
	
	@Embedded
	private Identifier semanticIdentification;

	/**
	 * List of {@link ReferableElement}s constituting 
	 * the {@link GetHasDataSpecification} of the current submodel
	 */
	@ManyToMany
	@JoinTable(name = "submodel_data_spec", joinColumns = {
			@JoinColumn(name = "model_element_id") }, inverseJoinColumns = { @JoinColumn(name = "spec_element_id") })
	private List<ReferableElement> dataSpecification;
	/**
	 * The list of {@link Constraint} elements for defining
	 * the {@link Qualifiable} 
	 */
	@ManyToMany
	@JoinTable(name = "submodel_qualifier", joinColumns = {
			@JoinColumn(name = "model_element_id") }, inverseJoinColumns = { @JoinColumn(name = "constraint_id") })
	private List<Constraint> qualifier;
	
//	@OneToMany(mappedBy = "submodel")
//	@MapKey(name="idShort")
//	private Map<String, SubmodelElement> elementMap = new HashMap<String, SubmodelElement>();
	/**
	 * Default constructor
	 */
	public Submodel() {
		// default constructor
	}
	/**
	 * Convenience constructor assigning a submodel to 
	 * the provided {@link AssetAdministrationShell}
	 * @param shell
	 */
	public Submodel(Identifier identifier, AssetAdministrationShell shell) {
		setParent(shell);
		shell.addSubmodel(this);
		setIdentification(identifier);
	}
	@JsonIgnore
	@Override
	public ReferableElement getSemanticElement() {
		return semanticElement;
	}
	@Override
	public void setSemanticElement(ReferableElement semanticId) {
		this.semanticElement = semanticId;
	}
	@Override
	public List<Constraint> getQualifier() {
		return qualifier;
	}

	@Override
	public void setQualifier(List<Constraint> qualifier) {
		this.qualifier = qualifier;
	}

	@Override
	public Kind getKind() {
		return kind;
	}

	@Override
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public AssetAdministrationShell getParentElement() {
		return (AssetAdministrationShell) super.getParentElement();
	}

	public void setParent(AssetAdministrationShell parent) {
		if ( ! parent.getSubModel().contains(this)) {
			parent.addSubmodel(this);
		}
		super.setParentElement(parent);
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
	/**
	 * @return the submodelElements
	 */
	public Collection<SubmodelElement> getSubmodelElements() {
		if ( getChildren().size()>0) {
			return getChildren().stream()
					.filter(new Predicate<ReferableElement>() {
	
						@Override
						public boolean test(ReferableElement t) {
							return t instanceof SubmodelElement;
						}
					})
					.map(new Function<ReferableElement, SubmodelElement>() {
	
						@Override
						public SubmodelElement apply(ReferableElement t) {
							return SubmodelElement.class.cast(t);
						}
						
					})
					.collect(Collectors.toList());
		}
		return new ArrayList<SubmodelElement>();
	}
	/** 
	 * Direct access to a direct submodel element
	 * @param path
	 * @return
	 */
	public Optional<SubmodelElement> getSubmodelElement(String idShort) {
		return getSubmodelElements().stream()
					.filter(new Predicate<SubmodelElement>() {

						@Override
						public boolean test(SubmodelElement t) {
							return t.getIdShort().equals(idShort);
						}})
					.findFirst();
	}
	public void addChildElement(SubmodelElement element) {
		// the submodel is the parent element
		element.setParentElement(this);
		// the submodel element belongs to this submodel
		element.setSubmodel(this);
		// keep the element in the map
		this.addChild(element);
	}

	@Override
	public Identifier getSemanticIdentifier() {
		return semanticIdentification;
	}
	@Override
	public void setSemanticIdentifier(Identifier identifier) {
		this.semanticIdentification = identifier;
		
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Submodel)) {
			return false;
		}
		return super.equals(other);
		
	}

}
