package delta.games.lotro.maps.ui.displaySelection.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import delta.common.ui.swing.tables.GenericTableController;
import delta.common.ui.swing.tables.ListDataProvider;
import delta.common.ui.swing.tables.Sort;
import delta.common.ui.swing.tables.TableColumnController;
import delta.common.ui.swing.tables.TableColumnsManager;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.data.displaySelection.DisplaySelection;
import delta.games.lotro.maps.data.displaySelection.DisplaySelectionItem;
import delta.games.lotro.maps.data.markers.filters.DisplaySelectionFilter;
import delta.games.lotro.maps.ui.displaySelection.DisplaySelectionUpdateListener;
import delta.games.lotro.maps.ui.displaySelection.filter.DisplaySelectionItemsFilter;

/**
 * Controller for a table that shows the display selection elements for a single category.
 * @author DAM
 */
public class DisplaySelectionTableController
{
  // Data
  private DisplaySelection _data;
  private List<DisplaySelectionItem> _items;
  // GUI
  private JTable _table;
  private GenericTableController<DisplaySelectionItem> _tableController;

  /**
   * Constructor.
   * @param data Data to show.
   * @param categoriesMgr Categories manager.
   * @param itemsFilter Items filter.
   * @param filter Contents filter.
   * @param visibilityListener Visibility update listener.
   */
  public DisplaySelectionTableController(DisplaySelection data, CategoriesManager categoriesMgr,
      DisplaySelectionItemsFilter itemsFilter,
      DisplaySelectionFilter filter, DisplaySelectionUpdateListener visibilityListener)
  {
    _data=data;
    _tableController=buildTable(categoriesMgr,visibilityListener);
    _tableController.setFilter(itemsFilter);
  }

  private GenericTableController<DisplaySelectionItem> buildTable(CategoriesManager categoriesMgr, DisplaySelectionUpdateListener listener)
  {
    _items=new ArrayList<DisplaySelectionItem>();
    _items.addAll(_data.getItems());
    ListDataProvider<DisplaySelectionItem> provider=new ListDataProvider<DisplaySelectionItem>(_items);
    GenericTableController<DisplaySelectionItem> table=new GenericTableController<DisplaySelectionItem>(provider);
    // Columns
    List<TableColumnController<DisplaySelectionItem,?>> columns=DisplaySelectionColumnsBuilder.buildColumns(categoriesMgr,listener);
    for(TableColumnController<DisplaySelectionItem,?> column : columns)
    {
      table.addColumnController(column);
    }

    // Setup
    List<String> columnsIds=getColumnIds();
    TableColumnsManager<DisplaySelectionItem> columnsManager=table.getColumnsManager();
    columnsManager.setColumns(columnsIds);

    // Sort
    Sort sort=Sort.buildFromString(Sort.SORT_ASCENDING+DisplaySelectionColumnIds.NAME);
    table.setSort(sort);
    return table;
  }

  private List<String> getColumnIds()
  {
    List<String> columnIds=new ArrayList<String>();
    columnIds.add(DisplaySelectionColumnIds.ID.name());
    columnIds.add(DisplaySelectionColumnIds.VISIBLE.name());
    columnIds.add(DisplaySelectionColumnIds.NAME.name());
    columnIds.add(DisplaySelectionColumnIds.COUNT.name());
    columnIds.add(DisplaySelectionColumnIds.CATEGORY.name());
    columnIds.add(DisplaySelectionColumnIds.POSITIONS.name());
    return columnIds;
  }

  /**
   * Get the managed table controller.
   * @return the managed table controller.
   */
  public GenericTableController<DisplaySelectionItem> getTableController()
  {
    return _tableController;
  }

  /**
   * Refresh table contents.
   */
  public void refresh()
  {
    _items.clear();
    _items.addAll(_data.getItems());
    _tableController.refresh();
  }

  /**
   * Update managed filter.
   */
  public void updateFilter()
  {
    _tableController.filterUpdated();
  }

  /**
   * Get the total number of items.
   * @return A number of items.
   */
  public int getNbItems()
  {
    return _data.getItemsCount();
  }

  /**
   * Get the number of filtered items in the managed table.
   * @return A number of items.
   */
  public int getNbFilteredItems()
  {
    int ret=_tableController.getNbFilteredItems();
    return ret;
  }

  /**
   * Get the managed table.
   * @return the managed table.
   */
  public JTable getTable()
  {
    if (_table==null)
    {
      _table=_tableController.getTable();
      _table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }
    return _table;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // GUI
    _table=null;
    if (_tableController!=null)
    {
      _tableController.dispose();
      _tableController=null;
    }
    // Data
    _data=null;
  }
}
