// Imported packages
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Servlet class Login
@WebServlet("/Login")
public class Login extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");

        // I get an error locating com.mysql.jdc.Driver

        // Checks if account is in system then redirects if it is a valid account
        if(AccountFunctions.checkLogin(AccountFunctions.OpenDatabase(),userEmail,userPassword) == true){
            response.sendRedirect("homeloggedin.jsp");
        }
        // Redirects back to login screen if invalid account inputs
        else response.sendRedirect("login.jsp");


        /* Old example code
        if(userEmail.equals("test@gmail.com")&& userPassword.equals("test")){
            response.sendRedirect("homeloggedin.jsp");
        }*/
    }
}
