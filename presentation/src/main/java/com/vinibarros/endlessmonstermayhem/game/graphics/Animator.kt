package com.vinibarros.endlessmonstermayhem.game.graphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import com.vinibarros.endlessmonstermayhem.game.core.GameDisplay
import com.vinibarros.endlessmonstermayhem.game.objects.Player
import com.vinibarros.endlessmonstermayhem.game.objects.PlayerState
import com.vinibarros.endlessmonstermayhem.util.getPixelFromDp


class Animator(private val spriteSheet: SpriteSheet) {

    private var idleFrameIndex = 0
    private var updatesBeforeNextIdleFrame = MAX_UPDATES_BEFORE_NEXT_FRAME
    private var movingFrameIndex = 0
    private var updatesBeforeNextMovingFrame = MAX_UPDATES_BEFORE_NEXT_FRAME
    private var dyingFrameIndex = 0
    private var updatesBeforeNextDyingFrame = MAX_UPDATES_BEFORE_NEXT_FRAME
    private lateinit var player: Player

    var dyingAnimationFinished = false

    fun draw(canvas: Canvas, gameDisplay: GameDisplay, player: Player, context: Context) {
        this.player = player

        when (player.getPlayerState().state) {
            PlayerState.State.NOT_MOVING -> {
                updatesBeforeNextIdleFrame--
                if (updatesBeforeNextIdleFrame == 0) {
                    updatesBeforeNextIdleFrame = MAX_UPDATES_BEFORE_NEXT_FRAME
                    toggleIdleFrame()
                }
                drawPlayerFrame(
                    canvas,
                    gameDisplay,
                    player,
                    spriteSheet.getIdleSprite(player.directionX, idleFrameIndex),
                    context
                )
            }

            PlayerState.State.IS_MOVING -> {
                updatesBeforeNextMovingFrame--
                if (updatesBeforeNextMovingFrame == 0) {
                    updatesBeforeNextMovingFrame = MAX_UPDATES_BEFORE_NEXT_FRAME
                    toggleMovingFrame()
                }
                drawPlayerFrame(
                    canvas,
                    gameDisplay,
                    player,
                    spriteSheet.getRunningSprite(player.velocityX, movingFrameIndex),
                    context
                )
            }

            PlayerState.State.DEAD -> {
                if (!dyingAnimationFinished) {
                    updatesBeforeNextDyingFrame--
                    if (updatesBeforeNextDyingFrame == 0) {
                        updatesBeforeNextDyingFrame = MAX_UPDATES_BEFORE_NEXT_FRAME
                        toggleDyingFrame()
                    }
                }
                drawPlayerFrame(
                    canvas,
                    gameDisplay,
                    player,
                    spriteSheet.getDyingSprite(player.directionX, dyingFrameIndex),
                    context
                )
            }
        }
    }

    private fun toggleIdleFrame() {
        idleFrameIndex = (idleFrameIndex + 1) % spriteSheet.idleSpriteCount
    }

    private fun toggleMovingFrame() {
        movingFrameIndex = (movingFrameIndex + 1) % spriteSheet.runningSpriteCount
    }

    private fun toggleDyingFrame() {
        if (dyingFrameIndex < spriteSheet.dyingSpriteCount - 1) dyingFrameIndex++
        else dyingAnimationFinished = true
    }

    private fun drawPlayerFrame(
        canvas: Canvas,
        gameDisplay: GameDisplay,
        player: Player,
        sprite: Sprite,
        context: Context
    ) {
        val halfWidth = context.getPixelFromDp(sprite.width) / 2
        val halfHeight = context.getPixelFromDp(sprite.height) / 2

        sprite.draw(
            canvas,
            Rect(
                gameDisplay.gameToDisplayCoordinatesX(player.positionX).toInt() - halfWidth.toInt(),
                gameDisplay.gameToDisplayCoordinatesY(player.positionY)
                    .toInt() - halfHeight.toInt(),
                gameDisplay.gameToDisplayCoordinatesX(player.positionX).toInt() + halfWidth.toInt(),
                gameDisplay.gameToDisplayCoordinatesY(player.positionY).toInt() + halfHeight.toInt()
            )
        )
    }

    companion object {
        private const val MAX_UPDATES_BEFORE_NEXT_FRAME = 5
    }
}