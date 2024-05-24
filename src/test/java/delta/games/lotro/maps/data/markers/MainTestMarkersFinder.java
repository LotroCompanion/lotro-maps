package delta.games.lotro.maps.data.markers;

import java.io.File;
import java.util.List;

import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;

/**
 * Simple test class for the markers finder.
 * @author DAM
 */
public class MainTestMarkersFinder
{
  private void doIt(MapsManager mapsManager, int zoneId, int contentLayer)
  {
    MarkersFinder finder=mapsManager.getMarkersFinder();
    List<Marker> markers=finder.findMarkers(zoneId,contentLayer);
    System.out.println("Found "+markers.size()+" markers");
    for(Marker marker : markers)
    {
      System.out.println(marker);
    }
  }

  private void doIt()
  {
    File rootDir=new File("../lotro-maps-db");
    MapsManager mapsManager=new MapsManager(rootDir);
    // Stangard
    int zoneId=1879232928; 
    int contentLayer=0;
    doIt(mapsManager,zoneId,contentLayer);
    // The Twenty-first Hall
    zoneId=1879198510; // 
    contentLayer=268435638;
    doIt(mapsManager,zoneId,contentLayer);
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestMarkersFinder().doIt();
  }
}
