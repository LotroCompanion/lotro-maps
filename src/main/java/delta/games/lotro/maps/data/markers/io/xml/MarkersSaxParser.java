package delta.games.lotro.maps.data.markers.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import delta.common.utils.NumericTools;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.Marker;

/**
 * SAX parser for markers files.
 * @author DAM
 */
public final class MarkersSaxParser extends DefaultHandler
{
  private static final Logger LOGGER=Logger.getLogger(MarkersSaxParser.class);

  private List<Marker> _parsedMarkers;

  private MarkersSaxParser()
  {
    _parsedMarkers=new ArrayList<Marker>();
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return List of parsed items.
   */
  public static List<Marker> parseMarkersFile(File source)
  {
    try
    {
      MarkersSaxParser handler=new MarkersSaxParser();

      // Use the default (non-validating) parser
      SAXParserFactory factory=SAXParserFactory.newInstance();
      SAXParser saxParser=factory.newSAXParser();
      saxParser.parse(source,handler);
      saxParser.reset();
      return handler._parsedMarkers;
    }
    catch (Exception e)
    {
      LOGGER.error("Error when loading markers file "+source,e);
    }
    return null;
  }

  @Override
  public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException
  {
    if (MarkersXMLConstants.MARKER_TAG.equals(qualifiedName))
    {
      Marker marker=new Marker();
      // Identifier
      String idStr=attributes.getValue(MarkersXMLConstants.ID_ATTR);
      int id=NumericTools.parseInt(idStr,0);
      marker.setId(id);
      // Label
      String label=attributes.getValue(MarkersXMLConstants.LABEL_ATTR);
      marker.setLabel(label);
      // Category code
      String categoryCodeStr=attributes.getValue(MarkersXMLConstants.CATEGORY_ATTR);
      int categoryCode=NumericTools.parseInt(categoryCodeStr,0);
      marker.setCategoryCode(categoryCode);
      // DID
      String didStr=attributes.getValue(MarkersXMLConstants.DID_ATTR);
      int did=NumericTools.parseInt(didStr,0);
      marker.setDid(did);
      // Parent zone ID
      String parentZoneIdStr=attributes.getValue(MarkersXMLConstants.PARENT_ZONE_ID_ATTR);
      int parentZoneId=NumericTools.parseInt(parentZoneIdStr,0);
      marker.setParentZoneId(parentZoneId);
      // Position
      float latitude=NumericTools.parseFloat(attributes.getValue(MarkersXMLConstants.LATITUDE_ATTR),0);
      float longitude=NumericTools.parseFloat(attributes.getValue(MarkersXMLConstants.LONGITUDE_ATTR),0);
      GeoPoint position=new GeoPoint(longitude,latitude);
      marker.setPosition(position);
      _parsedMarkers.add(marker);
    }
  }
}
