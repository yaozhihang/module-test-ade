package org.citygml.ade.testade.model;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.CityGMLClass;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.building.BuildingPart;
import org.citygml4j.model.common.visitor.FeatureFunctor;
import org.citygml4j.model.common.visitor.FeatureVisitor;
import org.citygml4j.model.common.visitor.GMLFunctor;
import org.citygml4j.model.common.visitor.GMLVisitor;

public class IndustrialBuildingPart extends BuildingPart implements ADEModelObject {
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	
	public boolean isSetRemark() {
		return remark != null;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new IndustrialBuildingPart(), copyBuilder);
	}
	
	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		IndustrialBuildingPart copy = (target == null) ? new IndustrialBuildingPart() : (IndustrialBuildingPart)target;
		
		if (isSetRemark())
			copy.setRemark(copyBuilder.copy(remark));
		
		return super.copyTo(copy, copyBuilder);
	}
	
	@Override
	public void accept(FeatureVisitor visitor) {
		visitor.visit((ADEModelObject)this);
	}

	@Override
	public <T> T accept(FeatureFunctor<T> visitor) {
		return visitor.apply((ADEModelObject)this);
	}

	@Override
	public void accept(GMLVisitor visitor) {
		visitor.visit((ADEModelObject)this);
	}

	@Override
	public <T> T accept(GMLFunctor<T> visitor) {
		return visitor.apply((ADEModelObject)this);
	}

	@Override
	public CityGMLClass getCityGMLClass() {
		return CityGMLClass.ADE_COMPONENT;
	}

}
