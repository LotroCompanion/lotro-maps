package delta.games.lotro.maps.ui.displaySelection.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import delta.common.ui.swing.tables.GenericTableController;
import delta.common.ui.swing.tables.ListDataProvider;
import delta.common.ui.swing.tables.Sort;
import delta.common.ui.swing.tables.TableColumnController;
import delta.common.ui.swing.tables.TableColumnsManager;
import delta.common.ui.swing.tables.panel.FilterUpdateListener;
import delta.games.lotro.maps.data.displaySelection.DisplaySelection;
import delta.games.lotro.maps.data.displaySelection.DisplaySelectionItem;

/**
 * Controller for a table that shows the display selection elements for a single category.
 * @author DAM
 */
public class DisplaySelectionTableController
{
  // Data
  private DisplaySelection _data;
  // GUI
  private JTable _table;
  private GenericTableController<DisplaySelectionItem> _tableController;

  /**
   * Constructor.
   * @param data Data to show.
   * @param listener Listener.
   */
  public DisplaySelectionTableController(DisplaySelection data, FilterUpdateListener listener)
  {
    _data=data;
    _tableController=buildTable(listener);
    _tableController.setFilter(null);
  }

  private GenericTableController<DisplaySelectionItem> buildTable(FilterUpdateListener listener)
  {
    List<DisplaySelectionItem> items=_data.getItems();
    ListDataProvider<DisplaySelectionItem> provider=new ListDataProvider<DisplaySelectionItem>(items);
    GenericTableController<DisplaySelectionItem> table=new GenericTableController<DisplaySelectionItem>(provider);
    // Columns
    List<TableColumnController<DisplaySelectionItem,?>> columns=DisplaySelectionColumnsBuilder.buildColumns(listener);
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
    columnIds.add(DisplaySelectionColumnIds.VISIBLE.name());
    columnIds.add(DisplaySelectionColumnIds.NAME.name());
    columnIds.add(DisplaySelectionColumnIds.COUNT.name());
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
