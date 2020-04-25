package at.srfg.iot.aas.service.basys;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.srfg.iot.aas.basic.Asset;
import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.referencing.IdType;
import at.srfg.iot.aas.common.referencing.Kind;
import at.srfg.iot.aas.dependency.SemanticLookup;
import at.srfg.iot.aas.dictionary.ConceptDescription;
import at.srfg.iot.aas.modeling.submodelelement.Property;
import at.srfg.iot.aas.modeling.submodelelement.SubmodelElementCollection;
import at.srfg.iot.aas.repository.basys.AssetAdministrationShellRepository;
import at.srfg.iot.aas.repository.basys.IdentifiableRepository;
import at.srfg.iot.aas.repository.basys.SubmodelRepository;
import at.srfg.iot.eclass.model.ClassificationClass;

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
	
	@Autowired
	private SemanticLookup eclass;
	
	
	
	public Optional<AssetAdministrationShell> createAsset() {
		
		Optional<Asset> theOptAsset = assetRepo.findByIdentification(new Identifier(IdType.URI, "http://pk.festo.com/3s7plfdrs35"));
		Asset theAsset = theOptAsset.orElse(new Asset()); 
		if ( theAsset.getElementId() == null) {
			theAsset.setDescription("en", "Festo Controller");
			theAsset.setIdentification(new Identifier(IdType.URI, "http://pk.festo.com/3s7plfdrs35"));
			theAsset.setKind(Kind.Instance);
			theAsset.setIdShort("3s7plfdrs35");
			assetRepo.save(theAsset);
		}

		Optional<AssetAdministrationShell> opt = aasRepo.findByIdShort("123456");
		AssetAdministrationShell shell = null;
		if (!opt.isPresent()) {
			shell = new AssetAdministrationShell(theAsset);
			shell.setIdentification(new Identifier(IdType.URI, "www.admin-shell.io/aas-sample/1/0"));
			shell.setIdShort("123456");
		}
		else {
			shell = opt.get();
		}

		aasRepo.save(shell);

		Identifier cdId = new Identifier(IdType.URI, "www.festo.com/dic/08111234");
		
		Identifier subId = new Identifier(IdType.URI, "http://www.zvei.de/demo/submodel/12345679");
		Identifier typeId = new Identifier(IdType.URI, "http://www.zvei.de/demo/submodelDefinitions/87654346");
		Optional<ConceptDescription> cdOpt = conceptDescRepo.findByIdentification(cdId);
		ConceptDescription cd = cdOpt.orElse(new ConceptDescription());
		if ( cd.getElementId() == null) {
			cd.setIdentification(cdId);
			conceptDescRepo.save(cd);
		}
		/*
		 * Demonstrate the possibilities of a submodel
		 */
		Submodel type = subModelRepo.findByIdentification(typeId);
		if ( type == null) {
			type = new Submodel(typeId,shell);
			type.setIdentification(typeId);
		}
		type.setKind(Kind.Type);
		type.setIdShort("87654346");
		subModelRepo.save(type);
		Submodel sub = subModelRepo.findByIdentification(subId);
		if ( sub == null) {
			sub = new Submodel(subId, shell);
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






	public Optional<ClassificationClass> getClassType(String id) {
//		return Optional.empty();

		return eclass.getClass(id);
	}


}
