package at.srfg.iot.aas.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.aas.api.HasDataSpecification;
import at.srfg.iot.aas.api.Identifiable;
import at.srfg.iot.aas.api.Referable;
import at.srfg.iot.aas.model.dictionary.ConceptDictionary;
import at.srfg.iot.aas.model.submodel.Submodel;

@Entity
@Table(name="aas")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("AssetAdministrationShell")
@PrimaryKeyJoinColumn(name="model_element_id")
public class AssetAdministrationShell extends IdentifiableElement implements Referable, Identifiable, HasDataSpecification {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="derivedFrom")
	private AssetAdministrationShell derivedFrom;
	
	@ManyToMany
	@JoinTable(name = "aas_data_spec", joinColumns = {
			@JoinColumn(name = "model_element_id") }, inverseJoinColumns = { @JoinColumn(name = "spec_element_id") })
	private List<ReferableElement> dataSpecification;
	
	@OneToMany (mappedBy = "assetAdministrationShell", cascade = CascadeType.ALL)
	private List<ConceptDictionary> conceptDictionary;
	@OneToOne (mappedBy = "assetAdministrationShell", cascade = CascadeType.ALL, optional = false)
	private Asset asset;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "aas_submodels", 
		joinColumns = {	@JoinColumn(name = "model_element_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "submodel_element_id") })
	private List<Submodel> subModel;
	
	/**
	 * Map of {@link Endpoint} elements, at least one entry
	 */
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "id.modelElement", fetch=FetchType.LAZY)
	@MapKey(name="id.index")
	private Map<Integer, Endpoint> endpointMap = new HashMap<Integer, Endpoint>(1);

	/**
	 * Default constructor
	 */
	public AssetAdministrationShell() {
		
	}
	public AssetAdministrationShell(String identifier) {
		this(new Identifier(identifier));
	}
	public AssetAdministrationShell(Identifier identifier) {
		this.setIdentification(identifier);
	}
	/**
	 * 
	 * @param asset
	 */
	public AssetAdministrationShell(Asset asset) {
		setAsset(asset);
		asset.setAssetAdministrationShell(this);
		// TODO: handle assetSubmodel
	}
	/**
	 * @return the derivedFrom
	 */
	public AssetAdministrationShell getDerivedFrom() {
		return derivedFrom;
	}

	/**
	 * @param derivedFrom the derivedFrom to set
	 */
	public void setDerivedFrom(AssetAdministrationShell derivedFrom) {
		this.derivedFrom = derivedFrom;
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
	 * @return the conceptDictionary
	 */
	public List<ConceptDictionary> getConceptDictionary() {
		return conceptDictionary;
	}
	/**
	 * Convenience method to obtain a dictionary based on its short id
	 * @param idShort
	 * @return
	 */
	public Optional<ConceptDictionary> getConceptDictionary(String idShort) {
		if ( getConceptDictionary()!= null && ! getConceptDictionary().isEmpty()) {
			return getConceptDictionary().stream()
					.filter(new Predicate<ConceptDictionary>() {

						@Override
						public boolean test(ConceptDictionary t) {
							return idShort.equals(t.getIdShort());
						}
					})
					.findFirst();
		}
		return Optional.empty();
	}
	public void setConceptDictionary(List<ConceptDictionary> conceptDictionary) {
		this.conceptDictionary = conceptDictionary;
	}

	/**
	 * @param conceptDictionary the conceptDictionary to set
	 */
	public void addConceptDictionary(ConceptDictionary toAdd) {
		if ( this.conceptDictionary == null ) {
			this.conceptDictionary = new ArrayList<ConceptDictionary>();
		}
		toAdd.setAssetAdministrationShell(this);
		toAdd.setParentElement(this);
		//
		this.conceptDictionary.add(toAdd);
	}
	/**
	 * @return the asset
	 */
	public Asset getAsset() {
		return asset;
	}
	/**
	 * @param asset the asset to set
	 */
	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	/**
	 * @return the subModel
	 */
	public List<Submodel> getSubModel() {
		if (subModel == null) {
			subModel = new ArrayList<Submodel>();
		}
		return subModel;
	}
	public Optional<Submodel> getSubmodel(Identifier identifier) {
		List<Submodel> subModel = getSubModel();
		if (! subModel.isEmpty()) {
			return subModel.stream()
				.filter(new Predicate<Submodel>() {

					@Override
					public boolean test(Submodel t) {
						return t.getIdentification().equals(identifier);
					}
				})
				.findFirst();
		}
		return Optional.empty();
	}
	/**
	 * @param subModel the subModel to set
	 */
	public void setSubModel(List<Submodel> subModel) {
		this.subModel = subModel;
	}
	/**
	 * Helper method to add a submodel to the asset administration shell
	 * @param submodel
	 */
	public void addSubmodel(Submodel submodel) {
		if (this.subModel == null) {
			this.subModel = new ArrayList<Submodel>();
		}
		if (! this.subModel.contains(submodel)) {
			this.subModel.add(submodel);
		}
	}
	/**
	 * Helper method storing a http endpoint for the element
	 * @param index the index value
	 * @param description
	 */
	public void setEndpoint(Integer index, String address, String type) {
		Endpoint desc = endpointMap.get(index);
		if ( desc != null ) {
			desc.setAddress(address);
			desc.setType(type);
		}
		else {
			this.endpointMap.put(index, new Endpoint(this, index, address, type));
		}
	}
	/**
	 * Helper method providing access to the element's {@link Endpoint} by index (0)
	 * @param index the (ordered) index number  
	 * @return the endpont
	 */
	public Endpoint getEndpoint(Integer index) {
		return this.endpointMap.get(index);
	}
	@JsonIgnore
	public Endpoint getFirstEndpoint() {
		return this.endpointMap.get(0);
	}
	/**
	 * Getter for the description
	 * @return
	 */
	public Collection<Endpoint> getEndpoint() {
		return this.endpointMap.values();
	}

}
