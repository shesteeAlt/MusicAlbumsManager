package com.shestee.interfaces;

import com.shestee.entity.Album;
import com.shestee.entity.Song;
import com.shestee.entity.enums.LengthType;
import com.shestee.entity.enums.Medium;

import java.util.List;

public interface AlbumService {
    List<Album> getAllAlbums();

    List<Album> findByArtist(String artist);

    Album findById(int id);

    List<Album> findByTitle(String title);

    List<Album> findByYear(int year);

    Album findByCatalogueNumber(String catalogue);

    List<Album> findByMedium(Medium medium);

    List<Album> findByLengthType(LengthType lengthType);

    List<Album> findByGenre(String genre);

    void addAlbum(Album album);

    int getIdByOwnId(int ownId);

    List<Song> getSongsFromAlbum(int id);

    void removeAlbum(int id);

    void viewAlbums(Cli cli, List<Album> albums);

    void addAllSongsToAlbum(int albumId, String releaseID);
}
