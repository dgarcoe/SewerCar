package com.dgarcoe.sewercar.gamestates

import com.dgarcoe.sewercar.SewerCarGame

/**
 * Created by Daniel on 02/08/2019.
 */
class GameOverState(game: SewerCarGame) :SewerCarGameState(game) {

    override fun startGame(): Int {
        game.currentState = game.playingState
        game.startNewScreen(game.playingScreen)
        return 0
    }

    override fun goMainMenu(): Int {
        game.currentState = game.mainMenuState
        game.startNewScreen(game.mainMenuScreen)
        return 0
    }

}