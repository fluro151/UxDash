/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvConverter;

/**
 *
 * @author Harrison
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.CSVReader;
import java.sql.Timestamp;

class CSVLoader {

    private static final String SQL_INSERT = "INSERT INTO ${table}(${keys}) VALUES(${values})";
    private static final String TABLE_REGEX = "\\$\\{table\\}";
    private static final String KEYS_REGEX = "\\$\\{keys\\}";
    private static final String VALUES_REGEX = "\\$\\{values\\}";

    private Connection connection;
    private char seperator;

    public CSVLoader(Connection connection) {
        this.connection = connection;
        this.seperator = ',';
    }

    public void loadCSV(String csvFile, String tableName, boolean truncateBeforeLoad) throws Exception {

        CSVReader csvReader = null;
        if (null == this.connection) {
            throw new Exception("Not a valid connection");
        }
        try {
            csvReader = new CSVReader(new FileReader(csvFile), this.seperator);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error occured while executing file. " + e.getMessage());
        }
        String[] headerRow = csvReader.readNext();

        if (null == headerRow) {
            throw new FileNotFoundException(
                    "No columns defined in given CSV file."
                    + "Please chek the CSV file Format.");
        }

        String questionmarks = StringUtils.repeat("?,", headerRow.length);
        questionmarks = (String) questionmarks.subSequence(0, questionmarks.length() - 1);

        String query = SQL_INSERT.replaceFirst(TABLE_REGEX, tableName);
        query = query.replaceFirst(KEYS_REGEX, StringUtils.join(headerRow, ","));
        query = query.replaceFirst(VALUES_REGEX, questionmarks);

        System.out.println("Query: " + query);

        String[] nextLine;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = this.connection;
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(query);

            if (truncateBeforeLoad) {
                //delete data from table before loadig csv
                conn.createStatement().execute("DELETE FROM " + tableName);
            }

            final int batchSize = 1000;
            int count = 0;
            Timestamp timestamp = null;
            while ((nextLine = csvReader.readNext()) != null) {
                if (null != nextLine) {
                    int index = 1;
                    int pos = 0;
                    for (String string : nextLine) {

                        if (pos == 2) {

                            System.out.println("Value is: " + string);
                            timestamp = Timestamp.valueOf(string);

                            if (null != timestamp) {
                                ps.setTimestamp(index++, new java.sql.Timestamp(timestamp.getTime()));
                            } else {
                                ps.setString(index++, string);
                            }
                        }

                        pos++;
                        if (pos > 3) {
                            pos = 0;
                        }

                    }
                    ps.addBatch();
                }
                if (++count % batchSize == 0) {
                ps.executeBatch();
                }
            }
            ps.executeBatch(); //insert remaining records
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            e.printStackTrace();
            throw new Exception("Error occured while loading data from file to database." + e.getMessage());
        } finally {
            if (null != ps) {
                ps.close();
            }
            if (null != conn) {
                conn.close();
            }

            csvReader.close();
        }
    }

    public char getSeperator() {
        return seperator;
    }

    public void setSeperator(char seperator) {
        this.seperator = seperator;
    }
}
