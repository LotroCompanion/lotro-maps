package delta.games.lotro.maps.data;

import java.io.File;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.games.lotro.maps.data.basemaps.GeoreferencedBasemapsManager;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.data.links.LinksManager;
import delta.games.lotro.maps.data.markers.GlobalMarkersManager;
import delta.games.lotro.maps.data.markers.MarkersFinder;
import delta.games.lotro.maps.utils.i18n.I18nFacade;

/**
 * Maps manager.
 * @author DAM
 */
public class MapsManager
{
  private File _rootDir;
  // I18n
  private I18nFacade _i18n;
  // Basemaps
  private GeoreferencedBasemapsManager _basemapsManager;
  // Categories
  private CategoriesManager _categoriesManager;
  // Markers
  private GlobalMarkersManager _markersManager;
  private MarkersFinder _markersFinder;
  // Links
  private LinksManager _linksManager;

  /**
   * Constructor.
   * @param rootDir Root directory for maps data.
   */
  public MapsManager(File rootDir)
  {
    this(rootDir,"en");
  }

  /**
   * Get the labels directory.
   * @return the labels directory.
   */
  public File getLabelsDir()
  {
    return new File(_rootDir,"labels");
  }

  /**
   * Constructor.
   * @param rootDir Root directory for maps data.
   * @param localeKey Locale key.
   */
  public MapsManager(File rootDir, String localeKey)
  {
    _rootDir=rootDir;
    // I18n
    File labelsDir=getLabelsDir();
    _i18n=new I18nFacade(labelsDir,localeKey);
    // Basemaps
    File mapsDir=new File(_rootDir,"maps");
    _basemapsManager=new GeoreferencedBasemapsManager(mapsDir);
    SingleLocaleLabelsManager baseMapsLabelsMgr=_i18n.getLabelsMgr("basemaps");
    _basemapsManager.load(baseMapsLabelsMgr);
    // Categories
    File categoriesDir=new File(_rootDir,"categories");
    _categoriesManager=new CategoriesManager(categoriesDir);
    // Markers
    File markersDir=new File(_rootDir,"markers");
    SingleLocaleLabelsManager markersLabelsMgr=_i18n.getLabelsMgr("markers");
    _markersManager=new GlobalMarkersManager(markersDir,markersLabelsMgr);
    _markersFinder=new MarkersFinder(_rootDir,_markersManager);
    // Links
    _linksManager=new LinksManager(rootDir);
  }

  /**
   * Get the basemaps manager.
   * @return the basemaps manager.
   */
  public GeoreferencedBasemapsManager getBasemapsManager()
  {
    return _basemapsManager;
  }

  /**
   * Get the categories manager.
   * @return the categories manager.
   */
  public CategoriesManager getCategories()
  {
    return _categoriesManager;
  }

  /**
   * Get the markers manager.
   * @return the markers manager.
   */
  public GlobalMarkersManager getMarkersManager()
  {
    return _markersManager;
  }

  /**
   * Get the markers finder.
   * @return the markers finder.
   */
  public MarkersFinder getMarkersFinder()
  {
    return _markersFinder;
  }

  /**
   * Get the links manager.
   * @return the links manager.
   */
  public LinksManager getLinksManager()
  {
    return _linksManager;
  }
}
