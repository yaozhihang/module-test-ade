package org.citygml.ade.testade.model;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.gml.base.AssociationByRep;

public class EnergyPerformanceCertificationProperty extends AssociationByRep<EnergyPerformanceCertification> implements ADEModelObject {

	public EnergyPerformanceCertificationProperty() {
	}
	
	public EnergyPerformanceCertificationProperty(EnergyPerformanceCertification energyPerformanceCertification) {
		super(energyPerformanceCertification);
	}
	
	public EnergyPerformanceCertification getEnergyPerformanceCertification() {
		return super.getObject();
	}

	public boolean isSetEnergyPerformanceCertification() {
		return super.isSetObject();
	}

	public void setEnergyPerformanceCertification(EnergyPerformanceCertification energyPerformanceCertification) {
		super.setObject(energyPerformanceCertification);
	}

	public void unsetEnergyPerformanceCertification() {
		super.unsetObject();
	}
	
	@Override
	public Class<EnergyPerformanceCertification> getAssociableClass() {
		return EnergyPerformanceCertification.class;
	}

	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new EnergyPerformanceCertification(), copyBuilder);
	}

	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		EnergyPerformanceCertification copy = (target == null) ? new EnergyPerformanceCertification() : (EnergyPerformanceCertification)target;
		return super.copyTo(copy, copyBuilder);
	}

}
