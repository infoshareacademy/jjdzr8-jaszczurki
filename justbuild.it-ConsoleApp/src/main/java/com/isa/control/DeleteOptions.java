package com.isa.control;

public class DeleteOptions extends SubMenuNavigator{
    public void showDeleteDetails(){

        System.out.println("Skasuj ogłoszenie.");
        System.out.println("1 - ACCEPT\n2 - back to MENU");
        choose();
    }
}
