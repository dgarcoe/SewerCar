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
        val random = Random()

        val positionX = random.nextInt(84-8)+8

        sewers.add(Sewer(Vector2(positionX.toFloat(),300f), Rectangle(positionX.toFloat(),300f,22f,22f), Vector2(22f,22f)))

    }

}