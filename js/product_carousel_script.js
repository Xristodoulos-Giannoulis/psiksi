fetch('../products.json')
    .then(response => response.json())
    .then(products => {
        const productContainer = document.querySelector('#product-carousel');
        products.forEach(product => {
            const productCard = document.createElement('div');
            productCard.classList.add('product-card');
            
            // Create the anchor tag
            const productLink = document.createElement('a');
            productLink.href = `pages/productpage.html?id=${product.id}`;
            productLink.classList.add('product-link');
            
            // Set product card content
            const productImage = document.createElement('img');
            productImage.src = product.image;
            productImage.alt = product.alt; 
            productImage.loading = "lazy";
            
            const productName = document.createElement('p');
            productName.textContent = product.name;
            productName.style.color = 'black'; // Set text color to black
            
            const productPrice = document.createElement('p');
            productPrice.textContent = product.price;
            productPrice.style.color = 'black'; // Set text color to black
            
            const productButton = document.createElement('button');
            productButton.textContent = product.buttonText;
            
            // Append elements to the anchor tag
            productLink.appendChild(productImage);
            productLink.appendChild(productName);
            productLink.appendChild(productPrice);
            productLink.appendChild(productButton);
            
            // Add the anchor to the product card
            productCard.appendChild(productLink);
            
            // Add the product card to the container
            productContainer.appendChild(productCard);

            // Remove hover effects via JS (optional but if you want to control it all here)
            productLink.style.textDecoration = 'none'; // No underline
            productLink.addEventListener('mouseover', () => {
                productLink.style.color = 'black'; // Ensure no color change on hover
            });
            productLink.addEventListener('mouseout', () => {
                productLink.style.color = 'black'; // Ensure no color change when hover ends
            });
        });
    })
    .catch(error => console.error('Error loading products:', error));
