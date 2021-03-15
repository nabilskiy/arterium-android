package com.maritech.arterium.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PatientModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("has_img")
    @Expose
    private Boolean hasImg;
    @SerializedName("drug_program")
    @Expose
    private DrugProgramModel drugProgramModel;
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
    private Long createdAt;
    @SerializedName("last_purchase_at")
    @Expose
    private Long lastPurchaseAt;
    @SerializedName("purchases_count")
    @Expose
    private Integer purchasesCount;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("dose")
    @Expose
    private String dose;
    @SerializedName("hearth_attack_date")
    @Expose
    private Long hearthAttackDate;
    @SerializedName("prescribing_date")
    @Expose
    private Long prescribingDate;
    @SerializedName("ejection_fraction")
    @Expose
    private String ejectionFraction;
    @SerializedName("initial_potassium")
    @Expose
    private String initialPotassium;
    @SerializedName("final_potassium")
    @Expose
    private String finalPotassium;
    @SerializedName("date_oks")
    @Expose
    private Long dateOks;
    @SerializedName("option_oks")
    @Expose
    private String optionOks;
    @SerializedName("occlusion_zone")
    @Expose
    private String occlusionZone;
    @SerializedName("occlusion_degree")
    @Expose
    private String occlusionDegree;
    @SerializedName("coronary_angiography")
    @Expose
    private String coronaryAngiography;
    @SerializedName("coronary_dominance_type")
    @Expose
    private String coronaryDominanceType;
    @SerializedName("level_hba1c")
    @Expose
    private String levelHba1c;
    @SerializedName("fasting_glycemia")
    @Expose
    private String fastingGlycemia;
    @SerializedName("postprandial_glycemia")
    @Expose
    private String postprandialGlycemia;
    @SerializedName("index_homa_ir")
    @Expose
    private String indexHomaIr;
    @SerializedName("sad")
    @Expose
    private String sad;
    @SerializedName("dad")
    @Expose
    private String dad;
    @SerializedName("imt")
    @Expose
    private String imt;

    public final static Parcelable.Creator<PatientModel> CREATOR = new Creator<PatientModel>() {
        public PatientModel createFromParcel(Parcel in) {
            return new PatientModel(in);
        }

        public PatientModel[] newArray(int size) {
            return (new PatientModel[size]);
        }

    };

    protected PatientModel(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.hasImg = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.cardCode = ((String) in.readValue((String.class.getClassLoader())));
        this.phone = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((Long) in.readValue((Long.class.getClassLoader())));
        this.lastPurchaseAt = ((Long) in.readValue((Long.class.getClassLoader())));
        this.purchasesCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.weight = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.age = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.dose = ((String) in.readValue((String.class.getClassLoader())));
        this.hearthAttackDate = ((Long) in.readValue((Long.class.getClassLoader())));
        this.prescribingDate = ((Long) in.readValue((Long.class.getClassLoader())));
        this.ejectionFraction = ((String) in.readValue((String.class.getClassLoader())));
        this.initialPotassium = ((String) in.readValue((String.class.getClassLoader())));
        this.finalPotassium = ((String) in.readValue((String.class.getClassLoader())));
        this.dateOks = ((Long) in.readValue((Long.class.getClassLoader())));
        this.optionOks = ((String) in.readValue((String.class.getClassLoader())));
        this.occlusionZone = ((String) in.readValue((String.class.getClassLoader())));
        this.occlusionDegree = ((String) in.readValue((String.class.getClassLoader())));
        this.coronaryAngiography = ((String) in.readValue((String.class.getClassLoader())));
        this.coronaryDominanceType = ((String) in.readValue((String.class.getClassLoader())));
        this.levelHba1c = ((String) in.readValue((String.class.getClassLoader())));
        this.fastingGlycemia = ((String) in.readValue((String.class.getClassLoader())));
        this.postprandialGlycemia = ((String) in.readValue((String.class.getClassLoader())));
        this.indexHomaIr = ((String) in.readValue((String.class.getClassLoader())));
        this.sad = ((String) in.readValue((String.class.getClassLoader())));
        this.dad = ((String) in.readValue((String.class.getClassLoader())));
        this.imt = ((String) in.readValue((String.class.getClassLoader())));
        this.drugProgramModel = ((DrugProgramModel) in.readValue((DrugProgramModel.class.getClassLoader())));
    }

    public PatientModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getHasImg() {
        return hasImg;
    }

    public void setHasImg(Boolean hasImg) {
        this.hasImg = hasImg;
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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getLastPurchaseAt() {
        return lastPurchaseAt;
    }

    public void setLastPurchaseAt(Long lastPurchaseAt) {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public Long getHearthAttackDate() {
        return hearthAttackDate;
    }

    public void setHearthAttackDate(Long hearthAttackDate) {
        this.hearthAttackDate = hearthAttackDate;
    }

    public Long getPrescribingDate() {
        return prescribingDate;
    }

    public void setPrescribingDate(Long prescribingDate) {
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

    public Long getDateOks() {
        return dateOks;
    }

    public void setDateOks(Long dateOks) {
        this.dateOks = dateOks;
    }

    public String getOptionOks() {
        return optionOks;
    }

    public void setOptionOks(String optionOks) {
        this.optionOks = optionOks;
    }

    public String getOcclusionZone() {
        return occlusionZone;
    }

    public void setOcclusionZone(String occlusionZone) {
        this.occlusionZone = occlusionZone;
    }

    public String getOcclusionDegree() {
        return occlusionDegree;
    }

    public void setOcclusionDegree(String occlusionDegree) {
        this.occlusionDegree = occlusionDegree;
    }

    public String getCoronaryAngiography() {
        return coronaryAngiography;
    }

    public void setCoronaryAngiography(String coronaryAngiography) {
        this.coronaryAngiography = coronaryAngiography;
    }

    public String getCoronaryDominanceType() {
        return coronaryDominanceType;
    }

    public void setCoronaryDominanceType(String coronaryDominanceType) {
        this.coronaryDominanceType = coronaryDominanceType;
    }

    public String getLevelHba1c() {
        return levelHba1c;
    }

    public void setLevelHba1c(String levelHba1c) {
        this.levelHba1c = levelHba1c;
    }

    public String getFastingGlycemia() {
        return fastingGlycemia;
    }

    public void setFastingGlycemia(String fastingGlycemia) {
        this.fastingGlycemia = fastingGlycemia;
    }

    public String getPostprandialGlycemia() {
        return postprandialGlycemia;
    }

    public void setPostprandialGlycemia(String postprandialGlycemia) {
        this.postprandialGlycemia = postprandialGlycemia;
    }

    public String getIndexHomaIr() {
        return indexHomaIr;
    }

    public void setIndexHomaIr(String indexHomaIr) {
        this.indexHomaIr = indexHomaIr;
    }

    public String getSad() {
        return sad;
    }

    public void setSad(String sad) {
        this.sad = sad;
    }

    public String getDad() {
        return dad;
    }

    public void setDad(String dad) {
        this.dad = dad;
    }

    public String getImt() {
        return imt;
    }

    public void setImt(String imt) {
        this.imt = imt;
    }

    public DrugProgramModel getDrugProgramModel() {
        return drugProgramModel;
    }

    public void setDrugProgramModel(DrugProgramModel drugProgramModel) {
        this.drugProgramModel = drugProgramModel;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(hasImg);
        dest.writeValue(name);
        dest.writeValue(cardCode);
        dest.writeValue(phone);
        dest.writeValue(gender);
        dest.writeValue(createdAt);
        dest.writeValue(lastPurchaseAt);
        dest.writeValue(purchasesCount);
        dest.writeValue(height);
        dest.writeValue(weight);
        dest.writeValue(age);
        dest.writeValue(dose);
        dest.writeValue(hearthAttackDate);
        dest.writeValue(prescribingDate);
        dest.writeValue(ejectionFraction);
        dest.writeValue(initialPotassium);
        dest.writeValue(finalPotassium);
        dest.writeValue(dateOks);
        dest.writeValue(optionOks);
        dest.writeValue(occlusionZone);
        dest.writeValue(occlusionDegree);
        dest.writeValue(coronaryAngiography);
        dest.writeValue(coronaryDominanceType);
        dest.writeValue(levelHba1c);
        dest.writeValue(fastingGlycemia);
        dest.writeValue(postprandialGlycemia);
        dest.writeValue(indexHomaIr);
        dest.writeValue(sad);
        dest.writeValue(dad);
        dest.writeValue(imt);
        dest.writeValue(drugProgramModel);
    }

    public int describeContents() {
        return 0;
    }

    public static class DrugProgramModel implements Parcelable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("slogan")
        @Expose
        private String slogan;
        @SerializedName("description")
        @Expose
        private String description;

        public static Parcelable.Creator<DrugProgramModel> CREATOR = new Creator<DrugProgramModel>() {
            public DrugProgramModel createFromParcel(Parcel in) {
                return new DrugProgramModel(in);
            }

            public DrugProgramModel[] newArray(int size) {
                return (new DrugProgramModel[size]);
            }
        };

        protected DrugProgramModel(Parcel in) {
            this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.title = ((String) in.readValue((String.class.getClassLoader())));
            this.slogan = ((String) in.readValue((String.class.getClassLoader())));
            this.description = ((String) in.readValue((String.class.getClassLoader())));
        }

        public DrugProgramModel() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSlogan() {
            return slogan;
        }

        public void setSlogan(String slogan) {
            this.slogan = slogan;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(id);
            dest.writeValue(title);
            dest.writeValue(slogan);
            dest.writeValue(description);
        }

        public int describeContents() {
            return 0;
        }
    }
}
