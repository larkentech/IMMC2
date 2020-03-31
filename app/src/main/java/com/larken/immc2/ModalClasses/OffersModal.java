package com.larken.immc2.ModalClasses;

public class OffersModal {
    private String OfferCategory;
    private String OfferSubCategory;
    private String OfferImage;

    public OffersModal() {
    }



    public OffersModal(String offerCategory,String offerSubCategory,String offerImage){
        this.OfferSubCategory=offerSubCategory;
        this.OfferCategory=offerCategory;
        this.OfferImage=offerImage;

    }
    public String getOfferCategory() {
        return OfferCategory;
    }

    public void setOfferCategory(String offerCategory) {
        this.OfferCategory = offerCategory;
    }

    public String getOfferSubCategory() {
        return OfferSubCategory;
    }

    public void setOfferSubCategory(String offerSubCategory) {
        this.OfferSubCategory = offerSubCategory;
    }

    public String getOfferImage() {
        return OfferImage;
    }

    public void setOfferImage(String offerImage) {
        this.OfferImage = offerImage;
    }

}


