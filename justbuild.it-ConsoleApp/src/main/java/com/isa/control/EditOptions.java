package com.isa.control;


import static com.isa.entity.appConstants.AppConstants.ACCEPT_OR_BACK_TO_MENU;

public class EditOptions extends SubMenuNavigator{

    public static final String EDIT = "Edytuj ogłoszenie.";
    public void showEditDetails(){

        System.out.println(EDIT);
        System.out.println(ACCEPT_OR_BACK_TO_MENU);
        subMenuActions();
    }
}
