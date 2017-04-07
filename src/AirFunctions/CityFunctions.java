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
            //createCityTable(connection);
            //addCity(connection, "Chicago","Illinois","Ohare");
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
            //String sql = "ALTER TABLE cities change NAME cityname text;";
            //stmt.executeUpdate(sql);
            String sql = "CREATE TABLE CITIES " +
                    "(ID INT PRIMARY KEY     NOT NULL AUTO_INCREMENT," +
                    " CITYNAME           TEXT    NOT NULL, " +
                    " STATE        TEXT     NOT NULL," +
                    " AIRPORT       TEXT    NOT NULL)";

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

    public static boolean checkForCity(Connection con, String name, String state, String airport){
        Statement stmt = null;
        boolean airportExists = false;

        try{
            con.setAutoCommit(false);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM cities WHERE cityname = '"+ name +"'AND state = '"+ state +"'AND airport = '"+ airport +"';");

            if(rs.next()){
                airportExists = true;
            }
            stmt.close();

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return airportExists;
    }

    public static String getAirportLocation(Connection con){
        Statement stmt = null;
        String htmlCode = "";

        try{
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM cities ORDER BY state, cityName, airport;");
            htmlCode += "<table class=\"admin_man_table\"><tr>";
            htmlCode += "<th><b>State</b></th>";
            htmlCode += "<th><b>City</b></th>";
            htmlCode += "<th><b>Airport</b></th>";
            htmlCode += "<th><b>Action</b></th>";
            htmlCode += "</tr>";

            while(rs.next()){
                htmlCode += "<form action=\"AirFunctions.AdminFunctions\">";
                htmlCode += "<input type=\"hidden\" name=\"airportID\" value=\""+rs.getString("ID") + "\" >";
                htmlCode += "<tr>";
                htmlCode += "<td>" + rs.getString("state") + "</td>";
                htmlCode += "<td>" + rs.getString("cityname") + "</td>";
                htmlCode += "<td>" + rs.getString("airport") + "</td>";
                htmlCode += "<td> <button "+/*type=\"submit\"*/"name=\"editLocationButton\">Edit</button></td>";
                htmlCode += "</tr>";
                htmlCode += "</form>";
            }

            htmlCode += "</table>";

            con.close();

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }

        return htmlCode;
    }



}
