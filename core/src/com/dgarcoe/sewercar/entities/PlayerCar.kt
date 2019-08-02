package com.dgarcoe.sewercar.entities

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor

/**
 * Created by Daniel on 29/06/2019.
 */
class PlayerCar(position: Vector2, bounds: Rectangle, boundsSize: Vector2) :
        Car(position,bounds,boundsSize) {

    var score: Long = 0

    fun init() {
        health = 100F
        score = 0
        position.x = 85F
        position.y = 10F
        bounds.x = 85F
        bounds.y = 10F
    }

    fun update(positionUpdate: Vector3, boundsUpdate: Vector3) {
        position.x = positionUpdate.x
        bounds.x = boundsUpdate.x
    }

}