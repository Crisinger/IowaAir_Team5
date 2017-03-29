import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.*;

        
/**
 * Created by ReedS on 3/25/2017.
 */
@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    // Takes input from html and checks if user gave valid password and email
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");

        // Johnny fix this regular expression. make it super s
        if (userEmail.contains("@") && userEmail.length() > 12 && userPassword.length() < 15 && userPassword.length() > 8) {
            AccountFunctions.AddCustomer(AccountFunctions.OpenDatabase(),userEmail,userPassword);
            response.sendRedirect("homeloggedin.jsp");
            System.out.println("Made it hereeee");
        }
        // Redirects back to login screen if invalid account inputs
        else response.sendRedirect("createAccount.jsp");
    }
}
