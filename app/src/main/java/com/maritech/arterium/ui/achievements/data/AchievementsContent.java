package com.maritech.arterium.ui.achievements.data;

public class AchievementsContent {
    private int idImage;
    private int idName;
    private int idDescription;

    public AchievementsContent(int idImage, int idName, int idDescription) {
        setIdImage(idImage);
        setIdName(idName);
        setIdDescription(idDescription);
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public int getIdName() {
        return idName;
    }

    public void setIdName(int idName) {
        this.idName = idName;
    }

    public int getIdDescription() {
        return idDescription;
    }

    public void setIdDescription(int idDescription) {
        this.idDescription = idDescription;
    }
}
