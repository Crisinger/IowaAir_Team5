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
            //addStatesAndCities(connection);
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
            //String sql = "ALTER TABLE cities ADD COLUMN ACTIVE BOOLEAN NOT NULL, latitude DECIMAL(14,10) NOT NULL, longitude DECIMAL(14,10);";

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

    public static String getActiveCitiesList(){
        return cityList(true);
    }

    public static String getActiveStatesList(String call){
        return stateList(call,true);
    }

    // Only call this. It will populate States, Cities, Lat, and Long.
    private static void addStatesAndCities(Connection con){
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
                        {"Hawaii", "Honolulu", "Hilo", "Kailua", "Kapolei", "Kaneohe"},
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
                            sql = "INSERT INTO cities (CITYNAME, STATE, STATE_CODE, ACTIVE, latitude, longitude) VALUES ('" + currentCity + "', '" + currentState + "', 'AA', false, 0.0, 0.0);";
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
        addLatAndLon(con);
        addStateCodes(con);

    }

    public static String getStates(){
        return stateList( "alterCityMenu",false);
    }

    public static String getCities(){
        return cityList(false);
    }

    private static void addLatAndLon(Connection con){

        String[][] citylatlon = {{"South Dakota", "Aberdeen", "39.499909", "-76.231177"},
                                {"Ohio", "Akron", "43.013808", "-78.525296"},
                                {"New Mexico", "Albuquerque", "35.199592", "-106.644831"},
                                {"Pennsylvania", "Allentown", "40.149712", "-74.539598"},
                                {"Alaska", "Anchorage", "61.287624", "-149.486981"},
                                {"Michigan", "Ann Arbor", "42.266638", "-83.849042"},
                                {"Virginia", "Arlington", "42.417595", "-71.159696"},
                                {"Georgia", "Atlanta", "42.562012", "-77.468518"},
                                {"Maine", "Auburn", "42.184835", "-71.947184"},
                                {"Georgia", "Augusta", "44.351642", "-69.803773"},
                                {"Colorado", "Aurora", "39.729432", "-104.83192"},
                                {"Illinois", "Aurora", "44.641873", "-68.391481"},
                                {"Texas", "Austin", "41.615099", "-77.957975"},
                                {"Maryland", "Baltimore", "39.296536", "-76.623489"},
                                {"Maine", "Bangor", "45.061744", "-68.878893"},
                                {"Louisiana", "Baton Rouge", "30.44924", "-91.185607"},
                                {"Nebraska", "Bellevue", "41.154362", "-95.914557"},
                                {"Washington", "Bellevue", "39.10441", "-84.473642"},
                                {"Montana", "Billings", "41.759905", "-73.743714"},
                                {"Mississippi", "Biloxi", "30.432454", "-88.978634"},
                                {"Alabama", "Birmingham", "39.976041", "-74.711429"},
                                {"North Dakota", "Bismarck", "46.981207", "-100.502724"},
                                {"Minnesota", "Bloomington", "41.875232", "-74.043557"},
                                {"Idaho", "Boise", "43.603768", "-116.272921"},
                                {"Massachusetts", "Boston", "42.370567", "-71.026964"},
                                {"Kentucky", "Bowling Green", "38.008401", "-77.260353"},
                                {"Montana", "Bozeman", "45.809998", "-111.168212"},
                                {"Connecticut", "Bridgeport", "41.186548", "-73.195177"},
                                {"Oklahoma", "Broken Arrow", "35.986399", "-95.818064"},
                                {"South Dakota", "Brookings", "44.355091", "-96.762102"},
                                {"New York", "Buffalo", "42.929303", "-78.832706"},
                                {"Vermont", "Burlington", "42.504844", "-71.201539"},
                                {"Montana", "Butte", "47.69365", "-100.692443"},
                                {"Massachusetts", "Cambridge", "42.380442", "-71.132947"},
                                {"Indiana", "Carmel", "44.808062", "-68.940624"},
                                {"Wyoming", "Casper", "42.859875", "-106.312561"},
                                {"Iowa", "Cedar Rapids", "41.976612", "-91.657578"},
                                {"Arizona", "Chandler", "38.054569", "-87.267847"},
                                {"South Carolina", "Charleston", "45.066999", "-69.040695"},
                                {"West Virginia", "Charleston", "32.776475", "-79.931051"},
                                {"North Carolina", "Charlotte", "44.31658", "-73.226034"},
                                {"Tennessee", "Chattanooga", "35.017818", "-85.206426"},
                                {"Virginia", "Chesapeake", "36.749991", "-76.218759"},
                                {"Wyoming", "Cheyenne", "35.608071", "-99.672542"},
                                {"Illinois", "Chicago", "41.811929", "-87.68732"},
                                {"Ohio", "Cincinnati", "39.166759", "-84.53822"},
                                {"Tennessee", "Clarksville", "42.561988", "-73.957345"},
                                {"Ohio", "Cleveland", "43.27637", "-76.225752"},
                                {"Vermont", "Colchester", "44.49518", "-73.165092"},
                                {"Colorado", "Colorado Springs", "38.861469", "-104.857828"},
                                {"Maryland", "Columbia", "41.696124", "-72.300043"},
                                {"Missouri", "Columbia", "38.951705", "-92.334072"},
                                {"South Carolina", "Columbia", "34.00071", "-81.034814"},
                                {"Georgia", "Columbus", "32.460976", "-84.987709"},
                                {"Ohio", "Columbus", "40.049355", "-74.702475"},
                                {"New Hampshire", "Concord", "42.446396", "-71.459405"},
                                {"Kentucky", "Covington", "41.769456", "-77.000451"},
                                {"Rhode Island", "Cranston", "41.7917", "-71.435251"},
                                {"Texas", "Dallas", "41.271121", "-75.999621"},
                                {"Iowa", "Davenport", "41.523644", "-90.577637"},
                                {"Colorado", "Denver", "42.235727", "-74.56969"},
                                {"New Hampshire", "Derry", "42.950825", "-71.197169"},
                                {"Iowa", "Des Moines", "41.672687", "-93.572173"},
                                {"Michigan", "Detroit", "44.761527", "-69.322662"},
                                {"Delaware", "Dover", "42.236114", "-71.283072"},
                                {"Minnesota", "Duluth", "34.025598", "-84.13045"},
                                {"North Carolina", "Durham", "43.165772", "-70.962843"},
                                {"Rhode Island", "East Providence", "41.813429", "-71.363348"},
                                {"New Jersey", "Edison", "40.519753", "-74.393444"},
                                {"New Jersey", "Elizabeth", "40.672052", "-74.183438"},
                                {"Pennsylvania", "Erie", "42.087337", "-80.087341"},
                                {"Vermont", "Essex", "42.62781", "-70.780576"},
                                {"Oregon", "Eugene", "38.393423", "-92.363038"},
                                {"Indiana", "Evansville", "37.997128", "-87.574963"},
                                {"Alaska", "Fairbanks", "39.190101", "-87.547036"},
                                {"North Dakota", "Fargo", "31.05682", "-82.772422"},
                                {"Arkansas", "Fayetteville", "42.947789", "-75.995518"},
                                {"Colorado", "Fort Collins", "40.59227", "-105.298344"},
                                {"Arkansas", "Fort Smith", "35.231245", "-94.339412"},
                                {"Indiana", "Fort Wayne", "41.093763", "-85.070713"},
                                {"Texas", "Fort Worth", "32.771419", "-97.291484"},
                                {"California", "Fresno", "40.364667", "-81.755507"},
                                {"Maryland", "Germantown", "42.121298", "-73.792924"},
                                {"Wyoming", "Gillette", "40.689339", "-74.472198"},
                                {"Arizona", "Glendale", "42.279292", "-73.343545"},
                                {"North Dakota", "Grand Forks", "47.9041", "-97.431501"},
                                {"Nebraska", "Grand Island", "43.015256", "-78.958945"},
                                {"Michigan", "Grand Rapids", "41.394707", "-83.834935"},
                                {"Montana", "Great Falls", "39.001609", "-77.306416"},
                                {"Wisconsin", "Green Bay", "37.238139", "-78.461789"},
                                {"North Carolina", "Greensboro", "44.600325", "-72.286556"},
                                {"Oregon", "Gresham", "33.952411", "-79.360418"},
                                {"Mississippi", "Gulfport", "30.36742", "-89.092816"},
                                {"Connecticut", "Hartford", "43.672103", "-72.355539"},
                                {"Mississippi", "Hattiesburg", "31.245138", "-89.28071"},
                                {"Nevada", "Henderson", "43.812653", "-76.208109"},
                                {"Oregon", "Hillsboro", "43.12295", "-71.914287"},
                                {"Hawaii", "Hilo", "19.564519", "-155.26203"},
                                {"Hawaii", "Honolulu", "24.859832", "-168.021815"},
                                {"Texas", "Houston", "40.133091", "-80.133451"},
                                {"West Virginia", "Huntington", "42.313427", "-72.903677"},
                                {"Alabama", "Huntsville", "34.718428", "-86.556439"},
                                {"Idaho", "Idaho Falls", "43.516701", "-111.691535"},
                                {"Missouri", "Independence", "36.638835", "-81.194296"},
                                {"Indiana", "Indianapolis", "39.775092", "-86.13216"},
                                {"Mississippi", "Jackson", "43.883871", "-71.257726"},
                                {"Florida", "Jacksonville", "42.795363", "-72.817838"},
                                {"New Jersey", "Jersey City", "40.73276", "-74.075485"},
                                {"Illinois", "Joliet", "45.494126", "-108.992234"},
                                {"Arkansas", "Jonesboro", "45.002969", "-67.495548"},
                                {"Alaska", "Juneau", "40.640031", "-79.129445"},
                                {"Hawaii", "Kailua", "21.402222", "-157.739444"},
                                {"Hawaii", "Kaneohe", "21.399648", "-157.797365"},
                                {"Kansas", "Kansas City", "39.102404", "-94.598583"},
                                {"Missouri", "Kansas City", "39.114053", "-94.627464"},
                                {"Hawaii", "Kapolei", "21.341784", "-158.091383"},
                                {"Nebraska", "Kearney", "39.374073", "-94.363191"},
                                {"Wisconsin", "Kenosha", "42.622449", "-87.830375"},
                                {"Alaska", "Ketchikan", "55.400674", "-131.67409"},
                                {"Tennessee", "Knoxville", "41.93602", "-77.445729"},
                                {"Louisiana", "Lafayette", "41.128183", "-74.731759"},
                                {"Louisiana", "Lake Charles", "30.233355", "-93.214903"},
                                {"Colorado", "Lakewood", "39.94517", "-74.149784"},
                                {"Wyoming", "Laramie", "41.43902", "-105.801022"},
                                {"New Mexico", "Las Cruces", "32.41467", "-106.854065"},
                                {"Nevada", "Las Vegas", "35.551539", "-104.985643"},
                                {"Oklahoma", "Lawton", "41.820346", "-75.80463"},
                                {"Maine", "Lewiston", "44.086323", "-70.169297"},
                                {"Kentucky", "Lexington", "42.45631", "-71.21665"},
                                {"Nebraska", "Lincoln", "42.446396", "-71.459405"},
                                {"Arkansas", "Little Rock", "34.495502", "-79.348165"},
                                {"California", "Los Angeles", "33.973951", "-118.248405"},
                                {"Kentucky", "Louisville", "33.028798", "-82.437965"},
                                {"Massachusetts", "Lowell", "42.446396", "-71.459405"},
                                {"Georgia", "Macon", "37.553314", "-77.892964"},
                                {"Wisconsin", "Madison", "43.902311", "-71.124612"},
                                {"New Hampshire", "Manchester", "42.579503", "-70.755062"},
                                {"Tennessee", "Memphis", "43.098983", "-76.413216"},
                                {"Idaho", "Meridian", "43.089111", "-76.621747"},
                                {"Arizona", "Mesa", "39.14932", "-108.169723"},
                                {"Florida", "Miami", "38.296818", "-81.554655"},
                                {"Delaware", "Middletown", "41.519757", "-71.273101"},
                                {"Wisconsin", "Milwaukee", "36.363144", "-77.386748"},
                                {"Minnesota", "Minneapolis", "36.086466", "-81.934265"},
                                {"North Dakota", "Minot", "41.970474", "-70.701357"},
                                {"Montana", "Missoula", "46.853606", "-113.909123"},
                                {"Alabama", "Mobile", "30.701142", "-88.103184"},
                                {"Alabama", "Montgomery", "44.824855", "-72.895849"},
                                {"West Virginia", "Morgantown", "40.367731", "-75.89161"},
                                {"South Carolina", "Mount Pleasant", "40.202211", "-79.609982"},
                                {"Idaho", "Nampa", "43.595567", "-116.61063"},
                                {"Illinois", "Naperville", "41.759029", "-88.152381"},
                                {"New Hampshire", "Nashua", "42.771537", "-71.626336"},
                                {"Tennessee", "Nashville", "36.02049", "-77.982782"},
                                {"Connecticut", "New Haven", "41.308274", "-72.927884"},
                                {"Louisiana", "New Orleans", "29.951066", "-90.071532"},
                                {"New York", "New York City", "40.712784", "-74.005941"},
                                {"Delaware", "Newark", "39.683723", "-75.749657"},
                                {"New Jersey", "Newark", "40.735657", "-74.172367"},
                                {"Virginia", "Norfolk", "42.117511", "-71.331793"},
                                {"Oklahoma", "Norman", "35.162723", "-79.73794"},
                                {"South Carolina", "North Charleston", "32.853019", "-79.991295"},
                                {"Nevada", "North Las Vegas", "36.225271", "-115.15431"},
                                {"Oklahoma", "Oklahoma City", "35.491608", "-97.562817"},
                                {"Kansas", "Olathe", "38.899901", "-94.831991"},
                                {"Nebraska", "Omaha", "32.07663", "-84.849724"},
                                {"Utah", "Orem", "40.311353", "-111.72496"},
                                {"Florida", "Orlando", "38.945847", "-80.51449"},
                                {"Kansas", "Overland Park", "38.982228", "-94.670792"},
                                {"Kentucky", "Owensboro", "37.751818", "-87.257303"},
                                {"West Virginia", "Parkersburg", "39.286315", "-81.55477"},
                                {"New Jersey", "Paterson", "40.915045", "-74.174488"},
                                {"Rhode Island", "Pawtucket", "41.875149", "-71.392732"},
                                {"Pennsylvania", "Philadelphia", "44.116323", "-75.719346"},
                                {"Arizona", "Phoenix", "43.33021", "-76.260803"},
                                {"Pennsylvania", "Pittsburgh", "40.434436", "-80.024817"},
                                {"Idaho", "Pocatello", "42.770177", "-112.259807"},
                                {"Maine", "Portland", "43.661471", "-70.255326"},
                                {"Oregon", "Portland", "45.523062", "-122.676482"},
                                {"Rhode Island", "Providence", "41.82275", "-71.414451"},
                                {"Utah", "Provo", "37.229645", "-86.804803"},
                                {"Wisconsin", "Racine", "38.140996", "-81.658829"},
                                {"North Carolina", "Raleigh", "37.758469", "-81.167549"},
                                {"South Dakota", "Rapid City", "44.80389", "-85.250629"},
                                {"Pennsylvania", "Reading", "42.537065", "-71.107172"},
                                {"Nevada", "Reno", "41.411018", "-79.749341"},
                                {"Virginia", "Richmond", "42.233105", "-73.238358"},
                                {"New Mexico", "Rio Rancho", "35.282859", "-106.712495"},
                                {"Minnesota", "Rochester", "41.751812", "-70.846041"},
                                {"New Hampshire", "Rochester", "43.304526", "-70.975619"},
                                {"New York", "Rochester", "43.16103", "-77.610922"},
                                {"South Carolina", "Rock Hill", "41.619506", "-74.575902"},
                                {"Wyoming", "Rock Springs", "43.459884", "-89.931931"},
                                {"Illinois", "Rockford", "32.897085", "-86.156068"},
                                {"New Mexico", "Roswell", "34.055198", "-84.370475"},
                                {"Vermont", "Rutland", "42.383516", "-71.95463"},
                                {"Missouri", "Saint Louis", "43.324772", "-84.603002"},
                                {"Minnesota", "Saint Paul", "36.936767", "-82.364288"},
                                {"Oregon", "Salem", "42.512946", "-70.904237"},
                                {"Utah", "Salt Lake City", "40.756095", "-111.900719"},
                                {"Texas", "San Antonio", "18.476196", "-67.139597"},
                                {"California", "San Diego", "27.660127", "-98.517875"},
                                {"California", "San Francisco", "37.784827", "-122.727802"},
                                {"California", "San Jose", "40.301103", "-89.687211"},
                                {"New Mexico", "Santa Fe", "35.772652", "-87.146428"},
                                {"Georgia", "Savannah", "43.099386", "-76.762705"},
                                {"Washington", "Seattle", "47.432251", "-121.803388"},
                                {"Louisiana", "Shreveport", "32.525152", "-93.750179"},
                                {"Maryland", "Silver Spring", "40.064746", "-76.434285"},
                                {"Iowa", "Sioux City", "42.494745", "-96.399356"},
                                {"South Dakota", "Sioux Falls", "43.546358", "-96.69063"},
                                {"Alaska", "Sitka", "37.920063", "-82.832532"},
                                {"Delaware", "Smyrna", "42.672573", "-75.593362"},
                                {"Indiana", "South Bend", "41.673383", "-86.251654"},
                                {"Vermont", "South Burlington", "44.448119", "-73.098238"},
                                {"Maine", "South Portland", "43.641472", "-70.240881"},
                                {"Mississippi", "Southaven", "34.956633", "-89.99624"},
                                {"Nevada", "Sparks", "31.212878", "-83.412143"},
                                {"Washington", "Spokane", "36.854253", "-93.292806"},
                                {"Arkansas", "Springdale", "40.434436", "-80.024817"},
                                {"Massachusetts", "Springfield", "42.170731", "-72.604842"},
                                {"Missouri", "Springfield", "37.208957", "-93.292299"},
                                {"Florida", "St. Petersburg", "27.751828", "-82.626734"},
                                {"Connecticut", "Stamford", "42.782616", "-73.067501"},
                                {"Michigan", "Sterling Heights", "42.564395", "-83.068475"},
                                {"New York", "Syracuse", "43.02143", "-76.197701"},
                                {"Washington", "Tacoma", "47.253671", "-122.444335"},
                                {"Florida", "Tampa", "27.996097", "-82.582035"},
                                {"Ohio", "Toledo", "41.720684", "-83.569359"},
                                {"Kansas", "Topeka", "41.578904", "-85.540055"},
                                {"Arizona", "Tucson", "32.217975", "-110.970869"},
                                {"Oklahoma", "Tulsa", "36.039147", "-95.868667"},
                                {"Alabama", "Tuscaloosa", "33.241899", "-87.597599"},
                                {"Washington", "Vancouver", "45.74327", "-122.713366"},
                                {"Virginia", "Virginia Beach", "36.844004", "-76.12036"},
                                {"Maryland", "Waldorf", "38.603783", "-76.867928"},
                                {"Michigan", "Warren", "42.204027", "-72.199439"},
                                {"Rhode Island", "Warwick", "42.667059", "-72.339655"},
                                {"Connecticut", "Waterbury", "44.259518", "-72.585018"},
                                {"Iowa", "Waterloo", "42.892556", "-76.882263"},
                                {"South Dakota", "Watertown", "42.446396", "-71.459405"},
                                {"North Dakota", "West Fargo", "46.890692", "-96.925828"},
                                {"Utah", "West Jordan", "40.606125", "-111.978898"},
                                {"Utah", "West Valley City", "40.691613", "-112.00105"},
                                {"West Virginia", "Wheeling", "40.102703", "-80.647599"},
                                {"Kansas", "Wichita", "37.651974", "-97.258997"},
                                {"Delaware", "Wilmington", "42.561782", "-71.173888"},
                                {"North Carolina", "Winston-Salem", "36.09986", "-80.244216"},
                                {"Massachusetts", "Worcester", "42.265275", "-71.879415"},
                                {"New York", "Yonkers", "40.946107", "-73.866926"}};


        try {

            con.setAutoCommit(false);
            Statement stmt = null;
            stmt = con.createStatement();

            for(int i=0; i<citylatlon.length; i++){
                String state = citylatlon[i][0];
                String city = citylatlon[i][1];
                String lat = citylatlon[i][2];
                String lon = citylatlon[i][3];
                stmt = con.createStatement();
                String sql = "SELECT * FROM cities WHERE state='"+state+"' AND cityname='"+city+"';";
                ResultSet rs = stmt.executeQuery(sql);

                if(rs.next()){
                    stmt = con.createStatement();
                    stmt.executeUpdate("UPDATE cities SET latitude="+lat+", longitude="+lon+" WHERE id="+rs.getString("id")+";");
                }

            }

            stmt.close();
            con.commit();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    private static void addStateCodes(Connection con){

        String[][] stateCodes = {{"Alabama ","AL"},
                {"Alaska","AK"},
                {"Arizona","AZ"},
                {"Arkansas","AR"},
                {"California","CA"},
                {"Colorado","CO"},
                {"Connecticut","CT"},
                {"Delaware","DE"},
                {"Florida","FL"},
                {"Georgia","GA"},
                {"Hawaii","HI"},
                {"Idaho","ID"},
                {"Illinois","IL"},
                {"Indiana","IND"},
                {"Iowa","IA"},
                {"Kansas","KS"},
                {"Kentucky","KY"},
                {"Louisiana","LA"},
                {"Maine","ME"},
                {"Maryland","MD"},
                {"Massachusetts","MA"},
                {"Michigan","MI"},
                {"Minnesota","MN"},
                {"Mississippi","MS"},
                {"Missouri","MO"},
                {"Montana","MT"},
                {"Nebraska","NE"},
                {"Nevada","NV"},
                {"New Hampshire","NH"},
                {"New Jersey","NJ"},
                {"New Mexico","NM"},
                {"New York","NY"},
                {"North Carolina","NC"},
                {"North Dakota","ND"},
                {"Ohio","OH"},
                {"Oklahoma","OK"},
                {"Oregon","ORE"},
                {"Pennsylvania","PA"},
                {"Rhode Island","RI"},
                {"South Carolina","SC"},
                {"South Dakota","SD"},
                {"Tennessee","TN"},
                {"Texas","TX"},
                {"Utah","UT"},
                {"Vermont","VT"},
                {"Virginia","VA"},
                {"Washington","WA"},
                {"West Virginia","WV"},
                {"Wisconsin","WI"},
                {"Wyoming","WY"}};
        try {

            con.setAutoCommit(false);
            Statement stmt = null;
            stmt = con.createStatement();

            for(int i=0; i<stateCodes.length; i++){
                String sql = "UPDATE cities SET STATE_CODE ='"+stateCodes[i][1]+"' WHERE STATE ='"+stateCodes[i][0]+"';";
                stmt.executeUpdate(sql);
            }

            stmt.close();
            con.commit();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("UPDATED STATE CODES successfully");


    }

    private static String cityList(boolean activeList){
        Statement stmt = null;
        String cityCode = "";
        String sql = "SELECT * FROM cities ";
        if(activeList){
            sql += " WHERE active=true ";
        }
        sql += "ORDER BY state, cityname;";
        try{
             Connection con = AccountFunctions.OpenDatabase();

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String currentState = rs.getString("state");
                String currentCity = rs.getString("cityName");
                String classState = currentState.replace(" ","-").toLowerCase();

                cityCode += "<option class=\""+classState+" adminAllCities\" value=\"" + currentCity + "\">";
                cityCode += currentCity;
                cityCode += "</option>\n";

            }

            con.close();

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        return cityCode;
    }

    private static String stateList(String call, boolean activeList){
        Statement stmt = null;
        String stateCode = "";
        String sql = "SELECT DISTINCT state FROM cities ";
        if(activeList){
            sql += " WHERE active=true ";
        }
        sql += " ORDER BY state;";

        try{

            Connection con = AccountFunctions.OpenDatabase();
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String currentState = rs.getString("state");
                String classState = currentState.replace(" ","-").toLowerCase();

                stateCode += "<option value=\""+currentState+"\" onclick=\""+call+"(&quot;" + classState + "&quot;)\">";
                stateCode += currentState;
                stateCode += "</option>\n";
            }

            con.close();

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        return stateCode;
    }

    public static String[] getLatAndLong(Connection con, String state, String city){
        String[] latandlong = new String[2];
        Statement stmt = null;

        try{
            con.setAutoCommit(false);

            String sql = "SELECT * FROM cities WHERE state='"+state+"' AND cityname='"+city+"';";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()){
                latandlong[0] = rs.getString("latitude");
                latandlong[1] = rs.getString("longitude");
            }
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        return latandlong;
    }

}
