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
            AddFlight(connection, 1,new Date(04,04,2017), new Time(8),"Chicago",
                    new Date(04,04,2017), new Time(12), "Atlanta");

            deleteFlight(connection, 1);
            //working = checkLogin(connection,"test@gmail.com","test");
            //System.out.println(working);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }




    public static void CreateFlightsTable(Connection con)
    {
        Connection c = con;
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE `accounts`.`flights` (\n" +
                    "  `Flight_ID` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `Plane_ID` INT NOT NULL,\n" +
                    "  `DEPARTURE_DATE` DATE NOT NULL,\n" +
                    "  `DEPARTURE_TIME` TIME NOT NULL,\n" +
                    "  `DEPARTURE_LOCATION` TEXT NOT NULL,\n" +
                    "  `ARRIVAL_DATE` DATE NOT NULL,\n" +
                    "  `ARRIVAL_TIME` TIME NOT NULL,\n" +
                    "  `ARRIVAL_LOCATION` TEXT NOT NULL,\n" +
                    "  PRIMARY KEY (`Flight_ID`),\n" +
                    "  INDEX `PLANE_idx` (`Plane_ID` ASC),\n" +
                    "  CONSTRAINT `PLANE`\n" +
                    "    FOREIGN KEY (`Plane_ID`)\n" +
                    "    REFERENCES `accounts`.`planes` (`ID`)\n" +
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




    public static void AddFlight(Connection con, int planeID, Date deptDate, Time deptTime, String deptLocation,
                                 Date arrivalDate, Time arrivalTime, String arrivalLocation)
    //i assume that if a plane has first class, it also has business and coach. If it has business it also has coach
    {
        Connection c = con;
        Statement stmt = null;
        try {

            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "INSERT INTO FLIGHTS (PLANE_ID,DEPARTURE_DATE,DEPARTURE_TIME,DEPARTURE_LOCATION,ARRIVAL_DATE,ARRIVAL_TIME,ARRIVAL_LOCATION) " +
                    "VALUES ( '" + planeID + "' , '" + deptDate + "' , '"+ deptTime +"' , '"+ deptLocation +"' , '"+ arrivalDate + "','" +
                    arrivalTime + "','" + arrivalLocation +"');";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Flight created ");
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


}
