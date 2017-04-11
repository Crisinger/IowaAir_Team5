package AirFunctions.Admin;
import AirFunctions.AccountFunctions;
import AirFunctions.AirplaneFunctions;

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

import javax.servlet.annotation.WebServlet;

/**
 * Created by johnn on 4/8/2017.
 */
@WebServlet("/AirFunctions.Admin.AdminPlanes")
public class AdminPlanes extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            if(request.getParameter("addPlaneButton") != null){

                String[] planeInfo = getPlaneInfo(request);

                for(int i=0; i<planeInfo.length; i++){
                    if(planeInfo[i]==null){
                        planeInfo[i]="0";
                    }
                }

                Connection con = AccountFunctions.OpenDatabase();

                AirplaneFunctions.addAirplane(con,
                       planeInfo[0], planeInfo[1], planeInfo[2],
                       planeInfo[3], planeInfo[4], planeInfo[5],
                       planeInfo[6], planeInfo[7], planeInfo[8]);
                con.close();

            } else if(request.getParameter("removePlaneButton") != null){

                Connection con = AccountFunctions.OpenDatabase();
                String planeID = request.getParameter("removePlaneButton");
                AirplaneFunctions.deleteAirplane(con,planeID);

                con.close();

            } else if(request.getParameter("updatePlaneButton") != null){

                Connection con = AccountFunctions.OpenDatabase();

                String planeID = request.getParameter("updatePlaneButton");

                System.out.println(planeID);
                String[] planeInfo = getPlaneInfo(request);

                for(int i=0; i<planeInfo.length; i++){
                    if(planeInfo[i]==null){
                        planeInfo[i]="0";
                    }
                }

                AirplaneFunctions.updateAirplane(con, planeID,
                        planeInfo[0], planeInfo[1], planeInfo[2],
                        planeInfo[3], planeInfo[4], planeInfo[5],
                        planeInfo[6], planeInfo[7], planeInfo[8]);

                con.close();
            }

            response.sendRedirect("adminPlanes.jsp");

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static String getPlanes(){
        String htmlCode = "";
        try {
            Connection con = AccountFunctions.OpenDatabase();
            htmlCode = AirplaneFunctions.showPlanes(con);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return htmlCode;
    }

    public static String[] getPlaneInfo(HttpServletRequest request){
        return new String[]{
                request.getParameter("planeSelect"), request.getParameter("planeCapacity"),
                request.getParameter("planeEconomySeats"), request.getParameter("planeBusinessSeats"),
                request.getParameter("planeFirstSeats"), request.getParameter("planeBasePrice"),
                request.getParameter("planeEconomyMultiple"), request.getParameter("planeBusinessMultiple"),
                request.getParameter("planeFirstMultiple")
        };
    }
}
