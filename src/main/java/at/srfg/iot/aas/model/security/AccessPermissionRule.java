package at.srfg.iot.aas.model.security;

import java.util.List;

import at.srfg.iot.aas.api.Qualifiable;
import at.srfg.iot.aas.model.Constraint;
import at.srfg.iot.aas.model.ReferableElement;

public class AccessPermissionRule extends ReferableElement implements Qualifiable {
	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private List<SubjectAttribute> subjectAttributes;
	private PermissionsPerObject permissionsPerObject;
	private List<Constraint> qualifier;

	@Override
	public List<Constraint> getQualifier() {
		return qualifier;
	}

	@Override
	public void setQualifier(List<Constraint> qualifier) {
		this.qualifier = qualifier;
	}

}
