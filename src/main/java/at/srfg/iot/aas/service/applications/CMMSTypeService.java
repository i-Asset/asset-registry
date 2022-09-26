package at.srfg.iot.aas.service.applications;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import at.srfg.iot.aas.service.indexing.AssetTypeIndexingEventObject;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.GlobalReference;
import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.types.CategoryEnum;
import at.srfg.iot.common.datamodel.asset.aas.common.types.DataTypeEnum;
import at.srfg.iot.common.datamodel.asset.aas.common.types.DirectionEnum;
import at.srfg.iot.common.datamodel.asset.aas.dictionary.ConceptDescription;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.OperationVariable;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
import at.srfg.iot.common.datamodel.semanticlookup.model.ConceptBase;
import at.srfg.iot.common.datamodel.semanticlookup.model.ConceptBase.ConceptType;

@Service
public class CMMSTypeService extends ApplicationTypeService {
	public static final Identifier CMMS_TYPE_IDENTIFIER = new Identifier("http://iasset.salzburgresearch.at/registry/cmms");
	public static final Identifier CMMS_TYPE_INFO_MODEL= new Identifier("http://iasset.salzburgresearch.at/registry/cmms/info");
	public static final Identifier CMMS_TYPE_EVENT_MODEL= new Identifier("http://iasset.salzburgresearch.at/registry/cmms/events");
	public static final Identifier CMMS_TYPE_OPERATION_MODEL= new Identifier("http://iasset.salzburgresearch.at/registry/cmms/operations");
	public static final String CMMS_TYPE_CATEGORY = "cmmsType";
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostConstruct
	protected void init() {
		super.init();
		
		Optional<AssetAdministrationShell> applicationTypeAAS = aasRepo.findByIdentification(CMMS_TYPE_IDENTIFIER);
		AssetAdministrationShell theShell = applicationTypeAAS.orElseGet(new Supplier<AssetAdministrationShell>() {
			@Override
			public AssetAdministrationShell get() {
				Optional<AssetAdministrationShell> app = aasRepo.findByIdentification(APPLICATION_TYPE_IDENTIFIER);
				AssetAdministrationShell shell = new AssetAdministrationShell(CMMS_TYPE_IDENTIFIER);
				shell.setIdShort("appType");
				shell.setCategory(CategoryEnum.APPLICATION_CLASS);
				shell.setDescription("de", "CMMS Applikation");
				shell.setVersion("V0.01");
				shell.setRevision("001");
				// keep the CMMS as derived from the generic ApplicationType
				shell.setDerivedFromElement(app.orElse(null));
				return aasRepo.save(shell);
			}});
		
		Optional<Submodel> applicationTypeInformationModel = aasSubmodelRepo.findByIdentification(CMMS_TYPE_INFO_MODEL);
		Submodel infoModel = applicationTypeInformationModel.orElseGet(new Supplier<Submodel>() {
			@Override
			public Submodel get() {
				Optional<Submodel> sub = aasSubmodelRepo.findByIdentification(APPLICATION_TYPE_INFO_MODEL);
				Submodel model = new Submodel(CMMS_TYPE_INFO_MODEL, theShell);
				model.setIdShort("information");
				model.setCategory(CategoryEnum.APPLICATION_CLASS);
				model.setDescription("de", "CMMS Application Information");
				model.setVersion("V0.01");
				model.setRevision("001");
				model.setSemanticElement(sub.orElse(null));
				return aasSubmodelRepo.save(model);
			}
		});
		Optional<Property> vendorProperty = infoModel.getSubmodelElement("vendor", Property.class);
		Property vendor = vendorProperty.orElseGet(new Supplier<Property>() {

			@Override
			public Property get() {
				// 
				Property property = new Property("vendor", infoModel);
				property.setCategory(CategoryEnum.CONSTANT);
				property.setKind(Kind.Type);
				property.setDescription("de", "Hersteller");
				property.setValueQualifier(DataTypeEnum.STRING);
				return aasSubmodelElementRepo.save(property);
			}
			
		});
		Optional<Property> nameProperty = infoModel.getSubmodelElement("name", Property.class);
		Property name = vendorProperty.orElseGet(new Supplier<Property>() {

			@Override
			public Property get() {
				// 
				Property property = new Property("name", infoModel);
				property.setCategory(CategoryEnum.CONSTANT);
				property.setKind(Kind.Type);
				property.setDescription("de", "Name");
				property.setValueQualifier(DataTypeEnum.STRING);
				return aasSubmodelElementRepo.save(property);
			}
			
		});
		Optional<Submodel> applicationTypeOperationModel = aasSubmodelRepo.findByIdentification(CMMS_TYPE_OPERATION_MODEL);
		Submodel operationModel = applicationTypeOperationModel.orElseGet(new Supplier<Submodel>() {
			@Override
			public Submodel get() {
				Optional<Submodel> sub = aasSubmodelRepo.findByIdentification(APPLICATION_TYPE_OPERATION_MODEL);
				Submodel model = new Submodel(CMMS_TYPE_OPERATION_MODEL, theShell);
				model.setIdShort("operations");
				model.setCategory(CategoryEnum.APPLICATION_CLASS);
				model.setDescription("de", "CMMS Application Operations");
				model.setVersion("V0.01");
				model.setRevision("001");
				model.setKind(Kind.Type);
				model.setSemanticElement(sub.orElse(null));
				return aasSubmodelRepo.save(model);
			}
		});
		Optional<Property> operationVariableValue = operationModel.getSubmodelElement("maintenanceHistoryInputV1Value", Property.class);
		Property maintenanceHistoryInputV1Value = operationVariableValue.orElseGet(new Supplier<Property>() {

			@Override
			public Property get() {
				// 
				Property property = new Property("maintenanceHistoryInputV1Value", operationModel);
				property.setCategory(CategoryEnum.PARAMETER);
				property.setKind(Kind.Type);
				property.setDescription("de", "Typ-Defintion Input-Parameter 1 für Maintenance History");
				property.setValueQualifier(DataTypeEnum.STRING);
				return aasSubmodelElementRepo.save(property);
			}
			
		});
		Optional<Operation> historyOperation = operationModel.getSubmodelElement("maintenanceHistory", Operation.class);
		Operation maintenanceHistory = historyOperation.orElseGet(new Supplier<Operation>() {

			@Override
			public Operation get() {
				Operation m = new Operation("maintenanceHistory", operationModel);
				m.setCategory(CategoryEnum.FUNCTION);
				m.setDescription("de", "Abfrage der Maintenance Historie");
				
//				OperationVariable inParam = new OperationVariable("identifier", operationModel);
//				inParam.setCategory(CMMS_TYPE_CATEGORY);
//				inParam.setDescription("de", "CMMS Asset Identifier");
//				OperationVariable inParamStored = aasSubmodelElementRepo.save(inParam);
//				m.setIn(Collections.singletonList(inParamStored));
				m.setKind(Kind.Type);
				
				// the output variable is specified with the CMMS instance!
				return aasSubmodelElementRepo.save(m);
			}
		});
	
		Optional<OperationVariable> operationVariable = maintenanceHistory.getOperationVariable("maintenanceHistoryInputV1");
		OperationVariable  maintenanceHistoryV1 = operationVariable.orElseGet(new Supplier<OperationVariable>() {

			@Override
			public OperationVariable get() {
				OperationVariable v = new OperationVariable("maintenanceHistoryInputV1", maintenanceHistory, DirectionEnum.Input);
				v.setKind(Kind.Type);
				v.setDescription("de", "Input-Variable für Maintenance History" );
				// use the property as the value
				v.setValue(maintenanceHistoryInputV1Value);
				// 
				
				return aasSubmodelElementRepo.save(v);
			}
		});
		Optional<Submodel> applicationTypeEventModel = aasSubmodelRepo.findByIdentification(CMMS_TYPE_EVENT_MODEL);
		//
		Submodel eventModel = applicationTypeEventModel.orElseGet(new Supplier<Submodel>() {
			@Override
			public Submodel get() {
				Optional<Submodel> sub = aasSubmodelRepo.findByIdentification(APPLICATION_TYPE_EVENT_MODEL);
				Submodel model = new Submodel(CMMS_TYPE_EVENT_MODEL, theShell);
				model.setIdShort("events");
				model.setCategory(CategoryEnum.APPLICATION_CLASS);
				model.setDescription("de", "CMMS Application Event Settings");
				model.setVersion("V0.01");
				model.setRevision("001");
				model.setKind(Kind.Type);
				model.setSemanticElement(sub.orElse(null));
				return aasSubmodelRepo.save(model);
			}
		});
		Optional<EventElement> sensor = eventModel.getSubmodelElement("maintenance", EventElement.class);
		EventElement sensorData = sensor.orElseGet(new Supplier<EventElement>() {
			@Override
			public EventElement get() {
				EventElement event = new EventElement("maintenance", eventModel);
				event.setCategory(CategoryEnum.APPLICATION_CLASS);
				event.setDescription("de", "CMMS Maintenance Meldung");
				event.setKind(Kind.Type);
				event.setDirection(DirectionEnum.Input);
				event.setMessageTopic("maintenenance");
				return aasSubmodelElementRepo.save(event);
			}
		});
		Optional<GlobalReference> optRefToCauseOfError = globalReferenceRepo.findByIdentification(new Identifier("http://iasset.salzburgresearch.at/ispro/causeOfErrorEntry"));
		GlobalReference refToCauseOfError = optRefToCauseOfError.orElseGet(new Supplier<GlobalReference>() {

			@Override
			public GlobalReference get() {
				GlobalReference ref = new GlobalReference(new Identifier("http://iasset.salzburgresearch.at/ispro/causeOfErrorEntry"));
				ref.setCategory(CategoryEnum.APPLICATION_CLASS);
				ref.setIdShort("causeOfErrorEntry");
				ref.setDescription("de", "Störursache");
				ref.setDescription("en", "Cause of Error");
				return globalReferenceRepo.save(ref);
			}
		});

		// use a concept description 
		Optional<ConceptDescription> causeOfErrorEntryOpt = cdRepo.findByIdentification(new Identifier("http://iasset.salzburgresearch.at/cmms/causeOfErrorEntry"));
		ConceptDescription causeOfErrorEntry = causeOfErrorEntryOpt.orElseGet(new Supplier<ConceptDescription>() {

			@Override
			public ConceptDescription get() {
				ConceptDescription manufacturer = new ConceptDescription(new Identifier("http://iasset.salzburgresearch.at/cmms/causeOfErrorEntry"));
				manufacturer.setCategory(CategoryEnum.COLLECTION);
				manufacturer.setDescription("en", "Cause Of Error Entry");
				manufacturer.setDescription("de", "Eintrag Störursachentabelle");
//				manufacturer.addCaseOf(refToCauseOfError);
//				manufacturer.setCategory("A21");
//				manufacturer.setVersion("001");
//				manufacturer.setRevision("01");
//				manufacturer.addCaseOf(manufacturerName);
				
				return cdRepo.save(manufacturer);
			}
		});
		causeOfErrorEntry.setCaseOf(Collections.singletonList(refToCauseOfError));
		cdRepo.save(causeOfErrorEntry);
		// test searching for Event classes
		Collection<EventElement> event = getEvents("http://iasset.salzburgresearch.at/registry/cmms/events");
		System.out.println(event.size());
		
		publisher.publishEvent(new AssetTypeIndexingEventObject(this, theShell));
	}
	/**
	 * Collect all events of a submodel
	 * @param uri
	 * @return
	 */
	public Collection<EventElement> getEvents(String uri) {
		Optional<Submodel> submodel = aasSubmodelRepo.findByIdentification(new Identifier(uri));
		if ( submodel.isPresent() ) {
			Submodel sub = submodel.get();
			return sub.getSubmodelElements(EventElement.class);
		}
		return new ArrayList<EventElement>();
	}
	
	

}
