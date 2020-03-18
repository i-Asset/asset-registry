package at.srfg.iot.aas.repository;
import org.springframework.data.repository.CrudRepository;

import at.srfg.iot.aas.model.Identifier;
import at.srfg.iot.aas.model.submodel.Submodel;

public interface SubmodelRepository extends CrudRepository<Submodel, Long> {

	Submodel findByIdShort(String id);
	Submodel findByIdentification(Identifier identifier);
}