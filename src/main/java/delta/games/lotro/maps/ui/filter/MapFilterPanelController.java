package delta.games.lotro.maps.ui.filter;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextField;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.panels.AbstractPanelController;
import delta.common.ui.swing.tables.panel.FilterUpdateListener;
import delta.common.ui.swing.text.DynamicTextEditionController;
import delta.common.ui.swing.text.TextListener;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.data.markers.filters.MapMarkersFilter;

/**
 * Controller for a map filter edition panel.
 * @author DAM
 */
public class MapFilterPanelController extends AbstractPanelController
{
  // Data
  private MapMarkersFilter _filter;
  // GUI
  private JTextField _contains;
  // Controllers
  private CategoryChooserController _categoryChooser;
  private DynamicTextEditionController _textController;
  private FilterUpdateListener _listener;

  /**
   * Constructor.
   * @param categoriesManager Categories manager.
   * @param listener Filter updates listener.
   */
  public MapFilterPanelController(CategoriesManager categoriesManager, FilterUpdateListener listener)
  {
    _filter=new MapMarkersFilter();
    buildCategoriesChooser(categoriesManager);
    initCategoriesFilter(categoriesManager);
    _listener=listener;
  }

  /**
   * Get the managed filter.
   * @return the managed filter.
   */
  public MapMarkersFilter getFilter()
  {
    return _filter;
  }

  /**
   * Get the category chooser.
   * @return the category chooser.
   */
  public CategoryChooserController getCategoryChooser()
  {
    return _categoryChooser;
  }

  private void initCategoriesFilter(CategoriesManager categoriesManager)
  {
    Set<Integer> codes=categoriesManager.getCodes();
    _filter.getCategoryFilter().setCategories(codes);
  }

  @Override
  public JPanel getPanel()
  {
    JPanel panel=super.getPanel();
    if (panel==null)
    {
      panel=build();
      setPanel(panel);
      setFilter();
      filterUpdated();
    }
    return panel;
  }

  private void setFilter()
  {
    // Name
    String contains=_filter.getNameFilter().getPattern();
    if (contains!=null)
    {
      _contains.setText(contains);
    }
    // Categories
    Set<Integer> acceptedCategories=_filter.getCategoryFilter().getCategories();
    _categoryChooser.setSelectedCategories(acceptedCategories);
  }

  private JPanel build()
  {
    JPanel panel=GuiFactory.buildBackgroundPanel(new GridBagLayout());
    JPanel topPanel=builTopPanel();
    GridBagConstraints c=new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
    panel.add(topPanel,c);
    // Categories editor
    JPanel categoriesPanel=_categoryChooser.getPanel();
    c=new GridBagConstraints(0,1,1,1,1.0,1.0,GridBagConstraints.WEST,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
    panel.add(categoriesPanel,c);
    return panel;
  }

  private JPanel builTopPanel()
  {
    JPanel panel=GuiFactory.buildPanel(new FlowLayout());
    // Label filter
    JPanel containsPanel=buildLabelFilter();
    panel.add(containsPanel);
    return panel;
  }

  private void buildCategoriesChooser(CategoriesManager categoriesManager)
  {
    _categoryChooser=new CategoryChooserController(categoriesManager);
    CategoryFilterUpdateListener listener=new CategoryFilterUpdateListener()
    {
      public void categoryFilterUpdated(CategoryChooserController controller)
      {
        Set<Integer> selectedCategories=controller.getSelectedCategories();
        _filter.getCategoryFilter().setCategories(selectedCategories);
        filterUpdated();
      }
    };
    _categoryChooser.setListener(listener);
  }

  private JPanel buildLabelFilter()
  {
    JPanel containsPanel=GuiFactory.buildPanel(new FlowLayout(FlowLayout.LEADING));
    containsPanel.add(GuiFactory.buildLabel("Label:"));
    _contains=GuiFactory.buildTextField("");
    _contains.setColumns(20);
    containsPanel.add(_contains);
    TextListener listener=new TextListener()
    {
      public void textChanged(String newText)
      {
        if (newText.length()==0) newText=null;
        _filter.getNameFilter().setPattern(newText);
        filterUpdated();
      }
    };
    _textController=new DynamicTextEditionController(_contains,listener);
    return containsPanel;
  }

  /**
   * Called when the managed filter was updated.
   */
  public void filterUpdated()
  {
    _listener.filterUpdated();
  }

  @Override
  public void dispose()
  {
    super.dispose();
    // Data
    _filter=null;
    // Controllers
    if (_categoryChooser!=null)
    {
      _categoryChooser.dispose();
      _categoryChooser=null;
    }
    if (_textController!=null)
    {
      _textController.dispose();
      _textController=null;
    }
    // GUI
    _contains=null;
    _listener=null;
  }
}
