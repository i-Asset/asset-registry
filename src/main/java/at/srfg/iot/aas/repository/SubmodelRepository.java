package at.srfg.iot.aas.repository;
import org.springframework.data.repository.CrudRepository;

import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.basic.Submodel;

public interface SubmodelRepository extends CrudRepository<Submodel, Long> {

	Submodel findByIdShort(String id);
	Submodel findByIdentification(Identifier identifier);
}