package com.isa.control;

public class EditOptions extends SubMenuNavigator{
    public void showEditDetails(){

        System.out.println("Edytuj ogłoszenie.");
        System.out.println("1 - ACCEPT\n2 - back to MENU");
        choose();
    }
}
