package AirFunctions;

import java.sql.*;

/**
 * Created by crisi_000 on 3/30/2017.
 */
public class FlightsFunctions {
    public static void main( String[]  args){
        Connection connection = null;
        try {
            boolean working = false;
            connection = AccountFunctions.OpenDatabase();
            //CreateFlightsTable(connection);
            // add flight will not work until the city references (1 and 2) are defined in the city table
            //AdminAddFlight(connection, 1,new Date(2005,04,15), new Time(5),1, new Date(04,04,2017), new Time(12), 2, 250);

            //deleteFlight(connection, 1);
            //working = checkLogin(connection,"test@gmail.com","test");
            //System.out.println(working);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }




    public static void createFlightsTable(Connection con)
    {
        Connection c = con;
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "DROP TABLE flights;";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE `accounts`.`flights` (\n" +
                    "  `Flight_ID` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `Plane_ID` INT NOT NULL,\n" +
                    "  `DEPARTURE_DATE` DATE NOT NULL,\n" +
                    "  `DEPARTURE_TIME` TIME NOT NULL,\n" +
                    "  `DEPARTURE_LOCATION` INT NOT NULL,\n" +
                    "  `ARRIVAL_DATE` DATE NOT NULL,\n" +
                    "  `ARRIVAL_TIME` TIME NOT NULL,\n" +
                    "  `ARRIVAL_LOCATION` INT NOT NULL,\n" +
                    "  `PRICE` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`Flight_ID`),\n" +
                    "  INDEX `PLANE_idx` (`Plane_ID` ASC),\n" +
                    "  INDEX `DEPARTURE_idx` (`DEPARTURE_LOCATION` ASC),\n" +
                    "  INDEX `ARRIVAL_idx` (`ARRIVAL_LOCATION` ASC),\n" +
                    "  CONSTRAINT `PLANE`\n" +
                    "    FOREIGN KEY (`Plane_ID`)\n" +
                    "    REFERENCES `accounts`.`planes` (`ID`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `DEPARTURE`\n" +
                    "    FOREIGN KEY (`DEPARTURE_LOCATION`)\n" +
                    "    REFERENCES `accounts`.`cities` (`ID`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `ARRIVAL`\n" +
                    "    FOREIGN KEY (`ARRIVAL_LOCATION`)\n" +
                    "    REFERENCES `accounts`.`cities` (`ID`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION);";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }




    public static boolean addFlight(Connection con, String planeID, String deptDate, String deptTime, String deptLocation,
                                 String arrivalDate, String arrivalTime, String arrivalLocation, String demand, String distancePrice)
    //i assume that if a plane has first class, it also has business and coach. If it has business it also has coach
    {
        boolean isFlightAdded = false;
        Statement stmt = null;
        try {

            con.setAutoCommit(false);
            stmt = con.createStatement();
            String sql = "INSERT INTO FLIGHTS (PLANE_ID,DEPARTURE_DATE,DEPARTURE_TIME,DEPARTURE_LOCATION,ARRIVAL_DATE,ARRIVAL_TIME,ARRIVAL_LOCATION,DEMAND, DISTANCE_PRICE) " +
                    "VALUES ( " + planeID + " , '" + deptDate + "' , '"+ deptTime +"' , "+ deptLocation +" , '"+ arrivalDate + "','" +
                    arrivalTime + "'," + arrivalLocation +"," + demand + ", "+ distancePrice+");";
            stmt.executeUpdate(sql);
            isFlightAdded = true;

            stmt.close();
            con.commit();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Flight created ");
        return isFlightAdded;
    }

    public static void deleteFlight(Connection con, int flightID){
        boolean found = false;
        try{
            Statement stmt = con.createStatement();
            String sql = "";
            ResultSet rs = stmt.executeQuery( "SELECT * FROM FLIGHTS WHERE Flight_ID = "+  flightID + ";");
            if(rs.next()) {
                stmt.execute("DELETE FROM FLIGHTS WHERE Flight_ID = " + flightID + ";");
            }
            rs.close();
            stmt.close();


        } catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Flight deleted");

    }

    public static String[][] getUpcomingFlights(Connection con, int userID){ //gathers upcoming flights
        boolean found = false;
        String[][] display = new String[10][7];
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM BOOKEDFLIGHTS WHERE USERID = "+  userID + "AND Completed=0;");
            int i = 0;
            while (rs.next()){
                display[i][0] = rs.getString("FlightID");
                display[i][1] = rs.getString("TotalTickets");
                display[i][2] = rs.getString("ticketsEconomy");
                display[i][3] = rs.getString("ticketsBusiness");
                display[i][4] = rs.getString("ticketsFirst");
                display[i][5] = rs.getString("priceEconomy");
                display[i][6] = rs.getString("priceBusiness");
                display[i][7] = rs.getString("priceFirst");
                i++;
            }
            rs.close();
            stmt.close();


        } catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return display;
    }

    public static String[][] getCompletedFlights(Connection con, int userID){ //gathers upcoming flights
        boolean found = false;
        String[][] display = new String[10][7];
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM BOOKEDFLIGHTS WHERE USERID = "+  userID + "AND Completed=1;");
            int i = 0;
            while (rs.next()){
                display[i][0] = rs.getString("FlightID");
                display[i][1] = rs.getString("TotalTickets");
                display[i][2] = rs.getString("ticketsEconomy");
                display[i][3] = rs.getString("ticketsBusiness");
                display[i][4] = rs.getString("ticketsFirst");
                display[i][5] = rs.getString("priceEconomy");
                display[i][6] = rs.getString("priceBusiness");
                display[i][7] = rs.getString("priceFirst");
                i++;
            }
            rs.close();
            stmt.close();


        } catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return display;
    }

    public static void setFlightCompleted(Connection con, int flightID){
        try{
            Statement stmt = con.createStatement();
            stmt.executeQuery( "UPDATE BOOKEDFLIGHTS SET completed = 1 WHERE flightID = " + flightID +";");
            stmt.close();

        } catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
