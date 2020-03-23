package com.larken.immc2.ModalClasses;

public class BooksModal {
    private String BookName;
    private String BookPrice;
    private String BookDesc;
    private String BookImage;
    private String BookDesigner;
    private String BookCategory;
    private String BookID;
    private String BookSubCategory;
    private String BookPrice160Pages;

    public String getBookPrice160Pages() {
        return BookPrice160Pages;
    }

    public void setBookPrice160Pages(String bookPrice160Pages) {
        BookPrice160Pages = bookPrice160Pages;
    }

    public String getBookSubCategory() {
        return BookSubCategory;
    }

    public void setBookSubCategory(String bookSubCategory) {
        BookSubCategory = bookSubCategory;
    }

    public BooksModal() {
    }

    public BooksModal(String bookName, String bookPrice, String bookDesc, String bookImage, String bookDesigner, String bookCategory, String bookId, String bookSubCategory, String bookPrice160Pages) {
        BookName = bookName;
        BookPrice = bookPrice;
        BookDesc = bookDesc;
        BookImage = bookImage;
        BookDesigner = bookDesigner;
        BookCategory = bookCategory;
        BookID = bookId;
        BookSubCategory = bookSubCategory;
        BookPrice160Pages = bookPrice160Pages;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getBookPrice() {
        return BookPrice;
    }

    public void setBookPrice(String bookPrice) {
        BookPrice = bookPrice;
    }

    public String getBookDesc() {
        return BookDesc;
    }

    public void setBookDesc(String bookDesc) {
        BookDesc = bookDesc;
    }

    public String getBookImage() {
        return BookImage;
    }

    public void setBookImage(String bookImage) {
        BookImage = bookImage;
    }

    public String getBookDesigner() {
        return BookDesigner;
    }

    public void setBookDesigner(String bookDesigner) {
        BookDesigner = bookDesigner;
    }

    public String getBookCategory() {
        return BookCategory;
    }

    public void setBookCategory(String bookCategory) {
        BookCategory = bookCategory;
    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookId) {
        BookID = bookId;
    }
}
