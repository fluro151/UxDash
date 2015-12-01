/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChartGen;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author Harrison
 */
public class ChartBean {

    private BarChartModel model;

    public ChartBean() {
        model = new BarChartModel();
        ChartSeries sessions = new ChartSeries();

        

        model.addSeries(sessions);


        model.setLegendPosition("ne");

        Axis xAxis = model.getAxis(AxisType.X);

        Axis yAxis = model.getAxis(AxisType.Y);

        yAxis.setMin(0);
        yAxis.setMax(200);
    }

    public BarChartModel getModel() {
        return model;
    }
}
