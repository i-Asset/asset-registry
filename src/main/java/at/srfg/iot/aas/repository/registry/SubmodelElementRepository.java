package at.srfg.iot.aas.repository.registry;

import java.util.List;

import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.modeling.SubmodelElement;

public interface SubmodelElementRepository extends ReferableRepository<SubmodelElement> {
	List<SubmodelElement> findBySubmodel(Submodel element);

}
