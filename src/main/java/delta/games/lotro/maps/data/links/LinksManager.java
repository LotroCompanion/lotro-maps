package delta.games.lotro.maps.data.links;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.maps.data.links.io.xml.LinksXMLParser;
import delta.games.lotro.maps.data.links.io.xml.LinksXMLWriter;

/**
 * Global links manager.
 * @author DAM
 */
public class LinksManager
{
  private File _linksFile;
  private List<MapLink> _links;

  /**
   * Constructor.
   * @param linksFile Links file.
   */
  public LinksManager(File linksFile)
  {
    _linksFile=linksFile;
    _links=new ArrayList<MapLink>();
    load();
  }

  /**
   * Add a new link.
   * @param link Link to add.
   */
  public void addLink(MapLink link)
  {
    _links.add(link);
  }

  /**
   * Get a list of all managed links.
   * @return a list of links.
   */
  public List<MapLink> getAll()
  {
    return _links;
  }

  /**
   * Get the links for the given parent ID and content layer ID.
   * @param parentId Parent ID.
   * @param contentLayerId Content layer ID.
   * @return A possibly empty but never <code>null</code> list of links.
   */
  public List<MapLink> getLinks(int parentId, int contentLayerId)
  {
    List<MapLink> ret=new ArrayList<MapLink>();
    for(MapLink link : _links)
    {
      if ((link.getParentId()==parentId) && (link.getContentLayerId()==contentLayerId))
      {
        ret.add(link);
      }
    }
    return ret;
  }

  private void load()
  {
    _links.clear();
    if (_linksFile.exists())
    {
      _links.addAll(LinksXMLParser.loadLinks(_linksFile));
    }
  }

  /**
   * Write the links file.
   */
  public void write()
  {
    LinksXMLWriter.writeLinksFile(_linksFile,_links);
  }
}
