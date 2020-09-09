package at.srfg.iot.aas;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.referencing.IdPart;
import at.srfg.iot.aas.common.referencing.IdType;
import at.srfg.iot.aas.dependency.SemanticLookup;
import at.srfg.iot.aas.repository.registry.AssetAdministrationShellRepository;
import at.srfg.iot.aas.repository.registry.IdentifiableRepository;
import at.srfg.iot.aas.service.basys.RegistryProvider;
import at.srfg.iot.classification.model.ConceptBase;
import at.srfg.iot.classification.model.ConceptClass;
import at.srfg.iot.classification.model.ConceptProperty;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetRegistryApplicationTests {
	@Autowired
	private SemanticLookup rexRoth;
	
	@Autowired
	private RegistryProvider registry;
	
	@Autowired
	private IdentifiableRepository<Submodel> subModelRepo;
	
	@Autowired
	private AssetAdministrationShellRepository aasRepo;
	
	@Test
	public void contextLoads() {
	}
	@Test
	public void testFeign() {
		// 
		try {
			Optional<ConceptBase> cc = rexRoth.getConcept("0173-1#01-AFW236#002");
			assertTrue(cc.isPresent());
//			assertTrue(cc.get().getIdentifier().contentEquals("AFW236"));
			Collection<ConceptProperty> values = rexRoth.getPropertiesForConceptClass("0173-1#01-AFY428#003", false);//, "0173-1#02-AAP794#001");
			assertTrue(values.size()>0);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void testNamspaceDetection() {
		String full = "urn:indexing:"+ConceptClass.class.getSimpleName()+"#localName";
		IdType uri = IdType.getType(full);
		assertTrue(uri.equals(IdType.IRI));
		String nameSpace = IdPart.Namespace.getFrom(full);
		String localName = IdPart.LocalName.getFrom(full);
		assertTrue(nameSpace.equals("urn:indexing:"+ConceptClass.class.getSimpleName()+"#"));
		assertTrue(localName.equals("localName"));
	}
	
	@Test
	public void testIdTypeDetection() {

		IdType uri = IdType.getType("http://www.salzburgresearch.at/asset#registry");
		IdType eclassIrdi = IdType.getType("0173-1#01-AFW236#002");
		IdType iecIrdi = IdType.getType("0112/2///61360_4#AAA001#004");
		IdType idShort = IdType.getType("123456");
		IdType custom = IdType.getType("$%&1234");
		IdType urn = IdType.getType(new ModelUrn("srfg", "iot", "iasset", "1.0", "01", "asset", "instance01").getURN());
		IdType uuid = IdType.getType(UUID.randomUUID().toString());
		assertTrue(IdType.IRI.equals(uri));
		assertTrue(IdType.IRDI.equals(eclassIrdi));
		assertTrue(IdType.IRDI.equals(iecIrdi));
		assertTrue(IdType.IRDI.equals(urn));
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
		String legalEntity = IdPart.LegalEntity.getFrom(new ModelUrn("srfg", "iot", "iasset", "1.0", "01", "asset", "instance01").getURN());
		assertTrue("srfg".equals(legalEntity));
		
	}
	@Ignore
	@Test
	public void registerAsset() throws Exception {
		IIdentifier iid = new Identifier(IdentifierType.IRI, "http://example.com/aas/1");
		AASDescriptor descriptor = new AASDescriptor("aas_1", iid, "http://localhost");
		
		IIdentifier subId = new Identifier(IdentifierType.IRI, "http://example.com/aas/1/sub");
		SubmodelDescriptor sub1 = new SubmodelDescriptor("aas_1_sub", subId, "http://localhost/");
		descriptor.addSubmodelDescriptor(sub1);
		SubmodelDescriptor sub2 = new SubmodelDescriptor("aas_2_sub", subId, "http://localhost/");
		descriptor.addSubmodelDescriptor(sub2);
		// directory-service
		registry.deleteValue("http://example.com/aas/1");
		// 
		registry.createValue(null, descriptor);
		
		Optional<Submodel> model = subModelRepo.findByIdentification(new at.srfg.iot.aas.basic.Identifier("http://example.com/aas/1/sub"));
		assert(model.isPresent());
		
		Submodel subModel =model.get();
		Optional<at.srfg.iot.aas.basic.AssetAdministrationShell> theShell = aasRepo.findByIdentification(new at.srfg.iot.aas.basic.Identifier( "http://example.com/aas/1"));
		assertTrue(theShell.isPresent());
		
//		subModelRepo.delete(subModel);
		registry.deleteValue(theShell.get().getId());
		
		
	}
}
