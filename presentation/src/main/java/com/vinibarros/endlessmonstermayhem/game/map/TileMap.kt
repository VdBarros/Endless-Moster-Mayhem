package com.vinibarros.endlessmonstermayhem.game.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import com.vinibarros.endlessmonstermayhem.game.core.GameDisplay
import com.vinibarros.endlessmonstermayhem.game.graphics.SpriteSheet
import com.vinibarros.endlessmonstermayhem.game.map.MapLayout.Companion.NUMBER_OF_COLUMN_TILES
import com.vinibarros.endlessmonstermayhem.game.map.MapLayout.Companion.NUMBER_OF_ROW_TILES
import com.vinibarros.endlessmonstermayhem.game.map.MapLayout.Companion.TILE_HEIGHT_PIXELS
import com.vinibarros.endlessmonstermayhem.game.map.MapLayout.Companion.TILE_WIDTH_PIXELS
import com.vinibarros.endlessmonstermayhem.util.getPixelFromDp
import kotlin.math.max
import kotlin.math.min


class TileMap(spriteSheet: SpriteSheet, val context: Context) {
    private val mapLayout: MapLayout = MapLayout()
    private lateinit var tilemap: Array<Array<Tile?>>
    private val spriteSheet: SpriteSheet
    private lateinit var mapBitmap: Bitmap
    private lateinit var tilePositions: Array<Array<Pair<Rect, Tile>?>>

    init {
        this.spriteSheet = spriteSheet
        initializeTilemap()
    }

    private fun initializeTilemap() {
        val layout: Array<IntArray> = mapLayout.layout
        tilemap = Array(NUMBER_OF_ROW_TILES) {
            arrayOfNulls(NUMBER_OF_COLUMN_TILES)
        }
        tilePositions = Array(NUMBER_OF_ROW_TILES) {
            arrayOfNulls(NUMBER_OF_COLUMN_TILES)
        }
        for (iRow in 0 until NUMBER_OF_ROW_TILES) {
            for (iCol in 0 until NUMBER_OF_COLUMN_TILES) {
                val rect = getRectByIndex(iRow, iCol, context)
                val tile = Tile.getTile(
                    layout[iRow][iCol],
                    spriteSheet,
                    rect
                )
                tilemap[iRow][iCol] = tile
                tilePositions[iRow][iCol] = Pair(rect, tile)
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

    fun checkPlayerCollision(playerCircle: RectF): Boolean {
        for (iRow in 0 until NUMBER_OF_ROW_TILES) {
            for (iCol in 0 until NUMBER_OF_COLUMN_TILES) {
                val tileRect = tilePositions[iRow][iCol]
                if (tileRect?.second is TreeTile) {
                    val closestX = max(tileRect.first.left.toFloat(), min(playerCircle.centerX(), tileRect.first.right.toFloat()))
                    val closestY = max(tileRect.first.top.toFloat(), min(playerCircle.centerY(), tileRect.first.bottom.toFloat()))
                    val distanceX = playerCircle.centerX() - closestX
                    val distanceY = playerCircle.centerY() - closestY
                    val playerRadius = playerCircle.width() / 2
                    val distanceSquared = distanceX * distanceX + distanceY * distanceY
                    val tileRadiusSquared = playerRadius * playerRadius
                    if (distanceSquared < tileRadiusSquared) {
                        return true
                    }
                }
            }
        }
        return false
    }
}