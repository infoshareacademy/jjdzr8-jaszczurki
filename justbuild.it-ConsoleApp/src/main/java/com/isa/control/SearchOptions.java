package com.isa.control;

import static com.isa.entity.appConstants.AppConstants.ACCEPT_OR_BACK_TO_MENU;

public class SearchOptions extends SubMenuNavigator{

    public static final String SEARCH = "Wyszukaj po słowie kluczowym, po lokalizacji lub kategorii.";
    public void showSearchDetails(){

        System.out.println(SEARCH);
        System.out.println(ACCEPT_OR_BACK_TO_MENU);
        subMenuActions();
    }
}
