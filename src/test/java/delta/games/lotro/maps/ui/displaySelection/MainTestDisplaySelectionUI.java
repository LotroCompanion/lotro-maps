package delta.games.lotro.maps.ui.displaySelection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import delta.common.ui.swing.tables.panel.FilterUpdateListener;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.data.displaySelection.DisplaySelection;
import delta.games.lotro.maps.data.displaySelection.DisplaySelectionBuilder;
import delta.games.lotro.maps.data.markers.MarkersFinder;
import delta.games.lotro.maps.data.markers.filters.DisplaySelectionFilter;

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
    //int dungeonID=1879048295; // Thorin' Hall
    //int dungeonID=1879418771; 
    //List<Marker> markers=finder.findMarkers(dungeonID,0);

    int[] ids=new int[] {
        1879063921, // Vale of Thrain
        1879063922, // Low Lands
        1879063923, // Haudh Lin
        1879063924, // Falathlorn
        1879063925, // Sarn�r
        1879063926, // Rath Teraig
        1879063928, // Falathlorn
        1879063929, // Celondim
        1879063930, // Limael's Vineyard
        1879063931, // Kheled�l
        1879206150, // Low Lands
    };
    List<Marker> markers=new ArrayList<Marker>();
    for(int id : ids)
    {
      markers.addAll(finder.findMarkers(id,0));
    }

    DisplaySelection selection=DisplaySelectionBuilder.build(markers);
    CategoriesManager categoriesMgr=mapsManager.getCategories();
    DisplaySelectionFilter filter=new DisplaySelectionFilter(selection);
    FilterUpdateListener listener=new FilterUpdateListener()
    {
      @Override
      public void filterUpdated()
      {
        System.out.println(selection.toString());
      }
    };
    DisplaySelectionWindowController ctrl=new DisplaySelectionWindowController(null,categoriesMgr,filter,listener,selection);
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
