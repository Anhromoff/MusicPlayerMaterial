package agolubeff.musicplayermaterial.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import agolubeff.musicplayermaterial.activity.MainActivity;
import agolubeff.musicplayermaterial.fragment.AlbumArtFragment;
import agolubeff.musicplayermaterial.model.AllTracks;
import agolubeff.musicplayermaterial.model.AudioFile;
import agolubeff.musicplayermaterial.model.Playlist;
import agolubeff.musicplayermaterial.model.PlaylistManager;
import agolubeff.musicplayermaterial.service.AudioPlayerService;

public class SongFullscreenViewPagerAdapter extends FragmentPagerAdapter
{
    Context context;
    int playlist_id;

    public SongFullscreenViewPagerAdapter(FragmentManager fragmentManager, Context context, int playlist_id)
    {
        super(fragmentManager);
        this.context = context;
        this.playlist_id = playlist_id;
    }

    @Override
    public int getCount()
    {
        return PlaylistManager.FindPlaylistById(playlist_id).Size();
    }

    @Override
    public Fragment getItem(int position)
    {
        AudioFile audio = AllTracks.FindAudioById(PlaylistManager.FindPlaylistById(playlist_id).FindAudioIdByPosition(position));
        return AlbumArtFragment.NewInstance(audio.GetImageBitmap(context));
    }
}
