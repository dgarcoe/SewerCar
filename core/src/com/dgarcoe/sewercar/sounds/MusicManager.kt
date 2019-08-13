package com.dgarcoe.sewercar.sounds

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music


/**
 * Created by Daniel on 13/08/2019.
 */
class MusicManager {

    private var currentTrack: Music? = null

    val isLooping: Boolean
        get() = this.currentTrack!!.isLooping()

    val isPlaying: Boolean
        get() = this.currentTrack!!.isPlaying()

    fun loadMusicTrack(path: String) {
        currentTrack = Gdx.audio.newMusic(Gdx.files.internal(path))
    }

    fun playCurrentTrack(loop: Boolean) {
        currentTrack!!.setLooping(loop)
        currentTrack!!.play()
    }

    fun changeMusicTrack(path: String, loop: Boolean) {
        currentTrack!!.setVolume(0.5f)
        currentTrack!!.stop()
        currentTrack!!.dispose()
        loadMusicTrack(path)
        playCurrentTrack(loop)
    }

    fun changeVolume(volume: Float) {
        currentTrack!!.setVolume(volume)
    }

    fun disposeMusicTrack() {
        currentTrack!!.stop()
        currentTrack!!.dispose()
    }

}