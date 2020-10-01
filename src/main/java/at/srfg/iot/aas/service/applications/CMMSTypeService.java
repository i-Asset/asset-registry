package at.srfg.iot.aas.service.applications;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.GlobalReference;
import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.referencing.Kind;
import at.srfg.iot.aas.common.types.DirectionEnum;
import at.srfg.iot.aas.modeling.submodelelement.Event;
import at.srfg.iot.aas.modeling.submodelelement.Operation;

@Service
public class CMMSTypeService extends ApplicationTypeService {
	public static final Identifier CMMS_TYPE_IDENTIFIER = new Identifier("http://iasset.salzburgresearch.at/registry/cmms");
	public static final Identifier CMMS_TYPE_INFO_MODEL= new Identifier("http://iasset.salzburgresearch.at/registry/cmms/info");
	public static final Identifier CMMS_TYPE_EVENT_MODEL= new Identifier("http://iasset.salzburgresearch.at/registry/cmms/events");
	public static final Identifier CMMS_TYPE_OPERATION_MODEL= new Identifier("http://iasset.salzburgresearch.at/registry/cmms/operations");
	public static final String CMMS_TYPE_CATEGORY = "cmmsType";
	
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
				shell.setCategory(CMMS_TYPE_CATEGORY);
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
				model.setCategory(CMMS_TYPE_CATEGORY);
				model.setDescription("de", "CMMS Application Information");
				model.setVersion("V0.01");
				model.setRevision("001");
				model.setSemanticElement(sub.orElse(null));
				return aasSubmodelRepo.save(model);
			}
		});
		Optional<Submodel> applicationTypeOperationModel = aasSubmodelRepo.findByIdentification(CMMS_TYPE_OPERATION_MODEL);
		Submodel operationModel = applicationTypeOperationModel.orElseGet(new Supplier<Submodel>() {
			@Override
			public Submodel get() {
				Optional<Submodel> sub = aasSubmodelRepo.findByIdentification(APPLICATION_TYPE_OPERATION_MODEL);
				Submodel model = new Submodel(CMMS_TYPE_OPERATION_MODEL, theShell);
				model.setIdShort("operations");
				model.setCategory(CMMS_TYPE_CATEGORY);
				model.setDescription("de", "CMMS Application Operations");
				model.setVersion("V0.01");
				model.setRevision("001");
				model.setKind(Kind.Type);
				model.setSemanticElement(sub.orElse(null));
				return aasSubmodelRepo.save(model);
			}
		});
		Optional<Operation> historyOperation = operationModel.getSubmodelElement("maintenanceHistory", Operation.class);
		Operation maintenanceHistory = historyOperation.orElseGet(new Supplier<Operation>() {

			@Override
			public Operation get() {
				Operation m = new Operation("maintenanceHistory", operationModel);
				m.setCategory(CMMS_TYPE_CATEGORY);
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
		Optional<Submodel> applicationTypeEventModel = aasSubmodelRepo.findByIdentification(CMMS_TYPE_EVENT_MODEL);
		//
		Submodel eventModel = applicationTypeEventModel.orElseGet(new Supplier<Submodel>() {
			@Override
			public Submodel get() {
				Optional<Submodel> sub = aasSubmodelRepo.findByIdentification(APPLICATION_TYPE_EVENT_MODEL);
				Submodel model = new Submodel(CMMS_TYPE_EVENT_MODEL, theShell);
				model.setIdShort("events");
				model.setCategory(CMMS_TYPE_CATEGORY);
				model.setDescription("de", "CMMS Application Event Settings");
				model.setVersion("V0.01");
				model.setRevision("001");
				model.setKind(Kind.Type);
				model.setSemanticElement(sub.orElse(null));
				return aasSubmodelRepo.save(model);
			}
		});
		Optional<Event> sensor = eventModel.getSubmodelElement("maintenance", Event.class);
		Event sensorData = sensor.orElseGet(new Supplier<Event>() {
			@Override
			public Event get() {
				Event event = new Event("maintenance", eventModel);
				event.setCategory(CMMS_TYPE_CATEGORY);
				event.setDescription("de", "CMMS Maintenance Meldung");
				event.setKind(Kind.Type);
				event.setDirection(DirectionEnum.Input);
				event.setMessageTopic("maintenenance");
				return aasSubmodelElementRepo.save(event);
			}
		});
		
		// test searching for Event classes
		Collection<Event> event = getEvents("http://iasset.salzburgresearch.at/registry/cmms/events");
		System.out.println(event.size());
	}
	/**
	 * Collect all events of a submodel
	 * @param uri
	 * @return
	 */
	public Collection<Event> getEvents(String uri) {
		Optional<Submodel> submodel = aasSubmodelRepo.findByIdentification(new Identifier(uri));
		if ( submodel.isPresent() ) {
			Submodel sub = submodel.get();
			return sub.getSubmodelElements(Event.class);
		}
		return new ArrayList<Event>();
	}
	
	

}