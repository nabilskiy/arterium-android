package com.maritech.arterium.ui.dashboard.medicalRep.data;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class DoctorsContent {

    int id;
    String name;
    String allBuy;
    String level;
    String link;
    String textForTvInfo = "";

    DoctorsNavigator navigator = new DoctorsNavigator();

    public DoctorsContent(int id, String name, String allBuy, String level, String link) {
        this.id = id;
        this.name = name;
        this.allBuy = allBuy;
        this.level = level;
        this.link = link;
        setTextForTvInfo();


    }

    public String getTextForTvInfo() {
        return textForTvInfo;
    }

    public void setTextForTvInfo() {
        this.textForTvInfo = "Всього " + getAllBuy() + " покупки • Рiвень " + getLevel();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllBuy() {
        return allBuy;
    }

    public void setAllBuy(String allBuy) {
        this.allBuy = allBuy;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void goToItem(View v) {
        NavController navController = Navigation.findNavController(v);


        if ("DashboardMpFragment".equals(navController.getCurrentDestination().getLabel())) {
            navigator.goToDashboardMp(navController);
        } else if ("DashboardRmFragment".equals(navController.getCurrentDestination().getLabel())) {
            navigator.goToDashboardRp(navController);
        }
    }

}
