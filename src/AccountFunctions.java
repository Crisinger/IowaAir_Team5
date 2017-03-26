/**
 * Created by crisi_000 on 3/21/2017.
 */

import java.sql.*;
import com.mysql.jdbc.Driver;
public class AccountFunctions
{

    public static void main( String[]  args){
        Connection connection = null;
        try {
            boolean working = false;
            connection = OpenDatabase();
            //SqlUpdates(connection);
            //AddCustomer(connection, 2,"test@gmail.com","test");
            working = checkLogin(connection,"test@gmail.com","test");
            System.out.println(working);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public AccountFunctions(){}


    public static Connection OpenDatabase()
    {

        Connection c = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/accounts?autoReconnect=true&useSSL=false","root","charlie1");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }

    //probably wont be needed again
    public static void SqlUpdates(Connection con)
    {
        Connection c = con;
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            /*String sql = "CREATE TABLE ACCOUNTS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " EMAIL           TEXT    NOT NULL, " +
                    " PASSWORD        TEXT     NOT NULL)";*/
            String sql = "ALTER TABLE ACCOUNTS MODIFY COLUMN ID INT NOT NULL AUTO_INCREMENT ";
            stmt.executeUpdate(sql);
            System.out.println("ID MODIFIED");
            //sql = "ALTER TABLE ACCOUNTS MODIFY COLUMN EMAIL TEXT NOT NULL UNIQUE";
           // stmt.executeUpdate(sql);

            stmt.close();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }


    public static void AddCustomer(Connection con, String email, String password)
    {
        Connection c = con;
        Statement stmt = null;
        try {

            c.setAutoCommit(false);


            stmt = c.createStatement();
            String sql = "INSERT INTO ACCOUNTS (EMAIL,PASSWORD,ROLE) " +
                    "VALUES ( '" + email + "' , '" + password + "' , 'CUSTOMER');";
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

    public static boolean checkLogin(Connection con, String user, String pass)
    {
        Connection c = con;
        Statement stmt = null;
        boolean found = false;
        try {

            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM ACCOUNTS;" );
            while ( rs.next() && !found ) {
                int id = rs.getInt("id");
                String  username = rs.getString("email");
                String  password = rs.getString("password");
                if ((username.equals(user))&&(pass.equals(password))){
                    found = true;
                }
            }
            rs.close();
            stmt.close();
            //c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return found;
    }


    public static void closeConnection(Connection c){
        try {
            c.close();
            System.out.println("Database connection closed");
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void addManager(Connection c, String email, String password){
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO ACCOUNTS (ID,EMAIL,PASSWORD,ROLE) " +
                    "VALUES ( '" + email + "' , '" + password + "' , 'MANAGER' );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static Character checkRole(Connection c, String email){
        Statement stmt = null;
        String role = null;
        Character result = null;
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ROLE FROM ACCOUNTS WHERE EMAIL = " + email + ");");
            role = rs.getString("role");
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        if (role.equals("CUSTOMER")){
            result = 'C';
        }
        else if (role.equals("MANAGER")){
            result = 'M';
        }
        else if (role.equals("ADMIN")){
            result = 'A';
        }
        return result;
    }
}