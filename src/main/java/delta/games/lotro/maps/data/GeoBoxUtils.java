package delta.games.lotro.maps.data;

import java.util.Collection;

/**
 * Utility methods related to geographic boxes.
 * @author DAM
 */
public class GeoBoxUtils
{
  /**
   * Build a bounding box for a collection of points.
   * @param points Points to use.
   * @return A box (<code>null</code> if no points).
   */
  public static GeoBox buildBoundingBox(Collection<GeoPoint> points)
  {
    int nbPoints=points.size();
    if (nbPoints==0)
    {
      return null;
    }
    if (nbPoints==1)
    {
      GeoPoint p=points.iterator().next();
      return new GeoBox(p,p);
    }
    float minLat=100000;
    float maxLat=-100000;
    float minLon=100000;
    float maxLon=-100000;
    for(GeoPoint point : points)
    {
      float lat=point.getLatitude();
      if (lat>maxLat) maxLat=lat;
      if (lat<minLat) minLat=lat;
      float lon=point.getLongitude();
      if (lon>maxLon) maxLon=lon;
      if (lon<minLon) minLon=lon;
    }
    return new GeoBox(new GeoPoint(minLon,minLat),new GeoPoint(maxLon,maxLat));
  }
}
