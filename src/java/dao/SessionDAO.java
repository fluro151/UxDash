package dao;

import ChartGen.ChartBean;
import dto.SessionDTO;
import static java.lang.Integer.parseInt;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author Harry
 */
public class SessionDAO {

    public SessionDTO find(String id) throws SQLException, NamingException {
        SessionDTO result = new SessionDTO();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "Select id, value, sessionDate, sourceName "
                + "from sessions "
                + "where id = ? "
        );) {
            //Set parameters
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.setID(rs.getString("id"));
                result.setValue(rs.getString("value"));
                result.setDate(rs.getDate("sessionDate"));
                result.setSourceName(rs.getString("sourceName"));
            }
        }

        return result;
    }

    public ArrayList<SessionDTO> listAll() throws SQLException, NamingException {
        ArrayList<SessionDTO> sessions = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "Select id, value, sessionDate, sourceName "
                + "from sessions "
        );) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SessionDTO result = new SessionDTO();
                result.setID(rs.getString("id"));
                result.setValue(rs.getString("value"));
                result.setDate(rs.getDate("sessionDate"));
                result.setSourceName(rs.getString("sourceName"));
                sessions.add(result);
            }
        }

        return sessions;
    }

    public ArrayList<SessionDTO> listRecords(String source) throws SQLException, NamingException {
        ArrayList<SessionDTO> sessions = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "select id, value, sessionDate, sourceName "
                + "from sessions "
                + "where sourcename = ? "
        );) {
            ps.setString(1, source);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SessionDTO result = new SessionDTO();
                result.setID(rs.getString("id"));
                result.setValue(rs.getString("value"));
                result.setDate(rs.getDate("sessionDate"));
                result.setSourceName(rs.getString("sourceName"));
                sessions.add(result);
            }
        }
        return sessions;
    }

    public SessionDTO listID(int id) throws SQLException, NamingException {
        SessionDTO result = new SessionDTO();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "select id, value, sessionDate, sourceName  "
                + "from sessions "
                + "where id = ? "
        );) {
            ps.setInt(1, id);
            /**
             * Looping over every row in the result set *
             */
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.setID(rs.getString("id"));
                result.setValue(rs.getString("value"));
                result.setDate(rs.getDate("sessionDate"));
                result.setSourceName(rs.getString("sourceName"));
            }
        }

        return result;
    }

    public void add(SessionDTO session) throws NoSuchAlgorithmException, SQLException, NamingException {

        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement query = conn.prepareStatement(
                "insert into SESSIONS (id, value, sessionDate, sourceName )"
                + "values (?, ?, ?, ? )"
        );) {

            query.setString(1, session.getID());
            query.setString(2, session.getValue());
            query.setDate(3, session.getDate());
            query.setString(4, session.getSourceName());

            query.execute();

        }
    }

    public BarChartModel getModel() throws SQLException, NamingException {
        BarChartModel model = new BarChartModel();
        ChartSeries sessions = new ChartSeries();
        ArrayList<SessionDTO> sDTO = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "select sessionDate, sum(cast(value as int)) Total "
                + "from sessions "
                + "group by sessionDate "
                + "order by sessionDate "
        );) {

            ResultSet rs = ps.executeQuery();
            String[] results = new String[4];
            SessionDTO result = new SessionDTO();
            while (rs.next()) {
                result.setValue(rs.getString("total"));
                result.setDate(rs.getDate("sessionDate"));

                sessions.set(result.getDate(), parseInt(result.getValue()));
                sDTO.add(result);

            }
        }
        model.addSeries(sessions);
        model.setTitle("Sessions");

        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Sessions");

        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Session Count");
        yAxis.setMin(0);

        return model;
    }

    public BarChartModel getSourceModel(String source) throws SQLException, NamingException {
        BarChartModel model = new BarChartModel();
        ChartSeries sessions = new ChartSeries();
        ArrayList<SessionDTO> sDTO = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "select sessionDate, sum(cast(value as int)) Total "
                + "from sessions "
                + "where sourceName = ? "
                + "group by sessionDate "
                + "order by sessionDate"
        );) {
            ps.setString(1, source);
            ResultSet rs = ps.executeQuery();
            SessionDTO result = new SessionDTO();
            while (rs.next()) {

                result.setValue(rs.getString("Total"));
                result.setDate(rs.getDate("sessionDate"));

                sessions.set(result.getDate(), parseInt(result.getValue()));
                sDTO.add(result);

            }

        }
        model.addSeries(sessions);
        model.setTitle("Sessions by Source");
        model.setLegendPosition("se");

        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Date(by day)");

        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Session Count");
        yAxis.setMin(0);
        yAxis.setMax(500);
        return model;
    }

}
