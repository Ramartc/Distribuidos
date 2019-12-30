
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JFrame;

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

public class graficaEntrenamiento extends JFrame {

	
	public graficaEntrenamiento(final String title,ArrayList<Entrenamiento> l) {

		super(title);

		final CategoryDataset dataset = createDataset(l);

		// create the chart...
		final JFreeChart chart = ChartFactory.createBarChart(
				"Gráfico " + l.get(0).getNombre(),        // para poner en el titulo el nombre del ejercicio
				"Fecha",               // eje x
				"Peso (kg)",                  // eje y
				dataset,                 // datos
				PlotOrientation.VERTICAL, //para ver como quieres que salga
				true,                     // leyenda
				true,                     // raton por encima, te sale lo que vale
				false                     // URL generator?  Not required...
				);

		chart.setBackgroundPaint(Color.white); //color de fondo
		
		final ChartPanel chartPanel = new ChartPanel(chart); //meto el grafico en el panel
		
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270)); //tamaño del panel
		setContentPane(chartPanel);//al contenido del jframe hecho le meto el panel

	}

	// para crear los datos, el dataset
	//addValue(valor,nombre de la serie,fecha)
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
					dataset.addValue(e.getPeso().get(i-1), i + " serie", sdf3.format(e.getFecha()));//fecha eje x, peso eje y
				}


			}

		}
		return dataset;

	}


}