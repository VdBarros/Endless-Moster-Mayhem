package com.vinibarros.endlessmonstermayhem.game.graphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import com.vinibarros.endlessmonstermayhem.game.core.GameDisplay
import com.vinibarros.endlessmonstermayhem.game.objects.Player
import com.vinibarros.endlessmonstermayhem.game.objects.PlayerState
import com.vinibarros.endlessmonstermayhem.util.getPixelFromDp


class Animator(private val playerSpriteArray: Array<Sprite>) {
    private val idxNotMovingFrame = 0
    private var idxMovingFrame = 1
    private var updatesBeforeNextMoveFrame = 0
    fun draw(canvas: Canvas, gameDisplay: GameDisplay, player: Player, context: Context) {
        when (player.getPlayerState().state) {
            PlayerState.State.NOT_MOVING -> drawFrame(
                canvas, gameDisplay, player, playerSpriteArray[idxNotMovingFrame], context
            )

            PlayerState.State.STARED_MOVING -> {
                updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME
                drawFrame(canvas, gameDisplay, player, playerSpriteArray[idxMovingFrame], context)
            }

            PlayerState.State.IS_MOVING -> {
                updatesBeforeNextMoveFrame--
                if (updatesBeforeNextMoveFrame == 0) {
                    updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME
                    toggleIdxMovingFrame()
                }
                drawFrame(canvas, gameDisplay, player, playerSpriteArray[idxMovingFrame], context)
            }
        }
    }

    private fun toggleIdxMovingFrame() {
        idxMovingFrame = if (idxMovingFrame == 1) 2 else 1
    }

    private fun drawFrame(canvas: Canvas, gameDisplay: GameDisplay, player: Player, sprite: Sprite, context: Context) {
        sprite.draw(
            canvas,
            Rect(
                gameDisplay.gameToDisplayCoordinatesX(player.positionX).toInt() - (context.getPixelFromDp(sprite.width)/2).toInt(),
                gameDisplay.gameToDisplayCoordinatesY(player.positionY).toInt() - (context.getPixelFromDp(sprite.height)/2).toInt(),
                gameDisplay.gameToDisplayCoordinatesX(player.positionX).toInt() + (context.getPixelFromDp(sprite.width)/2).toInt(),
                gameDisplay.gameToDisplayCoordinatesY(player.positionY).toInt() + (context.getPixelFromDp(sprite.height)/2).toInt()
            )
        )
    }

    companion object {
        private const val MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME = 5
    }
}