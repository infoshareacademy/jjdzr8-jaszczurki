package com.isa.control;

public class SearchOptions extends SubMenuNavigator{
    public void showSearchDetails(){

        System.out.println("Wyszukaj po słowie kluczowym, po lokalizacji lub kategorii");
        System.out.println("1 - ACCEPT\n2 - back to MENU");
        choose();
    }
}
