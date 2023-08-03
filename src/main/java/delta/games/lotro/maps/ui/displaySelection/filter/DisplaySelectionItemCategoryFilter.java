package delta.games.lotro.maps.ui.displaySelection.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.maps.data.displaySelection.DisplaySelectionItem;

/**
 * Filter for quests of a given category.
 * @author DAM
 */
public class DisplaySelectionItemCategoryFilter implements Filter<DisplaySelectionItem>
{
  private Integer _categoryCode;

  /**
   * Constructor.
   * @param categoryCode Code of category to select (may be <code>null</code>).
   */
  public DisplaySelectionItemCategoryFilter(Integer categoryCode)
  {
    _categoryCode=categoryCode;
  }

  /**
   * Get the category code to use.
   * @return A category code or <code>null</code>.
   */
  public Integer getCategoryCode()
  {
    return _categoryCode;
  }

  /**
   * Set the category code to select.
   * @param categoryCode Category to use, may be <code>null</code>.
   */
  public void setCategoryCode(Integer categoryCode)
  {
    _categoryCode=categoryCode;
  }

  @Override
  public boolean accept(DisplaySelectionItem item)
  {
    if (_categoryCode==null)
    {
      return true;
    }
    int categoryCode=item.getCategoryCode();
    return (categoryCode==_categoryCode.intValue());
  }
}
