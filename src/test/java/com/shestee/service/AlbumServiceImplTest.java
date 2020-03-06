package com.shestee.service;

import com.shestee.CliUtil;
import com.shestee.entity.Album;
import com.shestee.interfaces.AlbumService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class AlbumServiceImplTest {

    @Test
    void shouldPrintNoAlbumMessageWhenAlbumsListIsEmpty() {
        //given
        CliUtil cliUtil = new CliUtil();
        AlbumService albumService = new AlbumServiceImpl();
        List<Album> albums = new ArrayList<>();

        //when
        albumService.viewAlbums(cliUtil, albums);

        //then
        assertThat(cliUtil.getOutputs().get(cliUtil.getOutputs().size()-1), is("There is no album to view"));
    }
}