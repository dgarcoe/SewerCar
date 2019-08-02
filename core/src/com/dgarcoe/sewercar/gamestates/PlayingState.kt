package com.dgarcoe.sewercar.gamestates

import com.dgarcoe.sewercar.SewerCarGame

/**
 * Created by Daniel on 23/06/2019.
 */
class PlayingState(game: SewerCarGame) : SewerCarGameState(game) {

    override fun endGame(): Int {
        game.currentState = game.gameOverState
        game.startNewScreen(game.gameOverScreen)
        return 0
    }
}