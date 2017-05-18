package org.citygml.ade.testade.test;

import java.io.File;

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
import org.citygml4j.CityGMLContext;
import org.citygml4j.builder.CityGMLBuilder;
import org.citygml4j.model.citygml.building.BuildingPart;
import org.citygml4j.model.citygml.building.BuildingPartProperty;
import org.citygml4j.model.citygml.core.CityModel;
import org.citygml4j.model.citygml.core.CityObjectMember;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.module.citygml.CityGMLVersion;
import org.citygml4j.util.walker.FeatureWalker;
import org.citygml4j.xml.io.CityGMLInputFactory;
import org.citygml4j.xml.io.CityGMLOutputFactory;
import org.citygml4j.xml.io.reader.CityGMLReader;
import org.citygml4j.xml.io.writer.CityGMLWriter;

public class Test {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting TestADE sample program.");
		
		// create a citygml4j context and register the TestADE context
		CityGMLContext context = new CityGMLContext();
		context.registerADEContext(new TestADEContext());
		
		/**
		 * Step 1: Create some TestADE features using the TestADE model classes.
		 * The model classes can be used seamlessly with the citygml4j model classes.
		 */
		
		System.out.println("Creating an example TestADE feature hierachy...");
		
		// create industrial building
		IndustrialBuilding industrialBuilding = new IndustrialBuilding();
		
		// create building part and assign to industrial building
		BuildingPart buildingPart = new BuildingPart();
		industrialBuilding.addConsistsOfBuildingPart(new BuildingPartProperty(buildingPart));
		industrialBuilding.setRemark("test remark");

		// create energy performance certification and
		// assign to building part via ADE hook
		EnergyPerformanceCertification certification = new EnergyPerformanceCertification();
		certification.addCertificationName("name");
		certification.setCertificationId("id");
		EnergyPerformanceCertificationProperty certificationProp = new EnergyPerformanceCertificationProperty(certification);
		
		EnergyPerformanceCertificationPropertyElement certificationADEProp = new EnergyPerformanceCertificationPropertyElement(certificationProp);
		buildingPart.addGenericApplicationPropertyOfAbstractBuilding(certificationADEProp);
		
		// create building unit with DHW facility and
		// assign to industrial building via ADE hook
		BuildingUnit buildingUnit = new BuildingUnit();
		DHWFacilities dhwFacilities = new DHWFacilities();
		Measure totalValue = new Measure();
		totalValue.setValue(123.45);
		totalValue.setUom("uom");
		
		dhwFacilities.setTotalValue(totalValue);
		buildingUnit.addEquippedWith(new FacilitiesProperty(dhwFacilities));
		
		BuildingUnitPropertyElement adeUnitProp = new BuildingUnitPropertyElement(new BuildingUnitProperty(buildingUnit));
		industrialBuilding.addGenericApplicationPropertyOfAbstractBuilding(adeUnitProp);
		
		// create city model and assign industrial building
		CityModel cityModel = new CityModel();
		cityModel.addCityObjectMember(new CityObjectMember(industrialBuilding));
		
		/**
		 * Step 2: Write the TestADE features to a CityGML dataset. 
		 * No special code is required since the citygml4j context knows 
		 * the TestADE context.
		 */
		
		System.out.println("Writing the TestADE feature hierachy to 'test.gml'...");
		
		// write CityGML dataset
		CityGMLBuilder builder = context.createCityGMLBuilder();
		CityGMLOutputFactory out = builder.createCityGMLOutputFactory(CityGMLVersion.v2_0_0);
		CityGMLWriter writer = out.createCityGMLWriter(new File("datasets/test.gml"));

		writer.setIndentString("  ");
		writer.setPrefixes(CityGMLVersion.v2_0_0);
		writer.setPrefixes(context.getADEContexts());
		writer.setSchemaLocations(CityGMLVersion.v2_0_0);
		
		// we provide the schema location to the CityGML-TestADE.xsd manually here.
		// If the schema would be available on the Internet, the schema location could be
		// hard-coded in the ADEModule of the TestADEContext.
		writer.setSchemaLocation("http://www.citygml.org/ade/TestADE/1.0", "CityGML-TestADE.xsd");

		writer.write(cityModel);
		writer.close();
		
		/**
		 * Step 3: Read the TestADE dataset. 
		 * Again, no special code is required.
		 */
		
		System.out.println("Reading the TestADE feature hierachy from 'test.gml'...");

		CityGMLInputFactory in = builder.createCityGMLInputFactory();
		CityGMLReader reader = in.createCityGMLReader(new File("datasets/test.gml"));
		
		// unmarshal dataset into a CityModel
		cityModel = (CityModel)reader.nextFeature();
		reader.close();
		
		// to demonstrate that we have successfully read all ADE content,
		// we use a FeatureWalker to iterate through the hierarchy and
		// simple print every feature it comes across to the console
		// (citygml4j features + ADE features)
		
		// note that we have to make the ADE context known to the 
		// feature walker. Otherwise the ADE features will not be found.		
		FeatureWalker walker = new FeatureWalker() {
			public void visit(AbstractFeature abstractFeature) {
				System.out.println(abstractFeature);
				super.visit(abstractFeature);
			}
		}.useADEContexts(context.getADEContexts());
		
		System.out.println("The dataset contains the following features:");
		cityModel.accept(walker);
		
		System.out.println("Sample program finished.");
	}

}
