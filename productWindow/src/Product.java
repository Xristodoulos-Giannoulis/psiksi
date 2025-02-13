public class Product {
    private String image;
    private String alt;
    private String name;
    private String price;
    private String buttonText;

    public Product(String image, String alt, String name, String price, String buttonText) {
        this.image = image;
        this.alt = alt;
        this.name = name;
        this.price = price;
        this.buttonText = buttonText;
    }

    // Getters and Setters (needed for Jackson serialization)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }
}

