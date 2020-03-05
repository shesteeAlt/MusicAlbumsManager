package com.shestee.manager.menus;

import com.shestee.entity.Album;
import com.shestee.entity.enums.LengthType;
import com.shestee.entity.enums.Medium;
import com.shestee.interfaces.AlbumService;
import com.shestee.interfaces.Cli;
import com.shestee.interfaces.SongService;
import com.shestee.parsers.AlbumJsonParser;
import com.shestee.service.AlbumServiceImpl;
import com.shestee.service.SongServiceImpl;
import org.json.JSONException;

public class AddRemoveMenu extends Menu {

    @Override
    void viewMenu() {
        System.out.println("""
            Add/Remove menu
            ---------------
            1 - Add album
            2 - Add album by Discogs release id
            3 - Remove album
            4 - Add songs to album
            ------------
            0 - Quit to main menu""");
    }

    @Override
    void chooseOption(Cli cli) {
        AlbumService albumService = new AlbumServiceImpl();
        SongService songService = new SongServiceImpl();

        switch (cli.readLine()) {
            case "1":
                Album album = new Album();
                System.out.print("Enter artist name: ");
                album.setArtist(cli.readLine());
                System.out.print("Enter title of the album: ");
                album.setTitle(cli.readLine());

                System.out.print("Enter number of source type: (1)VINYL, (2)CD, (3)CASSETTE, (4)FILES: ");
                chooseMedium(cli, album);

                System.out.print("Enter number of a type of the album; (1)LP, (2)EP, (3)SINGLE, (4)OTHER: ");
                chooseLengthType(cli, album);

                System.out.print("Enter genre of the album: ");
                album.setGenre(cli.readLine());
                System.out.print("Enter publisher label: ");
                album.setLabel(cli.readLine());
                System.out.print("Enter catalogue number of the album: ");
                album.setCatalogueNumber(cli.readLine());
                System.out.print("Enter issue year of the album: ");

                try {
                    album.setYear(Integer.parseInt(cli.readLine()));
                } catch (NumberFormatException e) {
                    album.setYear(0);
                    System.out.println("Wrong year format. Year set to 0.");
                }
                System.out.print("Do you want to add following album? (y)es/(n)o: ");
                System.out.println(album.toString());

                if ("y".equals(cli.readLine())) {
                    albumService.addAlbum(album);
                } else {
                    System.out.println("The album was not added");
                }
                break;
            case "2":
                System.out.println("Enter Discogs release id of album you want to be added:");
                String discogsReleaseId = cli.readLine();
                try {
                    AlbumJsonParser albumJsonParser = new AlbumJsonParser();
                    Album albumToAdd = albumJsonParser.parseAlbumFromAlbumJson(discogsReleaseId);
                    System.out.print("Do you want to add following album? (y)es/(n)o: ");
                    System.out.println(albumToAdd.toString());
                    if ("y".equals(cli.readLine())) {
                        albumService.addAlbum(albumToAdd);
                        albumToAdd.getId();
                        albumService.addAllSongsToAlbum(albumToAdd.getId(), discogsReleaseId);
                        System.out.println("The album was added");
                    } else {
                        System.out.println("The album was not added");
                    }
                }   catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Wrong release_id");
                }

                break;

            case "3":
                System.out.print("Enter id number of the album you want to be removed: ");
                int idToRemove;
                try {
                    idToRemove = Integer.parseInt(cli.readLine());
                    System.out.println("The album you want to be removed is:");
                    System.out.println(albumService.findById(idToRemove).toString());
                    System.out.print("Are you sure? (y)es/(n)o: ");
                    if ("y".equals(cli.readLine())) {
                        albumService.removeAlbum(idToRemove);
                    } else {
                        System.out.println("The album was not deleted");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Enter proper id number");
                } catch (NullPointerException e) {
                    System.out.println("Provided id doesn't exist");
                }
                break;
            case "4":
                System.out.println("Enter id number of the album you want to add songs to: ");
                int idToAddSongs = Integer.parseInt(cli.readLine());
                Album tempAlbum = albumService.findById(idToAddSongs);
                System.out.println("You want to add songs to album:");
                System.out.println(tempAlbum.toString());
                if (albumService.getSongsFromAlbum(idToAddSongs).size() == 0) {
                    System.out.println("The album doesn't contain any songs");
                } else if (albumService.getSongsFromAlbum(idToAddSongs).size() >0) {
                    System.out.println(("The album already contains songs: "));
                    songService.viewSongs(albumService.getSongsFromAlbum(idToAddSongs));
                }
                System.out.println("Are you sure you want to add songs?");
                switch (cli.readLine()) {
                    case "y":
                        System.out.println("Enter release id from discogs");
                        String releaseId = cli.readLine();
                        albumService.addAllSongsToAlbum(idToAddSongs, releaseId);
                        break;
                    default:
                        System.out.println("You didn't add any song.");
                        break;
                }

                break;
            case "0":
                this.setInMenu(false);
                break;
        }
    }


    private void chooseLengthType(Cli cli, Album album) {
        switch (cli.readLine()) {
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
    }

    private void chooseMedium(Cli cli, Album album) {
        switch (cli.readLine()) {
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
    }
}

