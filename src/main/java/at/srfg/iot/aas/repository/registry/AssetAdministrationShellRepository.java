package at.srfg.iot.aas.repository.registry;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Identifier;

public interface AssetAdministrationShellRepository extends IdentifiableRepository<AssetAdministrationShell> {

}