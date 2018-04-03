/*
package agolubeff.musicplayermaterial.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import agolubeff.musicplayermaterial.Globals;
import agolubeff.musicplayermaterial.MyRecyclerViewOnItemClickListener;
import agolubeff.musicplayermaterial.R;
import agolubeff.musicplayermaterial.fragment.MyMusicPlaylists;
import agolubeff.musicplayermaterial.model.AudioFile;
import agolubeff.musicplayermaterial.model.Playlist;
import agolubeff.musicplayermaterial.model.PlaylistManager;
import agolubeff.musicplayermaterial.service.AudioPlayerService;

*/
/**
 * Created by andre on 02.02.2018.
 *//*


public class PlaylistRecyclerAdapter extends RecyclerView.Adapter<PlaylistViewHolder>
{
    int id_playlist;
    Context context;
    MyRecyclerViewOnItemClickListener listener;

    public PlaylistRecyclerAdapter(Context context, MyRecyclerViewOnItemClickListener listener, int id_playlist)
    {
        this.context = context;
        this.listener = listener;
        this.id_playlist = id_playlist;
    }

    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_recycler_item, parent, false);
        final PlaylistViewHolder holder = new PlaylistViewHolder(v);

        v.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.OnItemClick(v, holder.getAdapterPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(PlaylistViewHolder holder, final int position)
    {
        AudioFile audio_file = PlaylistManager.FindAudioByPosition(position, id_playlist);
        Log.d(Globals.log_tag, "position " +position);

        holder.title.setText(audio_file.GetTitle());
        holder.artist.setText(audio_file.GetArtist());

        holder.edit_track.setOnClickListener(new View.OnClickListener()
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
                            case R.id.delete_track_from_playlist:
                                ShowDialogDeleteTrack(position);
                                break;
                        }
                        return false;
                    }
                });
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.playlist_track_options_menu, popup.getMenu());
                popup.show();
            }
        });

        Bitmap image = audio_file.GetImageBitmap(context);
        if(image != null) holder.album_art.setImageBitmap(image);
        else holder.album_art.setImageResource(0);

        if(audio_file.is_playing)
        {
            holder.play_pause.setImageResource(R.drawable.ic_pause);
        }
        else
        {
            holder.play_pause.setImageResource(R.drawable.ic_play);
        }

        if(audio_file.is_focused)
        {
            holder.play_pause.setColorFilter(ContextCompat.getColor(context, R.color.colorSecondaryLight));
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight));
            holder.artist.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight));
        }
        else
        {
            holder.play_pause.setColorFilter(ContextCompat.getColor(context, R.color.White));
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.White));
            holder.artist.setTextColor(ContextCompat.getColor(context, R.color.LightGrey));
        }
    }

    @Override
    public int getItemCount()
    {
        //return AudioPlayerService.music_list.Size();
        return PlaylistManager.GetPlaylistSize(id_playlist);
    }

    private void ShowDialogDeleteTrack(final int position)
    {
        final Playlist playlist = PlaylistManager.FindPlaylistById(id_playlist);
        final AudioFile track = playlist.FindAudioByPosition(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete track " + track.GetTitle() + " from playlist " + playlist.GetName() + "?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //MyMusicPlaylists.DeletePlaylist(position);
                playlist.DeleteTrack(track);
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
}

class PlaylistViewHolder extends RecyclerView.ViewHolder
{
    TextView title;
    TextView artist;
    ImageView play_pause;
    ImageView album_art;
    ImageButton edit_track;

    PlaylistViewHolder(View item)
    {
        super(item);
        title = (TextView) item.findViewById(R.id.playlist_title);
        artist = (TextView) item.findViewById(R.id.playlist_artist);
        play_pause = (ImageView) item.findViewById(R.id.playlist_play_pause);
        edit_track = (ImageButton) item.findViewById(R.id.playlist_track_options);
        album_art = (ImageView) item.findViewById(R.id.playlist_album_art);
        album_art.setClipToOutline(true);
    }
}*/
