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
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;


/**
 * Created by johnn on 4/8/2017.
 */
@WebServlet("/AirFunctions.Admin.AdminFlights")
public class AdminFlights extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {

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
            int distancePrice = Calculations.getDistancePrice(distance);

            String responseText = "{\"canTravel\":"+canTravel+",\"timed\":"+time+",\"distancePrice\":"+distancePrice+"}";

            System.out.println(responseText);

            response.setContentType("text/plain");
            response.getWriter().print(responseText);
            System.out.println("-----------------------");

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

            String json = compileJSON(getAvailableAirPlanes(planeModel,departDate,
                    departTime,arrivalDate,arrivalTime));

            System.out.println(json);
            response.setContentType("text/plain");
            response.getWriter().print(json);

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private static String[] getPlaneSpecifics(String planeModel){
        Connection con = AccountFunctions.OpenDatabase();
        String[] value = AirplaneFunctions.planeModelSpecifics(con, planeModel);
        AccountFunctions.closeConnection(con);
        return value;
    }

    private static double[] getLatAndLong(String state, String city) {
        double[] value = {0.0, 0.0};
        Connection con = AccountFunctions.OpenDatabase();
        String[] valueString = CityFunctions.getLatAndLong(con, state, city);
        value[0] = Double.parseDouble(valueString[0]);
        value[1] = Double.parseDouble(valueString[1]);
        AccountFunctions.closeConnection(con);

        return value;
    }

    private static ArrayList<String[]> getAvailableAirPlanes(String planeModel, String departDate, String departTime,
                                                             String arriveDate, String arriveTime){

        Connection con = AccountFunctions.OpenDatabase();
        ArrayList<String[]> planes = AirplaneFunctions.airplanesCurrentlyAvailable(con, planeModel, departDate, departTime,
                arriveDate, arriveTime);
        AccountFunctions.closeConnection(con);
        return planes;
    }

    private static String compileJSON(ArrayList<String[]> planeInfo){
        String[] list = {"planeType","id","capacity","maxEcon","maxBus","maxFirst","basePrice"};
        String jsonPlanes = "";
        boolean hasPlane = false;
        int numberOfPlanes = 0;
        for (int i = 0; i < planeInfo.size(); i++) {
            if (planeInfo.get(i)[0]!=null) {
                numberOfPlanes++;
                hasPlane = true;
                jsonPlanes += "{";
                jsonPlanes += "\"" + list[0] + "\":\"" + planeInfo.get(i)[0] + "\"";
                for (int j = 1; j < planeInfo.get(i).length; j++) {
                    jsonPlanes += ",\"" + list[j] + "\":" + planeInfo.get(i)[j];
                }
                jsonPlanes += "},";
            }
        }
        String json = "{\"numberOfPlanes\":"+numberOfPlanes+",\"planes\":";
        if(hasPlane){
            json += "["+jsonPlanes.substring(0,jsonPlanes.length()-1)+"]";
        }else{
            json += 0;
        }
        json += "}";
        return json;
    }

}
