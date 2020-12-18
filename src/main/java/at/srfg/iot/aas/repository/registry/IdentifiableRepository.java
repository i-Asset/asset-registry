package at.srfg.iot.aas.repository.registry;
import java.util.Optional;

import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdentifiableElement;

public interface IdentifiableRepository<T extends IdentifiableElement> extends ReferableRepository<T> {

	Optional<T> findByIdentification(Identifier identifier);
}