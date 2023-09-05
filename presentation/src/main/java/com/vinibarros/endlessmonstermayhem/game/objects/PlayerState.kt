package com.vinibarros.endlessmonstermayhem.game.objects

class PlayerState(private val player: Player) {
    enum class State {
        NOT_MOVING, STARTED_MOVING, IS_MOVING
    }

    var state: State
        private set

    init {
        state = State.NOT_MOVING
    }

    fun update() {
        when (state) {
            State.NOT_MOVING -> if (player.velocityX != 0.0 || player.velocityY != 0.0) state =
                State.STARTED_MOVING

            State.STARTED_MOVING -> if (player.velocityX != 0.0 || player.velocityY != 0.0) state =
                State.IS_MOVING

            State.IS_MOVING -> if (player.velocityX == 0.0 && player.velocityY == 0.0) state =
                State.NOT_MOVING
        }
    }
}