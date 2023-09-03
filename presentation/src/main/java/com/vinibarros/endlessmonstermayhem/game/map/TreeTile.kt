package com.vinibarros.endlessmonstermayhem.game.map

import android.graphics.Canvas
import android.graphics.Rect
import com.vinibarros.endlessmonstermayhem.game.graphics.Sprite
import com.vinibarros.endlessmonstermayhem.game.graphics.SpriteSheet


internal class TreeTile(spriteSheet: SpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    private val grassSprite: Sprite
    private val treeSprite: Sprite

    init {
        grassSprite = spriteSheet.grassSprite
        treeSprite = spriteSheet.treeSprite
    }

    override fun draw(canvas: Canvas) {
        grassSprite.draw(canvas, mapLocationRect.left, mapLocationRect.top)
        treeSprite.draw(canvas, mapLocationRect.left, mapLocationRect.top)
    }
}