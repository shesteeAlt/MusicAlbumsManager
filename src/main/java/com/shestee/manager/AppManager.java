package com.shestee.manager;

import com.shestee.interfaces.Cli;
import com.shestee.manager.menus.MainMenu;

public class AppManager {

    public void start(Cli cli) {
        MainMenu mainMenu = new MainMenu();
        while (mainMenu.isInMenu()) {
            mainMenu.proceedMenu(cli);
        }
    }
}