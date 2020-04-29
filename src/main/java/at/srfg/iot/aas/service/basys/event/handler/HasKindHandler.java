package at.srfg.iot.aas.service.basys.event.handler;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.service.basys.event.GetHasKind;
import at.srfg.iot.aas.service.basys.event.SetHasKind;
import at.srfg.iot.aas.service.basys.event.handler.util.MappingHelper;
import at.srfg.iot.aas.common.HasKind;
import at.srfg.iot.aas.common.referencing.Kind;
/**
 * Mapping Event handler processing the {@link HasKind} stereotype.
 * @author dglachs
 *
 */
@Component
public class HasKindHandler {
	private static final Logger logger = LoggerFactory.getLogger(HasKindHandler.class);

	@EventListener
	public void onHasKindSet(SetHasKind event) {
		logger.info("SetHasKind handling for {}", event.getLocal());
		
		String strKind = MappingHelper.getElementValue(event.getBasyxMap(), String.class, "kind");
		try {
			// try to get the kind of element
			Kind kind = Kind.valueOf(strKind);
			event.getLocal().setKind(kind);
		} catch (Exception e) {
			// use Kind.Type by default
			event.getLocal().setKind(Kind.Type);
		}
	}
	@EventListener
	public void onHasKindGet(GetHasKind event) {
		logger.info("GetHasKind handling for {}", event.getLocal());
		org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind facade = org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind.createAsFacade(event.getBasyxMap());
		//
		facade.setModelingKind(event.getLocal().getKind() == Kind.Type ?ModelingKind.TEMPLATE : ModelingKind.INSTANCE);
	}
}
