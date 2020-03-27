package com.larken.immc2.ModalClasses;

public class PaymentModal {


    private String BookName;
    private String CartPrice;
    private String SinglePrice;
    private String Count;
    private String BookPrice;
    private String BookDesigner;
    private String BookCategory;
    private String BookSubCategory;
    private String CartImage;

    public PaymentModal(){

    }
    public PaymentModal(String bookName,String cartImage,String bookPrice,String finalPrice,String bookSubCategory,String bookImage,String count,String bookDesigner,String bookCategory)
    {
        BookName = bookName;
        CartPrice = finalPrice;
        SinglePrice=bookImage;
        BookDesigner=bookDesigner;
        BookPrice=bookPrice;
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

    public String getCartPrice() {
        return CartPrice;
    }

    public void setCartPrice(String finalPrice) {
        CartPrice = finalPrice;
    }

    public String getSinglePrice() {
        return SinglePrice;
    }

    public void setSinglePrice(String bookImage) {
        SinglePrice = bookImage;
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

    public String getBookPrice() {
        return BookPrice;
    }

    public void setBookPrice(String bookPrice) {
        BookPrice = bookPrice;
    }



}
