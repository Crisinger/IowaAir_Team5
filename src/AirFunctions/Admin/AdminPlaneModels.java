package AirFunctions.Admin;
import AirFunctions.AccountFunctions;
import AirFunctions.AirplaneFunctions;
import AirFunctions.CityFunctions;

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

/**
 * Created by johnn on 4/8/2017.
 */
@WebServlet("/AirFunctions.Admin.AdminPlaneModels")
public class AdminPlaneModels extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String economy = request.getParameter("hasEconomyClass");
            String business = request.getParameter("hasBusinessClass");
            String firstclass = request.getParameter("hasFirstClass");
            if(economy==null){
                economy = "false";
            }
            if(business==null){
                business = "false";
            }
            if(firstclass==null){
                firstclass = "false";
            }

            if(request.getParameter("addPlaneModelButton") != null){

                Connection con = AccountFunctions.OpenDatabase();
                AirplaneFunctions.addPlaneModel(con,request.getParameter("planeModel").toUpperCase(),
                        request.getParameter("modelCapacity"),economy,business,firstclass,
                        request.getParameter("modelFuel"), request.getParameter("modelBurn"),
                        request.getParameter("modelVelocity"));
                con.close();

            } else if(request.getParameter("removePlaneModelButton") != null){

                Connection con = AccountFunctions.OpenDatabase();
                AirplaneFunctions.deletePlaneModel(con,request.getParameter("planeModelID"));
                con.close();

            } else if(request.getParameter("updatePlaneModelButton") != null){

                Connection con = AccountFunctions.OpenDatabase();
                AirplaneFunctions.updatePlaneModel(con,request.getParameter("planeModelID"),
                        request.getParameter("planeModel").toUpperCase(),
                        request.getParameter("modelCapacity"),economy,business,firstclass,
                        request.getParameter("modelFuel"), request.getParameter("modelBurn"),
                        request.getParameter("modelVelocity"));
                con.close();
            }

            response.sendRedirect("adminPlanes.jsp");

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static String getPlaneModels(){
        String htmlCode = "";
        try {
            Connection con = AccountFunctions.OpenDatabase();
            htmlCode = AirplaneFunctions.showPlaneModels(con);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return htmlCode;
    }


    public static String getPlaneModelList(){
        String htmlCode = "";
        try {
            Connection con = AccountFunctions.OpenDatabase();
            htmlCode = AirplaneFunctions.planeModelsList(con);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return htmlCode;
    }

}