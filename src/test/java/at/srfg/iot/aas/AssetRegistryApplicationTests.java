package at.srfg.iot.aas;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;import com.sun.jersey.api.client.async.ITypeListener;

import at.srfg.iot.aas.common.referencing.IdPart;
import at.srfg.iot.aas.common.referencing.IdType;
import at.srfg.iot.aas.dependency.SemanticLookup;
import at.srfg.iot.eclass.model.ClassificationClass;
import at.srfg.iot.eclass.model.PropertyDefinition;
import feign.FeignException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetRegistryApplicationTests {
	@Autowired
	private SemanticLookup rexRoth;
	
	@Test
	public void contextLoads() {
	}
	@Test
	public void testFeign() {
		// 
		try {
			Optional<ClassificationClass> cc = rexRoth.getClass("0173-1#01-AFW236#002");
			assertTrue(cc.isPresent());
			assertTrue(cc.get().getIdentifier().contentEquals("AFW236"));
			List<PropertyDefinition> values = rexRoth.getPropertiesForClass("0173-1#01-AFY428#003");//, "0173-1#02-AAP794#001");
			assertTrue(values.size()>0);
			
			
		} catch (FeignException e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void testNamspaceDetection() {
		String full = "urn:indexing:"+ClassificationClass.class.getSimpleName()+"#localName";
		IdType uri = IdType.getType(full);
		assertTrue(uri.equals(IdType.URI));
		String nameSpace = IdPart.Namespace.getFrom(full);
		String localName = IdPart.LocalName.getFrom(full);
		assertTrue(nameSpace.equals("urn:indexing:"+ClassificationClass.class.getSimpleName()+"#"));
		assertTrue(localName.equals("localName"));
	}
	
	@Test
	public void testIdTypeDetection() {
		
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
