fetch('../products2.json')
    .then(response => response.json())
    .then(products => {
        const productContainer = document.querySelector('.all_products');
        const urlParams = new URLSearchParams(window.location.search);
        const brandFilter = urlParams.get('id'); // Get the brand name (e.g., "Toyotomi")

        // Update the page title dynamically
        const pageTitle = document.querySelector("title");
        if (brandFilter) {
            pageTitle.textContent = `Κλιματιστικά ${brandFilter} | Ψύξη Γιαννούλης | Χανιά`;
        } else {
            pageTitle.textContent = `Κλιματιστικά | Ψύξη Γιαννούλης | Χανιά`;
        }

        // Update meta description and keywords
        const metaDescription = document.querySelector("meta[name='description']");
        const metaKeywords = document.querySelector("meta[name='keywords']");

        if (brandFilter) {
            metaDescription.content = `Η ${brandFilter} είναι μια από τις μεγαλύτερες εταιρείες στον τομέα του κλιματισμού, και ξεχωρίζει για την αξιοπιστία της και τις ανταγωνιστικές τιμές των κλιματιστικών της.`;
            metaKeywords.content += `, ${brandFilter} κλιματιστικά`; // Add brand to keywords
        } else {
            metaDescription.content = "Η Ψύξη Γιαννούλης προσφέρει αξιόπιστα και οικονομικά κλιματιστικά από κορυφαίες μάρκες, με εξαιρετική εξυπηρέτηση στα Χανιά.";
        }

        // Highlight the selected brand filter
        if (brandFilter) {
            document.querySelectorAll(".company-filter a").forEach(anchor => {
                if (anchor.dataset.company?.toLowerCase() === brandFilter.toLowerCase()) {
                    anchor.querySelector("img").style.border = "4px solid #ffbb78";
                }
            });
        }

        // If there's a brand filter, only show products from that brand
        const filteredProducts = brandFilter 
            ? products.filter(product => product.brand.toLowerCase() === brandFilter.toLowerCase()) 
            : products;

        filteredProducts.forEach(product => {
            const productCard = document.createElement('div');
            productCard.classList.add('product-card');
            productCard.innerHTML = `
                <a href="productpage.html?id=${product.id}" class="product-link">
                    <img src="${product.image}" alt="${product.alt}" loading="lazy">
                    <p>${product.name}</p>
                    <p>${product.price}</p>
                </a>
                <button>${product.buttonText}</button>
            `;
            productContainer.appendChild(productCard);
        });
    })
    .catch(error => console.error('Error loading products:', error));
