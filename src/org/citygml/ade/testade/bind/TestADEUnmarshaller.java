package org.citygml.ade.testade.bind;

import javax.xml.bind.JAXBElement;

import org.citygml.ade.testade._1.BuildingUnitPartPropertyType;
import org.citygml.ade.testade._1.BuildingUnitPartType;
import org.citygml.ade.testade._1.BuildingUnitType;
import org.citygml.ade.testade._1.DHWFacilitiesType;
import org.citygml.ade.testade._1.EnergyPerformanceCertificationPropertyType;
import org.citygml.ade.testade._1.EnergyPerformanceCertificationType;
import org.citygml.ade.testade._1.FacilitiesPropertyType;
import org.citygml.ade.testade._1.FacilitiesType;
import org.citygml.ade.testade._1.IndustrialBuildingType;
import org.citygml.ade.testade._1.LightingFacilitiesType;
import org.citygml.ade.testade._1._AbstractBuildingUnitPropertyType;
import org.citygml.ade.testade._1._AbstractBuildingUnitType;
import org.citygml.ade.testade.model.AbstractBuildingUnit;
import org.citygml.ade.testade.model.AbstractFacilities;
import org.citygml.ade.testade.model.BuildingUnit;
import org.citygml.ade.testade.model.BuildingUnitPart;
import org.citygml.ade.testade.model.BuildingUnitPartProperty;
import org.citygml.ade.testade.model.BuildingUnitProperty;
import org.citygml.ade.testade.model.BuildingUnitPropertyElement;
import org.citygml.ade.testade.model.DHWFacilities;
import org.citygml.ade.testade.model.EnergyPerformanceCertification;
import org.citygml.ade.testade.model.EnergyPerformanceCertificationProperty;
import org.citygml.ade.testade.model.EnergyPerformanceCertificationPropertyElement;
import org.citygml.ade.testade.model.FacilitiesProperty;
import org.citygml.ade.testade.model.FloorAreaProperty;
import org.citygml.ade.testade.model.IndustrialBuilding;
import org.citygml.ade.testade.model.LightingFacilities;
import org.citygml.ade.testade.model.OwnerNameProperty;
import org.citygml4j.builder.jaxb.unmarshal.citygml.ade.ADEUnmarshallerHelper;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.ade.binding.ADEUnmarshaller;
import org.citygml4j.model.common.base.ModelObject;
import org.citygml4j.model.gml.xlink.XLinkActuate;
import org.citygml4j.model.gml.xlink.XLinkShow;
import org.citygml4j.model.gml.xlink.XLinkType;
import org.citygml4j.util.jaxb.JAXBCheckedMapper;
import org.citygml4j.xml.io.reader.MissingADESchemaException;

import net.opengis.citygml._2.AddressPropertyType;
import net.opengis.gml.AreaType;
import net.opengis.gml.CodeType;

public class TestADEUnmarshaller implements ADEUnmarshaller {
	private final JAXBCheckedMapper<ADEModelObject> typeMapper;
	private ADEUnmarshallerHelper helper;

	public TestADEUnmarshaller() {
		typeMapper = JAXBCheckedMapper.<ADEModelObject>create()
				.with(DHWFacilitiesType.class, this::unmarshalDHWFacilities)
				.with(LightingFacilitiesType.class, this::unmarshalLightingFacilities)
				.with(FacilitiesPropertyType.class, this::unmarshalFacilitiesProperty)
				.with(BuildingUnitType.class, this::unmarshalBuildingUnit)
				.with(BuildingUnitPartType.class, this::unmarshalBuildingUnitPart)
				.with(_AbstractBuildingUnitPropertyType.class, this::unmarshalBuildingUnitProperty)
				.with(BuildingUnitPartPropertyType.class, this::unmarshalBuildingUnitPartProperty)
				.with(EnergyPerformanceCertificationType.class, this::unmarshalEnergyPerformanceCertification)
				.with(EnergyPerformanceCertificationPropertyType.class, this::unmarshalEnergyPerformanceCertificationProperty)
				.with(IndustrialBuildingType.class, this::unmarshalIndustrialBuilding)
				.with(JAXBElement.class, this::unmarshal);
	}

