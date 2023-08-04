package delta.games.lotro.maps.ui.displaySelection.filter;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.combobox.ComboBoxController;
import delta.common.ui.swing.combobox.ItemSelectionListener;
import delta.common.ui.swing.tables.panel.FilterUpdateListener;
import delta.common.ui.swing.text.DynamicTextEditionController;
import delta.common.ui.swing.text.TextListener;
import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.data.categories.Category;
import delta.games.lotro.maps.data.displaySelection.DisplaySelectionItem;

/**
 * Controller for an edition panel of a display selection filter.
 * @author DAM
 */
public class DisplaySelectionFilterController implements ActionListener
{
  // Data
  private CategoriesManager _categoriesMgr;
  private DisplaySelectionItemsFilter _filter;
  // GUI
  private JPanel _panel;
  private JButton _reset;
  // - name filter
  private JTextField _contains;
  private DynamicTextEditionController _textController;
  // - category filter
  private ComboBoxController<Integer> _categories;
  // Filter update listener
  private FilterUpdateListener _filterUpdateListener;

  /**
   * Constructor.
   * @param categoriesMgr Categories manager.
   * @param filter Managed filter.
   * @param filterUpdateListener Filter update listener.
   */
  public DisplaySelectionFilterController(CategoriesManager categoriesMgr, DisplaySelectionItemsFilter filter, FilterUpdateListener filterUpdateListener)
  {
    _categoriesMgr=categoriesMgr;
    _filter=filter;
    _filterUpdateListener=filterUpdateListener;
  }

  /**
   * Get the managed filter.
   * @return the managed filter.
   */
  public Filter<DisplaySelectionItem> getFilter()
  {
    return _filter;
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    if (_panel==null)
    {
      _panel=build();
      setFilter();
      filterUpdated();
    }
    return _panel;
  }

  /**
   * Invoked when the managed filter has been updated.
   */
  protected void filterUpdated()
  {
    _filterUpdateListener.filterUpdated();
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    Object source=e.getSource();
    if (source==_reset)
    {
      _categories.selectItem(null);
      _contains.setText("");
    }
  }

  private void setFilter()
  {
    // Name
    DisplaySelectionItemNameFilter nameFilter=_filter.getNameFilter();
    String contains=nameFilter.getPattern();
    if (contains!=null)
    {
      _contains.setText(contains);
    }
    // Category
    DisplaySelectionItemCategoryFilter categoryFilter=_filter.getCategoryFilter();
    Integer categoryCode=categoryFilter.getCategoryCode();
    _categories.selectItem(categoryCode);
  }

  private JPanel build()
  {
    JPanel panel=GuiFactory.buildPanel(new GridBagLayout());

    int y=0;

    // Display selection filter
    JPanel filterPanel=buildFilterPanel();
    GridBagConstraints c=new GridBagConstraints(0,y,1,1,0.0,0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
    panel.add(filterPanel,c);

    // Reset
    _reset=GuiFactory.buildButton("Reset"); // I18n
    _reset.addActionListener(this);
    c=new GridBagConstraints(1,y,1,1,0.0,0,GridBagConstraints.SOUTHWEST,GridBagConstraints.NONE,new Insets(0,5,5,5),0,0);
    panel.add(_reset,c);
    y++;

    return panel;
  }

  private JPanel buildFilterPanel()
  {
    JPanel panel=GuiFactory.buildPanel(new GridBagLayout());

    int y=0;
    JPanel line1Panel=GuiFactory.buildPanel(new FlowLayout(FlowLayout.LEADING,5,0));
    // Label
    {
      line1Panel.add(GuiFactory.buildLabel("Name:")); // I18n
      _contains=GuiFactory.buildTextField("");
      _contains.setColumns(20);
      line1Panel.add(_contains);
      TextListener listener=new TextListener()
      {
        @Override
        public void textChanged(String newText)
        {
          if (newText.length()==0) newText=null;
          DisplaySelectionItemNameFilter nameFilter=_filter.getNameFilter();
          nameFilter.setPattern(newText);
          filterUpdated();
        }
      };
      _textController=new DynamicTextEditionController(_contains,listener);
    }
    // Category
    {
      JLabel label=GuiFactory.buildLabel("Category:"); // I18n
      line1Panel.add(label);
      _categories=buildCategoriesCombobox();
      line1Panel.add(_categories.getComboBox());
    }
    GridBagConstraints c=new GridBagConstraints(0,y,1,1,1.0,0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(0,0,5,0),0,0);
    panel.add(line1Panel,c);
    y++;

    return panel;
  }

  private ComboBoxController<Integer> buildCategoriesCombobox()
  {
    ComboBoxController<Integer> ctrl=new ComboBoxController<Integer>();
    ctrl.addEmptyItem("");
    List<Category> categories=_categoriesMgr.getAllSortedByCode();
    for(Category category : categories)
    {
      Integer code=Integer.valueOf(category.getCode());
      ctrl.addItem(code,category.getName());
    }
    ctrl.selectItem(null);
    ItemSelectionListener<Integer> listener=new ItemSelectionListener<Integer>()
    {
      @Override
      public void itemSelected(Integer categoryCode)
      {
        DisplaySelectionItemCategoryFilter filter=_filter.getCategoryFilter();
        filter.setCategoryCode(categoryCode);
        filterUpdated();
      }
    };
    ctrl.addListener(listener);
    return ctrl;
  }

 /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Data
    _categoriesMgr=null;
    _filter=null;
    // Controllers
    if (_textController!=null)
    {
      _textController.dispose();
      _textController=null;
    }
    if (_categories!=null)
    {
      _categories.dispose();
      _categories=null;
    }
    // GUI
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
    _contains=null;
    _reset=null;
  }
}
