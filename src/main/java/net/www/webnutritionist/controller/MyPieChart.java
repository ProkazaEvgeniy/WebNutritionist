package net.www.webnutritionist.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/charts")
public class MyPieChart {

	@RequestMapping(value = "/piechart", method = RequestMethod.GET)
	public void drawPieChart(HttpServletResponse resp){
		resp.setContentType("image/png");
		PieDataset pdSet = createDataSet();
		JFreeChart chart = createChart(pdSet, "График приема пищи");
		try {
			ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 750, 400);
			resp.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset,true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}

	private PieDataset createDataSet() {
		DefaultPieDataset dpd = new DefaultPieDataset();
		dpd.setValue("Картофель", 21);
		dpd.setValue("Капуста", 30);
		dpd.setValue("Молоко", 40);
		dpd.setValue("Другое", 9);
		return dpd;
	}

	
	
	
}
