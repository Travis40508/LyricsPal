package com.tressler.travistressler.lyricspal.Repository.lyricsdatabase;

/**
 * Created by travistressler on 11/4/17.
 */

public interface SongDatabase {

    SongDao songDao();
    PlaylistDao playlistDao();
}
