package at.srfg.iot.aas.api;

import java.util.ArrayList;
import java.util.List;

import at.srfg.iot.aas.model.Constraint;


public interface Qualifiable {
	/**
	 * Get the list of qualifying constraints
	 * @return
	 */
	List<Constraint> getQualifier();
	/**
	 * Set the list of qualifying constraints
	 * @param qualifier
	 */
	void setQualifier(List<Constraint> qualifier);
	default void addQualifier(Constraint constraint) {
		if (getQualifier() == null) {
			setQualifier(new ArrayList<Constraint>());
		}
		if ( ! getQualifier().contains(constraint)) {
			getQualifier().add(constraint);
		}
	}

}
