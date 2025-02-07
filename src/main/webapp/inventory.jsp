<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Inventory Management</title>
    <style>
        /* Styling for Contact Details Footer - For test*/
        footer {
            text-align: center;
            background-color: #f8f9fa;
            padding: 20px;
            margin-top: 30px;
            font-size: 16px;
        }

        footer p {
            margin: 5px 0;
        }

        /* Styling for Chatbot */
        #chatbot {
            display: none; /* Initially hidden */
            position: fixed;
            bottom: 20px;
            right: 20px;
            width: 350px;
            height: 400px;
            background-color: #fff;
            border: 1px solid #ccc;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            border-radius: 8px;
        }

        #chatbotHeader {
            background-color: #007BFF;
            color: white;
            padding: 10px;
            border-top-left-radius: 8px;
            border-top-right-radius: 8px;
        }

        #chatbotMessages {
            height: 250px;
            overflow-y: auto;
            padding: 10px;
            border-bottom: 1px solid #ccc;
            background-color: #f9f9f9;
        }

        #chatbotInput {
            padding: 10px;
            width: calc(100% - 50px);
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        #sendButton {
            padding: 10px;
            border: none;
            background-color: #007BFF;
            color: white;
            cursor: pointer;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <h1>Inventory Management</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Brand</th>
                <th>Size (ml)</th>
                <th>Quantity</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${items}"> <!-- Use JSTL to loop through items -->
                <tr>
                    <td>${item.brand}</td>
                    <td>${item.size}</td>
                    <td>${item.quantity}</td>
                    <td>
                        <!-- Delete Button -->
                        <form method="post" action="inventory" style="display:inline;">
                            <input type="hidden" name="action" value="delete"> <!-- Action for delete -->
                            <input type="hidden" name="brand" value="${item.brand}">
                            <button type="submit">Delete</button>
                        </form>

                        <!-- Increase Quantity Button -->
                        <form method="post" action="inventory" style="display:inline;">
                            <input type="hidden" name="action" value="increase"> <!-- Action for increase -->
                            <input type="hidden" name="brand" value="${item.brand}">
                            <input type="hidden" name="quantity" value="${item.quantity}"> <!-- Pass current quantity -->
                            <button type="submit">Increase Quantity</button> <!-- Updated button text -->
                        </form>

                        <!-- Decrease Quantity Button -->
                        <form method="post" action="inventory" style="display:inline;">
                            <input type="hidden" name="action" value="decrease"> <!-- Action for decrease -->
                            <input type="hidden" name="brand" value="${item.brand}">
                            <input type="hidden" name="quantity" value="${item.quantity}"> <!-- Pass current quantity -->
                            <button type="submit">Decrease Quantity</button> <!-- Updated button text -->
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <h2>Add New Item</h2>
    <form method="post" action="inventory">
        <input type="hidden" name="action" value="add"> <!-- Set action for add -->
        <input type="text" name="brand" placeholder="Brand" required>
        <input type="number" name="size" placeholder="Size (ml)" required>
        <input type="number" name="quantity" placeholder="Quantity" required>
        <button type="submit">Add Item</button>
    </form>

    <!-- Static Contact Details and Copyright Footer Section -->
    <footer>
        <p><strong>Email:</strong> support@companyvendor.com</p>
        <p><strong>Phone:</strong> +6512343245</p>
        <!-- Copyright Information -->
        <p>&copy; 2024 Company Vendor. All rights reserved.</p>
    </footer>

    <!-- Chatbot Section -->
    <button onclick="toggleChatbot()">Chat with Support</button>
    <div id="chatbot">
        <div id="chatbotHeader">Customer Support</div>
        <div id="chatbotMessages"></div> <!-- Chat messages container -->
        <input type="text" id="chatbotInput" placeholder="Type your message..." onkeydown="handleKeyPress(event)">
        <button id="sendButton" onclick="sendMessage()">Send</button>
    </div>

    <script>
        // Function to toggle the chatbot visibility
        function toggleChatbot() {
            const chatbot = document.getElementById('chatbot');
            chatbot.style.display = chatbot.style.display === 'none' || chatbot.style.display === '' ? 'block' : 'none';
        }

        // Send message function
        function sendMessage() {
            const input = document.getElementById('chatbotInput');
            const message = input.value.trim();  // Get the value from the input field and remove extra spaces

            if (message) {
                // Add user message to the chat window
                const userMessage = document.createElement('div');
                userMessage.textContent = 'User: ' + message;
                document.getElementById('chatbotMessages').appendChild(userMessage);

                input.value = ''; // Clear the input field

                // Simulate bot response after a short delay
                setTimeout(() => {
                    const botResponse = 'Support: Thank you for your message! How can I assist you?';
                    const botMessage = document.createElement('div');
                    botMessage.textContent = botResponse;
                    document.getElementById('chatbotMessages').appendChild(botMessage);

                    // Scroll to the latest message
                    const messagesDiv = document.getElementById('chatbotMessages');
                    messagesDiv.scrollTop = messagesDiv.scrollHeight;
                }, 1000); // Simulate a response after 1 second
            }
        }

        // Add keypress event handler for 'Enter' key
        function handleKeyPress(event) {
            if (event.key === 'Enter') {
                event.preventDefault(); // Prevent form submission
                sendMessage(); // Send the message when Enter is pressed
            }
        }
    </script>
</body>
</html>
