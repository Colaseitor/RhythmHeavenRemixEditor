package io.github.chrislo27.rhre3.track.tracker

import com.badlogic.gdx.graphics.Color
import io.github.chrislo27.rhre3.editor.Editor
import io.github.chrislo27.rhre3.theme.Theme


abstract class Tracker<SELF : Tracker<SELF>>(val container: TrackerContainer<SELF>,
                                             var beat: Float, width: Float) {

    var width: Float = width
        set(value) {
            field = value.coerceAtLeast(0f)
        }
    val isZeroWidth: Boolean
        get() = width <= 0f
    val endBeat: Float
        get() = beat + width

    var text: String = ""
        protected set

    abstract fun getColour(theme: Theme): Color

    abstract fun editAction(editor: Editor)

    abstract fun createResizeCopy(beat: Float, width: Float): Tracker<SELF>

    open fun onScroll(amount: Int, control: Boolean, shift: Boolean): Boolean = false

}