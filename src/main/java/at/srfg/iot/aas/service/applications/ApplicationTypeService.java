package at.srfg.iot.aas.service.applications;

import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.srfg.iot.aas.repository.registry.AssetAdministrationShellRepository;
import at.srfg.iot.aas.repository.registry.IdentifiableRepository;
import at.srfg.iot.aas.repository.registry.SubmodelElementRepository;
import at.srfg.iot.aas.repository.registry.SubmodelRepository;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.GlobalReference;
import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.types.DirectionEnum;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;

@Service
public class ApplicationTypeService {
	/**
	 * the identifier for the most generic application type
	 */
	public static final Identifier APPLICATION_TYPE_IDENTIFIER = new Identifier("http://iasset.salzburgresearch.at/registry/application");
	public static final Identifier APPLICATION_TYPE_INFO_MODEL= new Identifier("http://iasset.salzburgresearch.at/registry/application#info");
	public static final Identifier APPLICATION_TYPE_EVENT_MODEL= new Identifier("http://iasset.salzburgresearch.at/registry/application#events");
	public static final Identifier APPLICATION_TYPE_OPERATION_MODEL= new Identifier("http://iasset.salzburgresearch.at/registry/application#operations");
	
	public static final String APPLICATION_TYPE_CATEGORY = "applicationType"; 
	
	@Autowired
	protected AssetAdministrationShellRepository aasRepo;
	@Autowired
	protected SubmodelRepository aasSubmodelRepo;
	@Autowired
	protected SubmodelElementRepository aasSubmodelElementRepo;
	
	@Autowired
	protected IdentifiableRepository<GlobalReference> globalReferenceRepo;

	@PostConstruct
	protected void init() {
		Optional<AssetAdministrationShell> applicationTypeAAS = aasRepo.findByIdentification(APPLICATION_TYPE_IDENTIFIER);
		AssetAdministrationShell theShell = applicationTypeAAS.orElseGet(new Supplier<AssetAdministrationShell>() {
			@Override
			public AssetAdministrationShell get() {
				AssetAdministrationShell shell = new AssetAdministrationShell(APPLICATION_TYPE_IDENTIFIER);
				shell.setIdShort("appType");
				shell.setCategory(APPLICATION_TYPE_CATEGORY);
				shell.setDescription("de", "i-Asset Applikation");
				shell.setVersion("V0.01");
				shell.setRevision("001");
				return aasRepo.save(shell);
				// TODO Auto-generated method stub
			}});
		//
		Optional<Submodel> applicationTypeInformationModel = aasSubmodelRepo.findByIdentification(APPLICATION_TYPE_INFO_MODEL);
		Submodel infoModel = applicationTypeInformationModel.orElseGet(new Supplier<Submodel>() {
			@Override
			public Submodel get() {
				Submodel model = new Submodel(APPLICATION_TYPE_INFO_MODEL, theShell);
				model.setIdShort("information");
				model.setCategory(APPLICATION_TYPE_CATEGORY);
				model.setDescription("de", "i-Asset Application Information");
				model.setVersion("V0.01");
				model.setRevision("001");
				return aasSubmodelRepo.save(model);
			}
		});
		Optional<Submodel> applicationTypeOperationModel = aasSubmodelRepo.findByIdentification(APPLICATION_TYPE_OPERATION_MODEL);
		Submodel operationModel = applicationTypeOperationModel.orElseGet(new Supplier<Submodel>() {
			@Override
			public Submodel get() {
				Submodel model = new Submodel(APPLICATION_TYPE_OPERATION_MODEL, theShell);
				model.setIdShort("operations");
				model.setCategory(APPLICATION_TYPE_CATEGORY);
				model.setDescription("de", "i-Asset Application Event Settings");
				model.setVersion("V0.01");
				model.setRevision("001");
				model.setKind(Kind.Type);
				return aasSubmodelRepo.save(model);
			}
		});
		
		Optional<Submodel> applicationTypeEventModel = aasSubmodelRepo.findByIdentification(APPLICATION_TYPE_EVENT_MODEL);
		//
		Submodel eventModel = applicationTypeEventModel.orElseGet(new Supplier<Submodel>() {
			@Override
			public Submodel get() {
				Submodel model = new Submodel(APPLICATION_TYPE_EVENT_MODEL, theShell);
				model.setIdShort("events");
				model.setCategory(APPLICATION_TYPE_CATEGORY);
				model.setDescription("de", "i-Asset Application Event Settings");
				model.setVersion("V0.01");
				model.setRevision("001");
				model.setKind(Kind.Type);
				return aasSubmodelRepo.save(model);
			}
		});
		Optional<GlobalReference> sensorEventType = globalReferenceRepo.findByIdentification(new Identifier("urn:iasset:sensorEventType"));
		GlobalReference sensorEvent = sensorEventType.orElseGet(new Supplier<GlobalReference>() {
			// provide the sensorEvent type ... 
			@Override
			public GlobalReference get() {
				GlobalReference ref = new GlobalReference(new Identifier("urn:iasset:sensorEventType"));
				ref.setCategory("eventType");
				ref.setIdShort("sensorEventType");
				ref.setDescription("de", "Event für Sensor-Daten");
				return globalReferenceRepo.save(ref);
			}
		});
		
		Optional<EventElement> sensor = eventModel.getSubmodelElement("sensorData", EventElement.class);
		EventElement sensorData = sensor.orElseGet(new Supplier<EventElement>() {
			@Override
			public EventElement get() {
				EventElement event = new EventElement("sensorData", eventModel);
				event.setCategory(APPLICATION_TYPE_CATEGORY);
				event.setDescription("de", "i-Asset Sensor Event-Settings");
				event.setKind(Kind.Type);
				event.setDirection(DirectionEnum.Output);
				event.setMessageTopic("sensorData");
				event.setSemanticElement(sensorEvent);
				return aasSubmodelElementRepo.save(event);
			}
		});
		
		
		
	}
}
