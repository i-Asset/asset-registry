package at.srfg.iot.aas.model.dictionary;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.aas.api.Referable;
import at.srfg.iot.aas.model.AssetAdministrationShell;
import at.srfg.iot.aas.model.ReferableElement;

@Entity
@Table(name = "concept_dictionary")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("ConceptDictionary")
@PrimaryKeyJoinColumn(name="model_element_id")
public class ConceptDictionary extends ReferableElement implements Referable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConceptDictionary() {
		// default
	}
	public ConceptDictionary(String idShort, AssetAdministrationShell shell) {
		this.setIdShort(idShort);
		shell.addConceptDictionary(this);
	}
	/** 
	 * Hods the list of assigned {@link ConceptDescription} 
	 * organized in this {@link ConceptDictionary}
	 */
	@ManyToMany
	@JoinTable(name = "concept_dictionary_items", 
		joinColumns = {		@JoinColumn(name = "model_element_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "description_element_id") })
	private List<ConceptDescription> conceptDictionary;
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="aas_element_id")
	private AssetAdministrationShell assetAdministrationShell;


	/**
	 * @return the conceptDictionary
	 */
	public List<ConceptDescription> getConceptDictionary() {
		return conceptDictionary;
	}
	public void addConceptDescription(ConceptDescription conceptDesc) {
		if (this.conceptDictionary == null) {
			this.conceptDictionary = new ArrayList<ConceptDescription>();
		}
		this.conceptDictionary.add(conceptDesc);
	}

	/**
	 * @param conceptDictionary the conceptDictionary to set
	 */
	public void setConceptDictionary(List<ConceptDescription> conceptDictionary) {
		this.conceptDictionary = conceptDictionary;
	}
	
	/**
	 * @return the assetAdministrationShell
	 */
	public AssetAdministrationShell getAssetAdministrationShell() {
		return assetAdministrationShell;
	}

	/**
	 * @param assetAdministrationShell the assetAdministrationShell to set
	 */
	public void setAssetAdministrationShell(AssetAdministrationShell assetAdministrationShell) {
		this.assetAdministrationShell = assetAdministrationShell;
	}
}
