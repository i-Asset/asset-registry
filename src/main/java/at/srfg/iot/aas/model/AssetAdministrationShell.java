package at.srfg.iot.aas.model;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.srfg.iot.aas.api.HasDataSpecification;
import at.srfg.iot.aas.api.Identifiable;
import at.srfg.iot.aas.model.dictionary.ConceptDictionary;
import at.srfg.iot.aas.model.submodel.Submodel;

@Entity
@Table(name="aas")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("AssetAdministrationShell")
@PrimaryKeyJoinColumn(name="model_element_id")
public class AssetAdministrationShell extends IdentifiableElement implements Identifiable, HasDataSpecification {
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
	
	@OneToOne (mappedBy = "assetAdministrationShell", cascade = CascadeType.ALL)
	private ConceptDictionary conceptDictionary;
	@OneToOne (mappedBy = "assetAdministrationShell", cascade = CascadeType.ALL, optional = false)
	private Asset asset;
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "aas_submodels", 
		joinColumns = {	@JoinColumn(name = "model_element_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "submodel_element_id") })
	private List<Submodel> subModel;
	/**
	 * Default constructor
	 */
	public AssetAdministrationShell() {
		
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
	public ConceptDictionary getConceptDictionary() {
		return conceptDictionary;
	}

	/**
	 * @param conceptDictionary the conceptDictionary to set
	 */
	public void setConceptDictionary(ConceptDictionary conceptDictionary) {
		conceptDictionary.setAssetAdministrationShell(this);
		conceptDictionary.setParentElement(this);
		this.conceptDictionary = conceptDictionary;
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

}
