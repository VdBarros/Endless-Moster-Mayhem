package com.vinibarros.endlessmonstermayhem.game.graphics

import android.graphics.Canvas
import android.graphics.Rect

class Sprite(private val spriteSheet: SpriteSheet, rect: Rect) {
    var rect: Rect

    init {
        this.rect = rect
    }

    fun draw(canvas: Canvas, dstRect: Rect) {
        canvas.drawBitmap(
            spriteSheet.bitmap,
            rect,
            dstRect,
            null
        )
    }

    val width: Int
        get() = rect.width()
    val height: Int
        get() = rect.height()
}