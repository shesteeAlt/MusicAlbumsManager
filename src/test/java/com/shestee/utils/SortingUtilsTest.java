package com.shestee.utils;

import com.shestee.entity.Album;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class SortingUtilsTest {
    @Test
    void albumsShouldBeSortedByArtistNameInProperAlphabeticalOrder() {
        //given
        Album album1 = new Album();
        album1.setArtist("ZZ top");
        Album album2 = new Album();
        album2.setArtist("Metallica");
        Album album3 = new Album();
        album3.setArtist("Ścianka");
        List<Album> albums = Arrays.asList(album1, album2, album3);

        //when
        albums.sort(SortingUtils.albumComparator);

        //then
        assertThat(albums.get(1), equalTo(album3));
    }
}