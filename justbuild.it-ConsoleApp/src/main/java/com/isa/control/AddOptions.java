package com.isa.control;


import static com.isa.entity.appConstants.AppConstants.ACCEPT_OR_BACK_TO_MENU_MESSAGE;

public class AddOptions extends SubMenuNavigator{

    private static final String ADD = "Tu możesz dodać ogłoszenie";

    public void showAddDetails(){

        System.out.println(ADD);
        System.out.println(ACCEPT_OR_BACK_TO_MENU_MESSAGE);
        subMenuActions();
    }
}
