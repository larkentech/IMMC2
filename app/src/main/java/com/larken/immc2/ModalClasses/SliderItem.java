package com.larken.immc2.ModalClasses;

public class SliderItem {

    private String imageUrl;
    private String Description;

    public SliderItem() {
    }

    public SliderItem(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        Description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
