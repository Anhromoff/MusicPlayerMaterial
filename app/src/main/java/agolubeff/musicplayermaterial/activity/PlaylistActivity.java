package agolubeff.musicplayermaterial.activity;


import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import java.util.List;

import agolubeff.musicplayermaterial.Globals;

import agolubeff.musicplayermaterial.MyRecyclerViewOnItemClickListener;
import agolubeff.musicplayermaterial.R;

import agolubeff.musicplayermaterial.adapter.RecyclerAdapter;
import agolubeff.musicplayermaterial.model.AllTracks;
import agolubeff.musicplayermaterial.model.AudioFile;
import agolubeff.musicplayermaterial.model.Playlist;
import agolubeff.musicplayermaterial.model.PlaylistManager;
import agolubeff.musicplayermaterial.service.AudioPlayerService;

public class PlaylistActivity extends AppCompatActivity
{

    int id_playlist;
    RecyclerView recycler_view;
    RecyclerAdapter recycler_adapter;
    Toolbar toolbar;
    FloatingActionButton fab_shuffle_playlist;

    private ActionModeCallback action_mode_callback = new ActionModeCallback();
    private ActionMode action_mode;

    MyRecyclerViewOnItemClickListener audio_click = new MyRecyclerViewOnItemClickListener()
    {
        @Override
        public void OnItemClick(View v, int position)
        {
            recycler_adapter.ClickAudio(position);
            Integer id_new_audio = PlaylistManager.FindAudioIdByPosition(position, id_playlist);

            if(action_mode == null)
            {
                if(AllTracks.IsPlaying(id_new_audio))
                {
                    AudioPlayerService.Pause();
                }
                else
                {
                    if(AllTracks.IsStarted())
                    {
                        if(id_new_audio == AllTracks.GetCurrentAudioId())
                        {
                            AudioPlayerService.Resume();
                        }
                        else
                        {
                            AudioPlayerService.PlayNewAudio(id_new_audio);
                        }
                    }
                    else
                    {
                        AudioPlayerService.PlayNewAudio(id_new_audio);
                    }
                    startActivity(new Intent(PlaylistActivity.this, SongFullscreen.class).putExtra(Globals.PLAYLIST_ID, id_playlist));
                }
            }
            else ToggleSelection(position);

        }

        @Override
        public void OnItemLongClick(View v, int position)
        {
            if (action_mode == null)
            {
                action_mode = startActionMode(action_mode_callback);
                ToggleSelection(position);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.select_audio_activity_toolbar);
        setSupportActionBar(toolbar);

        InitPlaylistId();
        InitToolbar();
        InitRecyclerView();
        InitFab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.playlist_toolbar__menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.add_tracks:
                //Intent intent = new Intent(PlaylistActivity.this, SelectAudioActivity.class);
                //intent.putExtra(Globals.PLAYLIST_ID, id_playlist);
                //startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        recycler_adapter.notifyDataSetChanged();
    }

    private void InitPlaylistId()
    {
        Intent intent = getIntent();
        id_playlist = intent.getIntExtra(Globals.PLAYLIST_ID, -1);
    }

    private void InitRecyclerView()
    {
        LinearLayoutManager layout_manager = new LinearLayoutManager(this);
        recycler_view = (RecyclerView) findViewById(R.id.playlist_recycler);
        recycler_adapter = new RecyclerAdapter(this, id_playlist, audio_click);
        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(recycler_adapter);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recycler_view.getContext(), layout_manager.getOrientation());
        mDividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recycler_view.addItemDecoration(mDividerItemDecoration);
    }

    private void InitToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.playlist_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(PlaylistManager.FindPlaylistById(id_playlist).GetName());
    }

    private void ToggleSelection(int position)
    {
        recycler_adapter.ToggleSelection(position);
        int count = recycler_adapter.GetSelectedItemCount();

        if (count == 0)
        {
            action_mode.finish();
        }
        else
        {
            action_mode.setTitle(String.valueOf(count));
            action_mode.invalidate();
        }
    }

    private void InitFab()
    {
        fab_shuffle_playlist = (FloatingActionButton)findViewById(R.id.fab_shuffle_playlist);

    }

    private void ShowDialog()
    {
        final List<Integer> selection = recycler_adapter.GetSelectedItems();
        final Playlist playlist = PlaylistManager.FindPlaylistById(id_playlist);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete selected tracks from " + playlist.GetName() + "?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                for (int i:selection)
                {
                    playlist.DeleteTrack(playlist.FindAudioIdByPosition(i));
                }

                recycler_adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private class ActionModeCallback implements ActionMode.Callback
    {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu)
        {
            mode.getMenuInflater().inflate (R.menu.audio_in_playlist_selection_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu)
        {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.delete_from_playlist:
                    ShowDialog();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode)
        {
            recycler_adapter.ClearSelection();
            action_mode = null;
        }
    }

}
