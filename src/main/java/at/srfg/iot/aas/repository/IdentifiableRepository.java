package at.srfg.iot.aas.repository;
import java.util.Optional;

import at.srfg.iot.aas.model.IdentifiableElement;
import at.srfg.iot.aas.model.Identifier;

public interface IdentifiableRepository<T extends IdentifiableElement> extends ReferableRepository<T> {

	Optional<T> findByIdentification(Identifier identifier);
}