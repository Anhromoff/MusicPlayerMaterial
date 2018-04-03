package agolubeff.musicplayermaterial.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import agolubeff.musicplayermaterial.Globals;
import agolubeff.musicplayermaterial.MyRecyclerViewOnItemClickListener;
import agolubeff.musicplayermaterial.R;
import agolubeff.musicplayermaterial.activity.MainActivity;
import agolubeff.musicplayermaterial.activity.PlaylistActivity;
import agolubeff.musicplayermaterial.fragment.MyMusicPlaylists;
import agolubeff.musicplayermaterial.model.AudioFile;
import agolubeff.musicplayermaterial.model.Playlist;
import agolubeff.musicplayermaterial.model.PlaylistManager;
import agolubeff.musicplayermaterial.service.AudioPlayerService;

/**
 * Created by andre on 07.01.2018.
 */

public class PlaylistsRecyclerAdapter extends RecyclerView.Adapter<PlaylistsViewHolder>
{
    Context context;

    MyRecyclerViewOnItemClickListener playlist_click = new MyRecyclerViewOnItemClickListener()
    {
        @Override
        public void OnItemClick(View v, int position)
        {
            Intent intent = new Intent(context, PlaylistActivity.class);
            intent.putExtra(Globals.PLAYLIST_ID, PlaylistManager.FindPlaylistByPosition(position).GetId());
            context.startActivity(intent);
        }

        @Override
        public void OnItemLongClick(View v, int position)
        {

        }
    };

    public PlaylistsRecyclerAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public PlaylistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlists_recycler_item, parent, false);
        final PlaylistsViewHolder holder = new PlaylistsViewHolder(v);

        v.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                playlist_click.OnItemClick(v, holder.getAdapterPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(final PlaylistsViewHolder holder, final int position)
    {
        final String name = PlaylistManager.FindPlaylistByPosition(position).GetName();

        holder.playlist_name.setText(name);
        holder.playlist_edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PopupMenu popup = new PopupMenu(context,v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        int id = item.getItemId();
                        switch (id)
                        {
                            case R.id.rename:
                                ShowDialogRenamePlaylist(name, position);
                                break;
                            case R.id.change_image:
                                break;
                            case R.id.delete:
                                ShowDialogDeletePlaylist(position);
                                break;
                        }
                        return false;
                    }
                });
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.playlist_edit_menu, popup.getMenu());
                popup.show();
            }
        });
    }

    private void ShowDialogRenamePlaylist(final String old_name, final int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enter playlist name:");

        final EditText input = new EditText(context);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                MyMusicPlaylists.EditPlaylistName(input.getText().toString(), position);
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

    private void ShowDialogDeletePlaylist(final int position)
    {
        final Playlist playlist = PlaylistManager.FindPlaylistByPosition(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete playlist " + playlist.GetName() + "?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                PlaylistManager.DeletePlaylist(playlist);
                notifyDataSetChanged();
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

    @Override
    public int getItemCount()
    {
        return PlaylistManager.Size();
    }

}

class PlaylistsViewHolder extends RecyclerView.ViewHolder
{
    TextView playlist_name;
    ImageButton playlist_edit;

    PlaylistsViewHolder(View item)
    {
        super(item);

        playlist_edit = (ImageButton) item.findViewById(R.id.playlist_edit);
        playlist_name = (TextView) item.findViewById(R.id.playlist_name);
    }
}
