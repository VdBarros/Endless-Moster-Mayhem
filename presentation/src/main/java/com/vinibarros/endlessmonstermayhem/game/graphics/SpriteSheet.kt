package com.vinibarros.endlessmonstermayhem.game.graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import com.vinibarros.endlessmonstermayhem.presentation.R

class SpriteSheet(context: Context) {
    private val bitmapOptions: BitmapFactory.Options = BitmapFactory.Options().apply {
        inScaled = false
    }
    private val mapBitmap: Bitmap by lazy { BitmapFactory.decodeResource(context.resources, R.drawable.sprite_sheet, bitmapOptions) }
    private val playerIdleBitmap: Bitmap by lazy { BitmapFactory.decodeResource(context.resources, R.drawable.wizard_idle_sheet, bitmapOptions) }
    private val playerRunningBitmap: Bitmap by lazy { BitmapFactory.decodeResource(context.resources, R.drawable.wizard_running_sheet, bitmapOptions) }
    private val playerDyingBitmap: Bitmap by lazy { BitmapFactory.decodeResource(context.resources, R.drawable.wizard_dying_sheet, bitmapOptions) }

    fun getIdleSprite(directionX: Double, idxIdleFrame: Int): Sprite {
        val spriteArray = if (directionX > 0) getPlayerIdleSpriteArray() else getPlayerFlippedIdleSpriteArray()
        return spriteArray[idxIdleFrame]
    }

    fun getRunningSprite(velocityX: Double, idxMovingFrame: Int): Sprite {
        val spriteArray = if (velocityX > 0) getPlayerRunningSpriteArray() else getPlayerFlippedRunningSpriteArray()
        return spriteArray[idxMovingFrame]
    }

    fun getDyingSprite(directionX: Double, idxMovingFrame: Int): Sprite {
        val spriteArray = if (directionX > 0) getPlayerDyingSpriteArray() else getPlayerFlippedDyingSpriteArray()
        return spriteArray[idxMovingFrame]
    }

    val idleSpriteCount: Int
        get() = getPlayerIdleSpriteArray().size

    val runningSpriteCount: Int
        get() = getPlayerRunningSpriteArray().size

    val dyingSpriteCount: Int
        get() = getPlayerDyingSpriteArray().size

    private fun getPlayerIdleSpriteArray(): Array<Sprite> {
        return Array(4) { idx ->
            Sprite(playerIdleBitmap, Rect(idx * SPRITE_WIDTH_PIXELS, 0, (idx + 1) * SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS))
        }
    }

    private fun getPlayerRunningSpriteArray(): Array<Sprite> {
        return Array(6) { idx ->
            Sprite(playerRunningBitmap, Rect(idx * SPRITE_WIDTH_PIXELS, 0, (idx + 1) * SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS))
        }
    }

    private fun getPlayerFlippedRunningSpriteArray(): Array<Sprite> {
        val flippedBitmap = flipBitmapHorizontally(playerRunningBitmap)
        return Array(6) { idx ->
            Sprite(flippedBitmap, Rect(idx * SPRITE_WIDTH_PIXELS, 0, (idx + 1) * SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS))
        }
    }

    private fun getPlayerDyingSpriteArray(): Array<Sprite> {
        return Array(6) { idx ->
            Sprite(playerDyingBitmap, Rect(idx * SPRITE_WIDTH_PIXELS, 0, (idx + 1) * SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS))
        }
    }

    private fun getPlayerFlippedDyingSpriteArray(): Array<Sprite> {
        val flippedBitmap = flipBitmapHorizontally(playerDyingBitmap)
        return Array(6) { idx ->
            Sprite(flippedBitmap, Rect(idx * SPRITE_WIDTH_PIXELS, 0, (idx + 1) * SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS))
        }
    }

    private fun getPlayerFlippedIdleSpriteArray(): Array<Sprite> {
        val flippedBitmap = flipBitmapHorizontally(playerIdleBitmap)
        return Array(4) { idx ->
            Sprite(flippedBitmap, Rect(idx * SPRITE_WIDTH_PIXELS, 0, (idx + 1) * SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS))
        }
    }

    private fun flipBitmapHorizontally(bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.postScale(-1f, 1f)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    val waterSprite: Sprite
        get() = getSpriteByIndex(1, 0)
    val lavaSprite: Sprite
        get() = getSpriteByIndex(1, 1)
    val groundSprite: Sprite
        get() = getSpriteByIndex(1, 2)
    val grassSprite: Sprite
        get() = getSpriteByIndex(1, 3)
    val treeSprite: Sprite
        get() = getSpriteByIndex(1, 4)

    private fun getSpriteByIndex(idxRow: Int, idxCol: Int): Sprite {
        return Sprite(
            mapBitmap, Rect(
                idxCol * SPRITE_WIDTH_PIXELS,
                idxRow * SPRITE_HEIGHT_PIXELS,
                (idxCol + 1) * SPRITE_WIDTH_PIXELS,
                (idxRow + 1) * SPRITE_HEIGHT_PIXELS
            )
        )
    }

    companion object {
        private const val SPRITE_WIDTH_PIXELS = 64
        private const val SPRITE_HEIGHT_PIXELS = 64
    }
}