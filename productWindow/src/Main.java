import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        // Make fullscreen (maximize); remove undecorated if you want window controls
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        // Outer panel with padding
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBackground(new Color(34, 34, 34));
        outerPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 300)); // padding

        // Inner panel with the form (use GridLayout as before)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 20, 20)); // larger gaps for fullscreen
        panel.setBackground(new Color(34, 34, 34));
        outerPanel.add(panel, BorderLayout.CENTER);

        frame.add(outerPanel);
        Font labelFont = new Font("Arial", Font.BOLD, 20);
        // Create text fields for user input
        JLabel imageLabel = new JLabel("    Image:");
        styleLabel(imageLabel, labelFont);
        panel.add(imageLabel);

// Wrapper panel for text field + select button
        JTextField imageField = new JTextField();
        imageField.setFont(new Font("Arial", Font.PLAIN, 24));

        JPanel imagePanel = new JPanel(new BorderLayout(10, 0)); // 10px gap
        imagePanel.setBackground(new Color(34, 34, 34)); // match main panel

        JButton selectFileButton = new JButton("Select Image");
        selectFileButton.setFont(new Font("Arial", Font.PLAIN, 18));
        selectFileButton.setPreferredSize(new Dimension(180, 40)); // fixed button size

        imagePanel.add(imageField, BorderLayout.CENTER);
        imagePanel.add(selectFileButton, BorderLayout.EAST);

        panel.add(imagePanel);

// Action listener for file selection
        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            // Start in the parent directory (one level up)
            File parentDir = new File(".").getAbsoluteFile().getParentFile();
            fileChooser.setCurrentDirectory(parentDir);

            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    // Get relative path from project root
                    String relativePath = Paths.get(".").toAbsolutePath().normalize()
                            .relativize(selectedFile.toPath().toAbsolutePath()).toString();
                    // Use forward slashes for JSON consistency
                    relativePath = relativePath.replace("\\", "/");
                    imageField.setText(relativePath);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error getting relative path!");
                }
            }
        });

        JTextField altField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField buttonField = new JTextField();

        JLabel altLabel = new JLabel("    Alt:");
        styleLabel(altLabel, labelFont);
        panel.add(altLabel);
        altField.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(altField);

        JLabel nameLabel = new JLabel("    Name:");
        styleLabel(nameLabel, labelFont);
        panel.add(nameLabel);
        nameField.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(nameField);

        JLabel priceLabel = new JLabel("    Price:");
        styleLabel(priceLabel, labelFont);
        panel.add(priceLabel);
        priceField.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(priceField);

        JLabel buttonLabel = new JLabel("    Button Text:");
        styleLabel(buttonLabel, labelFont);
        panel.add(buttonLabel);
        panel.add(buttonField);

        // Button colors and size
        Color buttonColor = new Color(10, 36, 99); // navy blue
        Color textColor = Color.WHITE;
        Dimension buttonSize = new Dimension(250, 60); // larger height for fullscreen

        // Create rounded buttons (use large radius for pill effect)
        int radius = 60; // <- change this to 30/100 etc to tune

        RoundedButton addButton = new RoundedButton("Add Product", buttonColor, textColor, radius);
        addButton.setPreferredSize(buttonSize);
        panel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
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

        RoundedButton checkButton = new RoundedButton("Check Products", buttonColor, textColor, radius);
        checkButton.setPreferredSize(buttonSize);
        panel.add(checkButton);

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                JOptionPane.showMessageDialog(frame, productList.toString(), "Products", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    private static void styleLabel(JLabel label, Font font) {
        label.setFont(font);
        label.setForeground(Color.WHITE);
    }

    // Custom rounded button that paints a filled rounded background (not just a border)
    static class RoundedButton extends JButton {
        private final Color bg;
        private final Color fg;
        private final int radius;
        private Color currentBg;

        RoundedButton(String text, Color bg, Color fg, int radius) {
            super(text);
            this.bg = bg;
            this.fg = fg;
            this.radius = radius;
            this.currentBg = bg;

            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setForeground(fg);
            setFont(new Font("Arial", Font.BOLD, 18));
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);
            setBorder(new EmptyBorder(10, 20, 10, 20));

            // Hover and click effects
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    currentBg = bg.brighter(); // lighter on hover
                    repaint();
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    currentBg = bg; // reset when mouse exits
                    repaint();
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    currentBg = bg.darker(); // darker when clicked
                    repaint();
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    currentBg = bg; // reset on release
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fill rounded rect background using currentBg
            g2.setColor(currentBg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            // Optional subtle border
            g2.setColor(new Color(0, 0, 0, 60));
            g2.setStroke(new BasicStroke(1f));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

            super.paintComponent(g2);
            g2.dispose();
        }
    }

    private static void loadFromFile() {
        try {
            String jsonPath = "../products2.json"; // Update with your file path
            File file = new File(jsonPath);
            if (file.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(jsonPath)));
                productsArray = new JSONArray(content);
            } else {
                productsArray = new JSONArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
            productsArray = new JSONArray();
        }
    }

    private static void saveToFile() {
        try (FileWriter file = new FileWriter("../products2.json")) {
            file.write(productsArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


