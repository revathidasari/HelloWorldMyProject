package com.example.koin2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewManager
import androidx.annotation.Nullable

//class VisualizerView(context: Context?, attrs: AttributeSet?): View(context,attrs) {
class VisualizerView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var LINE_WIDTH = 1 // width of visualizer lines

    var LINE_SCALE = 75 // scales visualizer lines


    var MAX_AMPLITUDE = 32767
    private var amplitudes: ArrayList<Float>? = null
    //private var linePaint: Paint? = null
    var linePaint:Paint = Paint()
    var width_view = 0
    var height_view = 0
     var density = resources.displayMetrics.densityDpi
    var stroke = 0f

    //fun VisualizerView(context: Context?, @Nullable attrs: AttributeSet?) {
      //  super.getContext()

   //     super.VisualizerView(context, attrs)
/*        density = this.resources.displayMetrics.densityDpi //Get the display DPI
        linePaint = Paint()
        linePaint!!.color = Color.GREEN
        linePaint!!.isAntiAlias = true //Add AntiAlias for displaying strokes that are less than 1
    }*/

    override fun onSizeChanged(w: Int, h: Int, oldW: Int, oldH: Int) {
        linePaint.color = Color.GREEN
        linePaint.isAntiAlias = true
        width_view = w
        height_view = h
        amplitudes = ArrayList(width * 2)
        stroke =
            width * (density.toFloat() / 160) / 1000 //Calculate actual pixel size for the view based on view width and dpi
        linePaint?.strokeWidth= stroke
    }

    /**
     * Add a new value of int to the visualizer array
     * @param amplitude Int value
     */
    fun addAmplitude(amplitude: Int) {
        invalidate()
        val scaledHeight = amplitude.toFloat() / MAX_AMPLITUDE * (height - 1)
        amplitudes!!.add(scaledHeight)
    }

    /**
     * Clears Visualization
     */
    fun clear() {
        amplitudes!!.clear()
    }

    override fun onDraw(canvas: Canvas) {
        val middle = height / 2 // get the middle of the View
        var curX = 0f // start curX at zero

        // for each item in the amplitudes ArrayList
        for (power in amplitudes!!) {

            // draw a line representing this item in the amplitudes ArrayList
            canvas.drawLine(
                curX, middle + power / 2, curX, middle
                        - power / 2, linePaint!!
            )
            curX += stroke // increase X by line width
        }
    }

}

private fun View.VisualizerView(context: Context?, attrs: AttributeSet?) {


}
