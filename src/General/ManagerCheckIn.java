package General;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Charlie on 4/25/2017.
 */
public class ManagerCheckIn {

    public static void managerCheckIn(Connection con, int referenceID){
        Statement stmt = null;
        try {
            con.setAutoCommit(false);
            stmt = con.createStatement();
            stmt.executeUpdate("UPDATE BOOKEDFLIGHTS SET checkedIn = 1 WHERE ID = " + referenceID +";");
            stmt.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
