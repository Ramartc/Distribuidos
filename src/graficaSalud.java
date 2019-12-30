
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

public class graficaSalud extends JFrame {


	public graficaSalud(final String title,ArrayList<Salud> l) {

		super(title);

		final CategoryDataset dataset1 = createDataset1(l);

		final JFreeChart chart = ChartFactory.createBarChart(
				title,        // titulo
				"Fecha",               // eje x
				"Valor",                  // eje y
				dataset1,                 // datos
				PlotOrientation.VERTICAL,
				true,                     // leyenda
				true,                     // para ponerte encima de la linea y que te salgan datos
				false                     // URL generator?  Not required...
				);

		
		chart.setBackgroundPaint(Color.white); //color de fondo
		// añado una nueva categoria para añadir las pulsaciones, como otra nueva grafica con su propio eje y
		final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(new Color(0xEE, 0xEE, 0xFF));
		plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT); //derecha

		final CategoryDataset dataset2 = createDataset2(l); //creamos dataset de pulsacciones
		plot.setDataset(1, dataset2); //le añadimos al nuevo grafico las pulsaciones
		plot.mapDatasetToRangeAxis(1, 1);

		final CategoryAxis domainAxis = plot.getDomainAxis(); //eje de las pulsacones
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45); 

		final ValueAxis axis2 = new NumberAxis("Pulsaciones"); //nombre de la serie

		plot.setRangeAxis(1, axis2);//la metemos al grafico

		final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
		plot.setRenderer(1, renderer2);
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);

		final ChartPanel chartPanel = new ChartPanel(chart); //pintamos el panel
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);

	}

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

	private CategoryDataset createDataset2(ArrayList<Salud> l) {


		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy");
		for(Salud s : l) {

			dataset.addValue(s.getPulsaciones(), "Pulsaciones (Media del dia)", sdf3.format(s.getFecha()));
		}
		return dataset;

	}



}
