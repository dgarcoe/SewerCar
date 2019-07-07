package com.dgarcoe.sewercar.entities

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor

/**
 * Created by Daniel on 29/06/2019.
 */
class PlayerCar(position: Vector2, bounds: Rectangle, boundsSize: Vector2) :
        Car(position,bounds,boundsSize) {

    private val score: Long = 0

}