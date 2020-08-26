package delta.games.lotro.maps.ui.layers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import delta.common.ui.swing.draw.HaloPainter;
import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.MarkersManager;
import delta.games.lotro.maps.ui.MapView;
import delta.games.lotro.maps.ui.MarkerIconProvider;

/**
 * Layer for markers.
 * @author DAM
 */
public class MarkersLayer implements Layer
{
  private MapView _view;
  private Filter<Marker> _filter;
  private boolean _useLabels;
  private int _priority;
  private MarkerIconProvider _iconProvider;
  private MarkersProvider _markersProvider;

  /**
   * Constructor.
   * @param view Map view.
   * @param iconProvider Icon provider.
   * @param markersProvider Markers provider.
   */
  public MarkersLayer(MapView view, MarkerIconProvider iconProvider, MarkersProvider markersProvider)
  {
    _view=view;
    _filter=null;
    _useLabels=false;
    _priority=50;
    _iconProvider=iconProvider;
    _markersProvider=markersProvider;
  }

  @Override
  public int getPriority()
  {
    return _priority;
  }

  /**
   * Set a filter.
   * @param filter Filter to set or <code>null</code> to remove it.
   */
  public void setFilter(Filter<Marker> filter)
  {
    _filter=filter;
  }

  /**
   * Set the flag to use labels or not.
   * @param useLabels <code>true</code> to display labels, <code>false</code> to hide them.
   */
  public void useLabels(boolean useLabels)
  {
    _useLabels=useLabels;
  }

  @Override
  public List<Marker> getVisibleMarkers()
  {
    List<Marker> markers=getMarkers();
    return MarkersManager.getFilteredMarkers(_filter,markers);
  }

  private List<Marker> getMarkers()
  {
    List<Marker> markers=null;
    if (_markersProvider!=null)
    {
      markers=_markersProvider.getMarkers();
    }
    return markers;
  }

  /**
   * Paint the markers.
   * @param g Graphics.
   */
  @Override
  public void paintLayer(Graphics g)
  {
    List<Marker> markers=getMarkers();
    paintMarkers(markers,g);
  }

  private void paintMarkers(List<Marker> markers, Graphics g)
  {
    if ((markers==null) || (markers.size()==0))
    {
      return;
    }
    for(Marker marker : markers)
    {
      boolean ok=((_filter==null)||(_filter.accept(marker)));
      if (ok)
      {
        paintMarker(marker,g);
      }
    }
  }

  private void paintMarker(Marker marker, Graphics g)
  {
    GeoReference viewReference=_view.getViewReference();
    Dimension pixelPosition=viewReference.geo2pixel(marker.getPosition());

    // Grab icon
    BufferedImage image=null;
    if (_iconProvider!=null)
    {
      image=_iconProvider.getImage(marker);
    }
    int x=pixelPosition.width;
    int y=pixelPosition.height;
    if (image!=null)
    {
      int width=image.getWidth();
      int height=image.getHeight();
      g.drawImage(image,x-width/2,y-height/2,null);
    }
    // Label
    if (_useLabels)
    {
      String label=marker.getLabel();
      if ((label!=null) && (label.length()>0))
      {
        HaloPainter.drawStringWithHalo(g,x+10,y,label,Color.WHITE,Color.BLACK);
      }
    }
  }
}
