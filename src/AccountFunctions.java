/**
 * Created by crisi_000 on 3/21/2017.
 */

import java.sql.*;
import com.mysql.jdbc.Driver;
public class AccountFunctions
{
    /*
    public static void main( String[]  args){
        Connection connection = null;
        try {
            boolean working = false;
            connection = OpenDatabase();
            //CreateAccountsTable(connection);
            //AddRecord(connection, 2,"test@gmail.com","test");
            working = checkLogin(connection,"test@gmail.com","test");
            System.out.println(working);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    */
    public AccountFunctions(){}


    public Connection OpenDatabase()
    {

        Connection c = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/Accounts?autoReconnect=true&useSSL=false","root","charlie1");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }


    public void CreateAccountsTable(Connection con)
    {
        Connection c = con;
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            /*String sql = "CREATE TABLE ACCOUNTS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " EMAIL           TEXT    NOT NULL, " +
                    " PASSWORD        TEXT     NOT NULL)";*/
            String sql = "ALTER TABLE ACCOUNTS ADD COLUMN ROLE TEXT NOT NULL";
            stmt.executeUpdate(sql);
            stmt.close();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }


    public void AddRecord(Connection con,int id, String email, String password)
    {
        Connection c = con;
        Statement stmt = null;
        try {

            c.setAutoCommit(false);


            stmt = c.createStatement();
            String sql = "INSERT INTO ACCOUNTS (ID,EMAIL,PASSWORD) " +
                    "VALUES ( '" + id + "' , '" + email + "' , '" + password + "' );";
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

    public  boolean checkLogin(Connection con, String user, String pass)
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
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return found;
    }



}