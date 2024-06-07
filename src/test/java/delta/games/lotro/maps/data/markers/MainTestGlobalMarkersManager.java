package delta.games.lotro.maps.data.markers;

import java.io.File;

import delta.common.utils.i18n.SingleLocaleLabelsManager;

/**
 * Simple test class for the markers manager.
 * @author DAM
 */
public class MainTestGlobalMarkersManager
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    File rootDir=new File("../lotro-maps-db/markers");
    SingleLocaleLabelsManager labelsMgr=new SingleLocaleLabelsManager("en");
    GlobalMarkersManager markersMgr=new GlobalMarkersManager(rootDir,labelsMgr);
    long now=System.currentTimeMillis();
    /*Marker marker=*/markersMgr.getMarkerById(0);
    long now2=System.currentTimeMillis();
    System.out.println("Time to load markers: "+(now2-now)+"ms");
  }
}
