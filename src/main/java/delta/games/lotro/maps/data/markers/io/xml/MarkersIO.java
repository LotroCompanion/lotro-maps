package delta.games.lotro.maps.data.markers.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.games.lotro.maps.data.Marker;

/**
 * Map markers I/O.
 * @author DAM
 */
public class MarkersIO
{
  private static final Logger LOGGER=Logger.getLogger(MarkersIO.class);

  /**
   * Load map markers from the given file.
   * @param markersFile Markers file.
   * @param labelsMgr Labels manager.
   * @return A markers manager.
   */
  public static List<Marker> loadMarkers(File markersFile, SingleLocaleLabelsManager labelsMgr)
  {
    List<Marker> markers=null;
    if (markersFile.exists())
    {
      long now1=System.currentTimeMillis();
      markers=new MarkersSaxParser(labelsMgr).parseMarkersFile(markersFile);
      long now2=System.currentTimeMillis();
      LOGGER.info("Loaded "+markers.size()+" markers from file "+markersFile.getName()+" in "+(now2-now1)+"ms");
    }
    else
    {
      markers=new ArrayList<Marker>();
    }
    return markers;
  }
}
