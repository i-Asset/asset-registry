package at.srfg.iot.aas.api;

import java.util.Collection;
import java.util.Optional;

import at.srfg.iot.aas.model.Description;
import at.srfg.iot.aas.model.KeyElementsEnum;
import at.srfg.iot.aas.model.ReferableElement;
import at.srfg.iot.aas.model.Reference;
import at.srfg.iot.aas.model.submodel.Submodel;
import at.srfg.iot.aas.model.submodel.elements.SubmodelElementCollection;

public interface Referable {
	/**
	 * Getter for the short id. The {@link ReferableElement#getIdShort()}
	 * returns the local identifier. The local identifier is unique in the
	 * actual context of the Referable, e.g. within a {@link Submodel} 
	 * or a {@link SubmodelElementCollection}
	 * @return the short id.
	 */
	public String getIdShort();
	public void setIdShort(String idShort);
	/** 
	 * Getter for the element's category
	 * @return
	 */
	public String getCategory();
	public void setCategory(String category);
	/**
	 * Navigate to the parent element of the referable (if any)
	 * @return The parent referable
	 */
	public Referable getParentElement();
	
	public void setDescription(String language, String description);
	public Collection<Description> getDescription();
	
	public KeyElementsEnum getModelType();
	
	default Optional<Description> getFirstDescription() {
		if ( getDescription() != null && !getDescription().isEmpty()) {
			return getDescription().stream().findFirst();
		}
		return Optional.empty();
	}
	/**
	 * provide a {@link Reference} to the parent element
	 * @return
	 */
	default Reference getParent() {
		if ( getParentElement()== null) {
			return null;
		}
		return new Reference(getParentElement());
	}
}
