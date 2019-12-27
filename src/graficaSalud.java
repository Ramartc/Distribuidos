
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.*;
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
public class graficaSalud extends JFrame {

	/**
	 * Creates a new demo instance.
	 *
	 * @param title  the frame title.
	 */
	public graficaSalud(final String title,ArrayList<Salud> l) {

		super(title);

		final CategoryDataset dataset1 = createDataset1(l);

		// create the chart...
		final JFreeChart chart = ChartFactory.createBarChart(
				title,        // chart title
				"Fecha",               // domain axis label
				"Valor",                  // range axis label
				dataset1,                 // data
				PlotOrientation.VERTICAL,
				true,                     // include legend
				true,                     // tooltips?
				false                     // URL generator?  Not required...
				);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		chart.setBackgroundPaint(Color.white);
		//        chart.getLegend().setAnchor(Legend.SOUTH);

		// get a reference to the plot for further customisation...
		final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(new Color(0xEE, 0xEE, 0xFF));
		plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

		final CategoryDataset dataset2 = createDataset2(l);
		plot.setDataset(1, dataset2);
		plot.mapDatasetToRangeAxis(1, 1);

		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);

		final ValueAxis axis2 = new NumberAxis("Pulsaciones");

		plot.setRangeAxis(1, axis2);

		final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
		plot.setRenderer(1, renderer2);
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
		// OPTIONAL CUSTOMISATION COMPLETED.
		
		// add the chart to a panel...
		final ChartPanel chartPanel = new ChartPanel(chart);

//
//		try {
//			ChartUtilities.saveChartAsJPEG(new File("grafico.jpg"), chart, 500, 500);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);

	}

	// ****************************************************************************
	// * JFREECHART DEVELOPER GUIDE                                               *
	// * The JFreeChart Developer Guide, written by David Gilbert, is available   *
	// * to purchase from Object Refinery Limited:                                *
	// *                                                                          *
	// * http://www.object-refinery.com/jfreechart/guide.html                     *
	// *                                                                          *
	// * Sales are used to provide funding for the JFreeChart project - please    * 
	// * support us so that we can continue developing free software.             *
	// ****************************************************************************

	/**
	 * Creates a sample dataset.
	 *
	 * @return  The dataset.
	 */
	private CategoryDataset createDataset1(ArrayList<Salud> l) {



		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy");
		for(Salud s : l) {

			dataset.addValue(s.getPeso(), "Peso (KG)", sdf3.format(s.getFecha()));
			dataset.addValue(s.getAltura()*100, "Altura (CM)", sdf3.format(s.getFecha()));
			dataset.addValue(s.getImc(), "IMC", sdf3.format(s.getFecha()));
		}


		return dataset;

	}

	/**
	 * Creates a sample dataset.
	 *
	 * @return  The dataset.
	 */
	private CategoryDataset createDataset2(ArrayList<Salud> l) {



		// create the dataset...
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy");
		for(Salud s : l) {

			dataset.addValue(s.getPulsaciones(), "Pulsaciones (Media del dia)", sdf3.format(s.getFecha()));
		}
		return dataset;

	}

	/**
	 * Starting point for the demonstration application.
	 *
	 * @param args  ignored.
	 */

}
