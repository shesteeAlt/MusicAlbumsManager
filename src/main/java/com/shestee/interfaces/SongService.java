package com.shestee.interfaces;

import com.shestee.entity.Song;
import java.util.List;

public interface SongService {
    public List<Song> getAllSongs();

    public Song getById(int id);

    public List<Song> findByTitle(String title);

    public List<Song> findByMusic(String music);

    public List<Song> findByLyrics(String lyrics);

    public void addSong(Song song);

    public void removeSongById(int id);

    public String getAlbumBySong(int id);

    public void viewSongsByTitle(String title);

    public List<Song> findByArtist(String artist);

    public void viewSongs(List<Song> songs);

    public void viewSongInfoById(int id);

    //public List<Song> findSongsByArtist(String artist);
}