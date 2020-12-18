package at.srfg.iot.aas.repository.registry;
import java.util.List;

import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;

public interface AssetAdministrationShellRepository extends IdentifiableRepository<AssetAdministrationShell> {
	List<AssetAdministrationShell> findByDerivedFromElement(AssetAdministrationShell parent);
}