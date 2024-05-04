package delta.games.lotro.maps.data.displaySelection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.games.lotro.maps.data.Marker;

/**
 * Display selection.
 * @author DAM
 */
public class DisplaySelection
{
  private static final Logger LOGGER=Logger.getLogger(DisplaySelection.class);

  private Map<Integer,DisplaySelectionItem> _items;

  /**
   * Constructor.
   */
  public DisplaySelection()
  {
    _items=new HashMap<Integer,DisplaySelectionItem>();
  }

  /**
   * Get the number of items.
   * @return An items count.
   */
  public int getItemsCount()
  {
    return _items.size();
  }

  /**
   * Get the managed items.
   * @return the managed items.
   */
  public List<DisplaySelectionItem> getItems()
  {
    List<DisplaySelectionItem> ret=new ArrayList<DisplaySelectionItem>();
    ret.addAll(_items.values());
    return ret;
  }

  /**
   * Set the markers.
   * @param markers Markers to set.
   */
  public void setMarkers(List<Marker> markers)
  {
    LOGGER.debug("Set markers!");
    // Keep state of current selection items (update data)
    // - sort markers
    Map<Integer,List<Marker>> sortedMarkers=sortMarkers(markers);
    Set<Integer> idsToRemove=new HashSet<Integer>(_items.keySet());
    for(Map.Entry<Integer,List<Marker>> entry : sortedMarkers.entrySet())
    {
      // Remove the current key for the IDs to remove
      Integer key=entry.getKey();
      idsToRemove.remove(key);
      List<Marker> markersForID=entry.getValue();
      DisplaySelectionItem selectionItem=_items.get(key);
      if (selectionItem==null)
      {
        // New ID
        selectionItem=new DisplaySelectionItem(markersForID);
        _items.put(key,selectionItem);
      }
      else
      {
        // Updated ID
        selectionItem.setMarkers(markersForID);
      }
    }
    // - remove no more used IDs
    for(Integer id : idsToRemove)
    {
      _items.remove(id);
    }
  }

  private Map<Integer,List<Marker>> sortMarkers(List<Marker> markers)
  {
    Map<Integer,List<Marker>> ret=new HashMap<Integer,List<Marker>>();
    for(Marker marker : markers)
    {
      Integer key=Integer.valueOf(marker.getDid());
      List<Marker> list=ret.get(key);
      if (list==null)
      {
        list=new ArrayList<Marker>();
        ret.put(key,list);
      }
      list.add(marker);
    }
    return ret;
  }

  /**
   * Add a marker.
   * @param marker Marker to add.
   */
  public void addMarker(Marker marker)
  {
    int did=marker.getDid();
    Integer key=Integer.valueOf(did);
    DisplaySelectionItem item=_items.get(key);
    if (item==null)
    {
      item=new DisplaySelectionItem(marker);
      _items.put(key,item);
    }
    else
    {
      item.addMarker(marker);
    }
  }

  /**
   * Indicates if a given element is visible or not.
   * @param did Data identifier.
   * @return <code>true</code> if visible, <code>false</code> otherwise.
   */
  public boolean isVisible(int did)
  {
    Integer key=Integer.valueOf(did);
    DisplaySelectionItem item=_items.get(key);
    if (item!=null)
    {
      return item.isVisible();
    }
    return false;
  }

  private Set<Integer> getVisibleItems()
  {
    HashSet<Integer> ret=new HashSet<Integer>();
    for(DisplaySelectionItem item : _items.values())
    {
      if (item.isVisible())
      {
        ret.add(Integer.valueOf(item.getIdentifier()));
      }
    }
    return ret;
  }

  @Override
  public String toString()
  {
    return "Visible: "+getVisibleItems().size();
  }
}
