package com.maritech.arterium.data.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("role")
    @Expose
    private Integer role;
    @SerializedName("role_key")
    @Expose
    private String roleKey;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;
    @SerializedName("parents")
    @Expose
    private List<Parent> parents = null;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("region_id")
    @Expose
    private Object regionId;
    @SerializedName("region")
    @Expose
    private Object region;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("institution_type")
    @Expose
    private String institutionType;
    @SerializedName("drug_program_id")
    @Expose
    private Integer drugProgramId;
    @SerializedName("drug_program_title")
    @Expose
    private String drugProgramTitle;
    @SerializedName("sold_count")
    @Expose
    private Integer soldCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Parent> getParents() {
        return parents;
    }

    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getRegionId() {
        return regionId;
    }

    public void setRegionId(Object regionId) {
        this.regionId = regionId;
    }

    public Object getRegion() {
        return region;
    }

    public void setRegion(Object region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    public Integer getDrugProgramId() {
        return drugProgramId;
    }

    public void setDrugProgramId(Integer drugProgramId) {
        this.drugProgramId = drugProgramId;
    }

    public String getDrugProgramTitle() {
        return drugProgramTitle;
    }

    public void setDrugProgramTitle(String drugProgramTitle) {
        this.drugProgramTitle = drugProgramTitle;
    }

    public Integer getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(Integer soldCount) {
        this.soldCount = soldCount;
    }

    public static class Parent {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("role")
        @Expose
        private Integer role;
        @SerializedName("role_key")
        @Expose
        private String roleKey;
        @SerializedName("login")
        @Expose
        private String login;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("created_at")
        @Expose
        private Integer createdAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getRole() {
            return role;
        }

        public void setRole(Integer role) {
            this.role = role;
        }

        public String getRoleKey() {
            return roleKey;
        }

        public void setRoleKey(String roleKey) {
            this.roleKey = roleKey;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

    }
}