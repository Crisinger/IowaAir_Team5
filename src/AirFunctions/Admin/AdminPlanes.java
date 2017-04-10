package AirFunctions.Admin;
import AirFunctions.AccountFunctions;

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

                Connection con = AccountFunctions.OpenDatabase();

                con.close();

            } else if(request.getParameter("removePlaneButton") != null){

                Connection con = AccountFunctions.OpenDatabase();

                con.close();

            } else if(request.getParameter("updatePlaneButton") != null){

                Connection con = AccountFunctions.OpenDatabase();

                con.close();
            }

            response.sendRedirect("adminPlanes.jsp");

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
