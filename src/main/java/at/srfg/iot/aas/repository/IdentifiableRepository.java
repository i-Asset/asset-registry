package at.srfg.iot.aas.repository;
import at.srfg.iot.aas.model.IdentifiableElement;
import at.srfg.iot.aas.model.Identifier;

public interface IdentifiableRepository<T extends IdentifiableElement> extends ReferableRepository<T> {

	T findByIdentification(Identifier identifier);
}