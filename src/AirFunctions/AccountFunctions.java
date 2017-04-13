package AirFunctions; /**
 * Created by crisi_000 on 3/21/2017.
 */

import java.sql.*;

import AirFunctions.Email.SendEmail;
import com.mysql.jdbc.Driver;
public class AccountFunctions
{

    public static void main( String[]  args){
        Connection connection = null;
        try {
            boolean working = false;
            connection = OpenDatabase();
            addAdmin(connection,"admin@gmail.com","testing123");
            //CreateAccountsTable(connection);
            //AddCustomer(connection, 2,"test@gmail.com","test");
            //working = checkLogin(connection,"test@gmail.com","test");
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
    public static void CreateAccountsTable(Connection con)
    {
        Connection c = con;
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE ACCOUNTS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " EMAIL           TEXT    NOT NULL, " +
                    " PASSWORD        TEXT     NOT NULL)";
            stmt.executeUpdate(sql);

            sql = "ALTER TABLE ACCOUNTS ADD COLUMN ROLE TEXT NOT NULL";
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

    public static void addAdmin(Connection c, String email, String password){
        Statement stmt = null;
        try {
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "INSERT INTO ACCOUNTS (EMAIL,PASSWORD,ROLE) " +
                    "VALUES ('" + email + "' , '" + password + "' , 'ADMIN' );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static boolean addAccount(Connection c, String email, String password, String role){
        Statement stmt = null;
        boolean accountAdded = false;
        try {
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM accounts WHERE email='"+email+"';");

            if(!rs.next()){
                String sql = "INSERT INTO ACCOUNTS (EMAIL,PASSWORD,ROLE) " +
                        "VALUES ('" + email + "' , '" + password + "' , '"+role+"' );";
                stmt.executeUpdate(sql);
                stmt.close();
                accountAdded = true;

                // Send email to manager accounts created
                if (role.equals("MANAGER")){
                    // SMTP server information
                    String host = "smtp.gmail.com"; // Host
                    String port = "587"; // Port
                    String adminEmail = "test.iowa.air@gmail.com"; // Email address for sender
                    String adminPass = "testtesttest123"; // Password for sender email

                    // outgoing message information
                    // Would change email here however, so we don't spam random people it will stay as my email.
                    String mailTo = "ReedStock1992@gmail.com"; // Recipient
                    String subject = "Hello Manager"; // Subject line
                    String message = "This is your Iowa Air Email:" + email + "\nThis is your Iowa Air Password" + password; // Message contents

                    // Create new SendEmail class
                    SendEmail mailer = new SendEmail();

                    // Try catch block
                    try {
                        mailer.sendEmail(host, port, adminEmail, adminPass, mailTo,
                                subject, message);
                        System.out.println("Email sent.");
                    } catch (Exception ex) {
                        System.out.println("Failed to sent email.");
                        ex.printStackTrace();
                    }
                }
                // Ends email to manager account block
            }

            c.commit();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return accountAdded;
    }

    public static void deleteAccount(Connection c, String accountID){
        Statement stmt = null;
        try {
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "DELETE FROM accounts WHERE ID = "+accountID+";";
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
        Character result = 'C';
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ROLE FROM ACCOUNTS WHERE EMAIL = '" + email + "';");
            rs.next();
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

    public static void updateAccount(Connection con, String id, String email, String password, String role){
        Statement stmt = null;
        try {
            if(addAccount(con, email, password, role)){
                deleteAccount(con, id);
            }
            con.commit();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}