package at.srfg.iot.aas.repository;
import org.springframework.data.repository.CrudRepository;

import at.srfg.iot.aas.model.AssetAdministrationShell;

public interface AssetAdministrationShellRepository extends CrudRepository<AssetAdministrationShell, Long> {

	AssetAdministrationShell findByIdShort(String id);
}