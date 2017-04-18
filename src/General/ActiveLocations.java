package General;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by johnn on 4/17/2017.
 */
@WebServlet(name = "ActiveLocations")
public class ActiveLocations extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String activeList = attemptGetActiveLocations();
        response.setContentType("text/plain");
        response.getWriter().print(activeList);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static String attemptGetActiveLocations(){
        Connection con = AccountFunctions.OpenDatabase();
        String activeList = citiesToJSON(CityFunctions.getActiveLocations(con));
        AccountFunctions.closeConnection(con);
        return activeList;
    }

    private static String citiesToJSON(ArrayList<String[]> activeList){
        String[] list = {"acID","city","state"}; // and numberActive, states, cities;
        String jsonActive = "{\"numberActive\"="+activeList.size()+"\"states\"=[";

        for(int j=0; j<activeList.get(0).length;j++){
            jsonActive = "";
        }



        //\"location\":[";
        for(int i=0; i< activeList.size(); i++){
            jsonActive += "{\""+list[0]+"\":"+activeList.get(i)[0]+",";
            jsonActive += "\""+list[1]+"\":\""+activeList.get(i)[1]+"\",";
            jsonActive += "\""+list[2]+"\":\""+activeList.get(i)[2]+"\"},";
        }
        return jsonActive.substring(0,jsonActive.length()-1)+"]}";
    }





}
