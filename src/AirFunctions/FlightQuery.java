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
import javax.servlet.http.HttpSession;

@WebServlet("/AirFunctions.FlightQuery")
public class FlightQuery extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startLocation = "Departure_Location='" + request.getParameter("startLocation")+"'";
        String endLocation = "Arrival_Location='" + request.getParameter("endLocation")+"'";
        //String startDate = "Departure_Date="+request.getParameter("startDate");
        //String endDate = "Arrival_Date="+request.getParameter("endDate");
        //String passengers = "request.getParameter("passengers");

        String criteria = startLocation + " AND " + endLocation;// + " AND " + startDate + " AND " + endDate;
        //String[] criteria = {startLocation,endLocation,startDate,endDate,passengers};

        Connection con = AccountFunctions.OpenDatabase();
        Statement stmt = null;
        String htmlCode = "";

        try{
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM FLIGHTS WHERE "+criteria+";");
            while(rs.next()){
                int id = rs.getInt("Flight_ID");
                htmlCode += "<form id=\"flightQuery\">\n";
                htmlCode += "<p class=\"trip_places\"><leave>Depart</leave> " + rs.getString("Departure_Location");
                htmlCode += " <sep>to</sep> " + rs.getString("Arrival_Location") + "</p>";
                htmlCode += "<p class=\"trip_date\">" + rs.getString("Departure_Date");
                if(!rs.getString("Departure_Date").equals(rs.getString("Arrival_Date"))) {
                    htmlCode += " to " + rs.getString("Arrival_Date") + "</p>\n";
                } else {
                    htmlCode += "</p>";
                }
                htmlCode += "<p class=\"trip_time\">" + rs.getString("Departure_Time");
                htmlCode += " &#8594; " + rs.getString("Arrival_Time") + "</p>\n";
                htmlCode += "<input type=\"submit\" name=\""+id+"\" value=\"Book It!\">\n";
                htmlCode += "</form>\n";
            }

            HttpSession mySession = request.getSession();
            mySession.setAttribute("flightQuery",htmlCode);
            con.close();

            response.sendRedirect("index.jsp");

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }




    }

    public static String getLocations(String type){
        Connection con = AccountFunctions.OpenDatabase();
        Statement stmt = null;
        String htmlCode = "";

        try{
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT "+type+" FROM flights ;");
            while(rs.next()){
                String location = rs.getString(type);
                htmlCode += "<option value=\""+ location + "\">";
                htmlCode += location;
                htmlCode += "</option>\n";
            }

            con.close();

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }

        return htmlCode;
    }

    public static String getDates(){
        String htmlCode = "";
        htmlCode += "<link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css\">";
        htmlCode += "<link rel=\"stylesheet\" href=\"/resources/demos/style.css\">";
        htmlCode += "<script src=\"https://code.jquery.com/jquery-1.12.4.js\"></script>";
        htmlCode += "<script src=\"https://code.jquery.com/ui/1.12.1/jquery-ui.js\"></script>";

        htmlCode += "<link rel=\"stylesheet\" href=\"//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css\">";
        htmlCode += "<script src=\"//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js\"></script>";

        //htmlCode += "<script type=\"text/javascript\" src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js\"></script>";

        //htmlCode += "<script type=\"text/javascript\" src=\"http://jonthornton.github.io/jquery-timepicker/jquery.timepicker.js\"></script>";
        //htmlCode += "<link rel=\"stylesheet\" type=\"text/css\" href=\"http://jonthornton.github.io/jquery-timepicker/jquery.timepicker.css\">";
        //htmlCode += "<script type=\"text/javascript\" src=\"http://jonthornton.github.io/jquery-timepicker/lib/bootstrap-datepicker.js\"></script>";
        //htmlCode += "<link rel=\"stylesheet\" type=\"text/css\" href=\"http://jonthornton.github.io/jquery-timepicker/lib/bootstrap-datepicker.css\">";

        return htmlCode;
    }

    public static String addPickers(String type){
        String htmlCode = "";
        htmlCode += "<script> $( function() { $( \"#"+type+"datepicker\" ).datepicker();} ); </script>";
        htmlCode += "<script> $( function() { $(\"#"+type+"timepicker\").timepicker();}) </script>";//{" +
                /*"timeFormat: 'h:mm p'," +
                "interval: 30," +
                "minTime: '12:00 am'," +
                "maxTime: '11:30 pm'," +
                "defaultTime: '12:00 am'," +
                "startTime: '12:00 pm'," +
                "dynamic: false," +
                "dropdown: true," +
                "scrollbar: true" +
                "}); </script>\n";*/
        return htmlCode;
    }
}

