package delta.games.lotro.maps.utils.i18n;

import java.io.File;

import delta.common.utils.i18n.LabelsFacade;
import delta.common.utils.i18n.SingleLocaleLabelsManager;

/**
 * Facade for localization utilities.
 * @author DAM
 */
public class I18nFacade
{
  private LabelsFacade _labels;
  private String _localeKey;

  /**
   * Constructor.
   * @param rootDir Root directory.
   * @param localekey Locale key.
   */
  public I18nFacade(File rootDir, String localekey)
  {
    _labels=new LabelsFacade(rootDir);
    _localeKey=localekey;
  }

  /**
   * Get the labels manager for the given key and the current locale.
   * @param key Set key to use.
   * @return A labels manager.
   */
  public SingleLocaleLabelsManager getLabelsMgr(String key)
  {
    return _labels.getLabelsMgr(key,_localeKey);
  }
}
