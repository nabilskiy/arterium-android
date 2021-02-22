package com.maritech.arterium.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientCreateModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("card_code")
    @Expose
    private String cardCode;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;
    @SerializedName("last_purchase_at")
    @Expose
    private String lastPurchaseAt;
    @SerializedName("purchases_count")
    @Expose
    private Integer purchasesCount;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("hearth_attack_date")
    @Expose
    private String hearthAttackDate;
    @SerializedName("prescribing_date")
    @Expose
    private String prescribingDate;
    @SerializedName("ejection_fraction")
    @Expose
    private String ejectionFraction;
    @SerializedName("initial_potassium")
    @Expose
    private String initialPotassium;
    @SerializedName("final_potassium")
    @Expose
    private String finalPotassium;
    @SerializedName("has_img")
    @Expose
    private Boolean hasImg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastPurchaseAt() {
        return lastPurchaseAt;
    }

    public void setLastPurchaseAt(String lastPurchaseAt) {
        this.lastPurchaseAt = lastPurchaseAt;
    }

    public Integer getPurchasesCount() {
        return purchasesCount;
    }

    public void setPurchasesCount(Integer purchasesCount) {
        this.purchasesCount = purchasesCount;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getHearthAttackDate() {
        return hearthAttackDate;
    }

    public void setHearthAttackDate(String hearthAttackDate) {
        this.hearthAttackDate = hearthAttackDate;
    }

    public String getPrescribingDate() {
        return prescribingDate;
    }

    public void setPrescribingDate(String prescribingDate) {
        this.prescribingDate = prescribingDate;
    }

    public String getEjectionFraction() {
        return ejectionFraction;
    }

    public void setEjectionFraction(String ejectionFraction) {
        this.ejectionFraction = ejectionFraction;
    }

    public String getInitialPotassium() {
        return initialPotassium;
    }

    public void setInitialPotassium(String initialPotassium) {
        this.initialPotassium = initialPotassium;
    }

    public String getFinalPotassium() {
        return finalPotassium;
    }

    public void setFinalPotassium(String finalPotassium) {
        this.finalPotassium = finalPotassium;
    }

    public Boolean getHasImg() {
        return hasImg;
    }

    public void setHasImg(Boolean hasImg) {
        this.hasImg = hasImg;
    }

}