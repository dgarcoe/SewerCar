package com.dgarcoe.sewercar.gamestates

import com.dgarcoe.sewercar.SewerCarGame

/**
 * Created by Daniel on 23/06/2019.
 */
abstract class SewerCarGameState(val game:SewerCarGame) {
    open fun startGame():Int = throw UnsupportedOperationException("Operation not supported")
    open fun endGame():Int= throw UnsupportedOperationException("Operation not supported")
    open fun goMainMenu():Int = throw UnsupportedOperationException("Operation not supported")
    open fun goOptionsMenu():Int = throw UnsupportedOperationException("Operation not supported")
}