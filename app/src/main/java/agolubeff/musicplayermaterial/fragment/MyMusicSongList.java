package agolubeff.musicplayermaterial.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import agolubeff.musicplayermaterial.Globals;
import agolubeff.musicplayermaterial.MyRecyclerViewOnItemClickListener;
import agolubeff.musicplayermaterial.R;
import agolubeff.musicplayermaterial.activity.MainActivity;
import agolubeff.musicplayermaterial.adapter.RecyclerAdapter;
import agolubeff.musicplayermaterial.model.ActionModeCallback;
import agolubeff.musicplayermaterial.model.AllTracks;
import agolubeff.musicplayermaterial.model.AudioFile;
import agolubeff.musicplayermaterial.model.PlaylistManager;
import agolubeff.musicplayermaterial.service.AudioPlayerService;

/**
 * Created by Andrey on 25.10.2017.
 */

public class MyMusicSongList extends Fragment
{
    RecyclerView recycler_view;
    MainActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        activity = (MainActivity) getActivity();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        //if(AllTracks.GetPreviousAudioId() != null) activity.recycler_adapter.notifyItemChanged(AllTracks.GetPreviousAudioPosition());
        //activity.recycler_adapter.notifyItemChanged(AllTracks.GetCurrentAudioPosition());
        activity.recycler_adapter.ClickAudio(AllTracks.GetCurrentAudioPosition());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_my_music_song_list, null);

        LinearLayoutManager layout_manager = new LinearLayoutManager(activity);
        recycler_view = (RecyclerView) view.findViewById(R.id.song_list_recycler_view);
        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(activity.recycler_adapter);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recycler_view.getContext(), layout_manager.getOrientation());
        mDividerItemDecoration.setDrawable(ContextCompat.getDrawable(activity, R.drawable.divider));
        recycler_view.addItemDecoration(mDividerItemDecoration);

        return view;
    }
}
