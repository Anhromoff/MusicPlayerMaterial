/*
package agolubeff.musicplayermaterial.model;


import android.graphics.Bitmap;
import android.view.ActionMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import agolubeff.musicplayermaterial.MyRecyclerViewOnItemClickListener;
import agolubeff.musicplayermaterial.R;
import agolubeff.musicplayermaterial.activity.MainActivity;
import agolubeff.musicplayermaterial.adapter.RecyclerAdapter;
import agolubeff.musicplayermaterial.fragment.MyMusicSongList;
import agolubeff.musicplayermaterial.service.AudioPlayerService;

*/
/**
 * Created by andre on 14.03.2018.
 *//*


public class AudioClick implements MyRecyclerViewOnItemClickListener
{
    public ActionMode action_mode;
    RecyclerAdapter recycler_adapter;
    MainActivity activity;
    ActionModeCallback action_mode_callback;

    AudioClick(ActionMode action_mode, RecyclerAdapter recycler_adapter, MainActivity activity)
    {

    }

    @Override
    public void OnItemClick(View v, final int position)
    {
        AudioFile new_audio_file = PlaylistManager.FindAudioByPosition(position, PlaylistManager.ID_ALL_TRACKS);

        ImageView album_art = (ImageView) activity.findViewById(R.id.bottom_toolbar_album_art);
        final ImageButton play_pause = (ImageButton) activity.findViewById(R.id.bottom_toolbar_play_pause);
        play_pause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(AudioPlayerState.GetCurrentAudio().is_playing)
                {
                    AudioPlayerService.Pause();
                    recycler_adapter.notifyItemChanged(position);
                    play_pause.setImageResource(R.drawable.ic_play);
                }
                else
                {
                    AudioPlayerService.Resume();
                    recycler_adapter.notifyItemChanged(position);
                    play_pause.setImageResource(R.drawable.ic_pause);
                }
            }
        });

        TextView title = (TextView) activity.findViewById(R.id.bottom_toolbar_title);

        if (action_mode == null)
        {
            if(new_audio_file.is_playing)
            {
                AudioPlayerService.Pause();
                recycler_adapter.notifyItemChanged(position);
                play_pause.setImageResource(R.drawable.ic_play);
            }
            else
            {
                AudioFile current_audio_file = AudioPlayerState.GetCurrentAudio();
                if(AudioPlayerState.IsStarted())
                {
                    if(new_audio_file.GetId() == current_audio_file.GetId())
                    {
                        AudioPlayerService.Resume();
                        recycler_adapter.notifyItemChanged(position);
                        play_pause.setImageResource(R.drawable.ic_pause);
                    }
                    else
                    {
                        AudioPlayerService.PlayNewAudio(new_audio_file, PlaylistManager.ID_ALL_TRACKS);
                        recycler_adapter.notifyItemChanged(position);
                        if(AudioPlayerState.GetPreviousAudio() != null) recycler_adapter.notifyItemChanged(AudioPlayerState.GetPreviousAudioPosition());

                        Bitmap image = new_audio_file.GetImageBitmap(activity);
                        if(image != null) album_art.setImageBitmap(image);
                        else album_art.setImageResource(R.drawable.ic_no_album_art);

                        title.setText(new_audio_file.GetTitle());
                        play_pause.setImageResource(R.drawable.ic_pause);
                    }
                }
                else
                {
                    AudioPlayerService.PlayNewAudio(new_audio_file, PlaylistManager.ID_ALL_TRACKS);
                    recycler_adapter.notifyItemChanged(position);
                    if(AudioPlayerState.GetPreviousAudio() != null) recycler_adapter.notifyItemChanged(AudioPlayerState.GetPreviousAudioPosition());

                    Bitmap image = new_audio_file.GetImageBitmap(activity);
                    if(image != null) album_art.setImageBitmap(image);
                    else album_art.setImageResource(R.drawable.ic_no_album_art);

                    title.setText(new_audio_file.GetTitle());
                    play_pause.setImageResource(R.drawable.ic_pause);
                }
            }

            //startActivity(new Intent(activity, SongFullscreen.class));
        }
        else ToggleSelection(position);

    }

    @Override
    public void OnItemLongClick(View v, int position)
    {
        if (action_mode == null)
        {
            action_mode = activity.startActionMode(action_mode_callback);
            ToggleSelection(position);
        }
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
*/
