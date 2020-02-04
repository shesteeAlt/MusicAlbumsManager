package com.shestee.interfaces;

import com.shestee.entity.Album;
import com.shestee.entity.Song;
import com.shestee.entity.enums.LengthType;
import com.shestee.entity.enums.Medium;

import java.util.List;

public interface AlbumService {
    public List<Album> getAllAlbums();

    public List<Album> findByArtist(String artist);

    public Album findById(int id);

    public List<Album> findByTitle(String title);

    public List<Album> findByYear(int year);

    public Album findByCatalogueNumber(String catalogue);

    public List<Album> findByMedium(Medium medium);

    public List<Album> findByLengthType(LengthType lengthType);

    public List<Album> findByGenre(String genre);

    public void addAlbum(Album album);

    public int getIdByOwnId(int ownId);

    public List<Song> getSongsFromAlbum(int id);

    public void removeAlbum(int id);

    public void viewAlbums(List<Album> albums);
}
