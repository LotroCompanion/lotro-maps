package delta.games.lotro.maps.data.markers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.collections.ThriftyIntSet;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.markers.index.MarkersIndex;
import delta.games.lotro.maps.data.markers.index.MarkersIndexesManager;

/**
 * Finder for markers.
 * @author DAM
 */
public class MarkersFinder
{
  private static final Logger LOGGER=LoggerFactory.getLogger(MarkersFinder.class);

  private GlobalMarkersManager _markersMgr;
  private MarkersIndexesManager _indexsMgr;

  /**
   * Constructor.
   * @param indexsMgr Indexs manager.
   * @param markersMgr Markers manager.
   */
  public MarkersFinder(MarkersIndexesManager indexsMgr, GlobalMarkersManager markersMgr)
  {
    _markersMgr=markersMgr;
    _indexsMgr=indexsMgr;
  }

  /**
   * Get all the markers in the given landblock, using an option content layer filter.
   * @param region Region.
   * @param blockX Landblock X.
   * @param blockY Landblock Y.
   * @param contentLayer Optional content layer filter.
   * @return A possibly empty, but never <code>null</code> list of markers.
   */
  public List<Marker> findMarkersForBlock(int region, int blockX, int blockY, Integer contentLayer)
  {
    List<Marker> ret=new ArrayList<Marker>();
    LandblockMarkersManager landblockMarkersManager=_markersMgr.getLandblockMarkersManager(region,blockX,blockY);
    if (landblockMarkersManager!=null)
    {
      List<Marker> landblockMarkers=landblockMarkersManager.getMarkers();
      if (contentLayer!=null)
      {
        MarkersIndex markersIndex=_indexsMgr.getContentLayerIndex(contentLayer.intValue());
        ThriftyIntSet markerIds=markersIndex.getMarkers();
        for(Marker marker : landblockMarkers)
        {
          if (markerIds.contains(marker.getId()))
          {
            ret.add(marker);
          }
        }
      }
      else
      {
        ret.addAll(landblockMarkers);
      }
    }
    return ret;
  }

  /**
   * Find the markers for the given zone and DID.
   * @param did Data identifier.
   * @param zoneId Zone identifier (area or dungeon).
   * @return A possibly empty but never <code>null</code> list of markers.
   */
  public List<Marker> findMarkersForDid(int did, int zoneId)
  {
    MarkersIndex zoneIndex=_indexsMgr.getDidIndex(zoneId);
    ThriftyIntSet zoneMarkers=zoneIndex.getMarkers();
    MarkersIndex didIndex=_indexsMgr.getDidIndex(did);
    ThriftyIntSet didMarkers=didIndex.getMarkers();
    ThriftyIntSet markersToGet=new ThriftyIntSet(zoneMarkers);
    markersToGet.retainAll(didMarkers);
    List<Marker> ret=getMarkers(markersToGet);
    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("Found {} markers for zoneID={} and DID={}",Integer.valueOf(ret.size()),Integer.valueOf(zoneId),Integer.valueOf(did));
      for(Marker marker : ret)
      {
        LOGGER.debug("{}",marker);
      }
    }
    return ret;
  }

  /**
   * Find the markers for the given zone and content layer.
   * @param zoneId Zone identifier (area or dungeon).
   * @param contentLayer Content layer (0 for world).
   * @return A possibly empty but never <code>null</code> list of markers.
   */
  public List<Marker> findMarkers(int zoneId, int contentLayer)
  {
    MarkersIndex zoneIndex=_indexsMgr.getDidIndex(zoneId);
    ThriftyIntSet zoneMarkers=zoneIndex.getMarkers();
    MarkersIndex contentLayerIndex=_indexsMgr.getContentLayerIndex(contentLayer);
    ThriftyIntSet contentLayerMarkers=contentLayerIndex.getMarkers();
    ThriftyIntSet markersToGet=new ThriftyIntSet(zoneMarkers);
    markersToGet.retainAll(contentLayerMarkers);
    List<Marker> ret=getMarkers(markersToGet);
    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("Found {} markers for zoneID={} and contentLayer={}",Integer.valueOf(ret.size()),Integer.valueOf(zoneId),Integer.valueOf(contentLayer));
      for(Marker marker : ret)
      {
        LOGGER.debug("{}",marker);
      }
    }
    return ret;
  }

  /**
   * Find the markers for the given content layer.
   * @param contentLayer Content layer (0 for world).
   * @return A possibly empty but never <code>null</code> list of markers.
   */
  public List<Marker> findMarkersForContentLayer(int contentLayer)
  {
    MarkersIndex contentLayerIndex=_indexsMgr.getContentLayerIndex(contentLayer);
    ThriftyIntSet contentLayerMarkers=contentLayerIndex.getMarkers();
    List<Marker> ret=getMarkers(contentLayerMarkers);
    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("Found {} markers for contentLayer={}",Integer.valueOf(ret.size()),Integer.valueOf(contentLayer));
      for(Marker marker : ret)
      {
        LOGGER.debug("{}",marker);
      }
    }
    return ret;

  }

  private List<Marker> getMarkers(ThriftyIntSet markerIds)
  {
    List<Marker> ret=new ArrayList<Marker>();
    for(int markerId : markerIds.getValues())
    {
      Marker marker=_markersMgr.getMarkerById(markerId);
      if (marker!=null)
      {
        ret.add(marker);
      }
    }
    return ret;
  }
}
