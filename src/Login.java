// Imported packages
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Driver;

// Servlet class Login
@WebServlet("/Login")
public class Login extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");

        // I get an error locating com.mysql.jdc.Driver
        //AccountFunctions AF = new AccountFunctions();

        Connection c = AccountFunctions.OpenDatabase();
        // Checks if account is in system then redirects if it is a valid account
        if(AccountFunctions.checkLogin(c,userEmail,userPassword)){

            HttpSession mySession = request.getSession();
            mySession.setAttribute("userEmail",userEmail);

            // Checks the role of the user
            switch(AccountFunctions.checkRole(c, userEmail)){
                // Checks if role is admin
                case 'A':
                case 'a':
                    AccountFunctions.closeConnection(c);
                    response.sendRedirect("admin.jsp");
                    break;
                // Checks if role is manager
                case 'M':
                case 'm':
                    AccountFunctions.closeConnection(c);
                    response.sendRedirect("manager.jsp");
                    break;
                // Customers
                default:
                    AccountFunctions.closeConnection(c);
                    response.sendRedirect("index.jsp");
            }

        }
        // Redirects back to login screen if invalid account inputs
        else {
            AccountFunctions.closeConnection(c);
            response.sendRedirect("login.jsp");
        }
    }
}

