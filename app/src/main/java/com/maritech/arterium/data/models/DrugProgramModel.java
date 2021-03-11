package com.maritech.arterium.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugProgramModel implements Parcelable {

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
    @SerializedName("primary_sold_count")
    @Expose
    private int primarySoldCount;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("next_level")
    @Expose
    private String nextLevel;
    @SerializedName("sells_to_next_level")
    @Expose
    private String sellsToNextLevel;


    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    private boolean selected = false;

    public DrugProgramModel() {
    }

    protected DrugProgramModel(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((Integer.class.getClassLoader())));
        this.slogan = ((String) in.readValue((Integer.class.getClassLoader())));
        this.description = ((String) in.readValue((Integer.class.getClassLoader())));
        this.primarySoldCount = ((int) in.readValue((int.class.getClassLoader())));
        this.level = ((String) in.readValue((Integer.class.getClassLoader())));
        this.nextLevel = ((String) in.readValue((Integer.class.getClassLoader())));
        this.sellsToNextLevel = ((String) in.readValue((String.class.getClassLoader())));
        this.selected = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.products, (DrugProgramModel.class.getClassLoader()));
    }

    public static final Creator<DrugProgramModel> CREATOR = new Creator<DrugProgramModel>() {
        @Override
        public DrugProgramModel createFromParcel(Parcel in) {
            return new DrugProgramModel(in);
        }

        @Override
        public DrugProgramModel[] newArray(int size) {
            return new DrugProgramModel[size];
        }
    };

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getPrimarySoldCount() {
        return primarySoldCount;
    }

    public void setPrimarySoldCount(int primarySoldCount) {
        this.primarySoldCount = primarySoldCount;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
    }

    public String getSellsToNextLevel() {
        return sellsToNextLevel;
    }

    public void setSellsToNextLevel(String sellsToNextLevel) {
        this.sellsToNextLevel = sellsToNextLevel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(slogan);
        dest.writeValue(description);
        dest.writeValue(primarySoldCount);
        dest.writeValue(level);
        dest.writeValue(nextLevel);
        dest.writeValue(sellsToNextLevel);
        dest.writeValue(selected);
        dest.writeList(products);
    }

    public static class Product implements Parcelable{

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;

        public Product() {
        }

        protected Product(Parcel in) {
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
            name = in.readString();
        }

        public static final Creator<Product> CREATOR = new Creator<Product>() {
            @Override
            public Product createFromParcel(Parcel in) {
                return new Product(in);
            }

            @Override
            public Product[] newArray(int size) {
                return new Product[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(id);
            dest.writeValue(name);
        }
    }
}