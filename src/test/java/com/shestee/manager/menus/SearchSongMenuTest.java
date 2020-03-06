package com.shestee.manager.menus;

import com.shestee.CliUtil;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class SearchSongMenuTest {

    @Test
    void shouldAskForSongTitleWhenSearchingSongByTitle() {
        //given
        SearchSongMenu searchSongMenu = new SearchSongMenu();
        CliUtil cli = CliUtil.withInput("1", "some title");

        //when
        searchSongMenu.chooseOption(cli);

        //then
        assertThat(cli.getOutputs().get(0), containsString("Enter title of the song"));

    }
}