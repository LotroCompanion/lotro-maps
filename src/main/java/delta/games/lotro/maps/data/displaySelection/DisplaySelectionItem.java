package delta.games.lotro.maps.data.displaySelection;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.id.Identifiable;
import delta.games.lotro.maps.data.GeoPoints;
import delta.games.lotro.maps.data.Marker;

/**
 * Element of the display selection UI.
 * @author DAM
 */
public class DisplaySelectionItem implements Identifiable
{
  private Marker _reference;
  private List<Marker> _markers;
  private GeoPoints _positions;
  private boolean _visible;

  /**
   * Constructor.
   * @param marker Template marker.
   */
  public DisplaySelectionItem(Marker marker)
  {
    _reference=marker;
    _markers=new ArrayList<Marker>();
    _positions=null;
    addMarker(marker);
    _visible=true;
  }

  @Override
  public int getIdentifier()
  {
    return getDID();
  }

  /**
   * Add a marker.
   * @param marker Marker to add.
   */
  public void addMarker(Marker marker)
  {
    _markers.add(marker);
  }

  /**
   * Get the DID of the managed markers.
   * @return A DID.
   */
  public int getDID()
  {
    return _reference.getDid();
  }

  /**
   * Get the category code of the managed markers.
   * @return A category code.
   */
  public int getCategoryCode()
  {
    return _reference.getCategoryCode();
  }

  /**
   * Get the label of the managed markers.
   * @return A label.
   */
  public String getLabel()
  {
    return _reference.getLabel();
  }

  /**
   * Get the markers count.
   * @return A markers count.
   */
  public int getMarkersCount()
  {
    return _markers.size();
  }

  /**
   * Get the positions. 
   * @return the positions.
   */
  public GeoPoints getPositions()
  {
    if (_positions==null)
    {
      buildPositions();
    }
    return _positions;
  }

  private void buildPositions()
  {
    GeoPoints positions=new GeoPoints();
    for(Marker marker : _markers)
    {
      positions.addPosition(marker.getPosition());
    }
    positions.finish();
    _positions=positions;
  }

  /**
   * Indicates if the managed markers are visible or not.
   * @return <code>true</code> if visible, <code>false</code> otherwise.
   */
  public boolean isVisible()
  {
    return _visible;
  }

  /**
   * Set the visibility.
   * @param visible <code>true</code> if visible, <code>false</code> otherwise.
   */
  public void setVisible(boolean visible)
  {
    _visible=visible;
  }
}
