/*
package agolubeff.musicplayermaterial.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import agolubeff.musicplayermaterial.MyRecyclerViewOnItemClickListener;
import agolubeff.musicplayermaterial.R;
import agolubeff.musicplayermaterial.model.AudioFile;
import agolubeff.musicplayermaterial.model.Playlist;
import agolubeff.musicplayermaterial.model.PlaylistManager;
import agolubeff.musicplayermaterial.service.AudioPlayerService;

*/
/**
 * Created by Andrey on 26.01.2018.
 *//*


public class SelectAudioRecyclerAdapter extends RecyclerView.Adapter<SelectAudioViewHolder>
{
    private SparseBooleanArray selected_items;
    MyRecyclerViewOnItemClickListener listener;
    Context context;
    int id_playlist;

    public SelectAudioRecyclerAdapter(Context context, MyRecyclerViewOnItemClickListener listener, int id_playlist)
    {
        this.context = context;
        this.listener = listener;
        this.id_playlist = id_playlist;
        selected_items = new SparseBooleanArray();
    }

    @Override
    public SelectAudioViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_audio_recycler_item, parent, false);
        final SelectAudioViewHolder holder = new SelectAudioViewHolder(v);

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
    public void onBindViewHolder(SelectAudioViewHolder holder, int position)
    {
        AudioFile audio_file = PlaylistManager.FindAudioByPosition(position, PlaylistManager.ID_ALL_TRACKS);

        holder.title.setText(audio_file.GetTitle());
        holder.artist.setText(audio_file.GetArtist());

        Bitmap image = audio_file.GetImageBitmap(context);
        if(image != null) holder.album_art.setImageBitmap(image);
        else holder.album_art.setImageResource(0);

        //holder.test_text.setVisibility(IsSelected(position) ? View.VISIBLE : View.INVISIBLE);
        if(IsSelected(position)) holder.checkbox.setImageResource(R.drawable.ic_checkbox_marked_circle_outline);
        else holder.checkbox.setImageResource(R.drawable.ic_checkbox_blank_circle_outline);
    }

    @Override
    public int getItemCount()
    {
       return PlaylistManager.GetPlaylistSize(PlaylistManager.ID_ALL_TRACKS);
    }

    public List<Integer> GetSelectedItems()
    {
        List<Integer> items = new ArrayList<>(selected_items.size());
        for (int i = 0; i < selected_items.size(); ++i)
        {
            items.add(selected_items.keyAt(i));
        }
        return items;
    }

    public boolean IsSelected(int position)
    {
        return GetSelectedItems().contains(position);
    }

    public void ToggleSelection(int position)
    {
        if (selected_items.get(position, false))
        {
            selected_items.delete(position);
        }
        else
        {
            selected_items.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void ClearSelection()
    {
        List<Integer> selection = GetSelectedItems();
        selected_items.clear();
        for (Integer i : selection)
        {
            notifyItemChanged(i);
        }
    }

    public int GetSelectedItemCount()
    {
        return selected_items.size();
    }

    public void AddSelectedTracksToPlaylist()
    {
        List<Integer> selection = GetSelectedItems();
        for (int i:selection)
        {
            PlaylistManager.AddTrackToPlaylist(PlaylistManager.FindAudioByPositionFromAllTracks(i), id_playlist);
        }
    }
}

class SelectAudioViewHolder extends RecyclerView.ViewHolder
{
    TextView title;
    TextView artist;
    ImageView album_art;
    ImageView checkbox;

    SelectAudioViewHolder(View item)
    {
        super(item);
        title = (TextView) item.findViewById(R.id.title_select_audio);
        artist = (TextView) item.findViewById(R.id.artist_select_audio);
        checkbox = (ImageView) item.findViewById(R.id.select_audio_checkbox);
        album_art = (ImageView) item.findViewById(R.id.album_art_select_audio);
        album_art.setClipToOutline(true);
    }
}
*/
