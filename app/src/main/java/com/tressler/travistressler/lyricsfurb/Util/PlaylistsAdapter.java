package com.tressler.travistressler.lyricsfurb.Util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tressler.travistressler.lyricsfurb.R;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.PlaylistEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by travistressler on 11/6/17.
 */

public class PlaylistsAdapter extends RecyclerView.Adapter<PlaylistsAdapter.PlaylistViewHolder> {

    private List<PlaylistEntity> playListsList;

    public PlaylistsAdapter(List<PlaylistEntity> playListsList) {
        this.playListsList = playListsList;
    }

    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
        return new PlaylistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlaylistViewHolder holder, int position) {
        holder.onBind(playListsList.get(position));
    }

    @Override
    public int getItemCount() {
        return playListsList.size();
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_play_list_title)
        protected TextView playListTitle;


        public PlaylistViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(PlaylistEntity playlistEntity) {
            playListTitle.setText(playlistEntity.getPlayListName());
        }
    }
}