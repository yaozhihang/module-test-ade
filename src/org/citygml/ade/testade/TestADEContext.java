package org.citygml.ade.testade;

import java.util.Arrays;
import java.util.List;

import org.citygml.ade.testade.bind.TestADEMarshaller;
import org.citygml.ade.testade.bind.TestADEUnmarshaller;
import org.citygml.ade.testade.model.module.TestADEModule;
import org.citygml.ade.testade.walker.TestADEFeatureFunctionWalker;
import org.citygml.ade.testade.walker.TestADEFeatureWalker;
import org.citygml.ade.testade.walker.TestADEGMLFunctionWalker;
import org.citygml.ade.testade.walker.TestADEGMLWalker;
import org.citygml4j.model.citygml.ade.binding.ADEContext;
import org.citygml4j.model.citygml.ade.binding.ADEMarshaller;
import org.citygml4j.model.citygml.ade.binding.ADEUnmarshaller;
import org.citygml4j.model.citygml.ade.binding.ADEWalker;
import org.citygml4j.model.module.ade.ADEModule;
import org.citygml4j.util.walker.FeatureFunctionWalker;
import org.citygml4j.util.walker.FeatureWalker;
import org.citygml4j.util.walker.GMLFunctionWalker;
import org.citygml4j.util.walker.GMLWalker;

public class TestADEContext implements ADEContext {
	private final TestADEMarshaller marshaller = new TestADEMarshaller();
	private final TestADEUnmarshaller unmarshaller = new TestADEUnmarshaller();
	
	@Override
	public List<ADEModule> getADEModules() {
		return Arrays.asList(new ADEModule[]{TestADEModule.v1_0});
	}

	@Override
	public List<String> getModelPackageNames() {
		return Arrays.asList(new String[]{"org.citygml.ade.testade.model"});
	}

	@Override
	public List<String> getJAXBPackageNames() {
		return Arrays.asList(new String[]{"org.citygml.ade.testade._1"});
	}

	@Override
	public ADEMarshaller getADEMarshaller() {
		return marshaller;
	}

	@Override
	public ADEUnmarshaller getADEUnmarshaller() {
		return unmarshaller;
	}

	@Override
	public ADEWalker<FeatureWalker> getDefaultFeatureWalker() {
		return new TestADEFeatureWalker();
	}

	@Override
	public ADEWalker<GMLWalker> getDefaultGMLWalker() {
		return new TestADEGMLWalker();
	}

	@Override
	public <T> ADEWalker<FeatureFunctionWalker<T>> getDefaultFeatureFunctionWalker() {
		return new TestADEFeatureFunctionWalker<>();
	}

	@Override
	public <T> ADEWalker<GMLFunctionWalker<T>> getDefaultGMLFunctionWalker() {
		return new TestADEGMLFunctionWalker<>();
	}

}
