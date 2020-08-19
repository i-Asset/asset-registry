package at.srfg.iot.aas.service.basys.event.handler;

import java.util.Optional;

import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangString;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.service.basys.event.GetReferable;
import at.srfg.iot.aas.service.basys.event.SetReferable;
import at.srfg.iot.aas.common.referencing.Description;

@Component
public class ReferableHandler {
	private static final Logger logger = LoggerFactory.getLogger(ReferableHandler.class);
	@EventListener
	public void onApplicationEvent(SetReferable event) {
		logger.info("Referable handling for {}", event.getLocal());
		Referable ref = event.getBasyxReferable();
		String idShort = ref.getIdShort();
		String category = ref.getCategory();
		LangStrings ls = ref.getDescription();
		for ( String lang : ls.getLanguages() ) {
			event.getLocal().setDescription(lang, ls.get(lang));
		}

		event.getLocal().setIdShort(idShort);
		event.getLocal().setCategory(category);
		
	}
	@EventListener
	public void onReadFromReferable(GetReferable readEvent) {
		Referable facade = Referable.createAsFacade(readEvent.getBasyxMap(), null);
		
		facade.setCategory(readEvent.getLocal().getCategory());
		facade.setIdShort(readEvent.getLocal().getIdShort());
		LangStrings ls = new LangStrings();
		Optional<Description> d = readEvent.getLocal().getFirstDescription();
		if ( d.isPresent()) {
			Description desc = d.get();
			LangString s = new LangString(desc.getLanguage(), desc.getDescription());
			ls.add(s);
		}
		facade.setDescription(ls);
		
	}
}
