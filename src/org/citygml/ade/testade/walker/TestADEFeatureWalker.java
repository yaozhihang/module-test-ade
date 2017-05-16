package org.citygml.ade.testade.walker;

import java.util.ArrayList;

import org.citygml.ade.testade.model.AbstractBuildingUnit;
import org.citygml.ade.testade.model.AbstractFacilities;
import org.citygml.ade.testade.model.BuildingUnit;
import org.citygml.ade.testade.model.BuildingUnitPart;
import org.citygml.ade.testade.model.BuildingUnitPartProperty;
import org.citygml.ade.testade.model.BuildingUnitPropertyElement;
import org.citygml.ade.testade.model.DHWFacilities;
import org.citygml.ade.testade.model.FacilitiesProperty;
import org.citygml.ade.testade.model.IndustrialBuilding;
import org.citygml.ade.testade.model.LightingFacilities;
import org.citygml4j.model.citygml.ade.binding.ADEWalker;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.citygml.core.AddressProperty;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.gml.feature.FeatureProperty;
import org.citygml4j.util.walker.FeatureWalker;

public class TestADEFeatureWalker implements ADEWalker<FeatureWalker> {
	private FeatureWalker walker;

	@Override
	public void setParentWalker(FeatureWalker walker) {
		this.walker = walker;
	}

	public void visit(AbstractFacilities abstractFacilities) {
		walker.visit((AbstractFeature)abstractFacilities);
	}

	public void visit(DHWFacilities dhwFacilities) {
		visit((AbstractFacilities)dhwFacilities);
	}

	public void visit(LightingFacilities lightingFacilities) {
		visit((AbstractFacilities)lightingFacilities);
	}

	public void visit(AbstractBuildingUnit abstractBuildingUnit) {
		walker.visit((AbstractCityObject)abstractBuildingUnit);

		if (abstractBuildingUnit.isSetEquippedWith()) {
			for (FacilitiesProperty property : abstractBuildingUnit.getEquippedWith())
				walker.visit((FeatureProperty<?>)property);
		}

		if (abstractBuildingUnit.isSetConsistsOf()) {
			for (BuildingUnitPartProperty property : abstractBuildingUnit.getConsistsOf())
				walker.visit((FeatureProperty<?>)property);
		}
		
		if (abstractBuildingUnit.isSetAddress()) {
			for (AddressProperty property : new ArrayList<AddressProperty>(abstractBuildingUnit.getAddress()))
				walker.visit((FeatureProperty<?>)property);
		}
	}

	public void visit(BuildingUnit buildingUnit) {
		visit((AbstractBuildingUnit)buildingUnit);
	}

	public void visit(BuildingUnitPart buildingUnitPart) {
		visit((AbstractBuildingUnit)buildingUnitPart);
	}
	
	public void visit(IndustrialBuilding industrialBuilding) {
		walker.visit((AbstractBuilding)industrialBuilding);
	}
	
	public void visit(BuildingUnitPropertyElement buildingUnitPropertyElement) {
		walker.visit((FeatureProperty<?>)buildingUnitPropertyElement.getValue());
	}

}
