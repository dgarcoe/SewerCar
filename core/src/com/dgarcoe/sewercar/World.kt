package com.dgarcoe.sewercar

import com.dgarcoe.sewercar.entities.Sewer
import com.badlogic.gdx.math.Vector2
import com.dgarcoe.sewercar.entities.PlayerCar
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import java.util.*


/**
 * Created by Daniel on 29/06/2019.
 */
class World {

    val MIN_SEWERS = 1
    val MAX_SEWERS = 3

    val MIDDLE_SCENE = 64
    val LEFT_BORDER = 1
    val RIGHT_BORDER = 105

    var sewers = mutableListOf<Sewer>()
    var player: PlayerCar? = null

    var camOffsetUp: Float = 0.toFloat()
    var camOffsetDown: Float = 0.toFloat()

    var viewportWidth: Int = 0
    var viewportHeight: Int = 0

    init {
        player = PlayerCar(Vector2(85f, 10f), Rectangle(85f,10f,24f,48f),Vector2(0f,0f))
    }

    fun cleanAll() {

        //Remove sewers
        if (sewers.size !== 0) {
            sewers.clear()
        }
        Gdx.app.log("WORLD", "Sewers list size: " + sewers.size)

    }

    fun generateSewer() {

        val randomGenerator = Random()

        val numberSewers = randomGenerator.nextInt((MAX_SEWERS+1)-MIN_SEWERS)+MIN_SEWERS
        var previousPosX = 0
        var positionX: Int

        for (i in 1..numberSewers) {
            if (previousPosX<MIDDLE_SCENE && previousPosX!=0) {
                positionX = randomGenerator.nextInt(RIGHT_BORDER-MIDDLE_SCENE)+MIDDLE_SCENE
            } else if (previousPosX>MIDDLE_SCENE && previousPosX!=0){
                positionX = randomGenerator.nextInt(MIDDLE_SCENE-LEFT_BORDER)+LEFT_BORDER
            } else {
                positionX = randomGenerator.nextInt(RIGHT_BORDER-LEFT_BORDER)+LEFT_BORDER
            }

            previousPosX = positionX
            val offsetY = randomGenerator.nextInt(80-24)+24
            val positionY = 250+offsetY*(i-1)

            sewers.add(Sewer(Vector2(positionX.toFloat(),positionY.toFloat()), Rectangle(positionX.toFloat(),positionY.toFloat(),22f,22f), Vector2(22f,22f)))
        }
    }

}