package com.vinibarros.endlessmonstermayhem.game.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import com.vinibarros.endlessmonstermayhem.game.graphics.SpriteSheet


internal abstract class Tile(mapLocationRect: Rect) {
    protected val mapLocationRect: Rect

    init {
        this.mapLocationRect = mapLocationRect
    }

    enum class TileType {
        WATER_TILE, LAVA_TILE, GROUND_TILE, GRASS_TILE, TREE_TILE
    }

    abstract fun draw(canvas: Canvas, context: Context)

    companion object {
        fun getTile(idxTileType: Int, spriteSheet: SpriteSheet, mapLocationRect: Rect): Tile {
            return when (TileType.values()[idxTileType]) {
                TileType.WATER_TILE -> WaterTile(spriteSheet, mapLocationRect)
                TileType.LAVA_TILE -> LavaTile(spriteSheet, mapLocationRect)
                TileType.GROUND_TILE -> GroundTile(spriteSheet, mapLocationRect)
                TileType.GRASS_TILE -> GrassTile(spriteSheet, mapLocationRect)
                TileType.TREE_TILE -> TreeTile(spriteSheet, mapLocationRect)
            }
        }
    }
}