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
    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    public final static Parcelable.Creator<DrugProgramModel> CREATOR = new Creator<DrugProgramModel>() {
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
        in.readList(this.products, (Product.class.getClassLoader()));
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(slogan);
        dest.writeValue(description);
        dest.writeList(products);
    }

    public int describeContents() {
        return 0;
    }

    public static class Product implements Parcelable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        public final Parcelable.Creator<Product> CREATOR = new Creator<Product>() {

            public Product createFromParcel(Parcel in) {
                return new Product(in);
            }

            public Product[] newArray(int size) {
                return (new Product[size]);
            }

        };

        protected Product(Parcel in) {
            this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.name = ((String) in.readValue((String.class.getClassLoader())));
        }

        public Product() {
        }

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

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(id);
            dest.writeValue(name);
        }

        public int describeContents() {
            return 0;
        }
    }
}