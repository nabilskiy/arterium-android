package com.maritech.arterium.ui.dialogs.dialog_with_recycler.data;

public class DialogContent {
    private int idTittle;
    private int idHint;
    private boolean isActive;
    private int idTextColor;

    public DialogContent(int idTittle, int idHint, boolean isActive, int idTextColor) {
        this.idTittle = idTittle;
        this.idHint = idHint;
        this.isActive = isActive;
        this.idTextColor = idTextColor;
    }

    public int getIdTittle() {
        return idTittle;
    }

    public void setIdTittle(int idTittle) {
        this.idTittle = idTittle;
    }

    public int getIdHint() {
        return idHint;
    }

    public void setIdHint(int idHint) {
        this.idHint = idHint;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getIdTextColor() {
        return idTextColor;
    }

    public void setIdTextColor(int idTextColor) {
        this.idTextColor = idTextColor;
    }
}