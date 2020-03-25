package at.srfg.iot.aas.repository;

import java.util.List;

import at.srfg.iot.aas.model.submodel.Submodel;
import at.srfg.iot.aas.model.submodel.elements.SubmodelElement;

public interface SubmodelElementRepository extends ReferableRepository<SubmodelElement> {
	List<SubmodelElement> findBySubmodel(Submodel element);

}
