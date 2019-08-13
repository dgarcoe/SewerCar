package com.dgarcoe.sewercar.entities

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

/**
 * Created by Daniel on 29/06/2019.
 */
class Sewer(position: Vector2, bounds: Circle) :
        GameObjectWithCircleBounds(position,bounds) {


    init {
        damage = 5F
        points = 10
    }

    fun update(positionUpdateY: Float, boundsUpdateY: Float) {

        bounds.y = boundsUpdateY
        position.y = positionUpdateY


        if (position.y < -32f) {
            alive = false
        }
    }

}