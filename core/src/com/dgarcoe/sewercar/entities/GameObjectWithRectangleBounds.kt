package com.dgarcoe.sewercar.entities

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

/**
 * Created by Daniel on 13/08/2019.
 */
abstract class GameObjectWithRectangleBounds(position: Vector2, var bounds: Rectangle, var boundsSize: Vector2) :
        GameObject(position) {


    fun collided(obj: GameObjectWithRectangleBounds): Boolean {
        return Intersector.overlaps(this.bounds,obj.bounds) && collidable
    }

}