package com.shestee.manager.menus;

import com.shestee.dao.AlbumDao;
import com.shestee.interfaces.AlbumService;
import com.shestee.interfaces.Cli;
import com.shestee.service.AlbumServiceImpl;

public class MainMenu extends Menu {
    private AlbumService albumService = new AlbumServiceImpl();
    private AlbumDao albumDao = AlbumDao.getInstance();

    public void viewMenu() {
        System.out.println("""
            MUSIC ALBUMS LIBRARY MAIN MENU
            ------------------------------
            1 - View all albums
            2 - Search for album
            3 - Search for song
            4 - Add/Remove album
            ------------
            0 - Quit""");
    }

    public void chooseOption(Cli cli) {
        switch (cli.readLine()) {
            case "1":
                albumService.viewAlbums(albumService.getAllAlbums());
                break;
            case "2":
                SearchAlbumMenu searchAlbumMenu = new SearchAlbumMenu();
                searchAlbumMenu.proceedMenu(cli);
                break;
            case "3":
                SearchSongMenu searchSongMenu = new SearchSongMenu();
                searchSongMenu.proceedMenu(cli);
                break;
            case "4":
                AddRemoveMenu addRemoveMenu = new AddRemoveMenu();
                addRemoveMenu.proceedMenu(cli);
                break;
            case "0":
                this.setInMenu(false);
                break;
            case "import from excel":
                albumDao.copyFromXclToDB(cli);
                albumDao.addSongsFromXCLsheet(cli);
                break;
        }
    }
}
