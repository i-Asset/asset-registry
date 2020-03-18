package at.srfg.iot.assetregistry;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import at.srfg.iot.aas.model.IdPart;
import at.srfg.iot.aas.model.IdType;
import junit.framework.TestCase;

class AssetRegistryApplicationTests extends TestCase {

	@Test
	void testIdTypeDetection() {
		
		IdType uri = IdType.getType("http://www.salzburgresearch.at/asset#registry");
		IdType eclassIrdi = IdType.getType("0173-1#01-AFW236#002");
		IdType iecIrdi = IdType.getType("0112/2///61360_4#AAA001#004");
		IdType idShort = IdType.getType("123456");
		IdType custom = IdType.getType("$%&1234");
		IdType uuid = IdType.getType(UUID.randomUUID().toString());
		assertTrue(IdType.URI.equals(uri));
		assertTrue(IdType.IRDI.equals(eclassIrdi));
		assertTrue(IdType.IRDI.equals(iecIrdi));
		assertTrue(IdType.IdShort.equals(idShort));
		assertTrue(IdType.UUID.equals(uuid));
		assertTrue(IdType.Custom.equals(custom));
		String supplier = IdPart.Supplier.getFrom("0173-1#01-AFW236#002");
		assertTrue("0173-1".equals(supplier));
		String itemCode = IdPart.ItemCode.getFrom("0173-1#01-AFW236#002");
		assertTrue("AFW236".equals(itemCode));
		String localName = IdPart.LocalName.getFrom("http://www.salzburgresearch.at/asset#registry"); 
		assertTrue("registry".equals(localName));
		String nameSpace = IdPart.Namespace.getFrom("http://www.salzburgresearch.at/asset#registry"); 
		assertTrue("http://www.salzburgresearch.at/asset#".equals(nameSpace));
		String protocol = IdPart.Protocol.getFrom("http://www.salzburgresearch.at/asset#registry");
		assertTrue("http".equals(protocol));
		
	}
}
