package at.srfg.iot.aas.service.types;

import at.srfg.iot.aas.repository.registry.*;
import at.srfg.iot.common.datamodel.asset.aas.basic.*;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.types.CategoryEnum;
import at.srfg.iot.common.datamodel.asset.aas.common.types.DataTypeEnum;
import at.srfg.iot.common.datamodel.asset.aas.common.types.DirectionEnum;
import at.srfg.iot.common.datamodel.asset.aas.dictionary.ConceptDescription;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class PandaAssetType {
    public static final Identifier ASSET_TYPE_AAS = new Identifier("http://iasset.salzburgresearch.at/labor/panda#aas");
    public static final Identifier ASSET_TYPE_IDENTIFIER = new Identifier("http://iasset.salzburgresearch.at/labor/panda");
    public static final Identifier ASSET_TYPE_INFO_MODEL = new Identifier("http://iasset.salzburgresearch.at/labor/panda#info");
    public static final Identifier ASSET_TYPE_PROPERTY_MODEL = new Identifier("http://iasset.salzburgresearch.at/labor/panda#properties");
    public static final Identifier ASSET_TYPE_OPERATION_MODEL = new Identifier("http://iasset.salzburgresearch.at/labor/panda#operations");
    public static final Identifier ASSET_TYPE_EVENT_MODEL = new Identifier("http://iasset.salzburgresearch.at/labor/panda#events");

    public static final Identifier APPLICATION_TYPE_EVENT_MODEL = new Identifier("http://iasset.salzburgresearch.at/registry/application#events");

    public static final String ASSET_TYPE_CATEGORY = "iAssetLabor";
    @Autowired
    protected AssetAdministrationShellRepository aasRepo;
    @Autowired
    protected SubmodelRepository aasSubmodelRepo;
    @Autowired
    protected SubmodelElementRepository aasSubmodelElementRepo;
    @Autowired
    protected ConceptDescriptionRepository cdRepo;
    @Autowired
    protected IdentifiableRepository<GlobalReference> globalReferenceRepo;

    @PostConstruct
    @Transactional
    protected void init() {
        Optional<AssetAdministrationShell> applicationTypeAAS = aasRepo.findByIdentification(ASSET_TYPE_AAS);
        AssetAdministrationShell theShell = applicationTypeAAS.orElseGet(new Supplier<AssetAdministrationShell>() {
            @Override
            public AssetAdministrationShell get() {
                Asset asset = new Asset(ASSET_TYPE_IDENTIFIER);
                asset.setDescription("de", "i-Asset Labor - Panda Roboterarm (Franka Emika)");
                asset.setDescription("en", "i-Asset Lab - Panda Robot Arm (Franka Emika)");
                asset.setKind(Kind.Type);
                AssetAdministrationShell shell = new AssetAdministrationShell(asset);
                shell.setIdentification(ASSET_TYPE_AAS);
                shell.setAsset(asset);
                shell.setIdShort("pandaType");
                shell.setCategory(CategoryEnum.APPLICATION_CLASS);
                // reuse asset description
                shell.setDescription(asset.getDescription());
                shell.setVersion("V0.01");
                shell.setRevision("001");
                // create the asset along with the
                return aasRepo.save(shell);
            }
        });
        Optional<Submodel> assetTypeInfoModel = aasSubmodelRepo.findByIdentification(ASSET_TYPE_INFO_MODEL);
        Submodel infoModel = assetTypeInfoModel.orElseGet(new Supplier<Submodel>() {
            @Override
            public Submodel get() {
                Submodel model = new Submodel(ASSET_TYPE_INFO_MODEL, theShell);
                model.setIdShort("information");
                model.setKind(Kind.Instance);
                model.setCategory(CategoryEnum.APPLICATION_CLASS);
                model.setDescription("de", "Franka Panda - Infomodel");
                model.setDescription("en", "Franka Panda - Infomodel");
                model.setVersion("V0.01");
                model.setRevision("001");
                return aasSubmodelRepo.save(model);
            }
        });
        Optional<GlobalReference> optManufacturerReference = globalReferenceRepo.findByIdentification(new Identifier("0173-1#02-AAO677#002"));
        GlobalReference manufacturerName = optManufacturerReference.orElseGet(new Supplier<GlobalReference>() {
            // provide the sensorEvent type ...
            @Override
            public GlobalReference get() {
                GlobalReference ref = new GlobalReference(new Identifier("0173-1#02-AAO677#002"));
                ref.setCategory(CategoryEnum.PROPERTY);
                ref.setIdShort("0173-1#02-AAO677#002");
                // eClass Merkmal "02-AAO677" Herstellername
                ref.setDescription("de", "Herstellername");
                return globalReferenceRepo.save(ref);
            }
        });

        // use a concept description
        Optional<ConceptDescription> manufacturerDescription = cdRepo.findByIdentification(new Identifier("http://iasset.salzburgresearch.at/dictionary/manufacturer"));
        ConceptDescription manufacturer = manufacturerDescription.orElseGet(new Supplier<ConceptDescription>() {

            @Override
            public ConceptDescription get() {
                ConceptDescription manufacturer = new ConceptDescription(new Identifier("http://iasset.salzburgresearch.at/dictionary/manufacturer"));
                manufacturer.setCategory(CategoryEnum.PROPERTY);
                manufacturer.setDescription("en", "Manufacturer name");
                manufacturer.setDescription("de", "Hersteller Name");
//				manufacturer.setCategory("A21");
//				manufacturer.setVersion("001");
//				manufacturer.setRevision("01");
//				manufacturer.addCaseOf(manufacturerName);

                return cdRepo.save(manufacturer);
            }
        });
        // add the instance property for the manufacturer
        Optional<Property> optManufacturer = infoModel.getSubmodelElement("manufacturer", Property.class);
        Property manufacturerProperty = optManufacturer.orElseGet(new Supplier<Property>() {

            @Override
            public Property get() {
                //
                Property property = new Property("manufacturer", infoModel);
                property.setCategory(CategoryEnum.CONSTANT);
                property.setKind(Kind.Instance);
                property.setDescription("de", "Hersteller");
                // the semantic element uses the global reference as semantic id
                property.setSemanticElement(manufacturerName);
                property.setValueQualifier(DataTypeEnum.STRING);
                property.setValue("Franka Panda Emika");
                return aasSubmodelElementRepo.save(property);
            }

        });
        Optional<Submodel> assetTypePropertyModel = aasSubmodelRepo.findByIdentification(ASSET_TYPE_PROPERTY_MODEL);
        Submodel propertyModel = assetTypePropertyModel.orElseGet(new Supplier<Submodel>() {
            @Override
            public Submodel get() {
                Submodel model = new Submodel(ASSET_TYPE_PROPERTY_MODEL, theShell);
                model.setIdShort("properties");
                model.setKind(Kind.Type);
                model.setCategory(CategoryEnum.APPLICATION_CLASS);
                model.setDescription("de", "i-Asset Labor - Panda-Properties");
                model.setVersion("V0.01");
                model.setRevision("001");
                return aasSubmodelRepo.save(model);
            }
        });
        Optional<SubmodelElementCollection> optPandaPosition = propertyModel.getSubmodelElement("position", SubmodelElementCollection.class);
        SubmodelElementCollection pandaPosition = optPandaPosition.orElseGet(new Supplier<SubmodelElementCollection>() {

            @Override
            public SubmodelElementCollection get() {
                SubmodelElementCollection dataContainer = new SubmodelElementCollection("position", propertyModel);
                dataContainer.setCategory(CategoryEnum.COLLECTION);
                dataContainer.setDescription("de", "Aktuelle Position des End-Effektors");
                dataContainer.setDescription("en", "Current Position of the end effector");
                dataContainer.setKind(Kind.Type);

                return aasSubmodelElementRepo.save(dataContainer);
            }
        });
        Optional<Property> optPosX = pandaPosition.getSubmodelElement("posX", Property.class);
        Property posX = optPosX.orElseGet(new Supplier<Property>() {

            @Override
            public Property get() {
                //
                Property property = new Property("posX", pandaPosition);
                property.setCategory(CategoryEnum.VARIABLE);
                property.setKind(Kind.Type);
                property.setDescription("de", "End-Effektor Position: X Koordinate");
                property.setDescription("en", "End effector position: X coordinate");
                // TODO: define semantic id for X-POS
                // property.setSemanticElement(manufacturer);
                property.setValueQualifier(DataTypeEnum.DECIMAL);
                return aasSubmodelElementRepo.save(property);
            }

        });

        Optional<Property> optPosY = pandaPosition.getSubmodelElement("posY", Property.class);
        Property posY = optPosY.orElseGet(new Supplier<Property>() {

            @Override
            public Property get() {
                //
                Property property = new Property("posY", pandaPosition);
                property.setCategory(CategoryEnum.VARIABLE);
                property.setKind(Kind.Type);
                property.setDescription("de", "End-Effektor Position: Y Koordinate");
                property.setDescription("en", "End effector position: Y coordinate");
                // TODO: define semantic id for Y-POS
                // property.setSemanticElement(manufacturer);
                property.setValueQualifier(DataTypeEnum.DECIMAL);
                return aasSubmodelElementRepo.save(property);
            }

        });
        Optional<Property> optPosZ = pandaPosition.getSubmodelElement("posZ", Property.class);
        Property posZ = optPosZ.orElseGet(new Supplier<Property>() {

            @Override
            public Property get() {
                //
                Property property = new Property("posZ", pandaPosition);
                property.setCategory(CategoryEnum.VARIABLE);
                property.setKind(Kind.Type);
                property.setDescription("de", "End-Effektor Position: Z Koordinate");
                property.setDescription("en", "End effector position: Z coordinate");
                // TODO: define semantic id for Z-POS
                // property.setSemanticElement(manufacturer);
                property.setValueQualifier(DataTypeEnum.DECIMAL);
                return aasSubmodelElementRepo.save(property);
            }

        });
        Optional<SubmodelElementCollection> optPandaGripper = propertyModel.getSubmodelElement("gripper", SubmodelElementCollection.class);
        SubmodelElementCollection pandaGripper = optPandaGripper.orElseGet(new Supplier<SubmodelElementCollection>() {

            @Override
            public SubmodelElementCollection get() {
                SubmodelElementCollection dataContainer = new SubmodelElementCollection("gripper", propertyModel);
                dataContainer.setCategory(CategoryEnum.COLLECTION);
                dataContainer.setDescription("de", "Gripper-Information");
                dataContainer.setDescription("en", "gripper information");
                dataContainer.setKind(Kind.Type);

                return aasSubmodelElementRepo.save(dataContainer);
            }
        });
        Optional<Property> optGripperDistance = pandaPosition.getSubmodelElement("distance", Property.class);
        Property gripperDistance = optGripperDistance.orElseGet(new Supplier<Property>() {

            @Override
            public Property get() {
                //
                Property property = new Property("distance", pandaGripper);
                property.setCategory(CategoryEnum.VARIABLE);
                property.setKind(Kind.Type);
                property.setDescription("de", "Distanz zwischen den beiden Gripper-Fingern");
                property.setDescription("de", "Distance between gripper fingers");
                // TODO: define semantic id for Distance
                // property.setSemanticElement(manufacturer);
                property.setValueQualifier(DataTypeEnum.DECIMAL);
                return aasSubmodelElementRepo.save(property);
            }

        });
        Optional<Property> optGripperZForce = pandaPosition.getSubmodelElement("zForce", Property.class);
        Property gripperZForcee = optGripperDistance.orElseGet(new Supplier<Property>() {

            @Override
            public Property get() {
                //
                Property property = new Property("zForce", pandaGripper);
                property.setCategory(CategoryEnum.VARIABLE);
                property.setKind(Kind.Type);
                property.setDescription("de", "Gripper-Belastung (Gewicht)");
                property.setDescription("en", "Gripper-Down-Force (Weight)");
                // TODO: define semantic id for Distance
                // property.setSemanticElement(manufacturer);
                property.setValueQualifier(DataTypeEnum.DECIMAL);
                return aasSubmodelElementRepo.save(property);
            }

        });

        Property gripperType = optGripperDistance.orElseGet(new Supplier<Property>() {

            @Override
            public Property get() {
                //
                Property property = new Property("type", pandaGripper);
                property.setCategory(CategoryEnum.VARIABLE);
                property.setKind(Kind.Type);
                property.setDescription("de", "Gripper-Typ (Parallel, Centric, Angular, etc.)");
                property.setDescription("en", "type of gripper (parallel, centric, angular, etc.");
                // TODO: define semantic id for Gripper-Typ
                // property.setSemanticElement(manufacturer);
                property.setValueQualifier(DataTypeEnum.STRING);
                return aasSubmodelElementRepo.save(property);
            }

        });

        Optional<Submodel> assetTypeOperationModel = aasSubmodelRepo.findByIdentification(ASSET_TYPE_OPERATION_MODEL);
        Submodel operationModel = assetTypeOperationModel.orElseGet(new Supplier<Submodel>() {
            @Override
            public Submodel get() {
                Submodel model = new Submodel(ASSET_TYPE_OPERATION_MODEL, theShell);
                model.setIdShort("operations");
                model.setKind(Kind.Type);
                model.setCategory(CategoryEnum.APPLICATION_CLASS);
                model.setDescription("de", "i-Asset Labor - Panda-Funktionen");
                model.setDescription("en", "i-Asset Lab - Panda functions");
                model.setVersion("V0.01");
                model.setRevision("001");
                return aasSubmodelRepo.save(model);
            }
        });

//		Optional<Property> optBeltSpeed = beltDataContainer.getSubmodelElement("speed", Property.class);
//		Property beltSpeed = optBeltSpeed.orElseGet(new Supplier<Property>() {
//
//			@Override
//			public Property get() {
//				// 
//				Property property = new Property("speed", beltDataContainer);
//				property.setCategory(CategoryEnum.VARIABLE);
//				property.setKind(Kind.Type);
//				property.setDescription("de", "Geschwindigkeit für Förderband");
//				property.setValueQualifier(DataTypeEnum.DECIMAL);
//				return aasSubmodelElementRepo.save(property);
//			}
//			
//		});
        Optional<Operation> optMoveToPosition = operationModel.getSubmodelElement("moveToPosition", Operation.class);
        Operation moveToPosition = optMoveToPosition.orElseGet(new Supplier<Operation>() {
            @Override
            public Operation get() {
                Operation m = new Operation("moveToPosition", operationModel);
                m.setCategory(CategoryEnum.FUNCTION);
                m.setDescription("de", "Bewege den End-Effektor zu definierter Position mit automatischer Pfadplanung");
                m.setDescription("en", "move end effektor to defined position with automatic path planning");
                m.setKind(Kind.Type);

                // the output variable is specified with the CMMS instance!
                return aasSubmodelElementRepo.save(m);
            }
        });
        Optional<OperationVariable> optPosXInput = moveToPosition.getOperationVariable("posX");
        OperationVariable posXInput = optPosXInput.orElseGet(new Supplier<OperationVariable>() {
            @Override
            public OperationVariable get() {
                OperationVariable v = new OperationVariable("posX", moveToPosition, DirectionEnum.Input);
                v.setCategory(CategoryEnum.PARAMETER);
                v.setKind(Kind.Type);
                v.setDescription("de", "X-Koordinate der Zielposition");
                v.setDescription("en", "X coordinate of target position");

                // use the property as the value
                v.setValue(posX);
                //

                return aasSubmodelElementRepo.save(v);
            }
        });
        Optional<OperationVariable> optPosYInput = moveToPosition.getOperationVariable("posY");
        OperationVariable posYInput = optPosYInput.orElseGet(new Supplier<OperationVariable>() {
            @Override
            public OperationVariable get() {
                OperationVariable v = new OperationVariable("posY", moveToPosition, DirectionEnum.Input);
                v.setCategory(CategoryEnum.PARAMETER);
                v.setKind(Kind.Type);
                v.setDescription("de", "Y-Koordinate der Zielposition");
                v.setDescription("en", "Y coordinate of target position");

                // use the property as the value
                v.setValue(posY);
                //

                return aasSubmodelElementRepo.save(v);
            }
        });
        Optional<OperationVariable> optPosZInput = moveToPosition.getOperationVariable("posZ");
        OperationVariable posZInput = optPosZInput.orElseGet(new Supplier<OperationVariable>() {
            @Override
            public OperationVariable get() {
                OperationVariable v = new OperationVariable("posZ", moveToPosition, DirectionEnum.Input);
                v.setCategory(CategoryEnum.PARAMETER);
                v.setKind(Kind.Type);
                v.setDescription("de", "Z-Koordinate der Zielposition");
                v.setDescription("en", "Z coordinate of target position");

                // use the property as the value
                v.setValue(posZ);
                //

                return aasSubmodelElementRepo.save(v);
            }
        });

        Optional<Submodel> assetTypeEventModel = aasSubmodelRepo.findByIdentification(ASSET_TYPE_EVENT_MODEL);
        Submodel eventModel = assetTypeEventModel.orElseGet(new Supplier<Submodel>() {
            @Override
            public Submodel get() {
                Submodel model = new Submodel(ASSET_TYPE_EVENT_MODEL, theShell);
                model.setIdShort("events");
                model.setKind(Kind.Type);
                model.setCategory(CategoryEnum.APPLICATION_CLASS);
                model.setDescription("de", "i-Asset Labor - Panda-Ereignisse");
                model.setDescription("en", "i-Asset Lab - Panda events");
                model.setVersion("V0.01");
                model.setRevision("001");
                return aasSubmodelRepo.save(model);
            }
        });

        Optional<EventElement> optStateEvent = eventModel.getSubmodelElement("position", EventElement.class);
        EventElement stateEvent = optStateEvent.orElseGet(new Supplier<EventElement>() {

            @Override
            public EventElement get() {
                EventElement m = new EventElement("position", eventModel);
                m.setCategory(CategoryEnum.EVENT);
                m.setDescription("de", "Positionsinformation für End Effektor");
                m.setDescription("en", "position information of end effector");
                m.setKind(Kind.Type);
                // the element is deactivated by default
                m.setActive(false);
                // let the element observe the position, e.g. report posX, posY, posZ
                m.setObservableElement(pandaPosition);
                m.setMessageTopic("at.srfg.iasset-lab.panda.position.current");
                m.setDirection(DirectionEnum.Output);
                // the output variable is specified with the CMMS instance!
                return aasSubmodelElementRepo.save(m);
            }
        });
        Optional<Submodel> appEventSubmodel = aasSubmodelRepo.findByIdentification(APPLICATION_TYPE_EVENT_MODEL);
        if (appEventSubmodel.isPresent()) {
            Submodel eventSubmodel = appEventSubmodel.get();
            Optional<SubmodelElementCollection> mBroker = eventSubmodel.getSubmodelElement("messageBroker", SubmodelElementCollection.class);
            if (mBroker.isPresent()) {
                stateEvent.setMessageBrokerElement(mBroker.get());
                aasSubmodelElementRepo.save(stateEvent);
            }
        }
    }

}
