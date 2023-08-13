package delta.games.lotro.maps.ui.displaySelection.filter;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.games.lotro.maps.data.displaySelection.DisplaySelectionItem;

/**
 * Filter of display selection items.
 * @author DAM
 */
public class DisplaySelectionItemsFilter implements Filter<DisplaySelectionItem>
{
  private Filter<DisplaySelectionItem> _filter;

  private DisplaySelectionItemNameFilter _nameFilter;
  private DisplaySelectionItemCategoryFilter _categoryFilter;
  private DisplaySelectionItemVisibilityFilter _visibilityFilter;

  /**
   * Constructor.
   */
  public DisplaySelectionItemsFilter()
  {
    List<Filter<DisplaySelectionItem>> filters=new ArrayList<Filter<DisplaySelectionItem>>();
    // Name
    _nameFilter=new DisplaySelectionItemNameFilter();
    filters.add(_nameFilter);
    // Category
    _categoryFilter=new DisplaySelectionItemCategoryFilter(null);
    filters.add(_categoryFilter);
    // Visibility
    _visibilityFilter=new DisplaySelectionItemVisibilityFilter(null);
    filters.add(_visibilityFilter);
    _filter=new CompoundFilter<DisplaySelectionItem>(Operator.AND,filters);
  }

  /**
   * Get the filter on names.
   * @return a filter on names.
   */
  public DisplaySelectionItemNameFilter getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on the category.
   * @return a filter on the category.
   */
  public DisplaySelectionItemCategoryFilter getCategoryFilter()
  {
    return _categoryFilter;
  }

  /**
   * Get the filter on the visibility.
   * @return a filter on the visibility.
   */
  public DisplaySelectionItemVisibilityFilter getVisibilityFilter()
  {
    return _visibilityFilter;
  }

  @Override
  public boolean accept(DisplaySelectionItem item)
  {
    return _filter.accept(item);
  }
}
