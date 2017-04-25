package Customer.PaymentInfo;

import General.AccountFunctions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

/**
 * Created by Charlie on 4/25/2017.
 */

@WebServlet("/Customer.PaymentInfo.PaymentServlet")
public class PaymentServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cardName = request.getParameter("cardName");
        String cardNumber = request.getParameter("cardNumber");
        int securityCode = request.getIntHeader("securityCode");
        String address = request.getParameter("address");
        String state = request.getParameter("state");
        String city = request.getParameter("city");
        int zipCode = request.getIntHeader("zipCode");
        int month = request.getIntHeader("expMonth");
        int year = request.getIntHeader("expYear");
        String phoneNumber = request.getParameter("phoneNumber");

        Date aDate = new Date(year + 1900, month, 1);
        java.sql.Date expDate = new java.sql.Date(aDate.getTime());
        Connection con = AccountFunctions.OpenDatabase();
        PaymentFunctions.addPayment(con,cardName,cardNumber,expDate,securityCode,address,city,state,zipCode,phoneNumber);
        AccountFunctions.closeConnection(con);

        response.sendRedirect("payment.jsp");
    }
}
