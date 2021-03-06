package com.tressler.travistressler.lyricspal.AddToPlaylistView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tressler.travistressler.lyricspal.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricspal.R;
import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.SongEntity;
import com.tressler.travistressler.lyricspal.Util.SongListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by travistressler on 11/6/17.
 */

public class AddToPlaylistFragment extends Fragment implements AddToPlaylistView, SongListAdapter.Callback {

    @Inject AddToPlaylistPresenter presenter;

    @BindView(R.id.text_play_list_title)
    protected TextView playListTitle;

    @BindView(R.id.recycler_view_add_to_play_list_play_all_songs)
    protected RecyclerView recyclerViewAllSongs;

    @BindView(R.id.recycler_view_add_to_play_list_play_list_songs)
    protected RecyclerView recyclerViewPlaylistSongs;

    private SongListAdapter adapter;
    private SongListAdapter otherSongsAdapter;

    @OnClick(R.id.button_save_new_playlist)
    protected void onSaveNewPlaylistClicked(View view) {
        presenter.saveNewPlaylist();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_to_playlist, container, false);
        ButterKnife.bind(this, view);
        ((LyricsApplication)getActivity().getApplication()).getComponent().inject(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
        String playlistTitle = getArguments().getString("PLAYLIST");
        presenter.retrievePlaylistTitle(playlistTitle);
        presenter.getLists();
    }

    public static AddToPlaylistFragment newInstance() {

        Bundle args = new Bundle();

        AddToPlaylistFragment fragment = new AddToPlaylistFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onChosenSongCellClicked(SongEntity songEntity) {
        presenter.chosenSongCellClicked(songEntity);
        otherSongsAdapter.addSong(songEntity);
    }

    @Override
    public void onAllSongCellClicked(SongEntity songEntity) {
        presenter.allSongCellClicked(songEntity);
        adapter.addSong(songEntity);
    }

    @Override
    public void onCellLongClicked() {
        //Implemented with Adapter interface, in case schema ever changes.
    }

    @Override
    public void deleteClicked(SongEntity songEntity) {
        //Implemented with Adapter interface, in case schema ever changes.
    }

    @Override
    public void setTitleText(String playListTitleText) {
        playListTitle.setText(playListTitleText);
    }

    @Override
    public void displayPlaylistSongs(List<SongEntity> songList) {
        adapter = new SongListAdapter(songList, this, "addToPlaylistPlaylist");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewPlaylistSongs.setAdapter(adapter);
        recyclerViewPlaylistSongs.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayOtherListSongs(List<SongEntity> otherSongList) {
        otherSongsAdapter = new SongListAdapter(otherSongList, this, "addToPlaylistOtherSongs");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewAllSongs.setAdapter(otherSongsAdapter);
        recyclerViewAllSongs.setLayoutManager(linearLayoutManager);
        otherSongsAdapter.notifyDataSetChanged();
    }

    @Override
    public void toastSuccess() {
        Toast.makeText(getContext(), "Playlist Updated!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void detachFragment(List<String> newSongs) {
        getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_holder_playlists).onResume();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_holder_playlist)).commit();
    }
}
