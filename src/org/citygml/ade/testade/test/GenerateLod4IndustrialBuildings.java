package org.citygml.ade.testade.test;

import java.io.File;
import java.util.GregorianCalendar;

import org.citygml.ade.testade.TestADEContext;
import org.citygml.ade.testade.model.BuildingUnit;
import org.citygml.ade.testade.model.BuildingUnitProperty;
import org.citygml.ade.testade.model.BuildingUnitPropertyElement;
import org.citygml.ade.testade.model.DHWFacilities;
import org.citygml.ade.testade.model.EnergyPerformanceCertification;
import org.citygml.ade.testade.model.EnergyPerformanceCertificationProperty;
import org.citygml.ade.testade.model.EnergyPerformanceCertificationPropertyElement;
import org.citygml.ade.testade.model.FacilitiesProperty;
import org.citygml.ade.testade.model.IndustrialBuilding;
import org.citygml.ade.testade.model.IndustrialBuildingPart;
import org.citygml.ade.testade.model.OwnerNameProperty;
import org.citygml4j.CityGMLContext;
import org.citygml4j.builder.CityGMLBuilder;
import org.citygml4j.model.citygml.CityGML;
import org.citygml4j.model.citygml.CityGMLClass;
import org.citygml4j.model.citygml.building.Building;
import org.citygml4j.model.citygml.building.BuildingPartProperty;
import org.citygml4j.model.citygml.core.CityModel;
import org.citygml4j.model.citygml.core.CityObjectMember;
import org.citygml4j.model.citygml.generics.GenericCityObject;
import org.citygml4j.model.gml.GMLClass;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.geometry.AbstractGeometry;
import org.citygml4j.model.gml.geometry.GeometryProperty;
import org.citygml4j.model.gml.geometry.aggregates.MultiCurve;
import org.citygml4j.model.gml.geometry.aggregates.MultiCurveProperty;
import org.citygml4j.model.gml.geometry.primitives.CurveProperty;
import org.citygml4j.model.gml.geometry.primitives.LineString;
import org.citygml4j.model.module.citygml.CityGMLVersion;
import org.citygml4j.xml.io.CityGMLInputFactory;
import org.citygml4j.xml.io.CityGMLOutputFactory;
import org.citygml4j.xml.io.reader.CityGMLReader;
import org.citygml4j.xml.io.reader.FeatureReadMode;
import org.citygml4j.xml.io.writer.CityGMLWriter;

public class GenerateLod4IndustrialBuildings {

