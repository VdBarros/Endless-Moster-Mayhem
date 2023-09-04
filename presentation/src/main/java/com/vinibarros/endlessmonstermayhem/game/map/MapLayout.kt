package com.vinibarros.endlessmonstermayhem.game.map

class MapLayout {
    lateinit var layout: Array<IntArray>
        private set

    init {
        initializeLayout()
    }

    private fun initializeLayout() {
        layout = Array(NUMBER_OF_ROW_TILES) {
            IntArray(
                NUMBER_OF_COLUMN_TILES
            )
        }
        for (row in 0 until NUMBER_OF_ROW_TILES) {
            for (col in 0 until NUMBER_OF_COLUMN_TILES) {
                if (row == 0 || row == NUMBER_OF_ROW_TILES - 1 || col == 0 || col == NUMBER_OF_COLUMN_TILES - 1) {
                    layout[row][col] = 4
                } else {
                    layout[row][col] = 2
                }
            }
        }
    }

    companion object {
        const val TILE_WIDTH_PIXELS = 32
        const val TILE_HEIGHT_PIXELS = 32
        const val NUMBER_OF_ROW_TILES = 60
        const val NUMBER_OF_COLUMN_TILES = 60
    }
}