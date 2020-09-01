package at.srfg.iot.aas.service.basys;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.srfg.iot.aas.basic.Asset;
import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.GlobalReference;
import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.common.referencing.IdType;
import at.srfg.iot.aas.common.referencing.KeyElementsEnum;
import at.srfg.iot.aas.common.referencing.Kind;
import at.srfg.iot.aas.common.referencing.Reference;
import at.srfg.iot.aas.dependency.SemanticLookup;
import at.srfg.iot.aas.dictionary.ConceptDescription;
import at.srfg.iot.aas.modeling.SubmodelElement;
import at.srfg.iot.aas.modeling.submodelelement.Property;
import at.srfg.iot.aas.modeling.submodelelement.SubmodelElementCollection;
import at.srfg.iot.aas.repository.registry.AssetAdministrationShellRepository;
import at.srfg.iot.aas.repository.registry.IdentifiableRepository;
import at.srfg.iot.aas.repository.registry.SubmodelElementRepository;
import at.srfg.iot.aas.repository.registry.SubmodelRepository;
import at.srfg.iot.classification.model.ConceptClass;

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
//	@Autowired
//	private SubmodelService subModelService;
	@Autowired
	private SubmodelElementRepository submodelElementRepository;
	
	@Autowired
	private SemanticLookup eclass;
	

	public AssetAdministrationShell getApplicationTypeShell() {
		return getApplicationTypeShell("http://iasset.salzburgresarch.at/application");
	}
	public AssetAdministrationShell getApplicationTypeShell(String uri) {
		Optional<AssetAdministrationShell> opt = aasRepo.findByIdentification(new Identifier(uri));
		if ( ! opt.isPresent()) {
			AssetAdministrationShell shell = new AssetAdministrationShell(new Identifier(uri));
			shell.setIdShort("applicationTypeAAS");
			shell.setCategory("iAsset");
			shell.setDescription("de", "Definition i-Asset Anwendung");
			return aasRepo.save(shell);
			
		}
		else {
			return opt.get();
		}
	}
	public Submodel getApplicationTypeSubmodel(AssetAdministrationShell forShell) {
		Identifier id = new Identifier(forShell.getId()+"#typeModel");
		Optional<Submodel> opt = subModelRepo.findByIdentification(id);
		if ( ! opt.isPresent() ) {
			Submodel submodel = new Submodel(forShell);
			submodel.setIdShort("typeModel");
			submodel.setDescription("de", "Teilmodel Anwendungstyp");
			submodel.setKind(Kind.Type);
			return subModelRepo.save(submodel);
		}
		else {
			return opt.get();
		}
	}
	/**
	 * Hersteller der Application Instanz
	 * @param container
	 * @return
	 */
	public SubmodelElement getApplicationTypeVendor(SubmodelElementContainer container) {
		Optional<SubmodelElement> opt = container.getSubmodelElement("vendor");
		if ( opt.isPresent()) {
			return opt.get();
		}
		Property property = new Property("manufacturer", container);
		property.setDescription("de", "Hersteller");
		property.setValueQualifier("STRING");
		return submodelElementRepository.save(property);
		
		
	}
	public Optional<AssetAdministrationShell> createAsset(String uri) {
		
		Optional<Asset> theOptAsset = assetRepo.findByIdentification(new Identifier(uri+"#asset"));
		Asset theAsset = theOptAsset.orElseGet(new Supplier<Asset>() {

			@Override
			public Asset get() {
				Asset theAsset = new Asset(new Identifier(uri+"#asset"));
				theAsset.setDescription("en", "Demo Asset");
				theAsset.setKind(Kind.Instance);
				theAsset.setIdShort("asset");
				return assetRepo.save(theAsset);
			}
		});

		Optional<AssetAdministrationShell> opt = aasRepo.findByIdShort("123456");
		final AssetAdministrationShell shell = opt.orElseGet(new Supplier<AssetAdministrationShell>() {

			@Override
			public AssetAdministrationShell get() {
				AssetAdministrationShell shell = new AssetAdministrationShell(theAsset);
				shell.setIdentification(new Identifier(uri));
				shell.setIdShort("123456");
				shell.setDescription("de", "Demo AAS");
				return aasRepo.save(shell);
			}
		});


		Identifier cdId = new Identifier(IdType.IRI, uri + "#conceptDescription");
		
		String subId = "definitions";
		
		Identifier typeId = new Identifier(IdType.IRI, uri + "#model1");
		Optional<ConceptDescription> cdOpt = conceptDescRepo.findByIdentification(cdId);
		ConceptDescription cd = cdOpt.orElseGet(new Supplier<ConceptDescription>() {

			@Override
			public ConceptDescription get() {
				ConceptDescription c = new ConceptDescription(cdId);
				c.setDescription("de", "A first Concept description");
				c.addCaseOf(new GlobalReference(new Identifier("0173-1#02-AAM551#002")));
				return conceptDescRepo.save(c);
			}
		});
		/*
		 * Demonstrate the possibilities of a submodel
		 */
		Optional<Submodel> typeOpt = subModelRepo.findByIdentification(typeId);
		
		final Submodel type = typeOpt.orElseGet(new Supplier<Submodel>() {

			@Override
			public Submodel get() {
				Submodel type = new Submodel(typeId, shell);
				type.setKind(Kind.Type);
				type.setIdShort("type");
				return subModelRepo.save(type);
			}
		});
				
				
		// try to find the submodel with it's idShort
		Optional<Submodel> subOpt = shell.getSubmodel(subId); //subModelRepo.findByIdentification(subId);
		final Submodel sub = subOpt.orElseGet(new Supplier<Submodel>() {

			@Override
			public Submodel get() {
				Submodel s = new Submodel(subId, shell);
				s.setKind(Kind.Instance);
				s.setDescription("de", "SubModel mit idShort" );
				return subModelRepo.save(s);
			}
		});
		
		
		Optional<Property> prop = sub.getSubmodelElement("rotationSpeed", Property.class);
		Property property = prop.orElseGet(new Supplier<Property>() {

			@Override
			public Property get() {
				Property property = new Property("rotationSpeed", sub);
				property.setDescription("de", "Drehgeschwindingkeit");
				property.setDescription("en", "Rotation Speed");
				property.setValue("2000");
				property.setValueQualifier("double");
				property.setSemanticElement(cd);
				return submodelElementRepository.save(property);
			}
		});
		

		Optional<SubmodelElementCollection> c = sub.getSubmodelElement("operations", SubmodelElementCollection.class);
		
		final SubmodelElementCollection coll = c.orElseGet(new Supplier<SubmodelElementCollection>() {

			@Override
			public SubmodelElementCollection get() {
				SubmodelElementCollection c = new SubmodelElementCollection("operations", sub);
				c.setDescription("de", "Operationen");
				return submodelElementRepository.save(c);
			}
		});
		
		Optional<Property> max = coll.getSubmodelElement("rotatonSpeedMax", Property.class);
		Property collProp = max.orElseGet(new Supplier<Property>() {

			@Override
			public Property get() {
				// TODO Auto-generated method stub
				Property collProp = new Property("rotationSpeedMax", coll);
				collProp.setDescription("de", "Maximale Drehgeschwindingkeit");
				collProp.setDescription("en", "Maximum Rotation Speed");
				collProp.setValue("2000");
				collProp.setValueQualifier("double");
				collProp.setSemanticElement(cd);
				return submodelElementRepository.save(collProp);
			}
		});
		

		subModelRepo.save(sub);
		
		aasRepo.save(shell);
		
		
		
		return Optional.of(shell);
		
	}






	public Optional<ConceptClass> getClassType(String id) {
//		return Optional.empty();

		return eclass.getConceptClass(id);
	}


}
