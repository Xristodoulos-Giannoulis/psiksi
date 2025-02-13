// Wait for the DOM to fully load
document.addEventListener("DOMContentLoaded", function() {
    // Get the button element by its class
    const allProductsButton = document.querySelector(".all-products-btn");
    
    // Add a click event listener to the button
    allProductsButton.addEventListener("click", function() {
        // Navigate to the desired page
        window.location.href = "pages/proionta.html"; // Ensure the correct file extension
    });
});
