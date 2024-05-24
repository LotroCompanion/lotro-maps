package delta.games.lotro.maps.ui.displaySelection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.data.displaySelection.DisplaySelection;
import delta.games.lotro.maps.data.markers.MarkersFinder;
import delta.games.lotro.maps.data.markers.filters.MapMarkersFilter;

/**
 * Test for the display selection UI.
 * @author DAM
 */
public class MainTestDisplaySelectionUI
{
  private void doIt()
  {
    File rootDir=new File("../lotro-maps-db");
    MapsManager mapsManager=new MapsManager(rootDir);
    MarkersFinder finder=mapsManager.getMarkersFinder();

    int[] ids=new int[] {
        1879048295, // Thorin's Hall
        1879418771, // Howlers' Notch
        1879063921, // Vale of Thrain
        1879063922, // Low Lands
        1879063923, // Haudh Lin
        1879063924, // Falathlorn
        1879063925, // Sarnúr
        1879063926, // Rath Teraig
        1879063928, // Falathlorn
        1879063929, // Celondim
        1879063930, // Limael's Vineyard
        1879063931, // Kheledûl
        1879206150, // Low Lands
    };
    List<Marker> markers=new ArrayList<Marker>();
    for(int id : ids)
    {
      markers.addAll(finder.findMarkers(id,0));
    }

    CategoriesManager categoriesMgr=mapsManager.getCategories();
    DisplaySelection[] ds=new DisplaySelection[1];
    DisplaySelectionUpdateListener listener=new DisplaySelectionUpdateListener()
    {
      @Override
      public void displaySelectionUpdated()
      {
        System.out.println("Selection changed: "+ds[0].toString());
      }
    };
    MapMarkersFilter basicFilter=new MapMarkersFilter();
    DisplaySelectionWindowController ctrl=new DisplaySelectionWindowController(null,categoriesMgr,basicFilter,listener);
    ds[0]=ctrl.getDisplaySelection();
    ctrl.setMarkers(markers);
    ctrl.show();
  }

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    new MainTestDisplaySelectionUI().doIt();
  }
}