	@Override
	public void setADEUnmarshallerHelper(ADEUnmarshallerHelper helper) {
		this.helper = helper;
	}

	@Override
	public ADEModelObject unmarshal(JAXBElement<?> src) throws MissingADESchemaException {
		final Object value = src.getValue();

		// generic application properties
		switch (src.getName().getLocalPart()) {
		case "ownerName":
			return new OwnerNameProperty((String)value);
		case "floorArea":
			return new FloorAreaProperty(helper.getGMLUnmarshaller().unmarshalArea((AreaType)value));
		case "energyPerformanceCertification":
			return new EnergyPerformanceCertificationPropertyElement(unmarshalEnergyPerformanceCertificationProperty((EnergyPerformanceCertificationPropertyType)value));
		case "buildingUnit":
			return new BuildingUnitPropertyElement(unmarshalBuildingUnitProperty((_AbstractBuildingUnitPropertyType)value));
		}
		
		// all other types
		return unmarshal(value);
	}

	@Override
	public ADEModelObject unmarshal(Object src) throws MissingADESchemaException {
		return typeMapper.apply(src);
	}

	public void unmarshalAbstractFacilities(FacilitiesType src, AbstractFacilities dest) throws MissingADESchemaException {
		helper.getGMLUnmarshaller().unmarshalAbstractFeature(src, dest);

		if (src.isSetTotalValue())
			dest.setTotalValue(helper.getGMLUnmarshaller().unmarshalMeasure(src.getTotalValue()));
	}
	
	public DHWFacilities unmarshalDHWFacilities(DHWFacilitiesType src) throws MissingADESchemaException {
		DHWFacilities dest = new DHWFacilities();
		unmarshalAbstractFacilities(src, dest);
		
		return dest;
	}
	
	public LightingFacilities unmarshalLightingFacilities(LightingFacilitiesType src) throws MissingADESchemaException {
		LightingFacilities dest = new LightingFacilities();
		unmarshalAbstractFacilities(src, dest);
		
		return dest;
	}
	
	public FacilitiesProperty unmarshalFacilitiesProperty(FacilitiesPropertyType src) throws MissingADESchemaException {
		FacilitiesProperty dest = new FacilitiesProperty();
		
		if (src.isSetFacilities()) {
			ModelObject object = helper.getJAXBUnmarshaller().unmarshal(src.getFacilities());
			if (object instanceof AbstractFacilities)
				dest.setFacilities((AbstractFacilities)object);
		}
		
		if (src.isSetRemoteSchema())
			dest.setRemoteSchema(src.getRemoteSchema());

		if (src.isSetType())
			dest.setType(XLinkType.fromValue(src.getType().value()));

		if (src.isSetHref())
			dest.setHref(src.getHref());

		if (src.isSetRole())
			dest.setRole(src.getRole());

		if (src.isSetArcrole())
			dest.setArcrole(src.getArcrole());

		if (src.isSetTitle())
			dest.setTitle(src.getTitle());

		if (src.isSetShow())
			dest.setShow(XLinkShow.fromValue(src.getShow().value()));

		if (src.isSetActuate())
			dest.setActuate(XLinkActuate.fromValue(src.getActuate().value()));
		
		return dest;
	}
	
