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
import org.citygml4j.util.walker.FeatureFunctionWalker;

public class TestADEFeatureFunctionWalker<T> implements ADEWalker<FeatureFunctionWalker<T>> {
	private FeatureFunctionWalker<T> walker;

	@Override
	public void setParentWalker(FeatureFunctionWalker<T> walker) {
		this.walker = walker;
	}

	public T apply(AbstractFacilities abstractFacilities) {
		return walker.apply((AbstractFeature)abstractFacilities);
	}

	public T apply(DHWFacilities dhwFacilities) {
		return apply((AbstractFacilities)dhwFacilities);
	}

	public T apply(LightingFacilities lightingFacilities) {
		return apply((AbstractFacilities)lightingFacilities);
	}

	public T apply(AbstractBuildingUnit abstractBuildingUnit) {
		T object = walker.apply((AbstractCityObject)abstractBuildingUnit);
		if (object != null)
			return object;
		
		if (abstractBuildingUnit.isSetEquippedWith()) {
			for (FacilitiesProperty property : abstractBuildingUnit.getEquippedWith()) {
				object = walker.apply((FeatureProperty<?>)property);
				if (object != null)
					return object;
			}
		}

		if (abstractBuildingUnit.isSetConsistsOf()) {
			for (BuildingUnitPartProperty property : abstractBuildingUnit.getConsistsOf()) {
				object = walker.apply((FeatureProperty<?>)property);
				if (object != null)
					return object;
			}
		}
		
		if (abstractBuildingUnit.isSetAddress()) {
			for (AddressProperty property : new ArrayList<AddressProperty>(abstractBuildingUnit.getAddress())) {
				object = walker.apply((FeatureProperty<?>)property);
				if (object != null)
					return object;
			}
		}
		
		return null;
	}

	public T apply(BuildingUnit buildingUnit) {
		return apply((AbstractBuildingUnit)buildingUnit);
	}

	public T apply(BuildingUnitPart buildingUnitPart) {
		return apply((AbstractBuildingUnit)buildingUnitPart);
	}
	
	public T apply(IndustrialBuilding industrialBuilding) {
		return walker.apply((AbstractBuilding)industrialBuilding);
	}
	
	public T apply(BuildingUnitPropertyElement buildingUnitPropertyElement) {
		return walker.apply((FeatureProperty<?>)buildingUnitPropertyElement.getValue());
	}

}
