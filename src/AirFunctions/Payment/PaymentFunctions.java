package AirFunctions.Payment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by crisi_000 on 4/9/2017.
 */
public class PaymentFunctions {

    public static void addPayment(Connection con, String name, int cardNumber, Date expDate, int securityCode, String billingAddress,
                                  String city, String state, String country, int zipcode, int phoneNumber) {
        Connection c = con;
        Statement stmt = null;
        try {

            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "INSERT INTO PAYMENTINFO (cardName,cardnumber,expdate,securitycode,address,state,country,zipcode,phoneNumber) " +
                    "VALUES ( '" + name + "' , '" + cardNumber + "' , '"+ expDate + "' , '"+ securityCode + "' , '"+ billingAddress +
                    "' , '"+ city + "' , '"+ state + "' , '"+ country + "' , '"+ zipcode + "' , '"+ phoneNumber +"');";
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
}
