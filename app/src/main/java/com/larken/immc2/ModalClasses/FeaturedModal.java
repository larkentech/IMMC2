package com.larken.immc2.ModalClasses;

public class FeaturedModal {
    private String bookName;
    private String imageUrl;
    private String price;

    public FeaturedModal() {
    }

    public FeaturedModal(String bookName, String imageUrl, String price) {
        this.bookName = bookName;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

