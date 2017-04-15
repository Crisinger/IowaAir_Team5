package AirFunctions;// Imported packages
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Servlet class AirFunctions.Login
@WebServlet("/AirFunctions.Login")
public class Login extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");

        // I get an error locating com.mysql.jdc.Driver
        //AirFunctions.AccountFunctions AF = new AirFunctions.AccountFunctions();

        System.out.println(userEmail);
        System.out.println(userPassword);

        Connection c = AccountFunctions.OpenDatabase();
        // Checks if account is in system then redirects if it is a valid account
        if(AccountFunctions.checkLogin(c,userEmail,userPassword)){

            System.out.println("i am currently right here!");
            HttpSession mySession = request.getSession();
            mySession.setAttribute("userEmail",userEmail);

            // Checks the role of the user
            switch(AccountFunctions.checkRole(c, userEmail)){
                // Checks if role is admin
                case 'A':
                    AccountFunctions.closeConnection(c);
                    System.out.println("HERE AT ADMIN");
                    mySession.setAttribute("role","ADMIN");
                    response.sendRedirect("admin.jsp");

                    break;
                // Checks if role is manager
                case 'M':
                    AccountFunctions.closeConnection(c);
                    System.out.println("HERE AT MANAGER");

                    mySession.setAttribute("role","MANAGER");
                    response.sendRedirect("manager.jsp");
                    break;
                // Customers
                default:
                    AccountFunctions.closeConnection(c);
                    System.out.println("HERE AT CUSTOMER");

                    mySession.setAttribute("role","CUSTOMER");
                    response.sendRedirect("index.jsp");
            }

        }
        // Redirects back to login screen if invalid account inputs
        else {
            AccountFunctions.closeConnection(c);
            System.out.println("HERE AT ELSE");
            response.sendRedirect("login.jsp");
        }
    }
}

