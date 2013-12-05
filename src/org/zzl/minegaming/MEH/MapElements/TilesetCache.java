package org.zzl.minegaming.MEH.MapElements;

import java.util.HashMap;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ROMManager;
import org.zzl.minegaming.MEH.DataStore;
import org.zzl.minegaming.MEH.Map;

public class TilesetCache
{
	private static HashMap<Integer, Tileset> cache = new HashMap<Integer, Tileset>();
	private GBARom rom;
	
	private TilesetCache(){}
	
	public static void contains(int offset)
	{
		
	}
	
	/**
	 * Pulls a tileset from the tileset cache. Create a new tileset if one is not cached.
	 * @param offset Tileset data offset
	 * @return
	 */
	public static Tileset get(int offset)
	{
		if(cache.containsKey(offset))
			return cache.get(offset);
		else
		{
			Tileset t =  new Tileset(ROMManager.getActiveROM(), offset);
			cache.put(offset, t);
			return t;
		}
	}

	public static void clearCache()
	{
		cache = new HashMap<Integer, Tileset>();
	}

	
	public static void switchTileset(Map loadedMap)
	{
		get(loadedMap.getMapData().globalTileSetPtr).resetPalettes();
		get(loadedMap.getMapData().localTileSetPtr).resetPalettes();
		for(int i = DataStore.MainTSPalCount-1; i < 13; i++)
			get(loadedMap.getMapData().globalTileSetPtr).getPalette()[i] = get(loadedMap.getMapData().localTileSetPtr).getROMPalette()[i];
		get(loadedMap.getMapData().localTileSetPtr).setPalette(get(loadedMap.getMapData().globalTileSetPtr).getPalette());
		get(loadedMap.getMapData().localTileSetPtr).renderPalettedTiles();
		get(loadedMap.getMapData().globalTileSetPtr).renderPalettedTiles();
		get(loadedMap.getMapData().localTileSetPtr).startTileThreads();
		get(loadedMap.getMapData().globalTileSetPtr).startTileThreads();
	}
}
