package delta.games.lotro.maps.ui.displaySelection.table;

import java.util.ArrayList;
import java.util.List;

import delta.common.ui.swing.tables.CellDataProvider;
import delta.common.ui.swing.tables.CellDataUpdater;
import delta.common.ui.swing.tables.DefaultTableColumnController;
import delta.common.ui.swing.tables.TableColumnController;
import delta.common.ui.swing.tables.panel.FilterUpdateListener;
import delta.games.lotro.maps.data.GeoPoints;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.data.categories.Category;
import delta.games.lotro.maps.data.displaySelection.DisplaySelectionItem;

/**
 * Builds column definitions for display selection items.
 * @author DAM
 */
public class DisplaySelectionColumnsBuilder
{
  /**
   * Indicates if 'technical' columns shall be available or not.
   * @return <code>true</code> to show them, <code>false</code> otherwise.
   */
  private static boolean showTechnicalColumns()
  {
    String env=System.getenv("LC_SHOW_TECHNICAL_COLUMNS");
    return "true".equals(env);
  }

  /**
   * Build the columns to show the attributes of a display selection item.
   * @param categoriesMgr Categories manager.
   * @param listener Filter update listener.
   * @return a list of columns.
   */
  public static List<TableColumnController<DisplaySelectionItem,?>> buildColumns(CategoriesManager categoriesMgr, FilterUpdateListener listener)
  {
    List<TableColumnController<DisplaySelectionItem,?>> ret=new ArrayList<TableColumnController<DisplaySelectionItem,?>>();
    // Identifier
    if (showTechnicalColumns())
    {
      ret.add(buildIdentifierColumn());
    }
    // Visibility
    ret.add(buildVisbilityColumn(listener));
    // Name
    ret.add(buildNameColumn());
    // Count
    ret.add(buildCountColumn());
    // Categories
    ret.add(buildCategoryColumn(categoriesMgr));
    // Positions
    ret.add(buildPositionsColumn());
    return ret;
  }

  private static TableColumnController<DisplaySelectionItem,Integer> buildIdentifierColumn()
  {
    CellDataProvider<DisplaySelectionItem,Integer> idCell=new CellDataProvider<DisplaySelectionItem,Integer>()
    {
      @Override
      public Integer getData(DisplaySelectionItem item)
      {
        return Integer.valueOf(item.getIdentifier());
      }
    };
    DefaultTableColumnController<DisplaySelectionItem,Integer> idColumn=new DefaultTableColumnController<DisplaySelectionItem,Integer>(DisplaySelectionColumnIds.ID.name(),"ID",Integer.class,idCell); // I18n
    idColumn.setWidthSpecs(100,100,100);
    return idColumn;
  }

  /**
   * Build a column to show/edit the visibility.
   * @param listener Listener for updates.
   * @return a column.
   */
  private static TableColumnController<DisplaySelectionItem,?> buildVisbilityColumn(final FilterUpdateListener listener)
  {
    CellDataProvider<DisplaySelectionItem,Boolean> visibleCell=new CellDataProvider<DisplaySelectionItem,Boolean>()
    {
      @Override
      public Boolean getData(DisplaySelectionItem item)
      {
        return Boolean.valueOf(item.isVisible());
      }
    };
    DefaultTableColumnController<DisplaySelectionItem,Boolean> visibleColumn=new DefaultTableColumnController<DisplaySelectionItem,Boolean>(DisplaySelectionColumnIds.VISIBLE.name(),"Visible",Boolean.class,visibleCell);
    visibleColumn.setWidthSpecs(30,30,30);
    visibleColumn.setEditable(true);
    // Updater
    CellDataUpdater<DisplaySelectionItem> updater=new CellDataUpdater<DisplaySelectionItem>()
    {
      @Override
      public void setData(DisplaySelectionItem item, Object value)
      {
        // Update visibility
        boolean visible=((Boolean)value).booleanValue();
        item.setVisible(visible);
        // Broadcast filter change
        if (listener!=null)
        {
          listener.filterUpdated();
        }
      }
    };
    visibleColumn.setValueUpdater(updater);
    return visibleColumn;
  }

  private static TableColumnController<DisplaySelectionItem,?> buildNameColumn()
  {
    CellDataProvider<DisplaySelectionItem,String> nameCell=new CellDataProvider<DisplaySelectionItem,String>()
    {
      @Override
      public String getData(DisplaySelectionItem item)
      {
        return item.getLabel();
      }
    };
    DefaultTableColumnController<DisplaySelectionItem,String> nameColumn=new DefaultTableColumnController<DisplaySelectionItem,String>(DisplaySelectionColumnIds.NAME.name(),"Label",String.class,nameCell);
    nameColumn.setWidthSpecs(70,-1,70);
    nameColumn.setEditable(false);
    return nameColumn;
  }

  private static TableColumnController<DisplaySelectionItem,?> buildCountColumn()
  {
    CellDataProvider<DisplaySelectionItem,Integer> countCell=new CellDataProvider<DisplaySelectionItem,Integer>()
    {
      @Override
      public Integer getData(DisplaySelectionItem status)
      {
        return Integer.valueOf(status.getMarkersCount());
      }
    };
    DefaultTableColumnController<DisplaySelectionItem,Integer> countColumn=new DefaultTableColumnController<DisplaySelectionItem,Integer>(DisplaySelectionColumnIds.COUNT.name(),"Count",Integer.class,countCell);
    countColumn.setWidthSpecs(50,50,50);
    return countColumn;
  }

  private static TableColumnController<DisplaySelectionItem,String> buildCategoryColumn(final CategoriesManager categoriesMgr)
  {
    CellDataProvider<DisplaySelectionItem,String> cell=new CellDataProvider<DisplaySelectionItem,String>()
    {
      @Override
      public String getData(DisplaySelectionItem item)
      {
        int code=item.getCategoryCode();
        Category category=categoriesMgr.getByCode(code);
        return (category!=null)?category.getName():null;
      }
    };
    DefaultTableColumnController<DisplaySelectionItem,String> column=new DefaultTableColumnController<DisplaySelectionItem,String>(DisplaySelectionColumnIds.CATEGORY.name(),"Category",String.class,cell);
    column.setWidthSpecs(70,150,150);
    column.setEditable(false);
    return column;
  }

  private static TableColumnController<DisplaySelectionItem,GeoPoints> buildPositionsColumn()
  {
    CellDataProvider<DisplaySelectionItem,GeoPoints> cell=new CellDataProvider<DisplaySelectionItem,GeoPoints>()
    {
      @Override
      public GeoPoints getData(DisplaySelectionItem item)
      {
        return item.getPositions();
      }
    };
    
    DefaultTableColumnController<DisplaySelectionItem,GeoPoints> column=new DefaultTableColumnController<DisplaySelectionItem,GeoPoints>(DisplaySelectionColumnIds.POSITIONS.name(),"Location(s)",GeoPoints.class,cell);
    column.setWidthSpecs(70,250,250);
    column.setEditable(false);
    return column;
  }
}
