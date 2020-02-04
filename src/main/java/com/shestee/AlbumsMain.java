package com.shestee;

//import com.shestee.dao.AlbumDao;


import com.shestee.dao.AlbumDao;
import com.shestee.entity.enums.LengthType;
import com.shestee.entity.enums.Medium;
import com.shestee.interfaces.AlbumService;
import com.shestee.interfaces.SongService;
import com.shestee.service.AlbumServiceImpl;
import com.shestee.entity.Album;
import com.shestee.service.SongServiceImpl;

import java.util.Scanner;

public class AlbumsMain {
    public static void mainMenu() {
        System.out.println("MUSIC ALBUMS LIBRARY MAIN MENU");
        System.out.println("------------------------------");
        System.out.println("1 - View all albums");
        System.out.println("2 - Search for album");
        System.out.println("3 - Search for song");
        System.out.println("4 - Add/Remove album");
        System.out.println("0 - Quit");
    }

    public static void searchAlbumMenu() {
        System.out.println("SEARCH ALBUM MENU");
        System.out.println("-----------------");
        System.out.println("Search by:");
        System.out.println("1 - artist name");
        System.out.println("2 - title");
        System.out.println("3 - genre");
        System.out.println("4 - year");
        System.out.println("------------");
        System.out.println("5 - view songs from album(by id number)");
        System.out.println("0 - Quit to main menu");
    }

    public static void searchSongMenu() {
        System.out.println("SEARCH SONG MENU");
        System.out.println("----------------");
        System.out.println("Search by:");
        System.out.println("1 - title");
        System.out.println("2 - artist");
        System.out.println("3 - music composer");
        System.out.println("4 - lyrics writer");
        System.out.println("------------------");
        System.out.println("0 - Quit  to main menu");
    }

    public static void addRemoveMenu() {
        System.out.println("Add/Remove menu");
        System.out.println("---------------");
        System.out.println("1 - Add album");
        System.out.println("2 - Remove album");
        System.out.println("0 - Quit to main menu");
    }


