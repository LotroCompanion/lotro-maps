package delta.games.lotro.maps.data.basemaps;

import java.io.File;

import delta.common.utils.id.Identifiable;
import delta.games.lotro.maps.data.GeoBox;
import delta.games.lotro.maps.data.GeoReference;

/**
 * Georeferenced base map.
 * @author DAM
 */
public class GeoreferencedBasemap implements Identifiable
{
  private int _id;
  private String _name;
  private GeoReference _geoRef;
  private GeoBox _boundingBox;
  private int _imageId;
  private File _imageFile;

  /**
   * Constructor.
   * @param id Identifier for this basemap.
   * @param name Map name.
   * @param geoReference Geographic reference.
   */
  public GeoreferencedBasemap(int id, String name, GeoReference geoReference)
  {
    _id=id;
    _name=name;
    _geoRef=geoReference;
  }

  /**
   * Get the identifier for this basemap.
   * @return an identifier.
   */
  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Get the name of this basemap.
   * @return a map name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the geographic reference.
   * @return the geographic reference.
   */
  public GeoReference getGeoReference()
  {
    return _geoRef;
  }

  /**
   * Get the geographic bounding box for this basemap.
   * @return a geographic bounding box.
   */
  public GeoBox getBoundingBox()
  {
    return _boundingBox;
  }

  /**
   * Set the geographic bounding box.
   * @param boundingBox Geographic bounding box to set.
   */
  public void setBoundingBox(GeoBox boundingBox)
  {
    _boundingBox=boundingBox;
  }

  /**
   * Get the image file.
   * @return the image file.
   */
  public File getImageFile()
  {
    return _imageFile;
  }

  /**
   * Set the image file.
   * @param imageFile Image file.
   */
  public void setImageFile(File imageFile)
  {
    _imageFile=imageFile;
  }

  /**
   * Get the basemap image identifier.
   * @return a basemap image identifier.
   */
  public int getImageId()
  {
    return _imageId;
  }

  /**
   * Set the basemap image identifier.
   * @param imageId Image identifier to set.
   */
  public void setImageId(int imageId)
  {
    _imageId=imageId;
  }

  @Override
  public String toString()
  {
    return "Basemap: ID="+_id+", name="+_name+", reference=" + _geoRef + ", file="+_imageFile+", imageId="+_imageId;
  }
}
