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
                layout[row][col] = 2
            }
        }

        layout[4][17] = 4
        layout[4][18] = 3
        layout[4][19] = 3
        layout[4][20] = 4
    }

    companion object {
        const val TILE_WIDTH_PIXELS = 64
        const val TILE_HEIGHT_PIXELS = 64
        const val NUMBER_OF_ROW_TILES = 60
        const val NUMBER_OF_COLUMN_TILES = 60
    }
}