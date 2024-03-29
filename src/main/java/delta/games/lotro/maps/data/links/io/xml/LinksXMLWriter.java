package delta.games.lotro.maps.data.links.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.links.MapLink;
import delta.games.lotro.maps.data.links.MapLinkType;
import delta.games.lotro.maps.data.markers.io.xml.MarkersXMLWriter;

/**
 * Writes links to an XML file.
 * @author DAM
 */
public class LinksXMLWriter
{
  /**
   * Write links to an XML file.
   * @param outFile Output file.
   * @param links Links to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeLinksFile(File outFile, final List<MapLink> links)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeLinks(hd,links);
      }
    };
    boolean ret=helper.write(outFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeLinks(TransformerHandler hd, List<MapLink> links) throws SAXException
  {
    hd.startElement("","",LinksXMLConstants.LINKS_TAG,new AttributesImpl());
    // Links
    for(MapLink link : links)
    {
      AttributesImpl linkAttrs=new AttributesImpl();
      // Parent ID
      int parentId=link.getParentId();
      linkAttrs.addAttribute("","",LinksXMLConstants.LINK_PARENT_ID_ATTR,XmlWriter.CDATA,String.valueOf(parentId));
      // Content layer ID
      int contentLayerId=link.getContentLayerId();
      linkAttrs.addAttribute("","",LinksXMLConstants.LINK_CONTENT_LAYER_ATTR,XmlWriter.CDATA,String.valueOf(contentLayerId));
      // Target
      int target=link.getTargetMapKey();
      linkAttrs.addAttribute("","",LinksXMLConstants.LINK_TARGET_ATTR,XmlWriter.CDATA,String.valueOf(target));
      // Label
      String label=link.getLabel();
      if ((label!=null) && (label.length()>0))
      {
        linkAttrs.addAttribute("","",LinksXMLConstants.LINK_LABEL_ATTR,XmlWriter.CDATA,label);
      }
      // From
      GeoPoint fromPoint=link.getPosition();
      MarkersXMLWriter.writeGeoPointAttrs(LinksXMLConstants.LINK_FROM_ATTR,fromPoint,linkAttrs);
      // To
      GeoPoint targetPoint=link.getTargetPoint();
      if (targetPoint!=null)
      {
        MarkersXMLWriter.writeGeoPointAttrs(LinksXMLConstants.LINK_TO_ATTR,targetPoint,linkAttrs);
      }
      // Type
      MapLinkType type=link.getType();
      if (type!=MapLinkType.TO_PARCHMENT_MAP)
      {
        linkAttrs.addAttribute("","",LinksXMLConstants.LINK_TYPE_ATTR,XmlWriter.CDATA,type.name());
      }
      hd.startElement("","",LinksXMLConstants.LINK_TAG,linkAttrs);
      hd.endElement("","",LinksXMLConstants.LINK_TAG);
    }
    hd.endElement("","",LinksXMLConstants.LINKS_TAG);
  }
}
