package ay2021s1_cs2103_w16_3.finesse.ui.visualizations

import io.data2viz.color.Colors
import io.data2viz.scale.Scales
import io.data2viz.viz.TextHAlign
import io.data2viz.viz.TextVAlign
import io.data2viz.viz.Viz
import io.data2viz.viz.viz

class BarChartDemoViz {
    companion object Factory {
        @JvmStatic
        fun createBarChartViz() : Viz {
            val data = listOf(4, 8, 15, 16, 23, 42)
            val barHeight = 14.0
            val padding = 2.0

            val xScale = Scales.Continuous.linear {
                domain = listOf(.0, data.maxOrNull()!!.toDouble())
                range = listOf(.0, 500 - 2 * padding)
            }

            val viz = viz {
                data.forEachIndexed { index, datum ->
                    group {
                        transform {
                            translate(
                                    x = padding,
                                    y = padding + index * (padding + barHeight) )
                        }
                        rect {
                            width = xScale(datum)
                            height = barHeight
                            fill = Colors.Web.steelblue
                        }
                        text {
                            textContent = datum.toString()
                            hAlign = TextHAlign.RIGHT
                            vAlign = TextVAlign.HANGING
                            x = xScale(datum) - 2.0
                            y = 1.5
                            textColor = Colors.Web.white
                            fontSize = 10.0
                        }
                    }
                }
            }

            return viz
        }
    }
}
