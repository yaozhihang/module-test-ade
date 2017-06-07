package org.citygml.ade.testade.model.module;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.namespace.QName;

import org.citygml.ade.testade.TestADEContext;
import org.citygml.ade.testade.model.AbstractBuildingUnit;
import org.citygml.ade.testade.model.AbstractFacilities;
import org.citygml.ade.testade.model.BuildingUnit;
import org.citygml.ade.testade.model.BuildingUnitPart;
import org.citygml.ade.testade.model.DHWFacilities;
import org.citygml.ade.testade.model.IndustrialBuilding;
import org.citygml.ade.testade.model.IndustrialBuildingPart;
import org.citygml.ade.testade.model.IndustrialBuildingRoofSurface;
import org.citygml.ade.testade.model.LightingFacilities;
import org.citygml.ade.testade.model.OtherConstruction;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.module.ade.ADEModule;
import org.citygml4j.model.module.citygml.CityGMLVersion;

public class TestADEModule extends ADEModule {
	private HashMap<String, Class<? extends AbstractFeature>> features;
	private HashSet<String> featureProperties;

	public TestADEModule() {
		super("http://www.citygml.org/ade/TestADE/1.0", 
				"test", 
				CityGMLVersion.v2_0_0);
		
		features = new HashMap<>();
		features.put("_AbstractBuildingUnit", AbstractBuildingUnit.class);
		features.put("BuildingUnit", BuildingUnit.class);
		features.put("BuildingUnitPart", BuildingUnitPart.class);
		features.put("Facilities", AbstractFacilities.class);
		features.put("DHWFacilities", DHWFacilities.class);
		features.put("LightingFacilities", LightingFacilities.class);
		features.put("IndustrialBuilding", IndustrialBuilding.class);		
		features.put("IndustrialBuildingPart", IndustrialBuildingPart.class);
		features.put("IndustrialBuildingRoofSurface", IndustrialBuildingRoofSurface.class);
		features.put("OtherConstruction", OtherConstruction.class);

		featureProperties = new HashSet<>();
		featureProperties.add("buildingUnit");
		featureProperties.add("boundedBy");
		featureProperties.add("equippedWith");
		featureProperties.add("consistsOf");
	}

	@Override
	public URL getSchemaResource() {
		return TestADEContext.class.getResource("/org/citygml/ade/testade/_1/schema/CityGML-TestADE.xsd");
	}

	@Override
	public boolean hasFeatureProperty(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasFeature(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Class<? extends AbstractFeature> getFeatureClass(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QName getFeatureName(Class<? extends AbstractFeature> featureClass) {
		// TODO Auto-generated method stub
		return null;
	}

}