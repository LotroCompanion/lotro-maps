package delta.games.lotro.maps.data.markers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.markers.index.MarkersIndex;
import delta.games.lotro.maps.data.markers.index.MarkersIndexesManager;

/**
 * Finder for markers.
 * @author DAM
 */
public class MarkersFinder
{
  private static MarkersFinder _instance=new MarkersFinder();

  private GlobalMarkersManager _markersMgr;
  private MarkersIndexesManager _indexsMgr;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static MarkersFinder getInstance()
  {
    return _instance;
  }

  /**
   * Constructor.
   */
  public MarkersFinder()
  {
    File rootDir=new File("../lotro-maps-db");
    File markersDir=new File(rootDir,"markers");
    _markersMgr=new GlobalMarkersManager(markersDir);
    _indexsMgr=new MarkersIndexesManager(rootDir);
  }

  /**
   * Find the markers for the given zone and content layer.
   * @param zoneId Zone identifier (area or dungeon).
   * @param contentLayer Content layer (0 for world).
   * @return A possibly empty but never <code>null</code> list of markers.
   */
  public List<Marker> findMarkers(int zoneId, int contentLayer)
  {
    MarkersIndex zoneIndex=_indexsMgr.getDidIndex(zoneId,true);
    Set<Integer> zoneMarkers=zoneIndex.getMarkers();
    MarkersIndex contentLayerIndex=_indexsMgr.getContentLayerIndex(contentLayer,true);
    Set<Integer> contentLayerMarkers=contentLayerIndex.getMarkers();
    Set<Integer> markersToGet=new TreeSet<Integer>(zoneMarkers);
    markersToGet.retainAll(contentLayerMarkers);
    return getMarkers(markersToGet);
  }

  private List<Marker> getMarkers(Set<Integer> markerIds)
  {
    List<Marker> ret=new ArrayList<Marker>();
    for(Integer markerId : markerIds)
    {
      Marker marker=_markersMgr.getMarkerById(markerId.intValue());
      if (marker!=null)
      {
        ret.add(marker);
      }
    }
    return ret;
  }
}