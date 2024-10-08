package delta.games.lotro.maps.ui.layers.basemap;

import java.awt.image.BufferedImage;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.ui.ImageUtils;
import delta.games.lotro.maps.data.basemaps.GeoreferencedBasemap;

/**
 * Default basemap image provider.
 * @author DAM
 */
public class DefaultBasemapImageProvider implements BasemapImageProvider
{
  private static final Logger LOGGER=LoggerFactory.getLogger(DefaultBasemapImageProvider.class);

  @Override
  public BufferedImage getImage(GeoreferencedBasemap basemap)
  {
    File mapImageFile=basemap.getImageFile();
    if (mapImageFile.exists())
    {
      return ImageUtils.loadImage(mapImageFile);
    }
    LOGGER.warn("Cannot find file: "+mapImageFile);
    return null;
  }
}
