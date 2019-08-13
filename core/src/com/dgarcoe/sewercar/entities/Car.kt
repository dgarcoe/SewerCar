package com.dgarcoe.sewercar.entities

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

/**
 * Created by Daniel on 29/06/2019.
 */
open class Car(position: Vector2, bounds: Rectangle, boundsSize: Vector2) :
        GameObjectWithRectangleBounds(position,bounds, boundsSize) {

}