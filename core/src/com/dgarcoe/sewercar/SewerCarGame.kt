package com.dgarcoe.sewercar

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.dgarcoe.sewercar.gamestates.GameOverState
import com.dgarcoe.sewercar.gamestates.MainMenuState
import com.dgarcoe.sewercar.gamestates.PlayingState
import com.dgarcoe.sewercar.gamestates.SewerCarGameState
import com.dgarcoe.sewercar.screens.GameOverScreen
import com.dgarcoe.sewercar.screens.MainMenuScreen
import com.dgarcoe.sewercar.screens.PlayingScreen

class SewerCarGame : Game() {

    private val TAG = "SewerCarGame"

    var preferences : Preferences? = null

    lateinit var currentState : SewerCarGameState
    lateinit var currentScreen : Screen

    val mainMenuState : MainMenuState = MainMenuState(this)
    lateinit var mainMenuScreen : MainMenuScreen

    val playingState : PlayingState = PlayingState(this)
    lateinit var playingScreen : PlayingScreen

    val gameOverState: GameOverState = GameOverState(this)
    lateinit var gameOverScreen : GameOverScreen

    lateinit var skin: Skin

    lateinit var world: World

    override fun create() {
        preferences = Gdx.app.getPreferences("SewerCarPreferences")

        skin = Skin(Gdx.files.internal("skin/metal/skin/metal-ui.json"))
        mainMenuScreen = MainMenuScreen(this,skin)
        playingScreen = PlayingScreen(this,skin)
        gameOverScreen = GameOverScreen(this,skin)


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
        world.player!!.init()
        currentState.startGame()
    }

    fun endGame() {
        world.cleanAll()
        val highScore = preferences!!.getLong("HiScore")
        if (world.player!!.score>highScore) {
            preferences!!.putLong("HiScore",world.player!!.score)
            preferences!!.flush()
        }
        currentState.endGame()
    }

    fun goOptionsMenu() {
        currentState.goOptionsMenu()
    }

    fun goMainMenu() {
        currentState.goMainMenu()
    }
}
