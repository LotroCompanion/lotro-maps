package delta.games.lotro.maps.ui.filter;

import delta.common.ui.swing.misc.Disposable;
import delta.common.ui.swing.tables.panel.FilterUpdateListener;
import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.data.markers.filters.MapMarkersFilter;
import delta.games.lotro.maps.ui.MapCanvas;
import delta.games.lotro.maps.ui.MapPanelController;

/**
 * Controller for filtering features on maps.
 * @author DAM
 */
public class MapFilteringController implements FilterUpdateListener,Disposable
{
  // Data
  private Filter<Marker> _filter;
  // Controllers
  private MapFilterPanelController _basicFilter;
  private PopupButtonController _filterButton;
  //TODO Add detailed filter
  private MapPanelController _mapPanel;

  /**
   * Constructor.
   * @param categoriesMgr Categories manager.
   * @param mapPanel Managed map panel.
   */
  public MapFilteringController(CategoriesManager categoriesMgr, MapPanelController mapPanel)
  {
    _mapPanel=mapPanel;
    _basicFilter=new MapFilterPanelController(categoriesMgr,this);
    buildFilter();
  }

  /**
   * Add the filter buttons to the map.
   */
  public void addFilterButtons()
  {
    _filterButton=new PopupButtonController(_basicFilter);
    _filterButton.addCloseButton(_basicFilter.getCategoryChooser().getCloseButton());
    _filterButton.getTriggerButton().setText("Filter...");
    _mapPanel.addButton(_filterButton.getTriggerButton());
  }

  private void buildFilter()
  {
    CompoundFilter<Marker> filter=new CompoundFilter<Marker>(Operator.AND);
    filter.addFilter(_basicFilter.getFilter());
    // TODO Add display selection filter
    _filter=filter;
  }

  /**
   * Get the managed "basic" filter for markers.
   * @return a filter.
   */
  public MapMarkersFilter getBasicFilter()
  {
    return _basicFilter.getFilter();
  }

  /**
   * Get the managed filter.
   * @return the managed filter.
   */
  public Filter<Marker> getFilter()
  {
    return _filter;
  }

  /**
   * Called when the managed filter was updated.
   */
  public void filterUpdated()
  {
    // Repaint the associated map
    MapCanvas canvas=_mapPanel.getCanvas();
    canvas.repaint();
  }

  @Override
  public void dispose()
  {
    // Data
    _filter=null;
    // Controllers
    if (_basicFilter!=null)
    {
      _basicFilter.dispose();
      _basicFilter=null;
    }
    if (_filterButton!=null)
    {
      _filterButton.dispose();
      _filterButton=null;
    }
    _mapPanel=null;
  }
}
