package com.vinibarros.endlessmonstermayhem.game.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.vinibarros.endlessmonstermayhem.game.core.GameDisplay
import com.vinibarros.endlessmonstermayhem.game.graphics.SpriteSheet
import com.vinibarros.endlessmonstermayhem.game.map.MapLayout.Companion.NUMBER_OF_COLUMN_TILES
import com.vinibarros.endlessmonstermayhem.game.map.MapLayout.Companion.NUMBER_OF_ROW_TILES
import com.vinibarros.endlessmonstermayhem.game.map.MapLayout.Companion.TILE_HEIGHT_PIXELS
import com.vinibarros.endlessmonstermayhem.game.map.MapLayout.Companion.TILE_WIDTH_PIXELS
import com.vinibarros.endlessmonstermayhem.util.getPixelFromDp


class TileMap(spriteSheet: SpriteSheet, val context: Context) {
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
        tilemap = Array(NUMBER_OF_ROW_TILES) {
            arrayOfNulls(NUMBER_OF_COLUMN_TILES)
        }
        for (iRow in 0 until NUMBER_OF_ROW_TILES) {
            for (iCol in 0 until NUMBER_OF_COLUMN_TILES) {
                tilemap[iRow][iCol] = Tile.getTile(
                    layout[iRow][iCol],
                    spriteSheet,
                    getRectByIndex(iRow, iCol, context)
                )
            }
        }
        val config = Bitmap.Config.ARGB_8888
        mapBitmap = Bitmap.createBitmap(
            context.getPixelFromDp(NUMBER_OF_COLUMN_TILES * TILE_WIDTH_PIXELS).toInt(),
            context.getPixelFromDp(NUMBER_OF_ROW_TILES * TILE_HEIGHT_PIXELS).toInt(),
            config
        )
        val mapCanvas = Canvas(mapBitmap)
        for (iRow in 0 until NUMBER_OF_ROW_TILES) {
            for (iCol in 0 until NUMBER_OF_COLUMN_TILES) {
                tilemap[iRow][iCol]?.draw(mapCanvas)
            }
        }
    }

    private fun getRectByIndex(idxRow: Int, idxCol: Int, context: Context): Rect {
        return Rect(
            idxCol * context.getPixelFromDp(TILE_WIDTH_PIXELS).toInt(),
            idxRow * context.getPixelFromDp(TILE_HEIGHT_PIXELS).toInt(),
            (idxCol + 1) * context.getPixelFromDp(TILE_WIDTH_PIXELS).toInt(),
            (idxRow + 1) * context.getPixelFromDp(TILE_HEIGHT_PIXELS).toInt()
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