package at.srfg.iot.aas.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.srfg.iot.aas.model.Asset;
import at.srfg.iot.aas.model.AssetAdministrationShell;
import at.srfg.iot.aas.model.IdType;
import at.srfg.iot.aas.model.Identifier;
import at.srfg.iot.aas.model.Kind;
import at.srfg.iot.aas.model.dictionary.ConceptDescription;
import at.srfg.iot.aas.model.dictionary.ConceptDictionary;
import at.srfg.iot.aas.model.submodel.Submodel;
import at.srfg.iot.aas.model.submodel.elements.Property;
import at.srfg.iot.aas.model.submodel.elements.SubmodelElementCollection;
import at.srfg.iot.aas.repository.AssetAdministrationShellRepository;
import at.srfg.iot.aas.repository.IdentifiableRepository;
import at.srfg.iot.aas.repository.SubmodelRepository;

@Service
public class BoschRexRoth {
	@Autowired
	private AssetAdministrationShellRepository aasRepo;
	@Autowired
	private SubmodelRepository subModelRepo;
	@Autowired
	private IdentifiableRepository<ConceptDescription> conceptDescRepo;
	@Autowired
	private IdentifiableRepository<Asset> assetRepo;
	@Autowired
	private SubmodelService subModelService;
	

	
	
	
	
	public Optional<AssetAdministrationShell> createAsset() {
		
		Asset theAsset = assetRepo.findByIdentification(new Identifier(IdType.URI, "http://pk.festo.com/3s7plfdrs35"));
		if ( theAsset == null) {
			theAsset = new Asset();
			theAsset.setDescription("en", "Festo Controller");
			theAsset.setIdentification(new Identifier(IdType.URI, "http://pk.festo.com/3s7plfdrs35"));
			theAsset.setKind(Kind.Instance);
			theAsset.setIdShort("3s7plfdrs35");
			assetRepo.save(theAsset);
		}

		AssetAdministrationShell shell = aasRepo.findByIdShort("123456");
		if (shell == null) {
			shell = new AssetAdministrationShell(theAsset);
			shell.setIdentification(new Identifier(IdType.URI, "www.admin-shell.io/aas-sample/1/0"));
			shell.setIdShort("123456");
		}
		if ( shell.getConceptDictionary() == null ) {
			shell.setConceptDictionary(new ConceptDictionary());
			shell.getConceptDictionary().setIdShort("SampleDictionary");
		}
		aasRepo.save(shell);

		Identifier cdId = new Identifier(IdType.URI, "www.festo.com/dic/08111234");
		
		Identifier subId = new Identifier(IdType.URI, "http://www.zvei.de/demo/submodel/12345679");
		Identifier typeId = new Identifier(IdType.URI, "http://www.zvei.de/demo/submodelDefinitions/87654346");
		ConceptDescription cd = conceptDescRepo.findByIdentification(cdId);
		if ( cd == null) {
			cd = new ConceptDescription();
			cd.setIdentification(cdId);
			conceptDescRepo.save(cd);
		}
		/*
		 * Demonstrate the possibilities of a submodel
		 */
		Submodel type = subModelRepo.findByIdentification(typeId);
		if ( type == null) {
			type = new Submodel(shell);
			type.setIdentification(typeId);
		}
		type.setKind(Kind.Type);
		type.setIdShort("87654346");
		subModelRepo.save(type);
		Submodel sub = subModelRepo.findByIdentification(subId);
		if ( sub == null) {
			sub = new Submodel(shell);
			sub.setIdentification(subId);
			
			
		}
		sub.setParent(shell);
		sub.setKind(Kind.Instance);
		sub.setSemanticElement(type);
		subModelRepo.save(sub);
		
		Property property = subModelService.getOrCreate(sub, Property.class, "rotationSpeed");

		property.setDescription("de", "Drehgeschwindingkeit");
		property.setDescription("en", "Rotation Speed");
		property.setValue("2000");
		property.setValueQualifier("double");
		property.setSemanticElement(cd);
		
		subModelService.save(property);
		
//		Optional<SubmodelElementCollection> collOptional = subModelService.getChildElement(sub, SubmodelElementCollection.class, "operations");
		
		SubmodelElementCollection coll = subModelService.getOrCreate(sub, SubmodelElementCollection.class, "operations");
		coll.setDescription("en", "Property Group");
		subModelService.save(coll);
		
		Property collProp = subModelService.getOrCreate(coll, Property.class, "rotationSpeedMax");
		collProp.setDescription("de", "Maximale Drehgeschwindingkeit");
		collProp.setDescription("en", "Maximum Rotation Speed");
		collProp.setValue("2000");
		collProp.setValueQualifier("double");
		collProp.setSemanticElement(cd);
		
//		ConceptDescription cDesc = new ConceptDescription();
//		cDesc.setIdentification(new Identifier(IdType.IRDI, id));
		
		subModelService.save(collProp);

		subModelRepo.save(sub);
		
		aasRepo.save(shell);
		
		
		
		return Optional.of(shell);
		
	}


}
