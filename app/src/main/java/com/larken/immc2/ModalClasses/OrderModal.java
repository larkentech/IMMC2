package com.larken.immc2.ModalClasses;


public class OrderModal {

    private String Address;
    private String BookID;
    private String BookName;
    private String FinalPrice;
    private String Name;
    private String OrderDate;
    private String ItemsCount;
    private String PhoneNumber;
    private Long TxnID;


    public OrderModal() {
    }

    public OrderModal(String address, String bookId, String bookName, String finalPrice, String name, String itemsCount, String phoneNumber, Long txnID, String orderDate) {
        Address = address;
        BookID = bookId;
        BookName = bookName;
        FinalPrice = finalPrice;
        Name = name;
        ItemsCount = itemsCount;
        OrderDate = orderDate;
        PhoneNumber = phoneNumber;
        TxnID = txnID;
    }


    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookId) {
        BookID = bookId;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getItemsCount() {
        return ItemsCount;
    }

    public void setItemsCount(String itemsCount) {
        ItemsCount = itemsCount;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) { PhoneNumber = phoneNumber; }

    public Long getTxnID() {
        return TxnID;
    }

    public void setTxnID(Long txnID) {
        TxnID = txnID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }
}
