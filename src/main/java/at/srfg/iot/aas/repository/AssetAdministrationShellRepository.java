package at.srfg.iot.aas.repository;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Identifier;

public interface AssetAdministrationShellRepository extends CrudRepository<AssetAdministrationShell, Long> {

	Optional<AssetAdministrationShell> findByIdShort(String id);
	Optional<AssetAdministrationShell> findByIdentification(Identifier identifier);
}