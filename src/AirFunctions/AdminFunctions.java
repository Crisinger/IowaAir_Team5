package AirFunctions;// Imported packages
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.Random;

@WebServlet("/AirFunctions.AdminFunctions")
public class AdminFunctions extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("addManagerButton") != null){

            addManager(request.getParameter("managerEmail"));
            response.sendRedirect("adminManagers.jsp");

        } else if(request.getParameter("removeManagerButton") != null){

            removeManager(request.getParameter("managerID"));
            response.sendRedirect("adminManagers.jsp");

        } else if (request.getParameter("addFlightButton") != null){



        } else if(request.getParameter("editFlightButton") != null){


        }
    }

    public static String getManagers(){
        Connection con = AccountFunctions.OpenDatabase();
        Statement stmt = null;
        String htmlCode = "";

        try{
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM accounts WHERE role='MANAGER';");
            htmlCode += "<table class=\"admin_man_table\"><tr>";
            htmlCode += "<th><b>Email</b></th>";
            htmlCode += "<th><b>Password</b></th>";
            htmlCode += "<th><b>Action</b></th>";
            htmlCode += "</tr>";

            while(rs.next()){
                htmlCode += "<form action=\"AirFunctions.AdminFunctions\">";
                htmlCode += "<input type=\"hidden\" name=\"managerID\" value=\""+rs.getString("ID") + "\" >";
                htmlCode += "<tr>";
                htmlCode += "<td>" + rs.getString("email") + "</td>";
                htmlCode += "<td>" + rs.getString("password") + "</td>";
                htmlCode += "<td> <button type=\"submit\" name=\"removeManagerButton\">Remove</button></td>";
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

    public static String getFlights(){
        Connection con = AccountFunctions.OpenDatabase();
        Statement stmt = null;
        String htmlCode = "";

        try{
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM flights ORDER BY departure_date , departure_location, departure_time, arrival_date, arrival_location,  arrival_time;");
            htmlCode += "<table class=\"admin_flight_table\"><tr>";
            htmlCode += "<th><b>Depart</b></th>";
            htmlCode += "<th></th>";
            htmlCode += "<th></th>";
            htmlCode += "<th><b>Arrive</b></th>";
            htmlCode += "<th></th>";
            htmlCode += "<th></th>";
            htmlCode += "<th><b>Action</b></th>";
            htmlCode += "</tr>";

            while(rs.next()){
                htmlCode += "<form action=\"AirFunctions.AdminFunctions\">";
                htmlCode += "<input type=\"hidden\" name=\"flightID\" value=\""+rs.getString("Flight_ID") + "\" >";
                htmlCode += "<tr>";
                htmlCode += "<td>" + rs.getString("Departure_Date") + "</td>";
                htmlCode += "<td>" + rs.getString("Departure_Location") + "</td>";
                htmlCode += "<td>" + rs.getString("Departure_Time") + "</td>";
                htmlCode += "<td>" + rs.getString("Arrival_Date") + "</td>";
                htmlCode += "<td>" + rs.getString("Arrival_Location") + "</td>";
                htmlCode += "<td>" + rs.getString("Arrival_Time") + "</td>";
                htmlCode += "<td> <button type=\"submit\" name=\"editFlightButton\">Edit</button></td>";
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

    public static String getPlanes(){
        Connection con = AccountFunctions.OpenDatabase();
        Statement stmt = null;
        String htmlCode = "";

        try{
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM planes ORDER BY plane_type, capacity, classes;");
            htmlCode += "<table class=\"admin_planes_table\"><tr>";
            htmlCode += "<th><b>Plane Type</b></th>";
            htmlCode += "<th><b>Capacity</b></th>";
            htmlCode += "<th><b>Classes</b></th>";
            htmlCode += "<th><b>Action</b></th>";
            htmlCode += "</tr>";

            while(rs.next()){
                htmlCode += "<form action=\"AirFunctions.AdminFunctions\">";
                htmlCode += "<input type=\"hidden\" name=\"planeID\" value=\""+rs.getString("id") + "\" >";
                htmlCode += "<tr>";
                htmlCode += "<td>" + rs.getString("Plane_Type") + "</td>";
                htmlCode += "<td>" + rs.getString("Capacity") + "</td>";
                htmlCode += "<td>" + rs.getString("Classes") + "</td>";
                htmlCode += "<td> <button type=\"submit\" name=\"editPlaneButton\">Edit</button></td>";
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

    public static String getPlaneInfo(String type){
        Connection con = AccountFunctions.OpenDatabase();
        Statement stmt = null;
        String htmlCode = "";
        String clause = "";

        try{
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT "  + type +  " FROM planes ORDER BY " + type + ";");
            while(rs.next()){
                htmlCode += "<option value=\""+ rs.getString(type) + "\">";
                htmlCode += rs.getString(type);
                htmlCode += "</option>\n";
            }
            con.close();

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }

        return htmlCode;
    }

    //protected static String getPlaneList(String type, String capacity, String )



    protected static void addManager(String managerEmail){

        try {
            Connection con = AccountFunctions.OpenDatabase();
            AccountFunctions.addManager(con,managerEmail,randomPassword());
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected static void removeManager( String managerID){
        System.out.println(managerID);
        try {
            Connection con = AccountFunctions.OpenDatabase();
            AccountFunctions.deleteManager(con,Integer.parseInt(managerID));
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String randomPassword(){
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String beta = "abcdefghijklmnopqrstuvwxyz";
        String numeric = "0123456789";

        Random rand = new Random(System.nanoTime());

        String password = "";

        for(int i=0; i<4; i++){

            password += alpha.charAt(rand.nextInt(alpha.length()));
            password += beta.charAt(rand.nextInt(beta.length()));
            password += beta.charAt(rand.nextInt(beta.length()));
            password += numeric.charAt(rand.nextInt(numeric.length()));

        }

        return password;
    }



}
