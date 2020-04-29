package at.srfg.iot.aas.repository.basys;

import java.util.List;

import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.modeling.SubmodelElement;

public interface SubmodelElementRepository extends ReferableRepository<SubmodelElement> {
	List<SubmodelElement> findBySubmodel(Submodel element);

}
