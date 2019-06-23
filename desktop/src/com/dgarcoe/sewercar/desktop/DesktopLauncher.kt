package com.dgarcoe.sewercar.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.dgarcoe.sewercar.SewerCarGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.title = "Sewer Car"
        config.width = 1024
        config.height = 768
        LwjglApplication(SewerCarGame(), config)
    }
}
