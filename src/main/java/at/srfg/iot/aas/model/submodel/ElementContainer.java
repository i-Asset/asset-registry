package at.srfg.iot.aas.model.submodel;

import java.util.Collection;
import java.util.Optional;

import at.srfg.iot.aas.model.submodel.elements.SubmodelElement;

public interface ElementContainer {
	/**
	 * Obtain all direct submodel elements contained in this {@link ElementContainer}
	 * @return
	 */
	public Collection<SubmodelElement> getSubmodelElements();
	/**
	 * Obtain the submodel element identified by its <code>idShort</code>
	 * @param idShort
	 * @return
	 */
	public Optional<SubmodelElement> getSubmodelElement(String idShort);
}
