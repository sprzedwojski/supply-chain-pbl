package plotting;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;

public class Plotter {
    JavaPlot jp;

    public Plotter() {
        jp = new JavaPlot();
    }

    public void plotPointsWithIntegerXFromZero(double[] points, String title, Style style) {
        double[][] corrected = new double[points.length][2];

        for (int i = 0; i < points.length; i++) {
            corrected[i][0] = i;
            corrected[i][1] = points[i];
        }

        plotPoints(corrected, title, style);
    }

    public void plotPointsWithIntegerXFromZero(int[] points, String title, Style style) {
        double[][] corrected = new double[points.length][2];

        for (int i = 0; i < points.length; i++) {
            corrected[i][0] = i;
            corrected[i][1] = points[i];
        }

        plotPoints(corrected, title, style);
    }

    public void plotPoints(double[][] points, String title, Style customStyle) {
        DataSetPlot dsp = new DataSetPlot(points);
        dsp.setTitle(title);
        PlotStyle style = new PlotStyle(customStyle == null ? Style.LINES : customStyle);
        style.setPointSize(2);
        dsp.setPlotStyle(style);
        jp.addPlot(dsp);
    }

    public void draw(String title) {
        jp.setTitle(title);
        jp.plot();
    }

}
