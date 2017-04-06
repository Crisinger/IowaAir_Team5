package AirFunctions;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by crisi_000 on 3/30/2017.
 */
public class AirplaneFunctions {

    public static void main( String[]  args){
        Connection connection = null;
        try {
            boolean working = false;
            connection = AccountFunctions.OpenDatabase();
            CreatePlanesTable(connection);
            AddAirplane(connection, "Boeing",250,3);
            //working = checkLogin(connection,"test@gmail.com","test");
            //System.out.println(working);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }




    public static void CreatePlanesTable(Connection con)
    {
        Connection c = con;
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE PLANES " +
                    "(ID INT PRIMARY KEY     NOT NULL AUTO_INCREMENT," +
                    " PLANE_TYPE           TEXT    NOT NULL, " +
                    " CAPACITY        INT     NOT NULL," +
                    " CLASSES         INT    NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }




    public static void AddAirplane(Connection con, String planeModel, int numPassengers, int classes)
    //i assume that if a plane has first class, it also has business and coach. If it has business it also has coach
    {
        Connection c = con;
        Statement stmt = null;
        try {

            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "INSERT INTO PLANES (PLANE_TYPE,CAPACITY,CLASSES) " +
                    "VALUES ( '" + planeModel + "' , '" + numPassengers + "' , '"+ classes +"');";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }
}
