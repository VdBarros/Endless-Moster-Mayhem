package com.vinibarros.endlessmonstermayhem.game.graphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import com.vinibarros.endlessmonstermayhem.util.getPixelFromDp

class Sprite(private val spriteSheet: SpriteSheet, rect: Rect) {
    private val rect: Rect

    init {
        this.rect = rect
    }

    fun draw(canvas: Canvas, x: Int, y: Int, context: Context) {
        canvas.drawBitmap(
            spriteSheet.bitmap,
            rect,
            Rect(x, y, x + context.getPixelFromDp(width).toInt(), y + context.getPixelFromDp(height).toInt()),
            null
        )
    }

    val width: Int
        get() = rect.width()
    val height: Int
        get() = rect.height()
}