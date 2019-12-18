
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration application showing how to create a dual axis chart based on data
 * from two {@link CategoryDataset} instances.
 *
 */
public class graficaEntrenamiento extends ApplicationFrame {

    /**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public graficaEntrenamiento(final String title,ArrayList<Entrenamiento> l) {

        super(title);

        final CategoryDataset dataset = createDataset(l);

        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "Gráfico " + l.get(0).getNombre(),        // chart title
            "Fecha",               // domain axis label
            "Peso (kg)",                  // range axis label
            dataset,                 // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips?
            false                     // URL generator?  Not required...
        );

        chart.setBackgroundPaint(Color.white);

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }

  
    private CategoryDataset createDataset(ArrayList<Entrenamiento> l) {
    	int maximo = 0;
    	for(Entrenamiento e : l) {
    		if(e.getSeries()>maximo) {
    			maximo = e.getSeries();
    		}
    	}
 
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy");
        for(int i = 1; i <= maximo;i++) {
        	for(Entrenamiento e : l) {
        			if(i <= e.getSeries()) {
        				dataset.addValue(e.getPeso().get(i-1), i + " serie", sdf3.format(e.getFecha()));
        			}
			       	
			       
        	}

        }
        return dataset;

    }

   
}