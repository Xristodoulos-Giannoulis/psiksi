import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static JSONArray productsArray = new JSONArray();

    public static void main(String[] args) {
        // Create and show the main window
        JFrame frame = new JFrame("Product Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        // Create text fields for user input
        JTextField imageField = new JTextField();
        JTextField altField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField buttonField = new JTextField();

        Font labelFont = new Font("Arial", Font.BOLD, 28); // Define a common font for all labels

        JLabel imageLabel = new JLabel("    Image:");
        imageLabel.setFont(labelFont);
        panel.add(imageLabel);
        panel.add(imageField);

        JLabel altLabel = new JLabel("    Alt:");
        altLabel.setFont(labelFont);
        panel.add(altLabel);
        panel.add(altField);

        JLabel nameLabel = new JLabel("    Name:");
        nameLabel.setFont(labelFont);
        panel.add(nameLabel);
        panel.add(nameField);

        JLabel priceLabel = new JLabel("    Price:");
        priceLabel.setFont(labelFont);
        panel.add(priceLabel);
        panel.add(priceField);

        JLabel buttonLabel = new JLabel("    Button Text:");
        buttonLabel.setFont(labelFont);
        panel.add(buttonLabel);
        panel.add(buttonField);

        // Create an "Add Product" button
        JButton addButton = new JButton("Add Product");
        panel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Load existing JSON data
                    loadFromFile(); // Ensure existing products are loaded into productsArray

                    // Get data from input fields
                    String image = imageField.getText();
                    String alt = altField.getText();
                    String name = nameField.getText();
                    String price = priceField.getText();
                    String buttonText = buttonField.getText();

                    // Create a new JSON object for the product
                    JSONObject productJSON = new JSONObject();
                    productJSON.put("image", image);
                    productJSON.put("alt", alt);
                    productJSON.put("name", name);
                    productJSON.put("price", price);
                    productJSON.put("buttonText", buttonText);

                    // Add the new product to the JSON array
                    productsArray.put(productJSON);

                    // Clear the fields
                    imageField.setText("");
                    altField.setText("");
                    nameField.setText("");
                    priceField.setText("");
                    buttonField.setText("");

                    // Save the updated JSON array to the file
                    saveToFile();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        // Create a "Check Products" button
        JButton checkButton = new JButton("Check Products");
        panel.add(checkButton);
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Read and display product names and prices
                StringBuilder productList = new StringBuilder();
                try (FileReader fileReader = new FileReader("../products2.json")) {
                    int data;
                    StringBuilder jsonContent = new StringBuilder();
                    while ((data = fileReader.read()) != -1) {
                        jsonContent.append((char) data);
                    }
                    JSONArray savedProducts = new JSONArray(jsonContent.toString());

                    for (int i = 0; i < savedProducts.length(); i++) {
                        JSONObject product = savedProducts.getJSONObject(i);
                        productList.append("Name: ").append(product.getString("name"))
                                .append(", Price: ").append(product.getString("price")).append("\n");
                    }
                } catch (IOException ex) {
                    productList.append("No products found or unable to read the file.");
                }

                // Show the products in a pop-up dialog
                JOptionPane.showMessageDialog(frame, productList.toString(), "Products", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.setVisible(true);
    }
    private static void loadFromFile() {
        try {
            String jsonPath = "../products2.json"; // Update with your file path
            File file = new File(jsonPath);
            if (file.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(jsonPath)));
                productsArray = new JSONArray(content); // Load existing products
            } else {
                productsArray = new JSONArray(); // Initialize empty if file doesn't exist
            }
        } catch (IOException e) {
            e.printStackTrace();
            productsArray = new JSONArray(); // Fallback to an empty array on error
        }
    }

    // Save the product list to a JSON file
    private static void saveToFile() {
        try (FileWriter file = new FileWriter("../products2.json")) {
            file.write(productsArray.toString(4)); // Indentation level = 4
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
