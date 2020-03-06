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
                cli.print("Enter artist name: ");
                album.setArtist(cli.readLine());
                cli.print("Enter title of the album: ");
                album.setTitle(cli.readLine());

                cli.print("Enter number of source type: (1)VINYL, (2)CD, (3)CASSETTE, (4)FILES: ");
                chooseMedium(cli, album);

                cli.print("Enter number of a type of the album; (1)LP, (2)EP, (3)SINGLE, (4)OTHER: ");
                chooseLengthType(cli, album);

                cli.print("Enter genre of the album: ");
                album.setGenre(cli.readLine());
                cli.print("Enter publisher label: ");
                album.setLabel(cli.readLine());
                cli.print("Enter catalogue number of the album: ");
                album.setCatalogueNumber(cli.readLine());
                cli.print("Enter issue year of the album: ");

                try {
                    album.setYear(Integer.parseInt(cli.readLine()));
                } catch (NumberFormatException e) {
                    album.setYear(0);
                    cli.println("Wrong year format. Year set to 0.");
                }
                cli.print("Do you want to add following album? (y)es/(n)o: ");
                cli.println(album.toString());

                if ("y".equals(cli.readLine())) {
                    albumService.addAlbum(album);
                } else {
                    cli.println("The album was not added");
                }
                break;
            case "2":
                cli.println("Enter Discogs release id of album you want to be added:");
                String discogsReleaseId = cli.readLine();
                try {
                    AlbumJsonParser albumJsonParser = new AlbumJsonParser();
                    Album albumToAdd = albumJsonParser.parseAlbumFromAlbumJson(discogsReleaseId);
                    cli.print("Do you want to add following album? (y)es/(n)o: ");
                    cli.println(albumToAdd.toString());
                    if ("y".equals(cli.readLine())) {
                        albumService.addAlbum(albumToAdd);
                        albumToAdd.getId();
                        albumService.addAllSongsToAlbum(albumToAdd.getId(), discogsReleaseId);
                        cli.println("The album was added");
                    } else {
                        cli.println("The album was not added");
                    }
                }   catch (JSONException e) {
                    e.printStackTrace();
                    cli.println("Wrong release_id");
                }

                break;

            case "3":
                cli.print("Enter id number of the album you want to be removed: ");
                int idToRemove;
                try {
                    idToRemove = Integer.parseInt(cli.readLine());
                    cli.println("The album you want to be removed is:");
                    cli.println(albumService.findById(idToRemove).toString());
                    cli.print("Are you sure? (y)es/(n)o: ");
                    if ("y".equals(cli.readLine())) {
                        albumService.removeAlbum(idToRemove);
                    } else {
                        cli.println("The album was not deleted");
                    }
                } catch (NumberFormatException e) {
                    cli.println("Enter proper id number");
                } catch (NullPointerException e) {
                    cli.println("Provided id doesn't exist");
                }
                break;
            case "4":
                cli.println("Enter id number of the album you want to add songs to: ");
                int idToAddSongs = Integer.parseInt(cli.readLine());
                Album tempAlbum = albumService.findById(idToAddSongs);
                cli.println("You want to add songs to album:");
                cli.println(tempAlbum.toString());
                if (albumService.getSongsFromAlbum(idToAddSongs).size() == 0) {
                    cli.println("The album doesn't contain any songs");
                } else if (albumService.getSongsFromAlbum(idToAddSongs).size() >0) {
                    cli.println(("The album already contains songs: "));
                    songService.viewSongs(albumService.getSongsFromAlbum(idToAddSongs));
                }
                cli.println("Are you sure you want to add songs?");
                switch (cli.readLine()) {
                    case "y":
                        cli.println("Enter release id from discogs");
                        String releaseId = cli.readLine();
                        albumService.addAllSongsToAlbum(idToAddSongs, releaseId);
                        break;
                    default:
                        cli.println("You didn't add any song.");
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