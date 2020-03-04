package com.shestee;

import com.shestee.manager.AppManager;

public class AlbumsMain {
    public static void main(String[] args) {
        AppManager app = new AppManager();

        app.start(new CliImpl());
    }
}
