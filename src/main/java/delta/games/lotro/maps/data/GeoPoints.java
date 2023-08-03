package delta.games.lotro.maps.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Collection of geo points.
 * @author DAM
 */
public class GeoPoints
{
  private List<GeoPoint> _points;
  private GeoBox _box;

  /**
   * Constructor.
   */
  public GeoPoints()
  {
    _points=new ArrayList<GeoPoint>();
  }

  /**
   * Get the points count.
   * @return A points count.
   */
  public int getPointsCount()
  {
    return _points.size();
  }

  /**
   * Add a position.
   * @param point Point to add.
   */
  public void addPosition(GeoPoint point)
  {
    _points.add(point);
  }

  /**
   * Finish.
   */
  public void finish()
  {
    // Sort points
    Collections.sort(_points,new GeoPointComparator());
    // Bounding box
    _box=GeoBoxUtils.buildBoundingBox(_points);
  }

  @Override
  public String toString()
  {
    int nbPoints=getPointsCount();
    if (nbPoints==0)
    {
      return "";
    }
    if (nbPoints==1)
    {
      return _points.get(0).asString();
    }
    return _box.asString()+" ("+nbPoints+" points)";
  }
}
