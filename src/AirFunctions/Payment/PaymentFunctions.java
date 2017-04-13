package AirFunctions.Payment;

import AirFunctions.AccountFunctions;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by crisi_000 on 4/9/2017.
 */
public class PaymentFunctions {


    public static void main( String[]  args){
        Connection connection = null;
        String[] result;
        try {
            boolean working = false;
            connection = AccountFunctions.OpenDatabase();
            result = displayPaymentInfo(connection, 1);
            //addPayment(connection, "John Doe", "1234-5678-9100-2457", new Date(2,2,2020),231,"555 Main St",
            //        "Iowa City", "IA","United States", 52245, "555-555-5555");
            for (int i = 0; i < 9; i++) {
                System.out.println(result[i]); //displays the first records information
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void addPayment(Connection con, String name, String cardNumber, Date expDate, int securityCode, String billingAddress,
                                  String city, String state, int zipcode, String phoneNumber) {
        Connection c = con;
        Statement stmt = null;
        try {

            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "INSERT INTO PAYMENTINFO (cardName,cardnumber,expdate,securitycode,address,state,city,zipcode,phoneNumber) " +
                    "VALUES ( '" + name + "' , '" + cardNumber + "' , '"+ expDate + "' , '"+ securityCode + "' , '"+ billingAddress +
                    "' , '"+ state + "' , '"+ city + "' , '"+ zipcode + "' , '"+ phoneNumber +"');";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Payment created successfully");
    }


    /*
        links one payment to another. both payments must be created already
     */
    public static void addSecondPayment(Connection con, int currentID, int nextID){
        Connection c = con;
        Statement stmt = null;
        try {

            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "UPDATE paymentInfo  " +
                    "SET nextPayment = '"+ nextID + "' WHERE paymentID = '" + currentID + "';";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Payment Linked Successfully");
    }


    public static int getNextPaymentID(Connection con, int paymentID){
        Statement stmt = null;
        int id = 0;
        try {
            stmt = con.createStatement();
            String sql = "SELECT * FROM paymentInfo WHERE paymentID='"+paymentID+"';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                id = rs.getInt("nextpayment");
            }

            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return id;
    }

    public static void removePayment(Connection con, int paymentID){
        Statement stmt = null;
        int id = 0;
        try {
            stmt = con.createStatement();
            String sql = "DELETE FROM paymentInfo WHERE paymentID='"+paymentID+"';";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Payment Deleted");
    }

    public static String[] displayPaymentInfo(Connection con, int paymentID){
        Statement stmt;
        String[] display = new String[9];
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PAYMENTINFO WHERE paymentID = '" + paymentID + "';");
            rs.next();
            display[0]= rs.getString("CardName");
            display[1]= rs.getString("CardNumber");
            display[2]= rs.getString("ExpDate");
            display[3]= rs.getString("SecurityCode");
            display[4]= rs.getString("Address");
            display[5]= rs.getString("State");
            display[6]= rs.getString("City");
            display[7]= rs.getString("Zipcode");
            display[8]= rs.getString("Phonenumber");
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return display;
    }
}
