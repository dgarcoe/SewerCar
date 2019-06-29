package com.dgarcoe.sewercar.gamestates

import com.dgarcoe.sewercar.SewerCarGame

/**
 * Created by Daniel on 23/06/2019.
 */
class MainMenuState(game: SewerCarGame) : SewerCarGameState(game)  {

    override fun startGame(): Int {
        game.currentState = game.playingState
        game.startNewScreen(game.playingScreen)
        return 0
    }
}