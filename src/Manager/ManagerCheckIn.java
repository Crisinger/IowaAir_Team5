package Manager;

import General.AccountFunctions;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by Charlie on 4/25/2017.
 */
public class ManagerCheckIn {

    public static void managerCheckIn(Connection con, int referenceID){
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("UPDATE BOOKEDFLIGHTS SET checkedIn = 1 WHERE ID = " + referenceID +";");
            stmt.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void managerCancelFlight(Connection con, int referenceID){
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM BOOKEDFLIGHTS WHERE ID = " + referenceID +";");
            stmt.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

   public static void addBag(Connection con, int referenceID){
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("UPDATE BOOKEDFLIGHTS SET bags = bags + 1 WHERE ID = " + referenceID +";");
            stmt.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }




}
