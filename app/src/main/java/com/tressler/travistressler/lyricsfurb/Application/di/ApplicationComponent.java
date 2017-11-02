package com.tressler.travistressler.lyricsfurb.Application.di;

import com.tressler.travistressler.lyricsfurb.AddSongView.AddSongFragment;
import com.tressler.travistressler.lyricsfurb.AllSongsView.AllSongsFragment;
import com.tressler.travistressler.lyricsfurb.CreatePlayListView.CreatePlayListFragment;
import com.tressler.travistressler.lyricsfurb.MainView.MainActivity;
import com.tressler.travistressler.lyricsfurb.PlayListView.PlayListFragment;
import com.tressler.travistressler.lyricsfurb.PlaylistsView.PlayListsFragment;
import com.tressler.travistressler.lyricsfurb.SplashView.SplashActivity;

import dagger.Component;

/**
 * Created by travistressler on 11/2/17.
 */

@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(PlayListsFragment playListsFragment);

    void inject(AllSongsFragment allSongsFragment);

    void inject(AddSongFragment addSongFragment);

    void inject(PlayListFragment playListFragment);

    void inject(CreatePlayListFragment createPlayListFragment);
}
