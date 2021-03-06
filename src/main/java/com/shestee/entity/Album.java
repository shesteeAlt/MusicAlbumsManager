package com.shestee.entity;

import com.shestee.entity.enums.LengthType;
import com.shestee.entity.enums.Medium;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    private String artist;
    private String title;

    @Column(name = "medium", columnDefinition = "enum")

    @Enumerated(EnumType.STRING)
    private Medium medium;

    @Column(name = "length_type", columnDefinition = "enum")
    @Enumerated(EnumType.STRING)
    private LengthType lengthType;

    private String genre;
    private String label;

    @Column(name = "catalogue")
    private String catalogueNumber;

    @Column(name = "year")
    private int year;

    @Column(name = "own_id")
    private int ownId;

    //add getting data with foreign key; added also getters and setters
    @OneToMany(mappedBy = "albums")
    private Set<Album> albums;


    public Album() {

    }

    public Album(int id, String artist, String albumTitle, Medium medium, LengthType lengthType,
                 String genre, String label, String catalogueNumber, int year, int ownId) {
        this.id = id;
        this.artist = artist;
        this.title = albumTitle;
        this.medium = medium;
        this.lengthType = lengthType;
        this.genre = genre;
        this.label = label;
        this.catalogueNumber = catalogueNumber;
        this.year = year;
        this.ownId = ownId;
    }

    public Album(String artist, String albumTitle, Medium medium, LengthType lengthType,
                 String genre, String label, String catalogueNumber, int year) {
        this.artist = artist;
        this.title = albumTitle;
        this.medium = medium;
        this.lengthType = lengthType;
        this.genre = genre;
        this.label = label;
        this.catalogueNumber = catalogueNumber;
        this.year = year;
    }

    public Album(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Album setId(int id) {
        this.id = id;
        return this;
    }

    public String getArtist() {
        return artist;
    }

    public Album setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Album setTitle(String title) {
        this.title = title;
        return this;
    }

    public Medium getMedium() {
        return medium;
    }

    @Column(name="medium")
    public Album setMedium(Medium medium) {
        this.medium = medium;
        return this;
    }

    public LengthType getLengthType() {
        return lengthType;
    }

    public Album setLengthType(LengthType lengthType) {
        this.lengthType = lengthType;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public Album setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Album setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getCatalogueNumber() {
        return catalogueNumber;
    }

    public Album setCatalogueNumber(String catalogueNumber) {
        this.catalogueNumber = catalogueNumber;
        return this;
    }

    public int getYear() {
        return year;
    }

    public Album setYear(int year) {
        this.year = year;
        return this;
    }

    public int getOwnId() {
        return ownId;
    }

    public Album setOwnId(int ownId) {
        this.ownId = ownId;
        return this;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public Album setAlbums(Set<Album> albums) {
        this.albums = albums;
        return this;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", medium=" + medium +
                ", lengthType=" + lengthType +
                ", genre='" + genre + '\'' +
                ", label='" + label + '\'' +
                ", catalogueNumber='" + catalogueNumber + '\'' +
                ", year=" + year +
                ", ownId=" + ownId +
                '}';
    }
}
