package com.tressler.travistressler.lyricsfurb.PlayListView;

import android.os.Bundle;
import android.util.Log;

import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.PlaylistEntity;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongDatabase;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by travistressler on 11/2/17.
 */

public class PlayListPresenter {

    private final SongDatabase songDatabase;
    private final Scheduler workerThread;
    private PlayListView view;
    private List<SongEntity> playlistSongs;
    private String playListName;
    private List<SongEntity> songList;
    private List<String> songsInPlaylist;

    @Inject
    public PlayListPresenter(SongDatabase songDatabase, Scheduler workerThread) {
        this.songDatabase = songDatabase;
        this.workerThread = workerThread;
        playlistSongs = new ArrayList<>();
        songList = new ArrayList<>();
    }

    public void attachView(PlayListView view) {
        this.view = view;
        if(view != null) {

        }
    }

    public void songsRetrieved(List<String> songsInPlaylist) {
        this.songsInPlaylist = songsInPlaylist;
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                songList.clear();
                for(String item : songsInPlaylist) {
                    SongEntity songEntity = songDatabase.songDao().getChosenSong(item);
                    songList.add(songEntity);
                }
            }
        });
        view.showPlaylistSongs(songList);
    }

    public void cellLongClicked() {
        view.hideAddSongButton();
        view.showDoneButton();
        view.showCancelButton();
    }

    public void cancelClicked() {
        songList.clear();
        view.hideCancelButton();
        view.hideDoneButton();
        view.hideExtraOptions();

        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                for(String item : songsInPlaylist) {
                    SongEntity songEntity = songDatabase.songDao().getChosenSong(item);
                    songList.add(songEntity);
                }
            }
        });
        view.showPlaylistSongs(songList);
        view.showAddSongButton();
    }

    public void doneClicked(List<SongEntity> songList) {
        List<String> songTitles = new ArrayList<>();
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                PlaylistEntity playlistEntity = songDatabase.playlistDao().getChosenPlaylist(playListName);
                for(SongEntity song : songList) {
                    songTitles.add(song.getSongTitle());
                }
                playlistEntity.setSongsInPlaylist(songTitles);
                songDatabase.playlistDao().updatePlaylist(playlistEntity);
            }
        });

        view.hideCancelButton();
        view.hideDoneButton();
        view.showSuccessToast();
        view.hideExtraOptions();
        view.showAddSongButton();
    }

    public void playListTitleRetrieved(String playListName) {
        this.playListName = playListName;
        view.setTitle(playListName);
    }

    public void addToPlaylistButtonClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("PLAYLIST", playListName);
        view.launchAddSongToPlaylistFragment(bundle);
    }
}
