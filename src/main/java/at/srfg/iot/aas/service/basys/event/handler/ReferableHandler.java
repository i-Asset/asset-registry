package at.srfg.iot.aas.service.basys.event.handler;

import java.util.Optional;

import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangString;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.service.basys.event.GetReferable;
import at.srfg.iot.aas.service.basys.event.SetReferable;
import at.srfg.iot.aas.common.referencing.Description;

@Component
public class ReferableHandler {
	@EventListener
	public void onApplicationEvent(SetReferable event) {
		System.out.println("Referable handling");
		Referable ref = Referable.createAsFacade(event.getBasyxMap());
		String idShort = ref.getIdShort();
		String category = ref.getCategory();
		LangStrings ls = ref.getDescription();
		for ( String lang : ls.getLanguages() ) {
			event.getLocal().setDescription(lang, ls.get(lang));
		}
//		Collection<Map<String,Object>> descriptions = MappingHelper.getElementAsCollection(event.getBasyxMap(), "description");
//		for ( Map<String,Object> desc : descriptions) {
//			String lang = MappingHelper.getElementValue(desc, String.class, "language");
//			String text = MappingHelper.getElementValue(desc, String.class, "text");
//			event.getLocal().setDescription(lang, text);
//			//
//		}
		event.getLocal().setIdShort(idShort);
		event.getLocal().setCategory(category);
		
	}
	@EventListener
	public void onReadFromReferable(GetReferable readEvent) {
		Referable facade = Referable.createAsFacade(readEvent.getBasyxMap());
		
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
