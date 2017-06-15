package org.citygml.ade.testade.model;

import org.citygml.ade.testade.model.module.TestADEModule;
import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEGenericApplicationProperty;
import org.citygml4j.model.module.ade.ADEModule;

public class EnergyPerformanceCertificationPropertyElement extends ADEGenericApplicationProperty<EnergyPerformanceCertificationProperty> {

	public EnergyPerformanceCertificationPropertyElement() {
	}
	
	public EnergyPerformanceCertificationPropertyElement(EnergyPerformanceCertificationProperty value) {
		super(value);
	}
	
	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new EnergyPerformanceCertificationPropertyElement(), copyBuilder);
	}
	
	@Override
	public ADEModule getADEModule() {
		return TestADEModule.v1_0;
	}
	
}