	public static void main(String[] args) throws Exception {
		System.out.println("Start generating test dataset");
		
		CityGMLContext ctx = new CityGMLContext();
		
		// register the TestADE context into the citygml4j context
		ctx.registerADEContext(new TestADEContext());
		
		CityGMLBuilder citygmlBuilder = ctx.createCityGMLBuilder();
		CityGMLInputFactory citygmlInputFactory = citygmlBuilder.createCityGMLInputFactory();
		citygmlInputFactory.setProperty(CityGMLInputFactory.FEATURE_READ_MODE, FeatureReadMode.SPLIT_PER_COLLECTION_MEMBER);			
		
		CityModel cityModel = new CityModel();
		
		// create a building unit being a city object member of the city model and
		// enrich it with a Lod3MultiCurve property
		BuildingUnit globalBuildingUnit = new BuildingUnit();
		MultiCurve lod4MultiCurve = new MultiCurve();
		globalBuildingUnit.setLod4MultiCurve(new MultiCurveProperty(lod4MultiCurve));
		cityModel.getCityObjectMember().add(new CityObjectMember(globalBuildingUnit));
		
		// create an industrial building
		IndustrialBuilding industrialBuilding = new IndustrialBuilding();
		
		industrialBuilding.addGenericApplicationPropertyOfAbstractBuilding(new OwnerNameProperty("TUM"));
		industrialBuilding.setCreationDate(new GregorianCalendar());
		industrialBuilding.setStoreysAboveGround(6);		
		
		// create industrial building part and assign to industrial building
		IndustrialBuildingPart industrialBuildingPart = new IndustrialBuildingPart();
		industrialBuilding.addConsistsOfBuildingPart(new BuildingPartProperty(industrialBuildingPart));
		industrialBuilding.setRemark("test remark");

		// create energy performance certification and assign to industrial building part via ADE hook		
		EnergyPerformanceCertification certification = new EnergyPerformanceCertification();
		certification.addCertificationName("test name");
		certification.setCertificationId("test id");
		EnergyPerformanceCertificationProperty certificationProp = new EnergyPerformanceCertificationProperty(certification);				
		EnergyPerformanceCertificationPropertyElement certificationADEProp = new EnergyPerformanceCertificationPropertyElement(certificationProp);
		industrialBuildingPart.addGenericApplicationPropertyOfAbstractBuilding(certificationADEProp);

		// Read geometry information from another CityGML file
		CityGMLReader citygmlReader = citygmlInputFactory.createCityGMLReader(new File("resources/rawdata/TUM_Munich_Lod4.gml"));
		while (citygmlReader.hasNext()) {
			CityGML feature = citygmlReader.nextFeature();

			if (feature.getCityGMLClass() == CityGMLClass.BUILDING) {
				Building building = (Building)feature;
				
				if (building.getAddress().size() > 0) {
					globalBuildingUnit.setAddress(building.getAddress());
				}

				// create building unit with DHW facility and
				// assign to industrial building via ADE hook
				BuildingUnit buildingUnit = new BuildingUnit();
				DHWFacilities dhwFacilities = new DHWFacilities();
				Measure totalValue = new Measure();
				totalValue.setValue(123.45);
				totalValue.setUom("uom");
				dhwFacilities.setTotalValue(totalValue);
				
				Code usage = new Code();
				usage.setCodeSpace("www.testade.de");
				usage.setValue(String.valueOf(1000));
				buildingUnit.getUsage().add(usage);
				
				buildingUnit.addEquippedWith(new FacilitiesProperty(dhwFacilities));
				buildingUnit.setLod4MultiSurface(building.getLod4MultiSurface());
				buildingUnit.getEnergyPerformanceCertification().add(new EnergyPerformanceCertificationProperty(certification));
				buildingUnit.getEnergyPerformanceCertification().add(new EnergyPerformanceCertificationProperty(certification));				
				BuildingUnitPropertyElement adeUnitProp = new BuildingUnitPropertyElement(new BuildingUnitProperty(buildingUnit));
				industrialBuilding.addGenericApplicationPropertyOfAbstractBuilding(adeUnitProp);		
			}
			else if (feature.getCityGMLClass() == CityGMLClass.GENERIC_CITY_OBJECT){
				GenericCityObject genericCityObject = (GenericCityObject)feature;
				GeometryProperty<? extends AbstractGeometry> geometryProperty = genericCityObject.getLod4Geometry();
				if (geometryProperty != null) {
					if (geometryProperty.isSetGeometry()) {
						AbstractGeometry abstractGeometry = geometryProperty.getGeometry();
						if (abstractGeometry.getGMLClass() == GMLClass.LINE_STRING) {
							lod4MultiCurve.addCurveMember(new CurveProperty((LineString)abstractGeometry));
						}
					}			
				}	
			}
		}
		
		// create city model and assign industrial building				
		cityModel.addCityObjectMember(new CityObjectMember(industrialBuilding));	
		
		citygmlReader.close();

		// write CityGML dataset
		citygmlBuilder = ctx.createCityGMLBuilder();
		CityGMLOutputFactory out = citygmlBuilder.createCityGMLOutputFactory(CityGMLVersion.v2_0_0);
		CityGMLWriter writer = out.createCityGMLWriter(new File("datasets/TestLod4IndustrialBuildings.gml"));

		writer.setIndentString("  ");
		writer.setPrefixes(CityGMLVersion.v2_0_0);
		writer.setPrefixes(ctx.getADEContexts());
		writer.setSchemaLocations(CityGMLVersion.v2_0_0);
		
		// we provide the schema location to the CityGML-TestADE.xsd manually here.
		// If the schema would be available on the Internet, the schema location could be
		// hard-coded in the ADEModule of the TestADEContext.
		writer.setSchemaLocation("http://www.citygml.org/ade/TestADE/1.0", "CityGML-TestADE.xsd");

		writer.write(cityModel);
		writer.close();
		
		System.out.println("Finished");
	}

}
