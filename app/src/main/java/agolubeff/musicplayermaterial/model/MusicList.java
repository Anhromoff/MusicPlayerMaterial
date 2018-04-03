/*
package agolubeff.musicplayermaterial.model;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

import java.io.FileDescriptor;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agolubeff.musicplayermaterial.Globals;
import agolubeff.musicplayermaterial.service.AudioPlayerService;


*/
/**
 * Created by Andrey on 06.09.2017.
 *//*


public class MusicList
{
    protected ArrayList<AudioFile> music_list;
    private List<Integer> order = new ArrayList<>();
    private boolean is_looped = false;
    private boolean is_shuffled = false;
    private static AudioFile current_audio = null;
    private static AudioFile previous_audio = null;
    private int order_index = -1;

    public MusicList()
    {

    }

    public MusicList(ContentResolver content_resolver)
    {
        music_list = new ArrayList<>();
        LoadAudio(content_resolver);
        for(AudioFile audio : music_list) order.add(audio.GetId());
    }

    */
/*public AudioFile FindAudioById(int id)
    {
        for(AudioFile audio : music_list)
        {
            if (audio.GetId() == id) return audio;
        }
        return null;
    }*//*


    public AudioFile FindAudioByPosition(int position)
    {
        return music_list.get(position);
    }

    private boolean IsInRange(int index)
    {
        return ((index >= 0)&&(index < music_list.size()));
    }

    public int Size()
    {
        return music_list.size();
    }

    public boolean IsLooped()
    {
        return is_looped;
    }

    public boolean IsShuffled()
    {
        return is_shuffled;
    }

    public int GetOrderIndex()
    {
        return order_index;
    }

    public void DecrementOrderIndex()
    {
        order_index--;
    }

    public void IncrementOrderIndex()
    {
        order_index++;
    }

    public void ChangeAudio(AudioFile new_audio)
    {
        previous_audio = current_audio;
        current_audio = new_audio;
    }

    public AudioFile GetCurrentAudio()
    {
        return current_audio;
    }

    public AudioFile GetPreviousAudio()
    {
        return previous_audio;
    }

    public AudioFile GetNextAudioInList()
    {
        //if(IsInRange(order_index + 1)) return FindAudioById(order.get(order_index + 1));
        if(IsInRange(order_index + 1)) return FindAudioByPosition(order.get(order_index + 1));
        else return null;
    }

    public AudioFile GetPreviousAudioInList()
    {
        //if(IsInRange(order_index - 1)) return FindAudioById(order.get(order_index - 1));
        if(IsInRange(order_index - 1)) return FindAudioByPosition(order.get(order_index - 1));
        else return null;
    }

    private void LoadAllTracks()
    {
        Cursor cursor = AudioPlayerService.database.query("tracks", null, null, null, null, null, null);
        if (cursor.moveToFirst())
        {
            do
            {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String artist = cursor.getString(cursor.getColumnIndex("artist"));
                String data = cursor.getString(cursor.getColumnIndex("data"));
                int duration = cursor.getInt(cursor.getColumnIndex("duration_seconds"));
                long image = cursor.getInt(cursor.getColumnIndex("image"));

                //Bitmap image = GetAlbumArt(album_image, content_resolver);
                music_list.add(new AudioFile(id, title, artist, data, duration, image));

                //Log.d(Globals.log_tag, id + title + artist);
            }
            while (cursor.moveToNext());
        }
        //else Log.d(Globals.log_tag, "0 rows");
        cursor.close();
    }

    private void LoadTracksFromPlaylist(int id_playlist)
    {
        String result = "";
        Cursor cursor = AudioPlayerService.database.query("contains", new String[]{"id_track"}, "id_playlist = ?", new String[]{String.valueOf(id_playlist)}, null, null, null);
        if(cursor.moveToFirst())
        {
            do
            {
                int id_track = cursor.getInt(cursor.getColumnIndex("id_track"));
                result += id_track +",";
            }
            while (cursor.moveToNext());

            result = result.substring(0, result.length() - 1);
        }

        cursor = AudioPlayerService.database.query("tracks", new String[]{"id", "title", "artist", "data", "duration_seconds", "image"}, "id in (" + result + ")", null, null, null, null);

        if(cursor.moveToFirst())
        {
            do
            {
                int id_track = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String artist = cursor.getString(cursor.getColumnIndex("artist"));
                String data = cursor.getString(cursor.getColumnIndex("data"));
                int duration = cursor.getInt(cursor.getColumnIndex("duration_seconds"));
                long image = cursor.getInt(cursor.getColumnIndex("image"));

                //Bitmap image = GetAlbumArt(album_image, content_resolver);
                music_list.add(new AudioFile(id_track, title, artist, data, duration, image));
            }
            while (cursor.moveToNext());
        }
    }



    public void Shuffle()
    {
        Collections.shuffle(order);
        order_index = -1;
        is_shuffled = true;
    }

    public void Continue()
    {
        is_looped = false;
    }

    public void Loop()
    {
        is_looped = true;
    }

    public void Sort()
    {
        Collections.sort(order);
        is_shuffled = false;
    }

}
*/
