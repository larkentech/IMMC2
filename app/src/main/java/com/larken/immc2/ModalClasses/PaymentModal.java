package com.larken.immc2.ModalClasses;

public class PaymentModal {


    private String BookName;
    private String FinalPrice;
    private String BookImage;
    private String Count;
    private String BookDesigner;
    private String BookCategory;
    private String BookSubCategory;
    private String CartImage;

    public PaymentModal(){

    }
    public PaymentModal(String bookName,String cartImage,String finalPrice,String bookSubCategory,String bookImage,String count,String bookDesigner,String bookCategory)
    {
        BookName = bookName;
        FinalPrice = finalPrice;
        BookImage=bookImage;
        BookDesigner=bookDesigner;
        CartImage=cartImage;
        Count = count;
        BookCategory=bookCategory;
        BookSubCategory=bookSubCategory;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getFinalPrice() {
        return FinalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        FinalPrice = finalPrice;
    }

    public String getBookImage() {
        return BookImage;
    }

    public void setBookImage(String bookImage) {
        BookImage = bookImage;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
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
    public String getBookSubCategory() {
        return BookSubCategory;
    }

    public void setBookSubCategory(String bookSubCategory) {
        BookSubCategory = bookSubCategory;
    }
    public String getCartImage() {
        return CartImage;
    }

    public void setCartImage(String cartImage) {
        CartImage = cartImage;
    }



}
