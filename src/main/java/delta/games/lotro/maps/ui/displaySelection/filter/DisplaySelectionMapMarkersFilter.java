package delta.games.lotro.maps.ui.displaySelection.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.displaySelection.DisplaySelectionItem;

/**
 * A filter for display selection items that uses a filter on markers.
 * @author DAM
 */
public class DisplaySelectionMapMarkersFilter implements Filter<DisplaySelectionItem>
{
  private Filter<Marker> _filter;

  /**
   * Constructor.
   * @param filter Filter on markers.
   */
  public DisplaySelectionMapMarkersFilter(Filter<Marker> filter)
  {
    _filter=filter;
  }

  @Override
  public boolean accept(DisplaySelectionItem item)
  {
    Marker marker=item.getReferenceMarker();
    return _filter.accept(marker);
  }
}
