package delta.games.lotro.maps.ui.displaySelection;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.tables.panel.FilterUpdateListener;
import delta.common.ui.swing.windows.DefaultWindowController;
import delta.common.ui.swing.windows.WindowController;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.data.displaySelection.DisplaySelection;
import delta.games.lotro.maps.data.markers.filters.DisplaySelectionFilter;
import delta.games.lotro.maps.ui.displaySelection.filter.DisplaySelectionFilterController;
import delta.games.lotro.maps.ui.displaySelection.filter.DisplaySelectionItemsFilter;
import delta.games.lotro.maps.ui.displaySelection.table.DisplaySelectionPanelController;
import delta.games.lotro.maps.ui.displaySelection.table.DisplaySelectionTableController;
import delta.games.lotro.maps.ui.displaySelection.visibility.DisplaySelectionVisibilityEditionPanelController;

/**
 * Controller for the display selection window.
 * @author DAM
 */
public class DisplaySelectionWindowController extends DefaultWindowController
{
  /**
   * Identifier for this window.
   */
  public static final String IDENTIFIER="DISPLAY_SELECTION";

  // Data
  private DisplaySelectionItemsFilter _filter;
  // Controllers
  private DisplaySelectionFilterController _filterController;
  private DisplaySelectionPanelController _panelController;
  private DisplaySelectionTableController _tableController;
  private DisplaySelectionVisibilityEditionPanelController _visibilityController;

  /**
   * Constructor.
   * @param parent Parent window.
   * @param categoriesMgr Categories manager.
   * @param filter Display selection filter.
   * @param visibilityListener Visibility update listener.
   * @param displaySelection Display selection.
   */
  public DisplaySelectionWindowController(WindowController parent, CategoriesManager categoriesMgr,
      DisplaySelectionFilter filter, FilterUpdateListener visibilityListener, 
      DisplaySelection displaySelection)
  {
    super(parent);
    _filter=new DisplaySelectionItemsFilter();
    _tableController=new DisplaySelectionTableController(displaySelection,categoriesMgr,_filter,filter,visibilityListener);
    _panelController=new DisplaySelectionPanelController(this,_tableController);
    _filterController=new DisplaySelectionFilterController(categoriesMgr,_filter,_panelController);
    _visibilityController=new DisplaySelectionVisibilityEditionPanelController(_tableController,displaySelection,_filter,visibilityListener);
  }

  @Override
  protected JFrame build()
  {
    JFrame frame=super.build();
    frame.setTitle("Display Selection"); // I18n
    frame.setMinimumSize(new Dimension(400,300));
    frame.setSize(950,700);
    return frame;
  }

  @Override
  public void configureWindow()
  {
    automaticLocationSetup();
  }

  @Override
  public String getWindowIdentifier()
  {
    return IDENTIFIER;
  }

  @Override
  protected JPanel buildContents()
  {
    JPanel panel=GuiFactory.buildPanel(new GridBagLayout());
    JPanel tablePanel=_panelController.getPanel();
    JPanel filterPanel=_filterController.getPanel();
    TitledBorder filterBorder=GuiFactory.buildTitledBorder("Filter"); // I18n
    filterPanel.setBorder(filterBorder);
    JPanel visibilityPanel=_visibilityController.getPanel();
    TitledBorder visibilityBorder=GuiFactory.buildTitledBorder("Visibility"); // I18n
    visibilityPanel.setBorder(visibilityBorder);
    // Whole panel
    GridBagConstraints c=new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
    panel.add(filterPanel,c);
    c=new GridBagConstraints(1,0,1,1,0,0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
    panel.add(visibilityPanel,c);
    c=new GridBagConstraints(2,0,1,1,1,0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0);
    panel.add(GuiFactory.buildPanel(null),c);
    c=new GridBagConstraints(0,1,3,1,1,1,GridBagConstraints.WEST,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
    panel.add(tablePanel,c);
    return panel;
  }

  /**
   * Release all managed resources.
   */
  @Override
  public void dispose()
  {
    saveBoundsPreferences();
    super.dispose();
    _filter=null;
    if (_filterController!=null)
    {
      _filterController.dispose();
      _filterController=null;
    }
    if (_panelController!=null)
    {
      _panelController.dispose();
      _panelController=null;
    }
    if (_tableController!=null)
    {
      _tableController.dispose();
      _tableController=null;
    }
    if (_visibilityController!=null)
    {
      _visibilityController.dispose();
      _visibilityController=null;
    }
  }
}
