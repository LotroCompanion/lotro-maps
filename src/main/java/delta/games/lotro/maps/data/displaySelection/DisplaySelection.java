package delta.games.lotro.maps.data.displaySelection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.maps.data.Marker;

/**
 * Display selection.
 * @author DAM
 */
public class DisplaySelection
{
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
   * @return
   */
  public List<DisplaySelectionItem> getItems()
  {
    List<DisplaySelectionItem> ret=new ArrayList<DisplaySelectionItem>();
    ret.addAll(_items.values());
    // TODO Sort
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
}
