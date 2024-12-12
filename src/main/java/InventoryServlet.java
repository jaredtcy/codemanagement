import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Servlet implementation for inventory management
@WebServlet("/inventory") // URL mapping for the servlet
public class InventoryServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/inventory"; // Database URL
    private static final String DB_USER = "root"; // Database username
    private static final String DB_PASSWORD = "password"; // Database password

    private static final String SELECT_ALL_ITEMS = "SELECT * FROM inventory"; // SQL query to select all items
    private static final String INSERT_ITEM = "INSERT INTO inventory (brand, size, quantity) VALUES (?, ?, ?)"; // SQL for inserting an item
    private static final String DELETE_ITEM = "DELETE FROM inventory WHERE brand = ?"; // SQL for deleting an item
    private static final String UPDATE_ITEM_QUANTITY = "UPDATE inventory SET quantity = ? WHERE brand = ?"; // SQL for updating item quantity

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver
        } catch (ClassNotFoundException e) {
            throw new ServletException("MySQL JDBC Driver not found.", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check for existing session and user attribute
        HttpSession session = request.getSession(false); // Do not create a new session
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login"); // Redirect to login if session is invalid
            return;
        }

        List<InventoryItem> items = listInventoryItems(); // Fetch inventory items
        request.setAttribute("items", items); // Set the items as request attribute
        request.getRequestDispatcher("/inventory.jsp").forward(request, response); // Forward to inventory.jsp
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action"); // Determine the action to perform
        System.out.println("Action received: " + action); // Debugging output

        if ("add".equals(action)) {
            addInventoryItem(request); // Call method to add item
        } else if ("delete".equals(action)) {
            deleteInventoryItem(request); // Call method to delete item
        } else if ("increase".equals(action)) {
            adjustQuantity(request, 1); // Increase quantity by 1
        } else if ("decrease".equals(action)) {
            adjustQuantity(request, -1); // Decrease quantity by 1
        }

        // After processing, redirect back to the inventory page
        response.sendRedirect("inventory");
    }

    private List<InventoryItem> listInventoryItems() {
        List<InventoryItem> items = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ITEMS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String brand = resultSet.getString("brand");
                int size = resultSet.getInt("size");
                int quantity = resultSet.getInt("quantity");
                items.add(new InventoryItem(brand, size, quantity)); // Add items to the list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print any SQL exceptions
        }
        return items;
    }

    private void addInventoryItem(HttpServletRequest request) {
        String brand = request.getParameter("brand");
        String sizeStr = request.getParameter("size");
        String quantityStr = request.getParameter("quantity");

        // Debugging output
        System.out.println("Adding item: brand=" + brand + ", size=" + sizeStr + ", quantity=" + quantityStr);

        // Input validation
        if (brand == null || brand.isEmpty() || sizeStr == null || quantityStr == null) {
            System.out.println("Invalid input: Brand or size or quantity is null or empty.");
            return; // Exit early if inputs are invalid
        }

        try {
            int size = Integer.parseInt(sizeStr);
            int quantity = Integer.parseInt(quantityStr);

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement statement = connection.prepareStatement(INSERT_ITEM)) {

                statement.setString(1, brand);
                statement.setInt(2, size);
                statement.setInt(3, quantity);
                statement.executeUpdate(); // Execute the insert statement
                System.out.println("Item added successfully."); // Log successful addition
            } catch (SQLException e) {
                System.out.println("SQL Error: " + e.getMessage()); // More specific error logging
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for size or quantity: " + e.getMessage());
        }
    }

    private void deleteInventoryItem(HttpServletRequest request) {
        String brand = request.getParameter("brand");
        System.out.println("Deleting item: brand=" + brand); // Debugging output

        if (brand == null || brand.isEmpty()) {
            System.out.println("Invalid input: Brand is null or empty.");
            return; // Exit early if input is invalid
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(DELETE_ITEM)) {

            statement.setString(1, brand);
            statement.executeUpdate(); // Execute the delete statement
            System.out.println("Item deleted successfully."); // Log successful deletion
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage()); // More specific error logging
            e.printStackTrace();
        }
    }

    private void adjustQuantity(HttpServletRequest request, int adjustment) {
        String brand = request.getParameter("brand"); // Get the brand
        String quantityStr = request.getParameter("quantity"); // Get the current quantity

        if (quantityStr == null || brand == null) {
            System.out.println("Missing quantity or brand.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr); // Convert quantity to integer
            int newQuantity = quantity + adjustment; // Adjust quantity

            if (newQuantity < 0) {
                newQuantity = 0; // Prevent negative quantities
            }

            System.out.println("Updated quantity for " + brand + ": " + newQuantity);

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement statement = connection.prepareStatement(UPDATE_ITEM_QUANTITY)) {

                statement.setInt(1, newQuantity); // Set the new quantity
                statement.setString(2, brand);    // Set the brand
                statement.executeUpdate(); // Execute the update statement
                System.out.println("Item quantity updated successfully."); // Log successful update
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity format: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
