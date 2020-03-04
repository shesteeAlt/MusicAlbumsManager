package com.shestee.service;

import com.shestee.entity.Album;
import com.shestee.entity.Song;
import com.shestee.entity.enums.LengthType;
import com.shestee.entity.enums.Medium;
import com.shestee.interfaces.AlbumService;
import com.shestee.parsers.AlbumJsonParser;
import com.shestee.utils.HibernateUtil;
import com.shestee.utils.JsonUtil;
import com.shestee.utils.SortingUtils;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;



public class AlbumServiceImpl implements AlbumService {
    private static AlbumServiceImpl instance = null;

    public AlbumServiceImpl() {

    }

    public static AlbumServiceImpl getInstance() {
        if (instance == null) {
            instance = new AlbumServiceImpl();
        }
        return instance;
    }


    public List<Album> getAllAlbums() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<Album> result = session.createQuery("from Album", Album.class).list();
        Collections.sort(result, SortingUtils.albumComparator);

        transaction.commit();
        session.close();

        return result;
    }

    public List<Album> findByArtist(String artist) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Album a where a.artist like concat('%',:artist,'%')", Album.class);
        query.setParameter("artist", artist);
        List<Album> result = query.list();

        transaction.commit();
        session.close();

        return result;
    }

    public Album findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Album result = session.get(Album.class, id);

        transaction.commit();
        session.close();
        return result;
    }

    //Used by AlbumDao for getting songs from XCL sheet
    public int getIdByOwnId(int ownId) {
        Album album = getAllAlbums().stream()
                        .filter(a -> a.getOwnId() == ownId)
                        .findFirst().get();
        return album.getId();
    }

    @Override
    public List<Album> findByTitle(String title) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Album a where a.title like concat('%',:title,'%')", Album.class);
        query.setParameter("title", title);
        List<Album> result = query.list();

        transaction.commit();
        session.close();

        return result;
    }

    @Override
    public List<Album> findByGenre(String genre) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Album a where a.genre like concat('%',:genre,'%')", Album.class);
        query.setParameter("genre", genre);
        List<Album> result = query.list();

        transaction.commit();
        session.close();

        return result;
    }

    @Override
    public List<Album> findByYear(int year) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Album a where a.year=:year", Album.class);
        query.setParameter("year", year);
        List<Album> result = query.list();

        transaction.commit();
        session.close();

        return result;
    }

    @Override
    public Album findByCatalogueNumber(String catalogue) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Album a where a.catalogue=:catalogue", Album.class);
        query.setParameter("catalogue", catalogue);
        Album result = (Album)query.getSingleResult();

        transaction.commit();
        session.close();

        return result;
    }

    @Override
    public List<Album> findByMedium(Medium medium) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Album a where a.medium=:medium", Album.class);
        query.setParameter("medium", medium);
        List<Album> result = query.getResultList();

        transaction.commit();
        session.close();

        return result;
    }

    @Override
    public List<Album> findByLengthType(LengthType lengthType) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Album a where a.lengthType=:lengthType", Album.class);
        query.setParameter("lengthType", lengthType);
        List<Album> result = query.getResultList();

        transaction.commit();
        session.close();

        return result;
    }


    @Override
    public void addAlbum(Album album) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(album);

        transaction.commit();
        session.close();
    }

    @Override
    public List<Song> getSongsFromAlbum(int id) {
        Album album = findById(id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("Select s from Song s where s.album.id = :id");
        query.setParameter("id", id);
        List<Song> songs = query.getResultList();

        transaction.commit();
        session.close();

        return songs;
    }

    @Override
    public void removeAlbum(int id) {
        Album album = findById(id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("delete Song where album.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.delete(album);

        transaction.commit();
        session.close();
    }

    public void viewAlbums(List<Album> albums) {
        for (int i=0; i<139; i++) {
            System.out.print("-");
        }
        System.out.println("");
        System.out.printf("%5s %30s %40s %20s %12s %15s %8s", "ID", "ARTIST", "TITLE", "GENRE", "FORMAT", "CATALOGUE No.", "YEAR");
        System.out.println();
        for (int i=0; i<139; i++) {
            System.out.print("-");
        }
        System.out.println("");

        for(Album album: albums){
            System.out.format("%5d %30s %40s %20s %12s %15s %8d",
                    album.getId(),
                    album.getArtist().length() >30 ? album.getArtist().substring(0, 26) + "..." : album.getArtist(),
                    album.getTitle().length() >40 ? album.getTitle().substring(0, 36) + "..." : album.getTitle(),
                    album.getGenre().length() >20 ? album.getGenre().substring(0, 16) + "..." : album.getGenre(),
                    album.getMedium().toString(),
                    album.getCatalogueNumber(),
                    album.getYear());
            System.out.println();
        }
        System.out.println("");
    }

    @Override
    public void addAllSongsToAlbum(int albumId, String releaseID) {
        SongServiceImpl songService = SongServiceImpl.getInstance();
        JsonUtil jsonUtil = new JsonUtil();

        List<Song> songs = AlbumJsonParser.parseSongsfromAlbumJson(jsonUtil.getAlbumJson(releaseID));
        for (Song song: songs) {
            song.setAlbumId(albumId);
            songService.addSong(song);
        }
    }
}
