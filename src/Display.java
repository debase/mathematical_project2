import java.awt.Dimension;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;


public class Display extends ApplicationFrame {

	List<Double> poissonList;
	List<Double> binoList;
	XYSeries	 poissonSeries;
	XYSeries	 binomSeries;
	XYSeriesCollection seriescol;
	
	private static final long serialVersionUID = 1L;

	public Display(String title, CalcPoisson poisson, CalcBinomial bino) {
			super(title);
			
			poissonSeries = new XYSeries("Loi de poisson");
			binomSeries = new XYSeries("Loi binomial");
			seriescol = new XYSeriesCollection();

			for (int i = 0 ; i <= 50 ; i++) {
				binomSeries.add(i, bino.getBinoProba().get(i));
			}
			
			for (int i = 0 ; i <= 50 ; i++) {
				poissonSeries.add(i, poisson.getPoissonProba().get(i));
			}
			
			seriescol.addSeries(poissonSeries);
			seriescol.addSeries(binomSeries);

			JFreeChart jfreechart = ChartFactory.createXYBarChart("Graph", "X", false, "Y", seriescol,
																	PlotOrientation.VERTICAL, true, true, false);
	
			XYPlot xyplot = jfreechart.getXYPlot(); 
	        ValueAxis rangeAxis = xyplot.getDomainAxis();
	        ((NumberAxis) rangeAxis).setTickUnit(new NumberTickUnit(10));

	        rangeAxis = xyplot.getRangeAxis();
	        ((NumberAxis) rangeAxis).setTickUnit(new NumberTickUnit(0.02));

	        xyplot.setRenderer(new ClusteredXYBarRenderer());

	        ChartPanel chartpanel = new ChartPanel(jfreechart); 
	        chartpanel.setPreferredSize(new Dimension(800, 600)); 
	        setContentPane(chartpanel); 
	}
}