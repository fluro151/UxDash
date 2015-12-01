/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChartGen;

/**
 *
 * @author Harrison
 */
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

@ViewScoped

@Named
public class InvoiceStatisticsBean implements Serializable {
private CartesianChartModel categoryModel; 

public InvoiceStatisticsBean() {
    categoryModel = new CartesianChartModel();  

    ChartSeries boys = new ChartSeries();  
    boys.setLabel("Boys");  

    boys.set("2004", 120);  
    boys.set("2005", 100);  
    boys.set("2006", 44);  
    boys.set("2007", 150);  
    boys.set("2008", 25);  

    ChartSeries girls = new ChartSeries();  
    girls.setLabel("Girls");  

    girls.set("2004", 52);  
    girls.set("2005", 60);  
    girls.set("2006", 110);  
    girls.set("2007", 135);  
    girls.set("2008", 120);  

    categoryModel.addSeries(boys);  
    categoryModel.addSeries(girls); 
}

public CartesianChartModel getCategoryModel() {
    return categoryModel;
}

public void setCategoryModel(CartesianChartModel categoryModel) {
    this.categoryModel = categoryModel;
}
}
