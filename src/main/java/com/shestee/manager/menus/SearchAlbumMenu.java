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
                System.out.print("Enter artist name (or part of it): ");
                String artist = cli.readLine();
                albumService.viewAlbums(albumService.findByArtist(artist));
                break;
            case "2":
                System.out.println("Enter album title (or part of it): ");
                String title = cli.readLine();
                albumService.viewAlbums(albumService.findByTitle(title));
                break;
            case "3":
                System.out.println("Enter genre: ");
                String genre = cli.readLine();
                albumService.viewAlbums(albumService.findByGenre(genre));
                break;
            case "4":
                try {
                    System.out.println("Enter a year: ");
                    int year = Integer.parseInt(cli.readLine());
                    albumService.viewAlbums(albumService.findByYear(year));
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid year");
                    System.out.println("");
                }
                break;
            case "5":
                System.out.println("Enter id number of album: ");
                int id = Integer.parseInt(cli.readLine());
                songService.viewSongs(albumService.getSongsFromAlbum(id));
                break;
            case "0":
                setInMenu(false);
                break;
        }
    }
}
