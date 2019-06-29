package com.dgarcoe.sewercar

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.dgarcoe.sewercar.gamestates.MainMenuState
import com.dgarcoe.sewercar.gamestates.PlayingState
import com.dgarcoe.sewercar.gamestates.SewerCarGameState
import com.dgarcoe.sewercar.screens.MainMenuScreen
import com.dgarcoe.sewercar.screens.PlayingScreen

class SewerCarGame : Game() {

    private val TAG = "SewerCarGame"

    lateinit var currentState : SewerCarGameState
    lateinit var currentScreen : Screen

    val mainMenuState : MainMenuState = MainMenuState(this)
    lateinit var mainMenuScreen : MainMenuScreen

    val playingState : PlayingState = PlayingState(this)
    lateinit var playingScreen : PlayingScreen

    lateinit var skin: Skin

    lateinit var world: World

    override fun create() {
        skin = Skin(Gdx.files.internal("skin/metal/skin/metal-ui.json"))
        mainMenuScreen = MainMenuScreen(this,skin)
        playingScreen = PlayingScreen(this)

        world = World()

        currentState = mainMenuState
        currentScreen = mainMenuScreen
        setScreen(mainMenuScreen)
    }

    fun startNewScreen(screen:Screen) {
        currentScreen = screen
        super.setScreen(screen)
    }

    fun startGame() {
        currentState.startGame()
    }

    fun endGame() {
        currentState.endGame()
    }

    fun goOptionsMenu() {
        currentState.goOptionsMenu()
    }

    fun goMainMenu() {
        currentState.goMainMenu()
    }
}
