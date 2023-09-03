package com.vinibarros.endlessmonstermayhem.game.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import com.vinibarros.endlessmonstermayhem.game.graphics.Sprite
import com.vinibarros.endlessmonstermayhem.game.graphics.SpriteSheet


internal class WaterTile(spriteSheet: SpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    private val sprite: Sprite

    init {
        sprite = spriteSheet.waterSprite
    }

    override fun draw(canvas: Canvas, context: Context) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top, context)
    }
}