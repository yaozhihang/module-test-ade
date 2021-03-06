package org.citygml.ade.testade.model;

import org.citygml.ade.testade.model.module.TestADEModule;
import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.gml.feature.FeatureProperty;
import org.citygml4j.model.module.ade.ADEModule;

public class BuildingUnitPartProperty extends FeatureProperty<BuildingUnitPart> implements ADEModelObject {

	public BuildingUnitPartProperty() {
	}
	
	public BuildingUnitPartProperty(BuildingUnitPart buildingUnitPart) {
		super(buildingUnitPart);
	}
	
	public BuildingUnitPartProperty(String href) {
		super(href);
	}
	
	public BuildingUnitPart getBuildingUnitPart() {
		return super.getObject();
	}

	public boolean isSetBuildingUnitPart() {
		return super.isSetObject();
	}

	public void setBuildingUnitPart(BuildingUnitPart buildingUnitPart) {
		super.setObject(buildingUnitPart);
	}

	public void unsetBuildingUnitPart() {
		super.unsetObject();
	}

	@Override
	public Class<BuildingUnitPart> getAssociableClass() {
		return BuildingUnitPart.class;
	}

	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new BuildingUnitPartProperty(), copyBuilder);
	}

	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		BuildingUnitPartProperty copy = (target == null) ? new BuildingUnitPartProperty() : (BuildingUnitPartProperty)target;
		return super.copyTo(copy, copyBuilder);
	}
	
	@Override
	public ADEModule getADEModule() {
		return TestADEModule.v1_0;
	}
}
