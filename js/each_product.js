// Get the product ID from the URL
const urlParams = new URLSearchParams(window.location.search);
const productId = urlParams.get('id');

// Fetch the product data from the JSON file
fetch('../products2.json')
  .then(response => response.json())
  .then(products => {
    // Find the product matching the ID from the URL
    const product = products.find(p => p.id === productId);
    
    if (product) {
      // Update the HTML elements with product data
      document.getElementById('product-name').textContent = product.name;
      document.getElementById('product-price').textContent = product.price;
      document.getElementById('product-title').textContent = product.name;
      document.getElementById('product-desc').textContent = product.description || 'Περιγραφή δεν είναι διαθέσιμη αυτή τη στιγμή.';
      document.getElementById('product-btu').textContent = `Ψυκτική Ικανότητα: ${product.btu || 'N/A'}`;
      document.getElementById('product-efficiency').textContent = `Ενεργειακή Κλάση: ${product.efficiency || 'N/A'}`;
      
      // Set the background image of the product
      document.querySelector('.full-width-image').style.backgroundImage = `url('${product.image}')`;
    } else {
      // Show an error message if the product isn't found
      document.body.innerHTML = `
        <div style="text-align: center; padding: 50px;">
          <h1>Το προϊόν δεν βρέθηκε</h1>
          <a href="../index.html">Επιστροφή στην αρχική σελίδα</a>
        </div>`;
    }
  })
  .catch(error => {
    console.error('Error loading product data:', error);
    document.body.innerHTML = `
      <div style="text-align: center; padding: 50px;">
        <h1>Υπήρξε ένα σφάλμα</h1>
        <p>Παρακαλώ προσπαθήστε ξανά αργότερα.</p>
      </div>`;
  });
