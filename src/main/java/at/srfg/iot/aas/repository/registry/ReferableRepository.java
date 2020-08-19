package at.srfg.iot.aas.repository.registry;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

import at.srfg.iot.aas.common.referencing.ReferableElement;
@Primary
public interface ReferableRepository<T extends ReferableElement> extends CrudRepository<T, Long> {
	
	Optional<T> findByIdShort(String id);
	List<T> findByParentElement(ReferableElement element);
	List<T> findByParentElementAndIdShort(ReferableElement element, String idShort);
}