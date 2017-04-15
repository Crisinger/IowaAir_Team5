package AirFunctions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.http.HttpSession;


/**
 * Created by ReedS on 3/25/2017.
 */
@WebServlet("/AirFunctions.CreateAccount")
public class CreateAccount extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    // Takes input from html and checks if user gave valid password and email
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");
        String confirmUserPassword = request.getParameter("confirmUserPassword");

        // Regular expression to check password matches criteria
        // ^                    start of string
        // (?=.*[0-9]           contains at least 1 number
        // (?=.*[a-z])          contains at least 1 lowercase
        // (?=.*[A-Z])          contains at least 1 uppercase
        // (?=\S+$)             does not contain space or tab
        // .{8,}                at least 8 characters long
        // $                    end of string

        // If the user's password does not match the given criteria reloads page to try again. Need to fix email.
        if (!userPassword.matches("\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])\\S{8,16}\\z") || !userEmail.contains("@") || !userPassword.equals(confirmUserPassword)) {
            response.sendRedirect("createAccount.jsp");
        } else {
            Connection con = AccountFunctions.OpenDatabase(); // open the database
            AccountFunctions.addAccount(con,userEmail,userPassword,"CUSTOMER"); // add customer to database
            AccountFunctions.closeConnection(con); // close connection
            HttpSession mySession = request.getSession();
            mySession.setAttribute("userEmail",userEmail);

            response.sendRedirect("index.jsp");
        }

    }
}
