package Manager;

import General.AccountFunctions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by ReedS on 4/25/2017.
 */
@WebServlet(name = "CheckinServlet")
public class CheckinServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    // Get info from checkin form "manager.jsp"
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Assign parameters to values
        // String customerName = request.getParameter("firstname") + request.getParameter("lastname");
        // String customerEmail = request.getParameter("customerEmail");
        String referenceID = request.getParameter("referenceID");
        String bags = request.getParameter("bags");

        // Add reference ID check


        // Open connection to database
        Connection con = AccountFunctions.OpenDatabase();

        // Checkin
        ManagerCheckIn.managerCheckIn(con, Integer.parseInt(referenceID));

        // Add bag
        ManagerCheckIn.addBag(con, Integer.parseInt(bags));

        // Maybe add emailing
    }
}
