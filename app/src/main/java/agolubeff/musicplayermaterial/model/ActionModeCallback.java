package agolubeff.musicplayermaterial.model;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import agolubeff.musicplayermaterial.R;
import agolubeff.musicplayermaterial.activity.MainActivity;
import agolubeff.musicplayermaterial.adapter.RecyclerAdapter;

/**
 * Created by andre on 14.03.2018.
 */

public class ActionModeCallback implements ActionMode.Callback
{
    RecyclerAdapter recycler_adapter;
    public ActionMode action_mode;
    int checked_item =0;
    MainActivity activity;

    public ActionModeCallback(RecyclerAdapter recycler_adapter, MainActivity activity)
    {
        this.recycler_adapter = recycler_adapter;
        this.activity = activity;
    }

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

    private void ShowDialog()
    {
        final List<Integer> selection = recycler_adapter.GetSelectedItems();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Select Playlist:");

        builder.setSingleChoiceItems(PlaylistManager.GetPlaylistNames(), checked_item, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                checked_item = which;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                AddSelectedTracksToPlaylist(selection, PlaylistManager.FindPlaylistByPosition(checked_item).GetId());
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

    public void AddSelectedTracksToPlaylist(List<Integer> selection, int id_playlist)
    {

        for (int i:selection)
        {
            PlaylistManager.AddTrackToPlaylist(AllTracks.FindAudioIdByPosition(i), id_playlist);
        }

        Toast.makeText(activity, "Track added to playlist", Toast.LENGTH_SHORT).show();
    }

    public void ToggleSelection(int position)
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
}
