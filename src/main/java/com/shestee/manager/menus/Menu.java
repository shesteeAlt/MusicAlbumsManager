package com.shestee.manager.menus;

import com.shestee.interfaces.Cli;

abstract class Menu {
    private boolean inMenu;

    Menu() {
        setInMenu(true);
    }

    abstract void viewMenu();

    abstract void chooseOption(Cli cli);

    public void proceedMenu(Cli cli) {
        while (this.inMenu) {
            viewMenu();
            chooseOption(cli);
        }
    }

    public boolean isInMenu() {
        return inMenu;
    }

    public void setInMenu(boolean inMenu) {
        this.inMenu = inMenu;
    }

}