    public static void main(String[] args) {
        System.getProperty("java.classpath");
        Scanner scanner = new Scanner(System.in);
        boolean appOn = true;

        while(appOn) {

            SongService songService = SongServiceImpl.getInstance();
            AlbumService albumService = AlbumServiceImpl.getInstance();
            AlbumDao albumDao = AlbumDao.getInstance();
            boolean inSearchAlbumMenu = false;
            boolean inSearchSongMenu = false;
            boolean inAddRemoveMenu = false;

            mainMenu();
            String menuRead = scanner.nextLine();

            switch (menuRead) {
                case "1":
                    albumService.viewAlbums(albumService.getAllAlbums());
                    break;
                case "2":
                    inSearchAlbumMenu = true;
                    break;
                case "3":
                    inSearchSongMenu = true;
                    break;
                case "4":
                    inAddRemoveMenu = true;
                    break;
                case "0":
                    appOn = false;
                    break;
                case "import from excel":
                    albumDao.copyFromXclToDB();
                    albumDao.addSongsFromXCLsheet();
                    break;



            }

            while (inSearchAlbumMenu) {
                searchAlbumMenu();
                String searchAlbumChoice = scanner.nextLine();
                switch (searchAlbumChoice) {
                    case "1":
                        System.out.print("Enter artist name (or part of it): ");
                        String artist = scanner.nextLine();
                        albumService.viewAlbums(albumService.findByArtist(artist));
                        break;
                    case "2":
                        System.out.println("Enter album title (or part of it): ");
                        String title = scanner.nextLine();
                        albumService.viewAlbums(albumService.findByTitle(title));
                        break;
                    case "3":
                        System.out.println("Enter genre: ");
                        String genre = scanner.nextLine();
                        albumService.viewAlbums(albumService.findByGenre(genre));
                        break;
                    case "4":
                        try {
                            System.out.println("Enter a year: ");
                            int year = Integer.parseInt(scanner.nextLine());
                            albumService.viewAlbums(albumService.findByYear(year));
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid year");
                            System.out.println("");
                        }
                        break;
                    case "5":
                        System.out.println("Enter id number of album: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        songService.viewSongs(albumService.getSongsFromAlbum(id));
                        break;
                    case "0":
                        inSearchAlbumMenu = false;
                        break;
                }
            }

            while (inSearchSongMenu) {
                searchSongMenu();
                String searchMenuChoice = scanner.nextLine();
                switch (searchMenuChoice) {
                    case "1":
                        System.out.print("Enter title of the song (or part of it): ");
                        String title = scanner.nextLine();
                        songService.viewSongs(songService.findByTitle(title));
                        break;
                    case "2":
                        System.out.println("Enter artist (or part): ");
                        String artist = scanner.nextLine();
                        songService.viewSongs(songService.findByArtist(artist));
                        //songService.findByArtist(artist).stream().forEach(System.out::println);
                        break;
                    case "0":
                        inSearchSongMenu = false;
                        break;
                }
            }

            while (inAddRemoveMenu) {
                addRemoveMenu();
                String addRemoveMenuChoice = scanner.nextLine();
                switch (addRemoveMenuChoice) {
                    case "1":
                        Album album = new Album();
                        System.out.print("Enter artist name: ");
                        album.setArtist(scanner.nextLine());
                        System.out.print("Enter title of the album: ");
                        album.setTitle(scanner.nextLine());
                        System.out.print("Enter number of source type: (1)VINYL, (2)CD, (3)CASSETTE, (4)FILES: ");
                        switch (scanner.nextLine()) {
                            case "1":
                                album.setMedium(Medium.VINYL);
                                break;
                            case "2":
                                album.setMedium(Medium.CD);
                                break;
                            case "3":
                                album.setMedium(Medium.CASSETTE);
                                break;
                            case "4":
                                album.setMedium(Medium.FILES);
                                break;
                            default:
                                album.setMedium(Medium.OTHER);
                                break;
                        }
                        System.out.print("Enter number of a type of the album; (1)LP, (2)EP, (3)SINGLE, (4)OTHER: ");
                        switch (scanner.nextLine()) {
                            case "1":
                                album.setLengthType(LengthType.LP);
                                break;
                            case "2":
                                album.setLengthType(LengthType.EP);
                                break;
                            case "3":
                                album.setLengthType(LengthType.SINGLE);
                                break;
                            case "4":
                                album.setLengthType(LengthType.OTHER);
                                break;
                        }
                        System.out.print("Enter genre of the album: ");
                        album.setGenre(scanner.nextLine());
                        System.out.print("Enter publisher label: ");
                        album.setLabel(scanner.nextLine());
                        System.out.print("Enter catalogue number of the album: ");
                        album.setCatalogueNumber(scanner.nextLine());
                        System.out.print("Enter issue year of the album: ");
                        album.setYear(Integer.parseInt(scanner.nextLine()));
                        System.out.print("Do you want to add following album? (y)es/(n)o: ");
                        System.out.println(album.toString());
                        switch(scanner.nextLine()) {
                            case "y":
                                albumService.addAlbum(album);
                                break;
                            default:
                                System.out.println("The album was not added");
                                break;
                        }
                        break;
                    case "2":
                        System.out.print("Enter id number of the album you want to be removed: ");
                        int idToRemove;
                        try {
                            idToRemove = Integer.parseInt(scanner.nextLine());
                            System.out.println("The album you want to be removed is:");
                            System.out.println(albumService.findById(idToRemove).toString());
                            System.out.print("Are you sure? (y)es/(n)o: ");
                            switch (scanner.nextLine()) {
                                case "y":
                                    albumService.removeAlbum(idToRemove);
                                    break;
                                default:
                                    System.out.println("The album was not deleted");
                                    break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Enter proper id number");
                        }

                        break;
                    case "0":
                        inAddRemoveMenu = false;
                        break;
                }
            }
        }
    }
}
