package com.shestee.service;

import com.shestee.entity.Album;
import com.shestee.entity.Song;
import com.shestee.interfaces.AlbumService;
import com.shestee.interfaces.SongService;
import com.shestee.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SongServiceImpl implements SongService {
    private static SongServiceImpl instance = null;

    public SongServiceImpl() {
    }

    public static SongServiceImpl getInstance() {
        if (instance == null) {
            return new SongServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Song> getAllSongs() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<Song> result = session.createQuery("from Song", Song.class).list();

        transaction.commit();
        session.close();

        return result;
    }

    @Override
    public Song getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Song result = session.get(Song.class, id);

        transaction.commit();
        session.close();
        return result;
    }

    @Override
    public List<Song> findByTitle(String title) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Song s where s.title like concat('%',:title,'%')", Song.class);
        query.setParameter("title", title);
        List<Song> result = query.list();

        transaction.commit();
        session.close();

        return result;
    }

    @Override
    public List<Song> findByMusic(String music) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Song s where s.music like concat('%',:music,'%')", Song.class);
        query.setParameter("music", music);
        List<Song> result = query.list();

        transaction.commit();
        session.close();

        return result;
    }

    @Override
    public List<Song> findByLyrics(String lyrics) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Song s where s.lyrics like concat('%',:lyrics,'%')", Song.class);
        query.setParameter("lyrics", lyrics);
        List<Song> result = query.list();

        transaction.commit();
        session.close();

        return result;
    }

    @Override
    public void addSong(Song song) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(song);

        transaction.commit();
        session.close();
    }

    @Override
    public void removeSongById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.delete(getById(id));

        transaction.commit();
        session.close();
    }

    public String getAlbumBySong(int id) {
        Song song = getById(id);
        return "From album: " + "\"" + song.getAlbum().getTitle()+"\"" + " by " + song.getAlbum().getArtist();
    }

    public void viewSongsByTitle(String title) {
        findByTitle(title).stream()
                .map(song -> song.toString() + getAlbumBySong(song.getId()) + "\n")
                .forEach(System.out :: println);
    }

    public List<Song> findByArtist (String artist) {
        AlbumService albumService = AlbumServiceImpl.getInstance();
        List<Album> albums = albumService.findByArtist(artist);
        List<Song> resultSongs = new ArrayList<>();
        for (Album a: albums) {
            resultSongs.addAll(albumService.getSongsFromAlbum(a.getId()));
        }
        return resultSongs;
    }

    @Override
    public void viewSongs(List<Song> songs) {
        String songTitle;
        for (int i=0; i<146; i++) {
            System.out.print("-");
        }
        System.out.println("");
        System.out.printf("%7s %5s %50s %30s %50s", "SONG ID", "TRACK", "TITLE", "ARTIST", "ALBUM TITLE");
        System.out.println();
        for (int i=0; i<146; i++) {
            System.out.print("-");
        }
        System.out.println("");
        for(Song song: songs){
            songTitle = song.getTitle().length() >40 ?  song.getTitle().substring(0, 40) + "..." : song.getTitle();
            System.out.format("%7d %5s %50s %30s %50s",
                    song.getId(), song.getTrackNumber(), songTitle, song.getAlbum().getArtist(),
                    song.getAlbum().getTitle());
            System.out.println();
        }
        System.out.println("");
    }

    @Override
    public void viewSongInfoById(int id) {

    }
}
