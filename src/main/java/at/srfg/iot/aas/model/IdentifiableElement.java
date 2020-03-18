package at.srfg.iot.aas.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.aas.api.Identifiable;
import at.srfg.iot.aas.model.dictionary.ConceptDescription;
import at.srfg.iot.aas.model.submodel.Submodel;
/**
 * Abstract base class representing {@link Identifiable} elements.
 * <p>
 * Any {@link Identifiable} elements provides an {@link Identifier} as
 * well as {@link AdministrativeInformation}. 
 * </p>
 * Subclasses are {@link AssetAdministrationShell}, {@link Submodel},
 * {@link Asset} and {@link ConceptDescription}.
 * <p>
 * </p>
 * @see ReferableElement
 * @author dglachs
 *
 */
@Entity
@Table(name="identifiable_element")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public abstract class IdentifiableElement extends ReferableElement implements Identifiable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Embedded
	private AdministrativeInformation administration;
	@Embedded
	private Identifier identification;

	public void setIdType(IdType idType) {
		if ( identification == null) {
			identification = new Identifier();
		}
		this.identification.setIdType(idType);
	}
	/**
	 * @return the administration
	 */
	public AdministrativeInformation getAdministration() {
		return administration;
	}
	/**
	 * @param administration the administration to set
	 */
	public void setAdministration(AdministrativeInformation administration) {
		this.administration = administration;
	}
	/**
	 * @return the identification
	 */
	public Identifier getIdentification() {
		return identification;
	}
	/**
	 * @param identification the identification to set
	 */
	public void setIdentification(Identifier identification) {
		this.identification = identification;
	}

}
