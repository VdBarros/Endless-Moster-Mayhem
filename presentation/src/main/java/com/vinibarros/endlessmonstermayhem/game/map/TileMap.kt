package com.vinibarros.endlessmonstermayhem.game.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.vinibarros.endlessmonstermayhem.game.core.GameDisplay
import com.vinibarros.endlessmonstermayhem.game.graphics.SpriteSheet
import com.vinibarros.endlessmonstermayhem.game.map.MapLayout.Companion.NUMBER_OF_COLUMN_TILES
import com.vinibarros.endlessmonstermayhem.game.map.MapLayout.Companion.NUMBER_OF_ROW_TILES
import com.vinibarros.endlessmonstermayhem.game.map.MapLayout.Companion.TILE_HEIGHT_PIXELS
import com.vinibarros.endlessmonstermayhem.game.map.MapLayout.Companion.TILE_WIDTH_PIXELS


class TileMap(spriteSheet: SpriteSheet) {
    private val mapLayout: MapLayout = MapLayout()
    private lateinit var tilemap: Array<Array<Tile?>>
    private val spriteSheet: SpriteSheet
    private lateinit var mapBitmap: Bitmap

    init {
        this.spriteSheet = spriteSheet
        initializeTilemap()
    }

    private fun initializeTilemap() {
        val layout: Array<IntArray> = mapLayout.layout
        tilemap = Array<Array<Tile?>>(NUMBER_OF_ROW_TILES) {
            arrayOfNulls<Tile>(
                NUMBER_OF_COLUMN_TILES
            )
        }
        for (iRow in 0 until NUMBER_OF_ROW_TILES) {
            for (iCol in 0 until NUMBER_OF_COLUMN_TILES) {
                tilemap[iRow][iCol] = Tile.getTile(
                    layout[iRow][iCol],
                    spriteSheet,
                    getRectByIndex(iRow, iCol)
                )
            }
        }
        val config = Bitmap.Config.ARGB_8888
        mapBitmap = Bitmap.createBitmap(
            NUMBER_OF_COLUMN_TILES * TILE_WIDTH_PIXELS,
            NUMBER_OF_ROW_TILES * TILE_HEIGHT_PIXELS,
            config
        )
        val mapCanvas = Canvas(mapBitmap)
        for (iRow in 0 until NUMBER_OF_ROW_TILES) {
            for (iCol in 0 until NUMBER_OF_COLUMN_TILES) {
                tilemap[iRow][iCol]!!.draw(mapCanvas)
            }
        }
    }

    private fun getRectByIndex(idxRow: Int, idxCol: Int): Rect {
        return Rect(
            idxCol * TILE_WIDTH_PIXELS,
            idxRow * TILE_HEIGHT_PIXELS,
            (idxCol + 1) * TILE_WIDTH_PIXELS,
            (idxRow + 1) * TILE_HEIGHT_PIXELS
        )
    }

    fun draw(canvas: Canvas, gameDisplay: GameDisplay) {
        canvas.drawBitmap(
            mapBitmap,
            gameDisplay.gameRect,
            gameDisplay.displayRect,
            null
        )
    }
}