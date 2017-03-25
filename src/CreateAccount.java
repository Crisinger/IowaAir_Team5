import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ReedS on 3/25/2017.
 */
@WebServlet(name = "CreateAccount")
public class CreateAccount extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    // Takes input from html and checks if user gave valid password and email
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");


        if (userEmail.contains("@") && userEmail.length() > 12 && userPassword.length() < 10 && userPassword.length() > 8) {
            response.sendRedirect("homeloggedin.jsp");
        }
        // Redirects back to login screen if invalid account inputs
        else response.sendRedirect("create_account.jsp");
    }
}
