package AirFunctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by crisi_000 on 3/30/2017.
 */

public class AirplaneFunctions {

    public static void main( String[]  args){
        Connection connection = null;
        try {
            boolean working = false;
            connection = AccountFunctions.OpenDatabase();
            //CreatePlanesTable(connection);
            //AddAirplane(connection, "Boeing",250,3);
            CreatePlaneModelTable(connection);
            //working = checkLogin(connection,"test@gmail.com","test");
            //System.out.println(working);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void CreatePlanesTable(Connection con)
    {
        Connection c = con;
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            /*String sql = "CREATE TABLE PLANES " +
                    "(ID INT PRIMARY KEY     NOT NULL AUTO_INCREMENT," +
                    " PLANE_TYPE           TEXT    NOT NULL, " +
                    " CAPACITY        INT     NOT NULL," +
                    " CLASSES         INT    NOT NULL );";*/

            //String sql = "ALTER TABLE PLANES DROP COLUMN CLASSES;";


            //stmt.executeUpdate(sql);

            /*sql = "ALTER TABLE PLANES ADD COLUMN " +
                    "(maxFIRST          INT NOT NULL, " +
                    " maxBUSINESS       INT NOT NULL, " +
                    " maxECONOMY        INT NOT NULL, " +
                    " takenFIRST        INT NOT NULL, " +
                    " takenBUSINESS     INT NOT NULL, " +
                    " takenECONOMY      INT NOT NULL, " +
                    " availableFIRST    INT AS (maxFIRST - takenFIRST), " +
                    " availableBUSINESS INT AS (maxBUSINESS - takenBUSInESS), " +
                    " availableECONOMY  INT AS (maxECONOMY - takenECONOMY), " +
                    " BASE_PRICE        INT NOT NULL, " +
                    " multipleFIRST     INT NOT NULL, " +
                    " multipleBUSINESS  INT NOT NULL, " +
                    " multipleECONOMY   INT NOT NULL);";*/
            //stmt.executeUpdate(sql);

            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    //i assume that if a plane has first class, it also has business and coach. If it has business it also has coach
    public static void AddAirplane(Connection con, String planeModel, int numPassengers, int classes) {
        Connection c = con;
        Statement stmt = null;
        try {

            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "INSERT INTO PLANES (PLANE_TYPE,CAPACITY,CLASSES) " +
                    "VALUES ( '" + planeModel + "' , '" + numPassengers + "' , '"+ classes +"');";
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


    public static void CreatePlaneModelTable(Connection con){
        Connection c = con;
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE PLANEMODELS " +
                    "(ID INT PRIMARY KEY    NOT NULL    AUTO_INCREMENT," +
                    " PLANE_MODEL            TEXT        NOT NULL, " +
                    " CAPACITY              INT         NOT NULL," +
                    " hasECONOMY            BOOLEAN         NOT NULL," +
                    " hasBUSINESS           BOOLEAN         NOT NULL," +
                    " hasFIRST              BOOLEAN         NOT NULL,"+
                    " FUEL_CAPACITY         INT             NOT NULL,"+
                    " FUEL_BURN_RATE        DECIMAL(5,4)    NOT NULL,"+
                    " AVERAGE_VELOCITY      INT             NOT NULL);";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");

    }

    public static String showPlaneModels(Connection con){
        Statement stmt = null;
        String htmlCode = "";
        String modalCode = "";

        try{
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM planemodels;");
            htmlCode += "<table class=\"admin_man_table\"><tr>";
            htmlCode += "<th><b>Model</b></th>";
            htmlCode += "<th><b>Capacity</b></th>";
            htmlCode += "<th><b>Economy</b></th>";
            htmlCode += "<th><b>Business</b></th>";
            htmlCode += "<th><b>First</b></th>";
            htmlCode += "<th><b>Fuel Capacity</b></th>";
            htmlCode += "<th><b>Fuel Burn Rate</b></th>";
            htmlCode += "<th><b>Average Velocity</b></th>";
            htmlCode += "</tr>";

            while(rs.next()){
                String id = rs.getString("id");
                String planeModel = rs.getString("plane_model");
                String capacity = rs.getString("capacity");
                String economy = rs.getString("hasECONOMY");
                String business = rs.getString("hasBUSINESS");
                String firstclass = rs.getString("hasFIRST");
                String fuel = rs.getString("fuel_capacity");
                String burn = rs.getString("fuel_burn_rate");
                String velocity = rs.getString("Average_Velocity");

                htmlCode += "<tr>";
                htmlCode += "<td>" + planeModel + "</td>";
                htmlCode += "<td>" + capacity + "</td>";
                if(economy.equals("1")){
                    htmlCode +=  "<td><img src=\"img/check-icon.png\" alt=\"YES\" style=\"width:24px;height:24px;\"></td>";
                } else {
                    htmlCode += "<td><img src=\"img/no-icon.png\" alt=\"NO\" style=\"width:24px;height:24px;\"></td>";
                }
                if(business.equals("1")){
                    htmlCode +=  "<td><img src=\"img/check-icon.png\" alt=\"YES\" style=\"width:24px;height:24px;\"></td>";
                } else {
                    htmlCode += "<td><img src=\"img/no-icon.png\" alt=\"NO\" style=\"width:24px;height:24px;\"></td>";
                }
                if(firstclass.equals("1")){
                    htmlCode +=  "<td><img src=\"img/check-icon.png\" alt=\"YES\" style=\"width:24px;height:24px;\"></td>";
                } else {
                    htmlCode += "<td><img src=\"img/no-icon.png\" alt=\"NO\" style=\"width:24px;height:24px;\"></td>";
                }
                htmlCode += "<td>" + fuel + "</td>";
                htmlCode += "<td>" + burn + "</td>";
                htmlCode += "<td>" + velocity + "</td>";
                htmlCode += "<td> <button name=\"editPlaneModelButton\" onclick=\"viewPlaneModelModal("+rs.getString("ID")+")\"> Edit </button></td>";
                htmlCode += "</tr>";

                modalCode += "<div id=\"planeModelModal"+id+"\" class=\"planeModelModal\">";
                modalCode += "<div class=\"planeModelModalContent\">";
                modalCode += "<span class=\"planeModelModalClose\" onclick=\"closeEditPlaneModel(&quot;"+id+"&quot;)\">&times;</span>";
                modalCode += "<form action=\"AirFunctions.Admin.AdminPlaneModels\">\n" +
                        "        <input type=\"hidden\" name=\"planeModelID\" value=\"" + id + "\" >"+
                        "        <ul class=\"modelForm\">\n" +
                        "            <li><b>Model</b> <input type=\"text\" name=\"planeModel\" placeholder=\"Type\" maxlength=\"40\" value=\""+planeModel+"\" required></li>\n" +
                        "            <li><b>Capacity</b><input type=\"number\" name=\"modelCapacity\" placeholder=\"Capacity\" min=\"1\" max=\"999\" maxlength = \"3\" value=\""+capacity+"\" required></li>\n" +
                        "            <li><b>Fuel Capacity (tonnes): </b><input type=\"number\" name=\"modelFuel\" placeholder=\"tonnes\" min=\"1\" max=\"300\" maxlength = \"6\" value=\"" + fuel + "\" required></li>\n" +
                        "            <li><b>Fuel Burn Rate (kg/km): </b><input type=\"number\" name=\"modelBurn\" placeholder=\"kg/km\" min=\"1\" max=\"10\" maxlength = \"4\" step=\"0.001\" value=\"" + burn + "\" required></li>\n" +
                        "            <li><b>Average Velocity (km/hr): </b><input type=\"number\" name=\"modelVelocity\" placeholder=\"km/hr\" min=\"1\" max=\"2000\" maxlength = \"4\" value=\"" + velocity + "\" required></li>" +
                        "            <li>\n" +
                        "                <div class=\"classesCheckboxLabel\">\n" +
                        "                    <b>Available Classes</b>\n" +
                        "                    <ul class=\"classesCheckbox\" >\n" +
                        "                        <li><input type=\"checkbox\" name=\"hasEconomyClass\" value=\"true\" title=\"Economy\" ";

                if(economy.equals("1")){
                    modalCode += " checked ";
                }

                modalCode += ">Economy</li>\n" +
                        "                        <li><input type=\"checkbox\" name=\"hasBusinessClass\" value=\"true\" title=\"Business\" ";

                if(business.equals("1")){
                    modalCode += " checked ";
                }

                modalCode += ">Business</li>\n" +
                        "                        <li><input type=\"checkbox\" name=\"hasFirstClass\" value=\"true\" title=\"First\" ";

                if(firstclass.equals("1")){
                    modalCode += " checked ";
                }
                modalCode += ">First</li>\n" +
                        "                    </ul>\n" +
                        "                </div>\n" +
                        "            </li>\n" +
                        "        </ul>\n" +
                        "        <button type=\"submit\" name=\"updatePlaneModelButton\"> Update </button>\n" +
                        "        <button type=\"submit\" name=\"removePlaneModelButton\"> Remove </button>\n" +
                        "    </form>";
                modalCode += "</div>\n</div>";
            }

            htmlCode += "</table>\n\n";
            htmlCode += modalCode;

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return htmlCode;
    }

    public static boolean addPlaneModel(Connection con, String model, String capacity, String hasEconomy, String hasBusiness, String hasFirst, String fuel, String burn, String velocity ){

        Statement stmt = null;
        boolean planeModelAdded = false;
        try {

            con.setAutoCommit(false);
            stmt = con.createStatement();

            String sql = "SELECT * FROM planemodels WHERE plane_model='"+model+"';";
            ResultSet rs = stmt.executeQuery(sql);

            if(!rs.next()){
                sql = "INSERT INTO planemodels (PLANE_MODEL, CAPACITY, hasECONOMY, hasBUSINESS, hasFIRST, FUEL_CAPACITY, FUEL_BURN_RATE, AVERAGE_VELOCITY) " +
                        "VALUES ( '" + model + "' , '" + capacity + "' , "+ hasEconomy +", "+ hasBusiness+", "+ hasFirst + ", "+fuel+","+burn+","+velocity+");";
                stmt.executeUpdate(sql);
                stmt.close();
                planeModelAdded = true;
            }

            con.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records updated successfully");

        return planeModelAdded;
    }

    public static void updatePlaneModel(Connection con, String planeModelID, String model, String capacity, String hasEconomy, String hasBusiness, String hasFirst, String fuel, String burn, String velocity ){
        Statement stmt = null;
        try {
            deletePlaneModel(con, planeModelID);
            addPlaneModel(con, model, capacity, hasEconomy, hasBusiness, hasFirst, fuel, burn, velocity);
            con.commit();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void deletePlaneModel(Connection c, String planeModelID){
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "DELETE FROM planemodels WHERE ID = "+planeModelID+";";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static String planeModelsList(Connection con){
        String htmlCode="";
        String detailCode="";
        Statement stmt = null;
        try {

            con.setAutoCommit(false);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT * FROM planemodels;");

            htmlCode += "<select id=\"planeModelSelect\" onchange=\"alterForm()\">";
            while(rs.next()){
                String id = rs.getString("id");
                String model = rs.getString("plane_model");
                String capacity = rs.getString("capacity");
                String hasEcon = rs.getString("hasEconomy");
                String hasBus = rs.getString("hasBusiness");
                String hasFirst = rs.getString("hasFirst");

                System.out.println(hasEcon);

                htmlCode +=
                        "<option id=\"planeModel"+id+"\" value=\""+id+"\">"+model+"</option>";
                detailCode +=
                        "<span id=\"planeModelModel"+id+"\" style=\"display: none;\" >"+model+"</span>\n" +
                        "<span id=\"planeModelCapacity"+id+"\" style=\"display: none;\">"+capacity+"</span>\n" +
                        "<span id=\"planeModelEconomy"+id+"\"  style=\"display: none;\">"+hasEcon+"</span>\n" +
                        "<span id=\"planeModelBusiness"+id+"\" style=\"display: none;\">"+hasBus+"</span>\n" +
                        "<span id=\"planeModelFirst"+id+"\" style=\"display: none;\">"+hasFirst+"</span>\n";
            }

            htmlCode += "</select>";
            htmlCode += detailCode;

            con.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records updated successfully");


        return htmlCode;
    }
}
