package delta.games.lotro.maps.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Dimension;

import org.junit.jupiter.api.Test;

/**
 * Geo conversions tests.
 * @author DAM
 */
class GeoReferenceTest
{
  private static GeoPoint _startPoint=new GeoPoint(-114.01f, -3.826f);
  private static GeoReference _geoReference=new GeoReference(_startPoint, 7.704f*10);

  /**
   * Test conversion from geo to pixels.
   */
  @Test
  void testConversionGeo2Pixel()
  {
    GeoPoint naga=new GeoPoint(-105.8f, -7.5f);
    Dimension nagaPixels = _geoReference.geo2pixel(naga);
    System.out.println(nagaPixels);
    assertEquals(632,nagaPixels.width);
    assertEquals(283,nagaPixels.height);
  }

  /**
   * Test conversion from pixels to geo.
   */
  @Test
  void testConversionPixel2Geo()
  {
    Dimension nagaPixels=new Dimension(632,283);
    GeoPoint naga=_geoReference.pixel2geo(nagaPixels);
    System.out.println(naga);
    assertTrue(Math.abs(-105.8f-naga.getLongitude())<0.01);
    assertTrue(Math.abs(-7.5f-naga.getLatitude())<0.01);
  }
}
