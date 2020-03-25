package at.srfg.iot.aas.model;

import java.util.List;

import javax.persistence.Column;
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

import at.srfg.iot.aas.api.HasDataSpecification;
import at.srfg.iot.aas.api.HasKind;
import at.srfg.iot.aas.api.Identifiable;
import at.srfg.iot.aas.api.Referable;
import at.srfg.iot.aas.model.submodel.Submodel;
@Entity
@Table(name="asset")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class Asset extends IdentifiableElement implements Referable, Identifiable, HasDataSpecification, HasKind {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToMany
	@JoinTable(name = "asset_data_spec", joinColumns = {
			@JoinColumn(name = "model_element_id") }, inverseJoinColumns = { @JoinColumn(name = "spec_element_id") })
	private List<ReferableElement> dataSpecification;
	/**
	 * the {@link Kind} of the element, either {@link Kind#Type}
	 * or {@link Kind#Instance}
	 */
	@Column(name = "kind", nullable = false)
	private Kind kind = Kind.Type;
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="aas_element_id")
	private AssetAdministrationShell assetAdministrationShell;
	
	public Asset() {
		
	}
	public Asset(Submodel identifyingSubmodel) {
		
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
	/**
	 * @return the kind
	 */
	public Kind getKind() {
		return kind;
	}
	/**
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}
}
