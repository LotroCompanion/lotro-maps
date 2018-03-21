package delta.games.lotro.maps.data.io.xml;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.Labels;
import delta.games.lotro.maps.data.Map;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapLink;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.MarkersManager;

/**
 * Writes a map bundle to an XML file.
 * @author DAM
 */
public class MapXMLWriter
{
  /**
   * Write map markers to an XML file.
   * @param outFile Output file.
   * @param mapBundle Map bundle to use.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeMarkersFile(File outFile, final MapBundle mapBundle, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeMarkers(hd,mapBundle);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write map markers from a map bundle to the given XML stream.
   * @param hd XML output stream.
   * @param mapBundle Map bundle to use.
   * @throws Exception If an error occurs.
   */
  private void writeMarkers(TransformerHandler hd, MapBundle mapBundle) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    Map map=mapBundle.getMap();
    // Key
    String key=map.getKey();
    attrs.addAttribute("","",MapXMLConstants.MAP_KEY_ATTR,XmlWriter.CDATA,key);
    // Last update
    Date lastUpdate=map.getLastUpdate();
    if (lastUpdate!=null)
    {
      String dateStr=Long.toString(lastUpdate.getTime());
      attrs.addAttribute("","",MapXMLConstants.MAP_LAST_UPDATE_ATTR,XmlWriter.CDATA,dateStr);
    }
    hd.startElement("","",MapXMLConstants.MAP_TAG,attrs);

    // Geo reference
    GeoReference geoRef=map.getGeoReference();
    if (geoRef!=null)
    {
      AttributesImpl geoAttrs=new AttributesImpl();
      float factor=geoRef.getGeo2PixelFactor();
      geoAttrs.addAttribute("","",MapXMLConstants.GEO_FACTOR_ATTR,XmlWriter.CDATA,String.valueOf(factor));
      hd.startElement("","",MapXMLConstants.GEO_TAG,geoAttrs);
      GeoPoint start=geoRef.getStart();
      if (start!=null)
      {
        writeGeoPoint(hd,start);
      }
      hd.endElement("","",MapXMLConstants.GEO_TAG);
    }
    // Labels
    Labels labels=map.getLabels();
    MapXMLWriterUtils.write(hd,labels);
    // Markers
    MarkersManager markers=mapBundle.getData();
    write(hd,markers);
    hd.endElement("","",MapXMLConstants.MAP_TAG);
  }

  /**
   * Write map markers to an XML file.
   * @param outFile Output file.
   * @param mapBundle Map bundle to use.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeLinksFile(File outFile, final MapBundle mapBundle, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeLinks(hd,mapBundle.getMap());
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeLinks(TransformerHandler hd, Map map) throws Exception
  {
    hd.startElement("","",MapXMLConstants.LINKS_TAG,new AttributesImpl());
    // Links
    List<MapLink> links=map.getAllLinks();
    for(MapLink link : links)
    {
      AttributesImpl linkAttrs=new AttributesImpl();
      String target=link.getTargetMapKey();
      linkAttrs.addAttribute("","",MapXMLConstants.LINK_TARGET_ATTR,XmlWriter.CDATA,target);
      hd.startElement("","",MapXMLConstants.LINK_TAG,linkAttrs);
      GeoPoint hotPoint=link.getHotPoint();
      writeGeoPoint(hd,hotPoint);
      hd.endElement("","",MapXMLConstants.LINK_TAG);
    }
    hd.endElement("","",MapXMLConstants.LINKS_TAG);
  }

  /**
   * Write a point to the given XML stream.
   * @param hd XML output stream.
   * @param point Point to write.
   * @throws Exception
   */
  private void writeGeoPoint(TransformerHandler hd, GeoPoint point) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Longitude
    float longitude=point.getLongitude();
    attrs.addAttribute("","",MapXMLConstants.LONGITUDE_ATTR,XmlWriter.CDATA,String.valueOf(longitude));
    // Latitude
    float latitude=point.getLatitude();
    attrs.addAttribute("","",MapXMLConstants.LATITUDE_ATTR,XmlWriter.CDATA,String.valueOf(latitude));
    hd.startElement("","",MapXMLConstants.POINT_TAG,attrs);
    hd.endElement("","",MapXMLConstants.POINT_TAG);
  }

  /**
   * Write a markers structure to the given XML stream.
   * @param hd XML output stream.
   * @param markersManager Markers to write.
   * @throws Exception
   */
  private void write(TransformerHandler hd, MarkersManager markersManager) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",MapXMLConstants.MARKERS_TAG,attrs);
    List<Marker> markers=markersManager.getAllMarkers();
    for(Marker marker : markers)
    {
      AttributesImpl markerAttrs=new AttributesImpl();
      // Identifier
      int id=marker.getId();
      markerAttrs.addAttribute("","",MapXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Category
      int category=marker.getCategoryCode();
      markerAttrs.addAttribute("","",MapXMLConstants.CATEGORY_ATTR,XmlWriter.CDATA,String.valueOf(category));
      // Comments
      String comment=marker.getComment();
      if (comment!=null)
      {
        markerAttrs.addAttribute("","",MapXMLConstants.COMMENT_ATTR,XmlWriter.CDATA,comment);
      }
      hd.startElement("","",MapXMLConstants.MARKER_TAG,markerAttrs);
      // Position
      GeoPoint position=marker.getPosition();
      if (position!=null)
      {
        writeGeoPoint(hd,position);
      }
      // Labels
      Labels labels=marker.getLabels();
      MapXMLWriterUtils.write(hd,labels);
      hd.endElement("","",MapXMLConstants.MARKER_TAG);
    }
    hd.endElement("","",MapXMLConstants.MARKERS_TAG);
  }
}
