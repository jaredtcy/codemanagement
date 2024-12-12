import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"}) // Servlet mapping for login
public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response); // Forward to login.jsp
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Valid credentials for demonstration purposes
        String validUsername = "admin";
        String validPassword = "password123";

        if (username.equals(validUsername) && password.equals(validPassword)) {
            HttpSession session = request.getSession(); // Create session
            session.setAttribute("user", username); // Store user information in session
            response.sendRedirect("inventory"); // Redirect to inventory page
        } else {
            request.setAttribute("error", "Invalid credentials. Please try again."); // Set error message
            request.getRequestDispatcher("/login.jsp").forward(request, response); // Forward back to login.jsp
        }
    }
}
