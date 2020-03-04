package com.larken.immc2.ModalClasses;


public class OrderModal {

    private String Address;
    private String BookId;
    private String BookName;
    private String FinalPrice;
    private String Name;
    private String OrderDate;
    private String OrderCount;
    private Long PhoneNumber;
    private String TxnID;


    public OrderModal() {
    }

    public OrderModal(String address, String bookId, String bookName, String finalPrice, String name, String orderCount, Long phoneNumber, String txnID, String orderDate) {
        Address = address;
        BookId = bookId;
        BookName = bookName;
        FinalPrice = finalPrice;
        Name = name;
        OrderCount = orderCount;
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

    public String getBookId() {
        return BookId;
    }

    public void setBookId(String bookId) {
        BookId = bookId;
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

    public String getOrderCount() {
        return OrderCount;
    }

    public void setOrderCount(String orderCount) {
        OrderCount = orderCount;
    }

    public Long getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) { PhoneNumber = phoneNumber; }

    public String getTxnID() {
        return TxnID;
    }

    public void setTxnID(String txnID) {
        TxnID = txnID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }
}
