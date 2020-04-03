package at.srfg.iot.aas.basys.event.handler;

import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basic.AdministrativeInformation;
import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.basys.event.GetIdentifiable;
import at.srfg.iot.aas.basys.event.SetIdentifiable;
import at.srfg.iot.aas.basys.event.handler.util.IdTypeHelper;
import at.srfg.iot.aas.basys.event.handler.util.MappingHelper;

@Component
public class IdentifiableHandler {

	@EventListener
	public void onIdentifiableSet(SetIdentifiable event) {
		System.out.println("Identifiable handling");
		String id = MappingHelper.getElementValue(event.getBasyxMap(), String.class, "identification","id");
		Identifier myId = new Identifier(id);
		event.getLocal().setIdentification(myId);
		
		String version = MappingHelper.getElementValue(event.getBasyxMap(), String.class, "administration", "version");
		String revision = MappingHelper.getElementValue(event.getBasyxMap(), String.class, "administration", "revision");
		// 
		event.getLocal().setVersion(version);
		event.getLocal().setRevision(revision);
	}
	@EventListener
	public void onIdentifiableGet(GetIdentifiable  event) {
		Identifiable facade = Identifiable.createAsFacade(event.getBasyxMap());
		AdministrativeInformation admin = event.getLocal().getAdministration();
		if ( admin!=null) {
			facade.setAdministration(admin.getVersion(), admin.getRevision());
		}
		Identifier id = event.getLocal().getIdentification();
		if ( id != null) {
			
			facade.setIdentification(IdTypeHelper.asIdentifierType(id.getIdType()), id.getId());
		}
		
	}

}
