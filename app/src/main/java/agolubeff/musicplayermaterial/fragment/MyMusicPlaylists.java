package agolubeff.musicplayermaterial.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import agolubeff.musicplayermaterial.R;
import agolubeff.musicplayermaterial.activity.MainActivity;
import agolubeff.musicplayermaterial.adapter.PlaylistsRecyclerAdapter;
import agolubeff.musicplayermaterial.adapter.RecyclerAdapter;
import agolubeff.musicplayermaterial.model.Playlist;
import agolubeff.musicplayermaterial.model.PlaylistManager;
import agolubeff.musicplayermaterial.service.AudioPlayerService;

/**
 * Created by Andrey on 26.10.2017.
 */

public class MyMusicPlaylists extends Fragment
{
    RecyclerView recycler_view;
    static PlaylistsRecyclerAdapter playlists_recycler_adapter;
    MainActivity activity;
    Button button_add_new_playlist;

    View.OnClickListener show_dialog = new View.OnClickListener()
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

                    AddNewPlaylist(input.getText().toString());
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
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        PlaylistManager.LoadPlaylistsFromDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_my_music_playlists, null);

        LinearLayoutManager layout_manager = new LinearLayoutManager(activity);
        recycler_view = (RecyclerView) view.findViewById(R.id.playlists_recycler_view);
        playlists_recycler_adapter = new PlaylistsRecyclerAdapter(getContext());
        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(playlists_recycler_adapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recycler_view.getContext(), layout_manager.getOrientation());
        mDividerItemDecoration.setDrawable(ContextCompat.getDrawable(activity, R.drawable.divider));
        recycler_view.addItemDecoration(mDividerItemDecoration);

        //button_add_new_playlist = (Button)view.findViewById(R.id.button_add_playlist);
        //button_add_new_playlist.setOnClickListener(show_dialog);

        return view;
    }

    public static void AddNewPlaylist(String name)
    {
        PlaylistManager.AddNewPlaylist(name);
        playlists_recycler_adapter.notifyDataSetChanged();
    }

    public static void EditPlaylistName(String new_name, int position)
    {
        PlaylistManager.EditPlaylistName(new_name, position);
        playlists_recycler_adapter.notifyItemChanged(position);
    }
}
