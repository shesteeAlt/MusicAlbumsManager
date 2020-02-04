package com.shestee.utils;

import com.shestee.entity.Album;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class SortingUtils {
    public static Comparator<Album> albumComparator = new Comparator<Album>() {
        Locale locale = Locale.getDefault();
        @Override
        public int compare(Album o1, Album o2) {
            Collator c = Collator.getInstance(locale);
            return c.compare(o1.getArtist(), o2.getArtist());
        }
    };
}
