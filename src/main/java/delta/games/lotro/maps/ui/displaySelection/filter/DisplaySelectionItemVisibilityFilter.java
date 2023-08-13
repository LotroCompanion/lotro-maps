package delta.games.lotro.maps.ui.displaySelection.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.maps.data.displaySelection.DisplaySelectionItem;

/**
 * Filter on the visibility of display selection items.
 * @author DAM
 */
public class DisplaySelectionItemVisibilityFilter implements Filter<DisplaySelectionItem>
{
  private Boolean _visibility;

  /**
   * Constructor.
   * @param visibility Visibility to select (may be <code>null</code>).
   */
  public DisplaySelectionItemVisibilityFilter(Boolean visibility)
  {
    _visibility=visibility;
  }

  /**
   * Get the visibility to use.
   * @return A visibility or <code>null</code>.
   */
  public Boolean getVisbility()
  {
    return _visibility;
  }

  /**
   * Set the visibility to select.
   * @param visibility Visibility to use, may be <code>null</code>.
   */
  public void setVisibility(Boolean visibility)
  {
    _visibility=visibility;
  }

  @Override
  public boolean accept(DisplaySelectionItem item)
  {
    if (_visibility==null)
    {
      return true;
    }
    boolean visible=item.isVisible();
    return (visible==_visibility.booleanValue());
  }
}
