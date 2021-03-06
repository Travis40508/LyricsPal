package com.tressler.travistressler.lyricspal.LyricsView;

import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.SongEntity;

import java.util.List;

/**
 * Created by travistressler on 11/7/17.
 */

public interface LyricsView {
    void showLyricsForPlaylist(List<SongEntity> lyricsList, int position);

    void showPlaylistTitle(String playListName);

    void setPagePosition();
}
