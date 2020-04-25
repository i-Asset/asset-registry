package at.srfg.iot.aas.repository.basys;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

import at.srfg.iot.aas.common.referencing.ReferableElement;
@Primary
public interface ReferableRepository<T extends ReferableElement> extends CrudRepository<T, Long> {
	
	T findByIdShort(String id);
	List<T> findByParentElement(ReferableElement element);
	List<T> findByParentElementAndIdShort(ReferableElement element, String idShort);
}