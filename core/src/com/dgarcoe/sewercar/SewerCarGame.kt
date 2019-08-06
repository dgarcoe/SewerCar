package com.dgarcoe.sewercar

import com.badlogic.gdx.*
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.dgarcoe.sewercar.gamestates.GameOverState
import com.dgarcoe.sewercar.gamestates.MainMenuState
import com.dgarcoe.sewercar.gamestates.PlayingState
import com.dgarcoe.sewercar.gamestates.SewerCarGameState
import com.dgarcoe.sewercar.screens.GameOverScreen
import com.dgarcoe.sewercar.screens.MainMenuScreen
import com.dgarcoe.sewercar.screens.PlayingScreen
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.ObjectMap





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

        val assetManager = AssetManager()

        val generatorTitle = FreeTypeFontGenerator(Gdx.files.internal("fonts/Facon.ttf"))
        val generatorButtons = FreeTypeFontGenerator(Gdx.files.internal("fonts/hyperspd.ttf"))
        val parameterTitle = FreeTypeFontGenerator.FreeTypeFontParameter()
        val parameterButtons = FreeTypeFontGenerator.FreeTypeFontParameter()
        val parameterScoreInGame = FreeTypeFontGenerator.FreeTypeFontParameter()
        val parameterScoreOver = FreeTypeFontGenerator.FreeTypeFontParameter()


        if (Gdx.graphics.height>1500) {
            parameterTitle.size = 140
            parameterButtons.size = 50
            parameterScoreInGame.size = 50
            parameterScoreOver.size = 40
        } else {
            parameterTitle.size = 90
            parameterButtons.size = 25
            parameterScoreInGame.size = 25
            parameterScoreOver.size = 20
        }

        parameterTitle.color = Color.RED
        parameterButtons.color = Color.BLACK
        parameterScoreInGame.color = Color.RED
        parameterScoreOver.color = Color.WHITE

        val fontTitle = generatorTitle.generateFont(parameterTitle)
        generatorTitle.dispose()

        val fontButtons = generatorButtons.generateFont(parameterButtons)
        val fontScoreInGame = generatorButtons.generateFont(parameterScoreInGame)
        val fontScoreOver = generatorButtons.generateFont(parameterScoreOver)
        generatorButtons.dispose()

        preferences = Gdx.app.getPreferences("SewerCarPreferences")

        skin = Skin()
        skin.add("font",fontButtons)
        skin.addRegions(TextureAtlas(Gdx.files.internal("skin/metal/skin/metal-ui.atlas")))
        skin.load(Gdx.files.internal("skin/metal/skin/metal-ui.json"))

        mainMenuScreen = MainMenuScreen(this,skin,fontTitle)
        playingScreen = PlayingScreen(this,skin,fontScoreInGame)
        gameOverScreen = GameOverScreen(this,skin,fontTitle,fontScoreOver)


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
