package agolubeff.musicplayermaterial.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import agolubeff.musicplayermaterial.R;
import agolubeff.musicplayermaterial.adapter.MyMusicViewPagerAdapter;

public class MyMusic extends Fragment
{
    private ViewPager view_pager;
    TabLayout tab_layout;
    private Toolbar toolbar;
    FloatingActionButton fab_shuffle;
    FloatingActionButton fab_add_playlist;

    public static MyMusic NewInstance()
    {
        MyMusic fragment = new MyMusic();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_my_music, container, false);

        view_pager = (ViewPager)view.findViewById(R.id.my_music_view_pager);
        view_pager.setAdapter(new MyMusicViewPagerAdapter(getActivity().getSupportFragmentManager()));

        fab_shuffle = (FloatingActionButton) view.findViewById(R.id.fab_shuffle);
        fab_add_playlist = (FloatingActionButton) view.findViewById(R.id.fab_add_playlist);
        fab_add_playlist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Enter playlist name:");

                final EditText input = new EditText(getContext());

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        MyMusicPlaylists.AddNewPlaylist(input.getText().toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        tab_layout = (TabLayout) view.findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(view_pager);
        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                AnimateFab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        toolbar = (Toolbar) view.findViewById(R.id.my_music_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My music");

        return view;
    }

    private void AnimateFab(int position)
    {
        switch (position)
        {
            case 0:
                fab_shuffle.show();
                fab_add_playlist.hide();
                break;
            case 1:
                fab_add_playlist.show();
                fab_shuffle.hide();
                break;

            default:
                fab_shuffle.show();
                fab_add_playlist.hide();
                break;
        }
    }
}
