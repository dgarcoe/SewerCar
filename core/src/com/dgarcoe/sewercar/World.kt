package com.dgarcoe.sewercar

import com.dgarcoe.sewercar.entities.Sewer
import com.badlogic.gdx.math.Vector2
import com.dgarcoe.sewercar.entities.PlayerCar
import java.nio.file.Files.size
import com.badlogic.gdx.Gdx




/**
 * Created by Daniel on 29/06/2019.
 */
class World {

    private var sewers = mutableListOf<Sewer>()
    private var player: PlayerCar? = null

    private val camOffsetUp: Float = 0.toFloat()
    private val camOffsetDown: Float = 0.toFloat()

    private val viewportWidth: Int = 0
    private val viewportHeight: Int = 0

    init {
        player = PlayerCar(Vector2(0f, 0f))
    }

    fun cleanAll() {

        //Remove sewers
        if (sewers.size !== 0) {
            sewers.clear()
        }
        Gdx.app.log("WORLD", "Sewers list size: " + sewers.size)

    }

}