//
// Generated with ade-xjc - XML Schema binding compiler for CityGML ADEs, version 2.4.3+1
// ade-xjc is part of the citygml4j project, see https://github.com/citygml4j
// Any modifications to this file will be lost upon recompilation of the source
// Generated: Wed May 31 12:15:07 CEST 2017
//


package org.citygml.ade.testade._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import net.opengis.citygml._2.AbstractSiteType;
import net.opengis.citygml.building._2.BoundarySurfacePropertyType;


/**
 * <p>Java-Klasse für OtherConstructionType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="OtherConstructionType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/citygml/2.0}AbstractSiteType">
 *       &lt;sequence>
 *         &lt;element name="boundedBy" type="{http://www.opengis.net/citygml/building/2.0}BoundarySurfacePropertyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OtherConstructionType", propOrder = {
    "rest"
})
public class OtherConstructionType
    extends AbstractSiteType
{

    @XmlElementRef(name = "boundedBy", namespace = "http://www.citygml.org/ade/TestADE/1.0", type = JAXBElement.class, required = false)
    protected List<JAXBElement<BoundarySurfacePropertyType>> rest;

    /**
     * Ruft das restliche Contentmodell ab. 
     * 
     * <p>
     * Sie rufen diese "catch-all"-Eigenschaft aus folgendem Grund ab: 
     * Der Feldname "BoundedBy" wird von zwei verschiedenen Teilen eines Schemas verwendet. Siehe: 
     * Zeile 147 von file:///C:/Eclipse_CityGML/3DCityDB_Workspace/ade-jaxb-binding-creator/sample/CityGML-TestADE.xsd
     * Zeile 28 von file:/C:/Eclipse_CityGML/3DCityDB_Workspace/ade-jaxb-binding-creator/schemas/GML/3.1.1/base/feature.xsd
     * <p>
     * Um diese Eigenschaft zu entfernen, wenden Sie eine Eigenschaftenanpassung für eine
     * der beiden folgenden Deklarationen an, um deren Namen zu ändern: 
     * Gets the value of the rest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link BoundarySurfacePropertyType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<BoundarySurfacePropertyType>> getRest() {
        if (rest == null) {
            rest = new ArrayList<JAXBElement<BoundarySurfacePropertyType>>();
        }
        return this.rest;
    }

}
