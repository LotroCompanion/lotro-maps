package delta.games.lotro.maps.data.markers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import delta.games.lotro.maps.data.Marker;

/**
 * Facade to access to all markers.
 * @author DAM
 */
public class GlobalMarkersManager
{
  private Map<String,BlockMarkersManager> _cache;
  private File _rootDir;
  private static final int BLOCK_SIZE=16;

  /**
   * Constructor.
   * @param rootDir Root directory.
   */
  public GlobalMarkersManager(File rootDir)
  {
    _rootDir=rootDir;
    _cache=new HashMap<String,BlockMarkersManager>();
  }

  /**
   * Get the block manager for a given zone.
   * @param region Region.
   * @param bigXBlock X big block coordinate.
   * @param bigYBlock Y big block coordinate.
   * @return A markers manager.
   */
  public BlockMarkersManager getBlockManager(int region, int bigXBlock, int bigYBlock)
  {
    String key=getKey(region,bigXBlock,bigYBlock);
    BlockMarkersManager blockManager=_cache.get(key);
    if (blockManager==null)
    {
      File blockFile=getBlockFile(region,bigXBlock,bigYBlock);
      int firstId=getFirstId(region,bigXBlock,bigYBlock);
      blockManager=new BlockMarkersManager(blockFile,firstId);
      blockManager.load();
      _cache.put(key,blockManager);
    }
    return blockManager;
  }

  /**
   * Get a marker using its identifier.
   * @param markerId Marker identifier.
   * @return A marker or <code>null</code> if not found.
   */
  public Marker getMarkerById(int markerId)
  {
    BlockMarkersManager blockManager=getBlockForMarker(markerId);
    return blockManager.getMarkerById(markerId);
  }

  /**
   * Register a marker.
   * @param marker Marker to add.
   * @param region Region code.
   * @param landblockX Landblock X.
   * @param landblockY Landblock Y.
   */
  public void registerMarker(Marker marker, int region, int landblockX, int landblockY)
  {
    BlockMarkersManager blockManager=getBlockManager(region,landblockX/BLOCK_SIZE,landblockY/BLOCK_SIZE);
    blockManager.allocateMarker(marker,landblockX%BLOCK_SIZE,landblockY%BLOCK_SIZE);
  }

  private static int getFirstId(int region, int bigXBlock, int bigYBlock)
  {
    int id=region<<28;
    id+=(bigXBlock<<24);
    id+=(bigYBlock<<20);
    return id;
  }

  private BlockMarkersManager getBlockForMarker(int markerId)
  {
    int region=(markerId&0x70000000)>>28;
    int bigXBlock=(markerId&0xF000000)>>24;
    int bigYBlock=(markerId&0xF00000)>>20;
    BlockMarkersManager blockManager=getBlockManager(region,bigXBlock,bigYBlock);
    return blockManager;
  }

  /**
   * Write the markers files.
   */
  public void write()
  {
    for(BlockMarkersManager blockManager : _cache.values())
    {
      blockManager.write();
    }
  }

  private File getBlockFile(int region, int xBlock, int yBlock)
  {
    String fileName="markers-"+getKey(region,xBlock,yBlock)+".xml";
    return new File(_rootDir,fileName);
  }

  private String getKey(int region, int xBlock, int yBlock)
  {
    return region+"-"+xBlock+"-"+yBlock;
  }
}
