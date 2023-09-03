package com.vinibarros.endlessmonstermayhem.game.graphics

import android.graphics.Canvas
import android.graphics.Rect

class Sprite(private val spriteSheet: SpriteSheet, rect: Rect) {
    private val rect: Rect

    init {
        this.rect = rect
    }

    fun draw(canvas: Canvas, x: Int, y: Int) {
        canvas.drawBitmap(
            spriteSheet.bitmap,
            rect,
            Rect(x, y, x + width, y + height),
            null
        )
    }

    val width: Int
        get() = rect.width()
    val height: Int
        get() = rect.height()
}