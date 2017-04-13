package AirFunctions.Admin;

import AirFunctions.AccountFunctions;
import AirFunctions.AirplaneFunctions;
import AirFunctions.Calculations;
import AirFunctions.CityFunctions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by johnn on 4/8/2017.
 */
@WebServlet("/AirFunctions.Admin.AdminFlights")
public class AdminFlights extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String departState = request.getParameter("flightDepartureLocationState");
            String departCity = request.getParameter("flightDepartureLocationCity");
            String arrivalState = request.getParameter("flightArrivalLocationState");
            String arrivalCity = request.getParameter("flightArrivalLocationCity");
            String planeModel = request.getParameter("flightPlaneModelSelect");

            System.out.println("-----------------------");
            System.out.println("departState: " + departState);
            System.out.println("departCity: " + departCity);
            System.out.println("arrivalState: " + arrivalState);
            System.out.println("arrivalCity: " + arrivalCity);
            System.out.println("planeModel: " + planeModel);
            System.out.println("-----------------------");

            System.out.println(request);
            System.out.println(request.getContextPath());
            System.out.println(request.getQueryString());

            double[] departLatAndLong = getLatAndLong(departState, departCity);
            double[] arrivalLatAndLong = getLatAndLong(arrivalState, arrivalCity);
            double distance = Calculations.getDistanceBetweenCitiesInKilometers(
                    departLatAndLong[0], departLatAndLong[1],
                    arrivalLatAndLong[0], arrivalLatAndLong[1]);

            String[] planeSpecifics = getPlaneSpecifics(planeModel);
            int passengerCapacity = Integer.parseInt(planeSpecifics[0]);
            int fuelCap = Integer.parseInt(planeSpecifics[1]);
            double fuelBurnRate = Double.parseDouble(planeSpecifics[2]);
            int averageVelocity = Integer.parseInt(planeSpecifics[3]);


            int canTravel = (Calculations.hasEnoughFuel(fuelCap, fuelBurnRate, distance)) ? 1: 0;
            int time = (int)(distance * 60 / averageVelocity);
            int basePrice = Calculations.getBasePrice(fuelCap, distance, passengerCapacity);

            String responseText = "{\"canTravel\":"+canTravel+",\"timed\":"+time+",\"basePrice\":"+basePrice+"}";

            System.out.println("hello");
            response.setContentType("text/plain");
            response.getWriter().print(responseText);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String departDate = request.getParameter("flightDepartureDate");
            String departTime = request.getParameter("flightDepartureTime");
            String arrivalDate = request.getParameter("flightArrivalDate");
            String arrivalTime = request.getParameter("flightArrivalTime");
            String planeModel = request.getParameter("flightPlaneModelSelect");

            System.out.println("-----------------------");
            System.out.println("departDate: " + departDate);
            System.out.println("departTime: " + departTime);
            System.out.println("arrivalDate: " + arrivalDate);
            System.out.println("arrivalTime: " + arrivalTime);
            System.out.println("planeModel: " + planeModel);
            System.out.println("-----------------------");


            //System.out.println(distance);

            //System.out.println(fuelCap);

            //System.out.println(fuelBurnRate);
            //System.out.println(canTravel);


            response.getWriter().print("birdsFly");

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private static String getJSONFormat(String title, String value){
        return "\""+title+"\":\""+value+"\"";
    }

    private static String[] getPlaneSpecifics(String planeModel){
        String[] value = new String[4];
        try {
            Connection con = AccountFunctions.OpenDatabase();
            value = AirplaneFunctions.planeModelSpecifics(con, planeModel);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    private static double[] getLatAndLong(String state, String city){
        double[] value = {0.0,0.0};
        try {
            Connection con = AccountFunctions.OpenDatabase();
            String[] valueString = CityFunctions.getLatAndLong(con,state,city);
            value[0] = Double.parseDouble(valueString[0]);
            value[1] = Double.parseDouble(valueString[1]);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;

    }





}
