package delta.games.lotro.maps.ui.displaySelection.filter;

import delta.common.utils.collections.filters.Filter;
import delta.common.utils.text.MatchType;
import delta.common.utils.text.StringFilter;
import delta.games.lotro.maps.data.displaySelection.DisplaySelectionItem;

/**
 * Filter on the name of display selection items.
 * @author DAM
 */
public class DisplaySelectionItemNameFilter implements Filter<DisplaySelectionItem>
{
  private StringFilter _filter;
  private String _pattern;

  /**
   * Constructor.
   */
  public DisplaySelectionItemNameFilter()
  {
    this("");
  }

  /**
   * Constructor.
   * @param pattern String filter for name.
   */
  public DisplaySelectionItemNameFilter(String pattern)
  {
    _filter=new StringFilter("",MatchType.CONTAINS,true);
    _pattern=pattern;
  }

  /**
   * Get the pattern to use to filter command.
   * @return A pattern string.
   */
  public String getPattern()
  {
    return _pattern;
  }

  /**
   * Set the string pattern.
   * @param pattern Pattern to set.
   */
  public void setPattern(String pattern)
  {
    if (pattern==null)
    {
      pattern="";
    }
    _pattern=pattern;
    _filter=new StringFilter(pattern,MatchType.CONTAINS,true);
  }

  @Override
  public boolean accept(DisplaySelectionItem item)
  {
    String label=item.getLabel();
    if (label!=null)
    {
      return _filter.accept(label);
    }
    return false;
  }
}
