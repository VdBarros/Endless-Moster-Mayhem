package com.vinibarros.endlessmonstermayhem.game.graphics

import android.graphics.Canvas
import com.vinibarros.endlessmonstermayhem.game.core.GameDisplay
import com.vinibarros.endlessmonstermayhem.game.objects.Player
import com.vinibarros.endlessmonstermayhem.game.objects.PlayerState


class Animator(private val playerSpriteArray: Array<Sprite>) {
    private val idxNotMovingFrame = 0
    private var idxMovingFrame = 1
    private var updatesBeforeNextMoveFrame = 0
    fun draw(canvas: Canvas, gameDisplay: GameDisplay, player: Player) {
        when (player.getPlayerState().state) {
            PlayerState.State.NOT_MOVING -> drawFrame(
                canvas, gameDisplay, player,
                playerSpriteArray[idxNotMovingFrame]
            )

            PlayerState.State.STARED_MOVING -> {
                updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME
                drawFrame(canvas, gameDisplay, player, playerSpriteArray[idxMovingFrame])
            }

            PlayerState.State.IS_MOVING -> {
                updatesBeforeNextMoveFrame--
                if (updatesBeforeNextMoveFrame == 0) {
                    updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME
                    toggleIdxMovingFrame()
                }
                drawFrame(canvas, gameDisplay, player, playerSpriteArray[idxMovingFrame])
            }

            else -> {}
        }
    }

    private fun toggleIdxMovingFrame() {
        idxMovingFrame = if (idxMovingFrame == 1) 2 else 1
    }

    fun drawFrame(canvas: Canvas, gameDisplay: GameDisplay, player: Player, sprite: Sprite) {
        sprite.draw(
            canvas,
            gameDisplay.gameToDisplayCoordinatesX(player.positionX).toInt() - sprite.width / 2,
            gameDisplay.gameToDisplayCoordinatesY(player.positionY).toInt() - sprite.height / 2
        )
    }

    companion object {
        private const val MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME = 5
    }
}