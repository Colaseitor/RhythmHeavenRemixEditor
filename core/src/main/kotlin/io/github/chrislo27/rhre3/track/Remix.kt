package io.github.chrislo27.rhre3.track

import chrislo27.rhre.oopsies.ActionHistory
import com.badlogic.gdx.graphics.OrthographicCamera
import io.github.chrislo27.rhre3.RHRE3Application
import io.github.chrislo27.rhre3.editor.Editor
import io.github.chrislo27.rhre3.entity.Entity
import io.github.chrislo27.rhre3.tempo.Tempos


class Remix(val camera: OrthographicCamera, val editor: Editor) : ActionHistory<Remix>() {

    val main: RHRE3Application
        get() = editor.main
    val entities: MutableList<Entity> = mutableListOf()
    val tempos: Tempos = Tempos()

    var seconds: Float = 0f
        set(value) {
            field = value
            beat = tempos.secondsToBeats(field)
        }
    var beat: Float = 0f
        private set

    fun entityUpdate(entity: Entity) {
        if (entity.playbackCompletion == PlaybackCompletion.WAITING) {
            if (beat in entity.bounds.x..(entity.bounds.x + entity.bounds.width)) {
                entity.playbackCompletion = PlaybackCompletion.PLAYING
                entity.onStart()
            }
        }

        if (entity.playbackCompletion == PlaybackCompletion.PLAYING) {
            entity.whilePlaying()
            if (entity.isFinished()) {
                entity.playbackCompletion = PlaybackCompletion.FINISHED
                entity.onEnd()
            }
        }
    }

    fun timeUpdate(delta: Float) {
        seconds += delta
        // TODO music sync

        entities.forEach { entity ->
            if (entity.playbackCompletion != PlaybackCompletion.FINISHED) {
                entityUpdate(entity)
            }
        }
    }

}