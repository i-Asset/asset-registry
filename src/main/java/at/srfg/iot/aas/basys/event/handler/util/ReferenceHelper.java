package at.srfg.iot.aas.basys.event.handler.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;

import at.srfg.iot.aas.common.referencing.Key;
import at.srfg.iot.aas.common.referencing.ReferableElement;
import at.srfg.iot.aas.common.referencing.Reference;

public class ReferenceHelper {
	
	public static IReference toIReference(ReferableElement referable) {
		Reference ref = new Reference(referable);
		// 
		List<IKey> iKeyList = new ArrayList<IKey>();
		for (Key k : ref.getKeys() ) {
			//
			IdentifierType idType = IdTypeHelper.asIdentifierType(k.getIdType());
			KeyElements modelType = KeyElements.fromString(k.getType().name());
			IKey iKey = new org.eclipse.basyx.submodel.metamodel.map.reference.Key(modelType, k.isLocal(), k.getValue(), idType);
			iKeyList.add(iKey);
		}
		return new org.eclipse.basyx.submodel.metamodel.map.reference.Reference(iKeyList);
	}

}
