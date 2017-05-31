package org.citygml.ade.testade.test;

import java.io.File;
import org.citygml.ade.testade.TestADEContext;
import org.citygml.ade.testade.model.IndustrialBuildingRoofSurface;
import org.citygml.ade.testade.model.OtherConstruction;
import org.citygml4j.CityGMLContext;
import org.citygml4j.builder.CityGMLBuilder;
import org.citygml4j.model.citygml.CityGML;
import org.citygml4j.model.citygml.CityGMLClass;
import org.citygml4j.model.citygml.building.BoundarySurfaceProperty;
import org.citygml4j.model.citygml.building.Building;
import org.citygml4j.model.citygml.core.CityModel;
import org.citygml4j.model.citygml.core.CityObjectMember;
import org.citygml4j.model.gml.geometry.aggregates.MultiSurfaceProperty;
import org.citygml4j.model.module.citygml.CityGMLVersion;
import org.citygml4j.xml.io.CityGMLInputFactory;
import org.citygml4j.xml.io.CityGMLOutputFactory;
import org.citygml4j.xml.io.reader.CityGMLReader;
import org.citygml4j.xml.io.reader.FeatureReadMode;
import org.citygml4j.xml.io.writer.CityGMLWriter;

public class GenerateLod2OtherConstructions {

	public static void main(String[] args) throws Exception {
		CityGMLContext ctx = new CityGMLContext();
		System.out.println("Start generating test dataset");
		// register the TestADE context into the citygml4j context
		ctx.registerADEContext(new TestADEContext());
		
		CityGMLBuilder citygmlBuilder = ctx.createCityGMLBuilder();
		CityGMLInputFactory citygmlInputFactory = citygmlBuilder.createCityGMLInputFactory();
		citygmlInputFactory.setProperty(CityGMLInputFactory.FEATURE_READ_MODE, FeatureReadMode.SPLIT_PER_COLLECTION_MEMBER);			
		
		CityModel cityModel = new CityModel();

		// Read geometry information from another CityGML file
		CityGMLReader citygmlReader = citygmlInputFactory.createCityGMLReader(new File("resources/rawdata/TUM_Munich_Lod2.gml"));
		while (citygmlReader.hasNext()) {
			CityGML feature = citygmlReader.nextFeature();
			
			if (feature.getCityGMLClass() == CityGMLClass.BUILDING) {
				Building building = (Building)feature;

				// create an other construction
				OtherConstruction otherConstruction = new OtherConstruction();	

				for (BoundarySurfaceProperty boundarySurfaceProperty: building.getBoundedBySurface()) {
					// create industrial building roof surface
					IndustrialBuildingRoofSurface industrialBuildingRoofSurface = new IndustrialBuildingRoofSurface();
					industrialBuildingRoofSurface.setRemark("This is a test industrial building roof surface");							
					MultiSurfaceProperty lod2MultiSurfaceProperty = boundarySurfaceProperty.getBoundarySurface().getLod2MultiSurface();
					industrialBuildingRoofSurface.setLod2MultiSurface(lod2MultiSurfaceProperty);					
					otherConstruction.addBoundedBySurface(new BoundarySurfaceProperty(industrialBuildingRoofSurface));
				}
				
				// create city model and assign other construction			
				cityModel.addCityObjectMember(new CityObjectMember(otherConstruction));		
			}
		}

		citygmlReader.close();

		// write CityGML dataset
		citygmlBuilder = ctx.createCityGMLBuilder();
		CityGMLOutputFactory out = citygmlBuilder.createCityGMLOutputFactory(CityGMLVersion.v2_0_0);
		CityGMLWriter writer = out.createCityGMLWriter(new File("datasets/TestLod2OtherConstructions.gml"));

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
