package Manager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        String customerName = request.getParameter("firstname") + request.getParameter("lastname");
        String customerEmail = request.getParameter("customerEmail");
        String flightID = request.getParameter("referenceID");
        String baggage = request.getParameter("baggage");

        // Add necessary function calls here
    }
}