	public void unmarshalAbstractBuildingUnit(_AbstractBuildingUnitType src, AbstractBuildingUnit dest) throws MissingADESchemaException {
		helper.getCore200Unmarshaller().unmarshalAbstractCityObject(src, dest);
		
		if (src.isSetClazz())
			dest.setClazz(helper.getGMLUnmarshaller().unmarshalCode(src.getClazz()));

		if (src.isSetFunction()) {
			for (CodeType function : src.getFunction())
				dest.addFunction(helper.getGMLUnmarshaller().unmarshalCode(function));
		}

		if (src.isSetUsage()) {
			for (CodeType usage : src.getUsage())
				dest.addUsage(helper.getGMLUnmarshaller().unmarshalCode(usage));
		}
		
		if (src.isSetEnergyPerformanceCertification()) {
			for (EnergyPerformanceCertificationPropertyType property : src.getEnergyPerformanceCertification())
				dest.addEnergyPerformanceCertification(unmarshalEnergyPerformanceCertificationProperty(property));
		}
		
		if (src.isSetLod1Solid())
			dest.setLod1Solid(helper.getGMLUnmarshaller().unmarshalSolidProperty(src.getLod1Solid()));
		
		if (src.isSetLod2Solid())
			dest.setLod2Solid(helper.getGMLUnmarshaller().unmarshalSolidProperty(src.getLod2Solid()));
		
		if (src.isSetLod3Solid())
			dest.setLod3Solid(helper.getGMLUnmarshaller().unmarshalSolidProperty(src.getLod3Solid()));
		
		if (src.isSetLod4Solid())
			dest.setLod4Solid(helper.getGMLUnmarshaller().unmarshalSolidProperty(src.getLod4Solid()));
		
		if (src.isSetLod1MultiSurface())
			dest.setLod1MultiSurface(helper.getGMLUnmarshaller().unmarshalMultiSurfaceProperty(src.getLod1MultiSurface()));
		
		if (src.isSetLod2MultiSurface())
			dest.setLod2MultiSurface(helper.getGMLUnmarshaller().unmarshalMultiSurfaceProperty(src.getLod2MultiSurface()));
		
		if (src.isSetLod3MultiSurface())
			dest.setLod3MultiSurface(helper.getGMLUnmarshaller().unmarshalMultiSurfaceProperty(src.getLod3MultiSurface()));
		
		if (src.isSetLod4MultiSurface())
			dest.setLod4MultiSurface(helper.getGMLUnmarshaller().unmarshalMultiSurfaceProperty(src.getLod4MultiSurface()));
		
		if (src.isSetLod2MultiCurve())
			dest.setLod2MultiCurve(helper.getGMLUnmarshaller().unmarshalMultiCurveProperty(src.getLod2MultiCurve()));
		
		if (src.isSetLod3MultiCurve())
			dest.setLod3MultiCurve(helper.getGMLUnmarshaller().unmarshalMultiCurveProperty(src.getLod3MultiCurve()));
		
		if (src.isSetLod4MultiCurve())
			dest.setLod4MultiCurve(helper.getGMLUnmarshaller().unmarshalMultiCurveProperty(src.getLod4MultiCurve()));
		
		if (src.isSetAddress()) {
			for (AddressPropertyType addressProperty : src.getAddress())
				dest.addAddress(helper.getCore200Unmarshaller().unmarshalAddressProperty(addressProperty));
		}
		
		if (src.isSetEquippedWith()) {
			for (FacilitiesPropertyType property : src.getEquippedWith())
				dest.addEquippedWith(unmarshalFacilitiesProperty(property));
		}
		
		if (src.isSetConsistsOf()) {
			for (BuildingUnitPartPropertyType buildingUnitPartProperty : src.getConsistsOf())
				dest.addConsistsOf(unmarshalBuildingUnitPartProperty(buildingUnitPartProperty));
		}
	}

	public BuildingUnit unmarshalBuildingUnit(BuildingUnitType src) throws MissingADESchemaException {
		BuildingUnit dest = new BuildingUnit();
		unmarshalAbstractBuildingUnit(src, dest);
		
		return dest;
	}
	
	public BuildingUnitPart unmarshalBuildingUnitPart(BuildingUnitPartType src) throws MissingADESchemaException {
		BuildingUnitPart dest = new BuildingUnitPart();
		unmarshalAbstractBuildingUnit(src, dest);
		
		return dest;
	}
	
