package com.larken.immc2.ModalClasses;

public class ComingSoonModal {

    private String DisplayName;
    private String DisplayImage;
    private String ProductImage;
    private String ProductName;
    private String ReleaseDate;

    private  ComingSoonModal(){

    }
    public ComingSoonModal(String displayName,String displayImage,String productImage,String productName,String releaseDate){
        this.DisplayImage=displayImage;
        this.DisplayName=displayName;
        this.ProductImage=productImage;
        this.ProductName=productName;
        this.ReleaseDate=releaseDate;

    }
    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        this.DisplayName = getDisplayName();
    }

    public String getDisplayImage() {
        return DisplayImage;
    }

    public void setDisplayImage(String displayImage) {
        this.DisplayImage = displayImage;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        this.ProductImage = productImage;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        this.ProductName = productName;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.ReleaseDate = releaseDate;
    }
}

