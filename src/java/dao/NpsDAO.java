package dao;

import ChartGen.ChartBean;
import dto.NpsDTO;
import static java.lang.Integer.parseInt;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.MeterGaugeChartModel;

/**
 *
 * @author Harry
 */
public class NpsDAO {

    public NpsDTO loadNps() throws SQLException, NamingException {
        NpsDTO result = new NpsDTO();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "Select count(taskPromoters) Promoters, count(taskDetractors) Detractors, count(taskPassives) Passives "
                + "from NPS "
        );) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               
                result.setTaskPromoters(rs.getInt("Promoters"));
                result.setTaskDetractors(rs.getInt("Detractors"));
                result.setTaskPassives(rs.getInt("Passives"));
                int temp1 = result.getTaskDetractors() + result.getTaskPassives() + result.getTaskPromoters();
                int temp2 = result.getTaskPromoters();
                int pPromoters = (temp2 *100)/temp1;
                int pDetractors = (result.getTaskDetractors() *100)/(result.getTaskDetractors() +result.getTaskPassives()+result.getTaskPromoters());
                int test = pPromoters - pDetractors;
                result.setNpsWhole(test);
            }
        }

        return result;
    }

    
    
    
    //NEeds to be updated to correspond with "loadNps" Method.
    public ArrayList<NpsDTO> listNpsTask(String taskTitle) throws SQLException, NamingException {
        ArrayList<NpsDTO> nps = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "Select ID, count(taskPromoters) Promoters, count(taskDetractors) Detractors "
                + "from NPS "
                + "where taskTitle = ? "
        );) {
            ps.setString(1, taskTitle);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NpsDTO result = new NpsDTO();
                result.setNpsWhole(rs.getInt("Promoters") - rs.getInt("Detractors"));
                
                result.setID(rs.getString("ID"));
                result.setTaskPromoters(rs.getInt("Promoters"));
                result.setTaskDetractors(rs.getInt("sessionDate"));
                nps.add(result);
            }
        }
       
        return nps;
    }
    
        public MeterGaugeChartModel getModel() throws SQLException, NamingException {
        MeterGaugeChartModel model = new MeterGaugeChartModel();
        ArrayList<NpsDTO> nDTO = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "select count(taskPromoters) Promoters "
                +"from NPS"

        );) {

            ResultSet rs = ps.executeQuery();
            NpsDTO result = new NpsDTO();
            while (rs.next()) {
                result.setTaskPromoters(rs.getInt("Promoters"));
                model.setValue(result.getTaskPromoters());
                nDTO.add(result);

            }
        }
        List<Number> intervals = new ArrayList<Number>(){{
                
                add(0);
                add(100);
            }};
        model.setTitle("Current Nps");
        model.setSeriesColors("cc6666,66cc66");
        model.setShowTickLabels(true);
        model.setMin(-100);
        model.setMax(100);
        model.setIntervals(intervals);
        return model;
    }
}
    