	public BuildingUnitProperty unmarshalBuildingUnitProperty(_AbstractBuildingUnitPropertyType src) throws MissingADESchemaException {
		BuildingUnitProperty dest = new BuildingUnitProperty();
		
		if (src.isSet_AbstractBuildingUnit()) {
			ModelObject object = helper.getJAXBUnmarshaller().unmarshal(src.get_AbstractBuildingUnit());
			if (object instanceof AbstractBuildingUnit)
				dest.setBuildingUnit((AbstractBuildingUnit)object);
		}
		
		if (src.isSetRemoteSchema())
			dest.setRemoteSchema(src.getRemoteSchema());

		if (src.isSetType())
			dest.setType(XLinkType.fromValue(src.getType().value()));

		if (src.isSetHref())
			dest.setHref(src.getHref());

		if (src.isSetRole())
			dest.setRole(src.getRole());

		if (src.isSetArcrole())
			dest.setArcrole(src.getArcrole());

		if (src.isSetTitle())
			dest.setTitle(src.getTitle());

		if (src.isSetShow())
			dest.setShow(XLinkShow.fromValue(src.getShow().value()));

		if (src.isSetActuate())
			dest.setActuate(XLinkActuate.fromValue(src.getActuate().value()));
		
		return dest;
	}
	
	public BuildingUnitPartProperty unmarshalBuildingUnitPartProperty(BuildingUnitPartPropertyType src) throws MissingADESchemaException {
		BuildingUnitPartProperty dest = new BuildingUnitPartProperty();
		
		if (src.isSetBuildingUnitPart())
			dest.setBuildingUnitPart(unmarshalBuildingUnitPart(src.getBuildingUnitPart()));
		
		if (src.isSetRemoteSchema())
			dest.setRemoteSchema(src.getRemoteSchema());

		if (src.isSetType())
			dest.setType(XLinkType.fromValue(src.getType().value()));

		if (src.isSetHref())
			dest.setHref(src.getHref());

		if (src.isSetRole())
			dest.setRole(src.getRole());

		if (src.isSetArcrole())
			dest.setArcrole(src.getArcrole());

		if (src.isSetTitle())
			dest.setTitle(src.getTitle());

		if (src.isSetShow())
			dest.setShow(XLinkShow.fromValue(src.getShow().value()));

		if (src.isSetActuate())
			dest.setActuate(XLinkActuate.fromValue(src.getActuate().value()));
		
		return dest;
	}
	
	public EnergyPerformanceCertification unmarshalEnergyPerformanceCertification(EnergyPerformanceCertificationType src) throws MissingADESchemaException {
		EnergyPerformanceCertification dest = new EnergyPerformanceCertification();
		
		if (src.isSetCertificationName()) {
			for (String certificationName : src.getCertificationName())
				dest.addCertificationName(certificationName);
		}
		
		if (src.isSetCertificationid())
			dest.setCertificationId(src.getCertificationid());
		
		return dest;
	}
	
	public EnergyPerformanceCertificationProperty unmarshalEnergyPerformanceCertificationProperty(EnergyPerformanceCertificationPropertyType src) throws MissingADESchemaException {
		EnergyPerformanceCertificationProperty dest = new EnergyPerformanceCertificationProperty();
		
		if (src.isSetEnergyPerformanceCertification())
			dest.setEnergyPerformanceCertification(unmarshalEnergyPerformanceCertification(src.getEnergyPerformanceCertification()));
		
		return dest;
	}
	
	public IndustrialBuilding unmarshalIndustrialBuilding(IndustrialBuildingType src) throws MissingADESchemaException {
		IndustrialBuilding dest = new IndustrialBuilding();
		helper.getBuilding200Unmarshaller().unmarshalAbstractBuilding(src, dest);
		
		return dest;
	}
	
}
