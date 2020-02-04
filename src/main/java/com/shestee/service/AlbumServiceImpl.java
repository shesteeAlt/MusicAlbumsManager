package com.shestee.service;

import com.shestee.entity.Album;
import com.shestee.entity.Song;
import com.shestee.entity.enums.LengthType;
import com.shestee.entity.enums.Medium;
import com.shestee.interfaces.AlbumService;
import com.shestee.utils.HibernateUtil;
import com.shestee.utils.SortingUtils;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;



public class AlbumServiceImpl implements AlbumService {
    private static AlbumServiceImpl instance = null;

    private AlbumServiceImpl() {

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

    /*@Override
    void removeAlbumById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.delete(findById(id));

        transaction.commit();
        session.close();
    }*/

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
    public void deleteSongsFromAlbum(int id) {
        Album album = findById(id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("delete Song where album.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.delete(album);

        transaction.commit();
        session.close();

        //removeAlbumById(id);
    }

    public void viewAlbums(List<Album> albums) {
        //1
        //String albumTitle;
        for (int i=0; i<125; i++) {
            System.out.print("-");
        }
        System.out.println("");
        System.out.printf("%5s %30s %40s %20s %15s %8s", "ID", "ARTIST", "TITLE", "GENRE", "CATALOGUE No.", "YEAR");
        System.out.println();
        for (int i=0; i<125; i++) {
            System.out.print("-");
        }
        System.out.println("");

        for(Album album: albums){
            //albumTitle = album.getTitle().length() >40 ?  album.getTitle().substring(0, 36) + "..." : album.getTitle();
            System.out.format("%5d %30s %40s %20s %15s %8d",
                    album.getId(),
                    album.getArtist(),
                    album.getTitle().length() >40 ? album.getTitle().substring(0, 36) + "..." : album.getTitle(),
                    album.getGenre().length() >20 ? album.getGenre().substring(0, 16) + "..." : album.getGenre(),
                    album.getCatalogueNumber(),
                    album.getYear());
            System.out.println();
        }
        System.out.println("");
    }

    /*public boolean deleteById(Serializable id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Object persistentInstance = session.load(Album.class, id);
        if (persistentInstance != null) {
            session.delete(persistentInstance);
            transaction.commit();
            session.close();
            return true;
        }
        transaction.commit();
        session.close();
        return false;
    }*/
}
