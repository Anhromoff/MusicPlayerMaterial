/*
package agolubeff.musicplayermaterial.activity;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import agolubeff.musicplayermaterial.Globals;
import agolubeff.musicplayermaterial.MyRecyclerViewOnItemClickListener;
import agolubeff.musicplayermaterial.R;
import agolubeff.musicplayermaterial.adapter.RecyclerAdapter;
import agolubeff.musicplayermaterial.adapter.SelectAudioRecyclerAdapter;
import agolubeff.musicplayermaterial.model.AudioFile;
import agolubeff.musicplayermaterial.model.Playlist;

public class SelectAudioActivity extends AppCompatActivity
{
    RecyclerView recycler_view;
    SelectAudioRecyclerAdapter recycler_adapter;
    Toolbar toolbar;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;
    private int playlist_id;

    MyRecyclerViewOnItemClickListener audio_click = new MyRecyclerViewOnItemClickListener()
    {
        @Override
        public void OnItemClick(View v, int position)
        {
            if (actionMode == null)
            {
                //actionMode = toolbar.startActionMode(actionModeCallback);
                actionMode = startActionMode(actionModeCallback);
            }

            ToggleSelection(position);
        }

        @Override
        public void OnItemLongClick(View v, int position)
        {

        }
    };

    private void ToggleSelection(int position)
    {
        recycler_adapter.ToggleSelection(position);
        int count = recycler_adapter.GetSelectedItemCount();

        if (count == 0)
        {
            actionMode.finish();
        }
        else
        {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_audio);

        InitPlaylistId();
        InitToolbar();
        InitRecyclerView();
    }

    private void InitPlaylistId()
    {
        Intent intent = getIntent();
        playlist_id=intent.getIntExtra(Globals.PLAYLIST_ID, -1);
    }

    private void InitToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.select_audio_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select audio");
    }

    private void InitRecyclerView()
    {
        LinearLayoutManager layout_manager = new LinearLayoutManager(this);
        recycler_view = (RecyclerView) findViewById(R.id.select_audio_recycler);
        recycler_adapter = new SelectAudioRecyclerAdapter(getApplicationContext(), audio_click, playlist_id);
        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(recycler_adapter);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recycler_view.getContext(), layout_manager.getOrientation());
        mDividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recycler_view.addItemDecoration(mDividerItemDecoration);
    }

    private class ActionModeCallback implements ActionMode.Callback
    {
        //@SuppressWarnings("unused")
        //private final String TAG = ActionModeCallback.class.getSimpleName();

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu)
        {
            mode.getMenuInflater().inflate (R.menu.audio_selection_menu, menu);
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
                case R.id.add_to_playlist:
                    recycler_adapter.AddSelectedTracksToPlaylist();
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
            actionMode = null;
        }
    }
}


*/
