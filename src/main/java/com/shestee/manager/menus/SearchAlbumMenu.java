package com.shestee.manager.menus;

import com.shestee.interfaces.AlbumService;
import com.shestee.interfaces.Cli;
import com.shestee.interfaces.SongService;
import com.shestee.service.AlbumServiceImpl;
import com.shestee.service.SongServiceImpl;

public class SearchAlbumMenu extends Menu {
    private AlbumService albumService = new AlbumServiceImpl();
    private SongService songService = new SongServiceImpl();

    public void viewMenu() {
        System.out.println("""
            SEARCH ALBUM MENU
            -----------------
            Search by:
            1 - artist name
            2 - title
            3 - genre
            4 - year

            5 - view songs from album(by id number)
            ------------
            0 - Quit to main menu""");
    }

    public void chooseOption(Cli cli) {
        switch (cli.readLine()) {
            case "1":
                cli.print("Enter artist name (or part of it): ");
                String artist = cli.readLine();
                albumService.viewAlbums(cli, albumService.findByArtist(artist));
                break;
            case "2":
                cli.println("Enter album title (or part of it): ");
                String title = cli.readLine();
                albumService.viewAlbums(cli, albumService.findByTitle(title));
                break;
            case "3":
                cli.println("Enter genre: ");
                String genre = cli.readLine();
                albumService.viewAlbums(cli, albumService.findByGenre(genre));
                break;
            case "4":
                try {
                    cli.println("Enter a year: ");
                    int year = Integer.parseInt(cli.readLine());
                    albumService.viewAlbums(cli, albumService.findByYear(year));
                } catch (NumberFormatException e) {
                    cli.println("Please enter a valid year");
                    cli.println("");
                }
                break;
            case "5":
                cli.println("Enter id number of album: ");
                int id = Integer.parseInt(cli.readLine());
                songService.viewSongs(albumService.getSongsFromAlbum(id));
                break;
            case "0":
                setInMenu(false);
                break;
        }
    }
}
