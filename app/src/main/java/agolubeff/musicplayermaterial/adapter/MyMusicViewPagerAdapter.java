package agolubeff.musicplayermaterial.adapter;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import agolubeff.musicplayermaterial.fragment.MyMusicPlaylists;
import agolubeff.musicplayermaterial.fragment.MyMusicSongList;

/**
 * Created by Andrey on 25.10.2017.
 */

public class MyMusicViewPagerAdapter extends FragmentStatePagerAdapter
{
    final static int item_count = 2;
    FloatingActionButton fab;

    public MyMusicViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        switch (position)
        {
            case 0:
                fragment = new MyMusicSongList();
                break;
            case 1:
                fragment = new MyMusicPlaylists();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        CharSequence s = null;
        switch (position)
        {
            case 0:
                s = "Song List";
                break;
            case 1:
                s = "Playlists";
                break;
        }
        return s;
    }

    @Override
    public int getCount()
    {
        return item_count;
    }
}
