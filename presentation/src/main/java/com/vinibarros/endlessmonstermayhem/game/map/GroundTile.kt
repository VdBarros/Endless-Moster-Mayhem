package com.vinibarros.endlessmonstermayhem.game.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import com.vinibarros.endlessmonstermayhem.game.graphics.Sprite
import com.vinibarros.endlessmonstermayhem.game.graphics.SpriteSheet


internal class GroundTile(spriteSheet: SpriteSheet, mapLocationRect: Rect) :
    Tile(mapLocationRect) {
    private val sprite: Sprite

    init {
        sprite = spriteSheet.groundSprite
    }

    override fun draw(canvas: Canvas) {
        sprite.draw(canvas, mapLocationRect)
    }
}