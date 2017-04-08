package AirFunctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by crisi_000 on 4/6/2017.
 */
public class CityFunctions {


    public static void main( String[]  args){
        Connection connection = null;
        try {
            boolean working = false;
            connection = AccountFunctions.OpenDatabase();
            //createCityTable(connection);
            addStatesAndCities(connection);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void createCityTable(Connection c) {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            //stmt.executeUpdate(sql);
            String sql = "CREATE TABLE CITIES " +
                    "(ID INT PRIMARY KEY     NOT NULL AUTO_INCREMENT," +
                    " CITYNAME           TEXT    NOT NULL, " +
                    " STATE        TEXT     NOT NULL);";
                    /*" AIRPORT       TEXT    NOT NULL)";*/
            //String sql = "ALTER TABLE cities DROP COLUMN AIRPORT;";
            //String sql = "ALTER TABLE cities ADD COLUMN ACTIVE BOOLEAN;";

            stmt.executeUpdate(sql);

            stmt.close();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public static String getIndex(Connection con, String state, String city){
        Statement stmt = null;
        String id = "0";
        try {
            stmt = con.createStatement();
            String sql = "SELECT * FROM cities WHERE state='"+state+"' AND cityname='"+city+"';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                id = rs.getString("id");
            }

            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return id;
    }

    public static void setActivityForCity(Connection con, String index, int active){

        Statement stmt = null;
        try {
            con.setAutoCommit(false);
            stmt = con.createStatement();
            stmt.executeUpdate("UPDATE cities SET active="+active+" WHERE id="+index+";");
            con.commit();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static String getActiveCities(Connection con){
        Statement stmt = null;
        String htmlCode = "";

        try{
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM cities WHERE active=TRUE ORDER BY state, cityName;");
            htmlCode += "<table class=\"admin_man_table\"><tr>";
            htmlCode += "<th><b>State</b></th>";
            htmlCode += "<th><b>City</b></th>";
            htmlCode += "<th><b>Action</b></th>";
            htmlCode += "</tr>";

            while(rs.next()){
                htmlCode += "<form action=\"AirFunctions.Admin.AdminLocations\">";
                htmlCode += "<input type=\"hidden\" name=\"cityID\" value=\""+ rs.getString("ID") + "\" >";
                htmlCode += "<tr>";
                htmlCode += "<td>" + rs.getString("state") + "</td>";
                htmlCode += "<td>" + rs.getString("cityname") + "</td>";
                htmlCode += "<td> <button type=\"submit\" name=\"removeLocationButton\">Remove</button></td>";
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

    public static void addStatesAndCities(Connection con){
        String[][] stateCityInfo =
                        {{"Alabama", "Birmingham", "Montgomery", "Mobile", "Huntsville", "Tuscaloosa"},
                        {"Alaska", "Anchorage", "Juneau", "Fairbanks", "Sitka", "Ketchikan"},
                        {"Arizona", "Phoenix", "Tucson", "Mesa", "Chandler", "Glendale"},
                        {"Arkansas", "Little Rock", "Fort Smith", "Fayetteville", "Springdale", "Jonesboro"},
                        {"California", "Los Angeles", "San Diego", "San Jose", "San Francisco", "Fresno"},
                        {"Colorado", "Denver", "Colorado Springs", "Aurora", "Fort Collins", "Lakewood"},
                        {"Connecticut", "Bridgeport", "New Haven", "Stamford", "Hartford", "Waterbury"},
                        {"Delaware", "Wilmington", "Dover", "Newark", "Middletown", "Smyrna"},
                        {"Florida", "Jacksonville", "Miami", "Tampa", "Orlando", "St. Petersburg"},
                        {"Georgia", "Atlanta", "Columbus", "Augusta", "Macon", "Savannah"},
                        {"Hawaii", "Honolulu", "Hilo", "Kailua1", "Kapolei", "Kaneohe1"},
                        {"Idaho", "Boise", "Meridian", "Nampa", "Idaho Falls", "Pocatello"},
                        {"Illinois", "Chicago", "Aurora", "Rockford", "Joliet", "Naperville"},
                        {"Indiana", "Indianapolis", "Fort Wayne", "Evansville", "South Bend", "Carmel"},
                        {"Iowa", "Des Moines", "Cedar Rapids", "Davenport", "Sioux City", "Waterloo"},
                        {"Kansas", "Wichita", "Overland Park", "Kansas City", "Olathe", "Topeka"},
                        {"Kentucky", "Louisville", "Lexington", "Bowling Green", "Owensboro", "Covington"},
                        {"Louisiana", "New Orleans", "Baton Rouge", "Shreveport", "Lafayette", "Lake Charles"},
                        {"Maine", "Portland", "Lewiston", "Bangor", "South Portland", "Auburn"},
                        {"Maryland", "Baltimore", "Columbia", "Germantown", "Silver Spring", "Waldorf"},
                        {"Massachusetts", "Boston", "Worcester", "Springfield", "Lowell", "Cambridge"},
                        {"Michigan", "Detroit", "Grand Rapids", "Warren", "Sterling Heights", "Ann Arbor"},
                        {"Minnesota", "Minneapolis", "Saint Paul", "Rochester", "Bloomington", "Duluth"},
                        {"Mississippi", "Jackson", "Gulfport", "Southaven", "Hattiesburg", "Biloxi"},
                        {"Missouri", "Kansas City", "Saint Louis", "Springfield", "Independence", "Columbia"},
                        {"Montana", "Billings", "Missoula", "Great Falls", "Bozeman", "Butte"},
                        {"Nebraska", "Omaha", "Lincoln", "Bellevue", "Grand Island", "Kearney"},
                        {"Nevada", "Las Vegas", "Henderson", "Reno", "North Las Vegas", "Sparks"},
                        {"New Hampshire", "Manchester", "Nashua", "Concord", "Derry", "Rochester"},
                        {"New Jersey", "Newark", "Jersey City", "Paterson", "Elizabeth", "Edison"},
                        {"New Mexico", "Albuquerque", "Las Cruces", "Rio Rancho", "Santa Fe", "Roswell"},
                        {"New York", "New York City", "Buffalo", "Rochester", "Yonkers", "Syracuse"},
                        {"North Carolina", "Charlotte", "Raleigh", "Greensboro", "Durham", "Winston-Salem"},
                        {"North Dakota", "Fargo", "Bismarck", "Grand Forks", "Minot", "West Fargo"},
                        {"Ohio", "Columbus", "Cleveland", "Cincinnati", "Toledo", "Akron"},
                        {"Oklahoma", "Oklahoma City", "Tulsa", "Norman", "Broken Arrow", "Lawton"},
                        {"Oregon", "Portland", "Salem", "Eugene", "Gresham", "Hillsboro"},
                        {"Pennsylvania", "Philadelphia", "Pittsburgh", "Allentown", "Erie", "Reading"},
                        {"Rhode Island", "Providence", "Warwick", "Cranston", "Pawtucket", "East Providence"},
                        {"South Carolina", "Columbia", "Charleston", "North Charleston", "Mount Pleasant", "Rock Hill"},
                        {"South Dakota", "Sioux Falls", "Rapid City", "Aberdeen", "Brookings", "Watertown"},
                        {"Tennessee", "Memphis", "Nashville", "Knoxville", "Chattanooga", "Clarksville"},
                        {"Texas", "Houston", "San Antonio", "Dallas", "Austin", "Fort Worth"},
                        {"Utah", "Salt Lake City", "West Valley City", "Provo", "West Jordan", "Orem"},
                        {"Vermont", "Burlington", "Essex", "South Burlington", "Colchester", "Rutland"},
                        {"Virginia", "Virginia Beach", "Norfolk", "Chesapeake", "Arlington", "Richmond"},
                        {"Washington", "Seattle", "Spokane", "Tacoma", "Vancouver", "Bellevue"},
                        {"West Virginia", "Charleston", "Huntington", "Morgantown", "Parkersburg", "Wheeling"},
                        {"Wisconsin", "Milwaukee", "Madison", "Green Bay", "Kenosha", "Racine"},
                        {"Wyoming", "Cheyenne", "Casper", "Laramie", "Gillette", "Rock Springs"}};
        try {

            con.setAutoCommit(false);

            for (int state=0; state<stateCityInfo.length; state++){
                String currentState = stateCityInfo[state][0];

                for(int city=1; city<stateCityInfo[state].length; city++){
                    String currentCity = stateCityInfo[state][city];
                    Statement stmt = null;
                    stmt = con.createStatement();
                    String sql = "Select COUNT(*) FROM cities WHERE CITYNAME='"+currentCity+"' AND STATE='"+currentState+"';";
                    ResultSet rs = stmt.executeQuery(sql);

                    if(rs.next()) {
                        if (rs.getString("Count(*)").equals("0")) {
                            sql = "INSERT INTO cities (CITYNAME, STATE, ACTIVE) VALUES ('" + currentCity + "', '" + currentState + "', false);";
                            stmt = null;
                            stmt = con.createStatement();
                            stmt.executeUpdate(sql);
                        }
                    }


                    stmt.close();
                }
            }
            con.commit();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");

    }

    public static String getStates(){
        Statement stmt = null;
        String stateCode = "";

        try{

            Connection con = AccountFunctions.OpenDatabase();
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT state FROM cities ORDER BY state;");

            stateCode = "<b>State</b>\n<select name=\"stateName\" required>\n<option selected=\"selected\" value=\"\"></option>\n";

            while(rs.next()){
                String currentState = rs.getString("state");
                String classState = currentState.replace(" ","-").toLowerCase();

                stateCode += "<option value=\""+currentState+"\" onclick=\"alterCityMenu(&quot;" + classState + "&quot;)\">";
                stateCode += currentState;
                stateCode += "</option>\n";
            }

            stateCode += "</select>";

            con.close();

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }

        return stateCode;
    }

    public static String getCities(){
        Statement stmt = null;
        String cityCode = "";

        try{
            Connection con = AccountFunctions.OpenDatabase();

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM cities ORDER BY state, cityname;");

            cityCode = "<b>City</b>\n<select name=\"cityName\" required>\n<option selected=\"selected\" value=\"\"></option>\n";

            while(rs.next()){
                String currentState = rs.getString("state");
                String currentCity = rs.getString("cityName");
                String classState = currentState.replace(" ","-").toLowerCase();

                cityCode += "<option class=\""+classState+" adminAllCities\" value=\"" + currentCity + "\">";
                cityCode += currentCity;
                cityCode += "</option>\n";

            }

            cityCode += "</select>";

            con.close();

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }

        return cityCode;
    }

}
