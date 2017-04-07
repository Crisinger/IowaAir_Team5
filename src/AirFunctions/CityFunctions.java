package AirFunctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by crisi_000 on 4/6/2017.
 */
public class CityFunctions {


    public static void main( String[]  args){
        Connection connection = null;
        try {
            boolean working = false;
            connection = AccountFunctions.OpenDatabase();
            createCityTable(connection);
            addCity(connection, "Chicago","Illinois","Ohare");
            System.out.println("Index number is:");
            System.out.println(getIndex(connection,"Chicago"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void createCityTable(Connection c) {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "ALTER TABLE cities change NAME cityname text;";
            //stmt.executeUpdate(sql);
            /*String sql = "CREATE TABLE CITIES " +
                    "(ID INT PRIMARY KEY     NOT NULL AUTO_INCREMENT," +
                    " CITYNAME           TEXT    NOT NULL, " +
                    " STATE        TEXT     NOT NULL," +
                    " AIRPORT       TEXT    NOT NULL)";
                    */
            stmt.executeUpdate(sql);

            stmt.close();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public static int getIndex(Connection con, String cityName){
        Statement stmt = null;
        int id = 0;
        try {
            stmt = con.createStatement();
            String sql = "SELECT * FROM cities C WHERE C.cityname = '" + cityName + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                id = rs.getInt("id");
            /*
            info about the city is gathered here but not stored or returned anywhere..
             */
                String name = rs.getString("cityname");
                String state = rs.getString("state");
                String airport = rs.getString("airport");
            }
            rs.close();

            stmt.close();
            con.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return id;
    }

    public static void addCity(Connection con, String name, String state, String airport){
        Connection c = con;
        Statement stmt = null;
        try {

            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "INSERT INTO cities (CITYNAME,STATE,AIRPORT) " +
                    "VALUES ( '" + name + "' , '" + state + "' , '"+ airport +"');";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

}
