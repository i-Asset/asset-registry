package at.srfg.iot.aas.basys.event.handler.util;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

import at.srfg.iot.aas.model.IdType;

public class IdTypeHelper {
	public static IdentifierType asIdentifierType(IdType idType) {
		switch(idType) {
		case URI:
			return IdentifierType.IRI;
		case IRDI:
			return IdentifierType.IRDI;
		default:
		case IdShort:
			return IdentifierType.CUSTOM;
		}
	}

}
