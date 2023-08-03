package delta.games.lotro.maps.data.displaySelection;

import java.util.Collection;

import delta.games.lotro.maps.data.Marker;

/**
 * Builder for display selection.
 * @author DAM
 */
public class DisplaySelectionBuilder
{
  /**
   * Build a display selection from a collection of markers.
   * @param markers Markers to use.
   * @return A display selection.
   */
  public static DisplaySelection build(Collection<Marker> markers)
  {
    DisplaySelection ret=new DisplaySelection();
    for(Marker marker : markers)
    {
      ret.addMarker(marker);
    }
    return ret;
  }
}
