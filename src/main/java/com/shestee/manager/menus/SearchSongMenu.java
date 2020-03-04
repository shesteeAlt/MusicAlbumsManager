package com.shestee.manager.menus;

import com.shestee.interfaces.Cli;
import com.shestee.interfaces.SongService;
import com.shestee.service.SongServiceImpl;

public class SearchSongMenu extends Menu {
    private SongService songService = new SongServiceImpl();

    @Override
    void viewMenu() {
        System.out.println("""
            SEARCH SONG MENU
            ----------------
            Search by:
            1 - title
            2 - artist
            ------------
            0 - Quit  to main menu""");
    }

    @Override
    void chooseOption(Cli cli) {
        switch (cli.readLine()) {
            case "1":
                System.out.print("Enter title of the song (or part of it): ");
                String title = cli.readLine();
                songService.viewSongs(songService.findByTitle(title));
                break;
            case "2":
                System.out.println("Enter artist (or part): ");
                String artist = cli.readLine();
                songService.viewSongs(songService.findByArtist(artist));
                break;
            case "0":
                this.setInMenu(false);
                break;
        }
    }
}
