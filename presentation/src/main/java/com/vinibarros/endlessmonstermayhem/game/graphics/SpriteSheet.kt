package com.vinibarros.endlessmonstermayhem.game.graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.vinibarros.endlessmonstermayhem.presentation.R


class SpriteSheet(context: Context) {
    val bitmap: Bitmap

    init {
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false
        bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.sprite_sheet, bitmapOptions)
    }

    val playerSpriteArray: Array<Sprite>
        get() {
            return arrayOf(
                Sprite(this, Rect(0 * 64, 0, 1 * 64, 64)),
                Sprite(this, Rect(1 * 64, 0, 2 * 64, 64)),
                Sprite(this, Rect(2 * 64, 0, 3 * 64, 64))
            )
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
            this, Rect(
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