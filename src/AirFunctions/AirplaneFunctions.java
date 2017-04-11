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
            //createPlanesTable(connection);
            //addAirplane(connection, "Boeing",250,3);
            //createPlaneModelTable(connection);
            //working = checkLogin(connection,"test@gmail.com","test");
            //System.out.println(working);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void createPlanesTable(Connection con)
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
    public static boolean addAirplane(Connection con, String modelID, String capacity, String econSeats,
                                   String busSeats, String firstSeats, String basePrice, String econMult,
                                   String busMult, String firstMult) {
        Statement stmt = null;
        boolean airplaneAdded = false;
        try {

            con.setAutoCommit(false);
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM planemodels WHERE id="+modelID+";");

            if(rs.next()) {
                String planeType = rs.getString("plane_model");

                String sql = "INSERT INTO PLANES (PLANE_TYPE, CAPACITY," +
                        " maxFirst, maxBusiness, maxEconomy, takenFirst, takenBusiness, takenEconomy," +
                        "Base_Price, multipleFirst," +
                        " multipleBusiness, multipleEconomy) " +
                        "VALUES ( '" + planeType + "' , " + capacity + " , "
                        + firstSeats + ", " + busSeats + " , "
                        + econSeats + ", 0, 0, 0, "+basePrice+","+firstMult+" , "+busMult+" , "+econMult+");";
                stmt.executeUpdate(sql);
                airplaneAdded = true;
            }

            stmt.close();
            con.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");

        return airplaneAdded;
    }


    public static void createPlaneModelTable(Connection con){
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM planemodels ORDER BY PLANE_MODEL;");
            htmlCode += "<table class=\"admin_man_table admin_planeModel_table\">"+"" +
                        "<tr>"+
                            "<th><b>Model</b></th>" +
                            "<th><b>Capacity</b></th>" +
                            "<th><b>Economy</b></th>" +
                            "<th><b>Business</b></th>" +
                            "<th><b>First</b></th>" +
                            "<th><b>Fuel</b></th>" +
                            "<th><b>Burn Rate</b></th>" +
                            "<th><b>Velocity</b></th>" +
                            "<th><b>Action</b></th>"+
                        "</tr>" +
                        "<tr>"+
                            "<th></th>" +
                            "<th>(persons)</th>" +
                            "<th></th>" +
                            "<th></th>" +
                            "<th></th>" +
                            "<th>(tonnes)</th>" +
                            "<th>(kg/km)</th>" +
                            "<th>(km/hr)</th>" +
                        "</tr>";

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
        String htmlCode=
                "<select id=\"planeModelSelect\" name=\"planeSelect\" onchange=\"alterForm()\">\n" +
                "       <option disabled selected>Select Plane Model</option>\n";
        String detailCode="";
        Statement stmt = null;
        try {

            con.setAutoCommit(false);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT * FROM planemodels;");

            while(rs.next()){
                String id = rs.getString("id");
                String model = rs.getString("plane_model");
                String capacity = rs.getString("capacity");
                String hasEcon = rs.getString("hasEconomy");
                String hasBus = rs.getString("hasBusiness");
                String hasFirst = rs.getString("hasFirst");

                System.out.println(hasEcon);

                htmlCode +=
                        "<option id=\"planeModel"+id+"\" name=\"planeSelectionModelID\" value=\""+id+"\">"+model+"</option>";
                detailCode +=
                        "<span id=\"planeModelModel"+id+"\" name=\"planeModelModel" + id + "\"style=\"display: none;\" >"+model+"</span>\n" +
                        "<span id=\"planeModelCapacity"+id+"\" name=\"planeModelCapacity" + id + "\" style=\"display: none;\">"+capacity+"</span>\n" +
                        "<span id=\"planeModelEconomy"+id+"\" name=\"planeModelEconomy" + id + "\"  style=\"display: none;\">"+hasEcon+"</span>\n" +
                        "<span id=\"planeModelBusiness"+id+"\" name=\"planeModelBusiness" + id + "\" style=\"display: none;\">"+hasBus+"</span>\n" +
                        "<span id=\"planeModelFirst"+id+"\" name=\"planeModelFirst" + id + "\" style=\"display: none;\">"+hasFirst+"</span>\n";
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

    public static String showPlanes(Connection con){
        Statement stmt = null;
        String htmlCode = "";
        String modalCode = "";

        try{
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM planes ORDER BY id;");
            htmlCode += "<table class=\"admin_man_table admin_planeModel_table admin_planes_table \">"+"" +
                    "<tr>"+
                    "<th><b>ID</b></th>" +
                    "<th><b>Model</b></th>" +
                    "<th><b>Capacity</b></th>" +
                    "<th><b>Economy</b></th>" +
                    "<th><b>Business</b></th>" +
                    "<th><b>First</b></th>" +
                    "<th><b>Action</br></th>" +
                    "</tr>" +
                    "<tr>"+
                    "<th></th>" +
                    "<th></th>" +
                    "<th>(persons)</th>" +
                    "<th>(persons)</th>" +
                    "<th>(persons)</th>" +
                    "<th>(persons)</th>" +
                    "<th></th>" +
                    "</tr>";

            while(rs.next()){
                String id = rs.getString("id");
                String planeModel = rs.getString("plane_type");
                String capacity = rs.getString("capacity");
                String economy = rs.getString("maxEconomy");
                String business = rs.getString("maxBusiness");
                String firstclass = rs.getString("maxFirst");
                String basePrice = rs.getString("base_Price");
                String econMult = rs.getString("multipleEconomy");
                String busMult = rs.getString("multipleBusiness");
                String firstMult = rs.getString("multipleFirst");

                htmlCode += "<tr>";
                htmlCode += "<td>" + id + "</td>";
                htmlCode += "<td>" + planeModel + "</td>";
                htmlCode += "<td>" + capacity + "</td>";
                htmlCode += "<td>" + economy + "</td>";
                htmlCode += "<td>" + business + "</td>";
                htmlCode += "<td>" + firstclass + "</td>";
                htmlCode += "<td> <button name=\"editPlaneButton\" onclick=\"viewPlaneModal("+rs.getString("ID")+");alterModalForm(&quot;"+id+"&quot;);\"> Edit </button></td>";
                htmlCode += "</tr>";

                modalCode += "<div id=\"planeModal"+id+"\" class=\"planeModal\">";
                modalCode += "<div class=\"planeModalContent\">";
                modalCode += "<span class=\"planeModalClose\" onclick=\"closeEditPlane(&quot;"+id+"&quot;)\">&times;</span>";
                modalCode += "<form action=\"AirFunctions.Admin.AdminPlanes\">\n" +
                        "        <ul class=\"planeForm\">\n" +
                        "            <li><div class=\"planeFormInputTitle\"><b>Plane Type: </b></div>\n" +
                                        getSelectedPlaneModel(con,id, planeModel) +
                        "            </li>\n" +
                        "            <li><div class=\"planeFormInputTitle\"><b>Capacity: </b></div>\n" +
                        "                <input type=\"number\" id=\"planeModalCapacity"+id+"\" name=\"planeCapacity\" value=\""+capacity+"\" min=\"1\" max=\"999\" maxlength=\"3\" step=\"1\" onchange=\"checkCapacity()\" required>\n" +
                        "            </li>\n" +
                        "            <li><div class=\"planeFormInputTitle\"><b>Economy Seats: </b></div>\n" +
                        "                <input type=\"number\" id=\"planeModalEconomySeats"+id+"\" name=\"planeEconomySeats\" value=\"" + economy + "\" min=\"0\" max=\"999\" maxlength=\"3\" step=\"1\" onchange=\"checkCapacity()\" required>\n" +
                        "            </li>\n" +
                        "            <li><div class=\"planeFormInputTitle\"><b>Business Seats: </b></div>\n" +
                        "                <input type=\"number\" id=\"planeModalBusinessSeats"+id+"\" name=\"planeBusinessSeats\" value=\"" + business + "\" min=\"0\" max=\"999\" maxlength=\"3\" step=\"1\" onchange=\"checkCapacity()\" required>\n" +
                        "            </li>\n" +
                        "            <li><div class=\"planeFormInputTitle\"><b>First Seats: </b></div>\n" +
                        "                <input type=\"number\" id=\"planeModalFirstSeats"+id+"\" name=\"planeFirstSeats\" value=\"" + firstclass + "\" min=\"0\" max=\"999\" maxlength=\"3\" step=\"1\" onchange=\"checkCapacity()\" required>\n" +
                        "            </li>\n" +
                        "            <li><div class=\"planeFormInputTitle\"><b>Base Price: </b></div>\n" +
                        "                <input type=\"number\" id=\"planeModalBasePrice"+id+"\" name=\"planeBasePrice\" value=\"" + basePrice + "\" min=\"0\" max=\"9999\" maxlength=\"5\" step=\"1.00\" required>\n" +
                        "            </li>\n" +
                        "            <li><div class=\"planeFormInputTitle\"><b>Economy Price Multiplier: </b></div>\n" +
                        "                <input type=\"number\" id=\"planeModalEconomyMultiple"+id+"\" name=\"planeEconomyMultiple\" value=\"" + econMult + "\" min=\"1\" max=\"10\" maxlength=\"4\" step=\"1\" required>\n" +
                        "            </li>\n" +
                        "            <li><div class=\"planeFormInputTitle\"><b>Business Price Multiplier: </b></div>\n" +
                        "                <input type=\"number\" id=\"planeModalBusinessMultiple"+id+"\" name=\"planeBusinessMultiple\" value=\"" + busMult + "\" min=\"1\" max=\"10\" maxlength=\"4\" step=\"1\" required>\n" +
                        "            </li>\n" +
                        "            <li><div class=\"planeFormInputTitle\"><b>First Price Multiplier: </b></div>\n" +
                        "                <input type=\"number\" id=\"planeModalFirstMultiple"+id+"\" name=\"planeFirstMultiple\" value=\"" + firstMult + "\" min=\"1\" max=\"10\" maxlength=\"4\" step=\"1\" required>\n" +
                        "            </li>\n" +
                        "            <li>\n"  +
                        "                <button type=\"submit\" name=\"updatePlaneButton\" value="+id+"> Update </button>\n" +
                        "                <button type=\"submit\" name=\"removePlaneButton\" value="+id+"> Remove </button>\n" +
                        "            </li>\n" +
                        "        </ul>\n" +
                        "    </form>\n</div>\n</div>";
            }

            htmlCode += "</table>\n\n";
            htmlCode += modalCode;

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return htmlCode;
    }

    public static String getSelectedPlaneModel(Connection con, String planeID, String planeModel){
        String htmlCode=
                "<select id=\"planeModalModelSelect"+planeID+"\" name=\"planeSelect\" onchange=\"alterModalForm(&quot;"+planeID+"&quot;)\">\n" +
                        "       <option disabled >Select Plane Model</option>\n";
        String detailCode="";
        Statement stmt = null;
        try {

            con.setAutoCommit(false);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT * FROM planemodels;");

            while(rs.next()){
                String id = rs.getString("id");
                String model = rs.getString("plane_model");
                String capacity = rs.getString("capacity");
                String hasEcon = rs.getString("hasEconomy");
                String hasBus = rs.getString("hasBusiness");
                String hasFirst = rs.getString("hasFirst");


                htmlCode +=
                        "<option id=\"planeModalModel"+id+"\" name=\"planeModalSelectionModelID\" value=\""+id+"\"";

                if(planeModel.equals(model)){
                    htmlCode += " selected ";
                }

                htmlCode += ">"+model+"</option>\n";

                /*detailCode +=
                        "<span id=\"planeModalModelModel"+id+"\" name=\"planeModalModelModel" + id + "\"style=\"display: none;\" >"+model+"</span>\n" +
                                "<span id=\"planeModalModelCapacity"+id+"\" name=\"planeModalModelCapacity" + id + "\" style=\"display: none;\">"+capacity+"</span>\n" +
                                "<span id=\"planeModalModelEconomy"+id+"\" name=\"planeModalModelEconomy" + id + "\"  style=\"display: none;\">"+hasEcon+"</span>\n" +
                                "<span id=\"planeModalModelBusiness"+id+"\" name=\"planeModalModelBusiness" + id + "\" style=\"display: none;\">"+hasBus+"</span>\n" +
                                "<span id=\"planeModalModelFirst"+id+"\" name=\"planeModalModelFirst" + id + "\" style=\"display: none;\">"+hasFirst+"</span>\n";
*/

            }

            htmlCode += "</select>";
            //htmlCode += detailCode;

            con.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records updated successfully");

        return htmlCode;
    }

    public static boolean updateAirplane(Connection con, String planeID,String modelID, String capacity, String econSeats,
                                      String busSeats, String firstSeats, String basePrice, String econMult,
                                      String busMult, String firstMult) {
        Statement stmt = null;
        boolean airplaneAdded = false;
        try {

            con.setAutoCommit(false);
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM planes WHERE id="+modelID+";");

            if(rs.next()) {
                String planeType = rs.getString("plane_model");

                String sql = "UPDATE INTO PLANES (PLANE_TYPE, CAPACITY," +
                        " maxFirst, maxBusiness, maxEconomy, takenFirst, takenBusiness, takenEconomy," +
                        "Base_Price, multipleFirst," +
                        " multipleBusiness, multipleEconomy) " +
                        "VALUES ( '" + planeType + "' , " + capacity + " , "
                        + firstSeats + ", " + busSeats + " , "
                        + econSeats + ", 0, 0, 0, "+basePrice+","+firstMult+" , "+busMult+" , "+econMult+")"+
                        "WHERE id="+planeID+";";
                stmt.executeUpdate(sql);
                airplaneAdded = true;
            }

            stmt.close();
            con.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");

        return airplaneAdded;
    }

    public static void deleteAirplane(Connection con, String planeID){

        Statement stmt = null;
        try {
            con.setAutoCommit(false);
            stmt = con.createStatement();
            String sql = "DELETE FROM planes WHERE ID = "+planeID+";";
            stmt.executeUpdate(sql);
            stmt.close();
            con.commit();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Airplaine "+planeID+" deleted");

    }
}
