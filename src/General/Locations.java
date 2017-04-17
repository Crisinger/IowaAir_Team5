package General;// Imported packages
import General.AccountFunctions;
import General.CityFunctions;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet(name="Locations",value="/General.Locations")
public class Locations extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("addLocationButton") != null){

            try {
                Connection con = AccountFunctions.OpenDatabase();
                CityFunctions.setActivityForCity(con,
                        CityFunctions.getIndex(con,
                                request.getParameter("stateName"),
                                request.getParameter("cityName")),
                        1);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            response.sendRedirect("adminLocations.jsp");

        } else if(request.getParameter("removeLocationButton") != null){

            try {
                Connection con = AccountFunctions.OpenDatabase();
                CityFunctions.setActivityForCity(con, request.getParameter("cityID"), 0);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendRedirect("adminLocations.jsp");

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public static String getAirports(){
        String htmlCode = "";
        try {
            Connection con = AccountFunctions.OpenDatabase();
            htmlCode = CityFunctions.getActiveCities(con);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return htmlCode;
    }


}
