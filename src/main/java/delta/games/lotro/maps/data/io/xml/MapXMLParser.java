package delta.games.lotro.maps.data.io.xml;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.maps.data.CategoriesManager;
import delta.games.lotro.maps.data.Category;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.Labels;
import delta.games.lotro.maps.data.Map;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.MarkersManager;

/**
 * Parser for a map stored in XML.
 * @author DAM
 */
public class MapXMLParser
{
  private CategoriesManager _categories;

  /**
   * Constructor.
   * @param categories Categories manager.
   */
  public MapXMLParser(CategoriesManager categories)
  {
    _categories=categories;
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed categories or <code>null</code>.
   */
  public MapBundle parseXML(File source)
  {
    MapBundle registry=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      registry=parseMap(root);
    }
    return registry;
  }

  private MapBundle parseMap(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    String key=DOMParsingTools.getStringAttribute(attrs,MapXMLConstants.MAP_KEY_ATTR,null);
    MapBundle bundle=new MapBundle(key);

    Map map=bundle.getMap();
    long lastUpdateTime=DOMParsingTools.getLongAttribute(attrs,MapXMLConstants.MAP_LAST_UPDATE_ATTR,0);
    if (lastUpdateTime!=0)
    {
      map.setLastUpdate(new Date(lastUpdateTime));
    }
    // Geographic reference
    Element geoTag=DOMParsingTools.getChildTagByName(root,MapXMLConstants.GEO_TAG);
    if (geoTag!=null)
    {
      GeoReference geoReference=parseGeoReference(geoTag);
      map.setGeoReference(geoReference);
    }
    // Labels
    Element labelsTag=DOMParsingTools.getChildTagByName(root,MapXMLConstants.LABELS_TAG);
    if (labelsTag!=null)
    {
      parseLabels(labelsTag,map.getLabels());
    }

    // Markers
    MarkersManager markersManager=bundle.getData();
    List<Element> markerTags=DOMParsingTools.getChildTagsByName(root,MapXMLConstants.MARKER_TAG,true);
    for(Element markerTag : markerTags)
    {
      Marker marker=parseMarker(markerTag);
      if (marker!=null)
      {
        markersManager.addMarker(marker);
      }
    }
    return bundle;
  }

  /**
   * Build a geographic reference from an XML tag.
   * @param geoTag Geographic reference tag.
   * @return A geographic reference.
   */
  public GeoReference parseGeoReference(Element geoTag)
  {
    GeoReference ret=null;
    NamedNodeMap attrs=geoTag.getAttributes();

    // Factor
    float factor=DOMParsingTools.getFloatAttribute(attrs,MapXMLConstants.GEO_FACTOR_ATTR,0);
    // Start point
    Element startTag=DOMParsingTools.getChildTagByName(geoTag,MapXMLConstants.POINT_TAG);
    if (startTag!=null)
    {
      GeoPoint start=parsePoint(startTag);
      ret=new GeoReference(start,factor);
    }
    return ret;
  }

  /**
   * Build a marker from an XML tag.
   * @param markerTag Marker tag.
   * @return A marker.
   */
  public Marker parseMarker(Element markerTag)
  {
    Marker marker=new Marker();
    NamedNodeMap attrs=markerTag.getAttributes();
    // Category code
    int categoryCode=DOMParsingTools.getIntAttribute(attrs,MapXMLConstants.CATEGORY_ATTR,0);
    Category category=_categories.getByCode(categoryCode);
    marker.setCategory(category);
    // Comment
    String comment=DOMParsingTools.getStringAttribute(attrs,MapXMLConstants.COMMENT_ATTR,null);
    marker.setComment(comment);
    // Position
    // Start point
    Element positionTag=DOMParsingTools.getChildTagByName(markerTag,MapXMLConstants.POINT_TAG);
    if (positionTag!=null)
    {
      GeoPoint position=parsePoint(positionTag);
      marker.setPosition(position);
    }
    // Labels
    Element labelsTag=DOMParsingTools.getChildTagByName(markerTag,MapXMLConstants.LABELS_TAG);
    if (labelsTag!=null)
    {
      parseLabels(labelsTag,marker.getLabels());
    }
    return marker;
  }

  /**
   * Build a point from an XML tag.
   * @param pointTag Point tag.
   * @return A point.
   */
  public GeoPoint parsePoint(Element pointTag)
  {
    NamedNodeMap attrs=pointTag.getAttributes();
    // Latitude
    float latitude=DOMParsingTools.getFloatAttribute(attrs,MapXMLConstants.LATITUDE_ATTR,0);
    // Longitude
    float longitude=DOMParsingTools.getFloatAttribute(attrs,MapXMLConstants.LONGITUDE_ATTR,0);
    return new GeoPoint(longitude,latitude);
  }

  /**
   * Parse labels.
   * @param labelsTag Labels tag.
   * @param labels Labels storage.
   */
  public static void parseLabels(Element labelsTag, Labels labels)
  {
    List<Element> labelTags=DOMParsingTools.getChildTagsByName(labelsTag,MapXMLConstants.LABEL_TAG,false);
    for(Element labelTag : labelTags)
    {
      NamedNodeMap attrs=labelTag.getAttributes();
      String locale=DOMParsingTools.getStringAttribute(attrs,MapXMLConstants.LABEL_LOCALE_ATTR,null);
      String value=DOMParsingTools.getStringAttribute(attrs,MapXMLConstants.LABEL_VALUE_ATTR,null);
      labels.putLabel(locale,value);
    }
  }
}