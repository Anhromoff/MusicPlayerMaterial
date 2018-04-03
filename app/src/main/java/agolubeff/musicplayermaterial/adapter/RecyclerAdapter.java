package agolubeff.musicplayermaterial.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import agolubeff.musicplayermaterial.Globals;
import agolubeff.musicplayermaterial.MyRecyclerViewOnItemClickListener;
import agolubeff.musicplayermaterial.R;
import agolubeff.musicplayermaterial.model.AllTracks;
import agolubeff.musicplayermaterial.model.AudioFile;
import agolubeff.musicplayermaterial.model.Playlist;
import agolubeff.musicplayermaterial.model.PlaylistManager;
import agolubeff.musicplayermaterial.service.AudioPlayerService;

/**
 * Created by Andrey on 04.10.2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder>
{
    static final int title_length = 28;
    static final int artist_length = 28;

    private Integer current_position=0;
    private Integer previous_position=null;

    int id_playlist;
    Context context;
    private SparseBooleanArray selected_items = new SparseBooleanArray();
    MyRecyclerViewOnItemClickListener audio_click;

    /*MyRecyclerViewOnItemClickListener audio_click = new MyRecyclerViewOnItemClickListener()
    {
        @Override
        public void OnItemClick(View v, int position)
        {
            AudioFile new_audio_file = PlaylistManager.FindAudioByPosition(position, id_playlist);

            if(new_audio_file.is_playing)
            {
                AudioPlayerService.Pause();
                notifyItemChanged(position);
            }
            else
            {
                AudioFile current_audio_file = AudioPlayerState.GetCurrentAudio();
                if(AudioPlayerState.IsStarted())
                {
                    if(new_audio_file.GetId() == current_audio_file.GetId())
                    {
                        AudioPlayerService.Resume();
                        notifyItemChanged(position);

                    }
                    else
                    {
                        AudioPlayerService.PlayNewAudio(new_audio_file, id_playlist);
                        notifyItemChanged(position);
                        if(AudioPlayerState.GetPreviousAudio() != null) notifyItemChanged(AudioPlayerState.GetPreviousAudioPosition());
                    }
                }
                else
                {
                    AudioPlayerService.PlayNewAudio(new_audio_file, id_playlist);
                    notifyItemChanged(position);
                    if(AudioPlayerState.GetPreviousAudio() != null) notifyItemChanged(AudioPlayerState.GetPreviousAudioPosition());
                }
            }
        }

        @Override
        public void OnItemLongClick(View v, int position)
        {

        }
    };*/

    public RecyclerAdapter(Context context, int id_playlist, MyRecyclerViewOnItemClickListener audio_click)
    {
        this.context = context;
        this.id_playlist = id_playlist;
        this.audio_click = audio_click;

        if(id_playlist != PlaylistManager.ID_ALL_TRACKS) current_position = PlaylistManager.FindPlaylistById(id_playlist).FindAudioPositionById(AllTracks.GetCurrentAudioId());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        final ViewHolder holder = new ViewHolder(v);

        v.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                audio_click.OnItemClick(v, holder.getAdapterPosition());
            }
        });

        v.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                audio_click.OnItemLongClick(v, holder.getAdapterPosition());
                return true;
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        AudioFile audio_file;
        if(id_playlist == PlaylistManager.ID_ALL_TRACKS) audio_file = AllTracks.FindAudioById(AllTracks.FindAudioIdByPosition(position));
        else audio_file = AllTracks.FindAudioById(PlaylistManager.FindAudioIdByPosition(position, id_playlist));

        holder.title.setText(audio_file.GetTruncatedTitle(title_length));
        holder.artist.setText(audio_file.GetTruncatedArtist(artist_length));

        Bitmap image = audio_file.GetImageBitmap(context);
        if(image != null) holder.album_art.setImageBitmap(image);
        else holder.album_art.setImageResource(0);

        if(IsSelected(position)) holder.selector.setVisibility(View.VISIBLE);
        else holder.selector.setVisibility(View.INVISIBLE);

        if(audio_file.IsPlaying())
        {
            holder.play_pause.setImageResource(R.drawable.ic_pause);
        }
        else
        {
            holder.play_pause.setImageResource(R.drawable.ic_play);
        }

        if(audio_file.IsFocused())
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
        if(id_playlist == PlaylistManager.ID_ALL_TRACKS) return AllTracks.Size();
        else return PlaylistManager.GetPlaylistSize(id_playlist);
    }

    public boolean IsSelected(int position)
    {
        return GetSelectedItems().contains(position);
    }

    public void ClearSelection()
    {
        List<Integer> selection = GetSelectedItems();
        selected_items.clear();
        for (Integer i : selection)
        {
            notifyItemChanged(i);
        }
        Log.d(Globals.log_tag, "selection cleared");
    }

    public List<Integer> GetSelectedItems()
    {
        List<Integer> items = new ArrayList<>(selected_items.size());
        for (int i = 0; i < selected_items.size(); ++i)
        {
            items.add(selected_items.keyAt(i));
            Log.d(Globals.log_tag, "item " + i + " got");
        }
        Log.d(Globals.log_tag, "size: " + items.size());
        return items;
    }

    public void ToggleSelection(int position)
    {
        if (selected_items.get(position, false))
        {
            selected_items.delete(position);
            Log.d(Globals.log_tag, "item " + position + " deleted");
        }
        else
        {
            selected_items.put(position, true);
            Log.d(Globals.log_tag, "item " + position + " put");
        }
        notifyItemChanged(position);
    }

    public int GetSelectedItemCount()
    {
        return selected_items.size();
    }

    public void ClickAudio(int new_position)
    {
        previous_position = current_position;
        current_position = new_position;

        notifyItemChanged(current_position);
        if((previous_position!=null)&&(previous_position != current_position)) notifyItemChanged(previous_position);
    }

    public void ClearPreviousSelection()
    {
        notifyItemChanged(previous_position);
    }
}

class ViewHolder extends RecyclerView.ViewHolder
{
    TextView title;
    TextView artist;
    ImageView play_pause;
    ImageView album_art;
    ImageView selector;

    ViewHolder(View item)
    {
        super(item);
        title = (TextView) item.findViewById(R.id.playlist_name);
        artist = (TextView) item.findViewById(R.id.artist);
        play_pause = (ImageView) item.findViewById(R.id.play_pause);
        selector = (ImageView) item.findViewById(R.id.selector);
        album_art = (ImageView) item.findViewById(R.id.album_art);
        album_art.setClipToOutline(true);
    }

}

