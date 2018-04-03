package agolubeff.musicplayermaterial.model;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import agolubeff.musicplayermaterial.activity.MainActivity;

/**
 * Created by andre on 15.03.2018.
 */

public class AllTracks
{
    private static ArrayList<Integer> id_audio_list = new ArrayList<>();
    private static HashMap<Integer, AudioFile> audio_list = LoadAllTracks();

    private static Integer current_audio_id = FindAudioIdByPosition(0);
    private static Integer previous_audio_id = null;
    private static boolean is_started = false;

    public static AudioFile GetCurrentAudio()
    {
        return FindAudioById(current_audio_id);
    }

    public static ArrayList<Integer> GetIdAudioList()
    {
        return id_audio_list;
    }

    @Nullable
    public static AudioFile FindAudioById(int id)
    {
        return audio_list.get(id);
    }

    @Nullable
    public static Integer FindAudioIdByPosition(int position)
    {
        try
        {
            return id_audio_list.get(position);
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    @Nullable
    public static Integer GetCurrentAudioPosition()
    {
        for(int i=0; i< id_audio_list.size(); i++)
        {
            if(id_audio_list.get(i) == current_audio_id) return i;
        }
        return null;
    }

    @Nullable
    public static Integer GetPreviousAudioPosition()
    {
        for(int i=0; i< id_audio_list.size(); i++)
        {
            if(id_audio_list.get(i) == previous_audio_id) return i;
        }
        return null;
    }

    public static boolean IsStarted()
    {
        return is_started;
    }

    public static Integer GetCurrentAudioId()
    {
        return current_audio_id;
    }

    public static Integer GetPreviousAudioId()
    {
        return previous_audio_id;
    }

    public static int Size()
    {
        return audio_list.size();
    }

    public static void Pause() throws NullPointerException
    {
        AudioFile current_audio = FindAudioById(current_audio_id);
        if(current_audio != null)
        {
            current_audio.Stop();
            current_audio.Focuse();
        }
        else throw new NullPointerException();
    }

    public static void Resume() throws NullPointerException
    {
        AudioFile current_audio = FindAudioById(current_audio_id);
        if(current_audio != null)
        {
            current_audio.Play();
            current_audio.Focuse();
        }
        else throw new NullPointerException();
    }

    public static void ChangeAudio(int new_audio_id) throws NullPointerException
    {
        is_started = true;

        previous_audio_id = current_audio_id;
        current_audio_id = new_audio_id;

        AudioFile previous_audio = FindAudioById(previous_audio_id);
        if(previous_audio != null)
        {
            previous_audio.Defocuse();
            previous_audio.Stop();
        }
        else throw new NullPointerException();

        Resume();
    }

    public static boolean IsPlaying(int id_audio)
    {
        return FindAudioById(id_audio).IsPlaying();
    }

    public static boolean CurrentIsPlaying()
    {
        return FindAudioById(current_audio_id).IsPlaying();
    }

    public static Bitmap GetImageBitmap(Integer id_audio, Context context)
    {
        return FindAudioById(id_audio).GetImageBitmap(context);
    }

    public static Bitmap GetCurrentImageBitmap(Context context)
    {
        return FindAudioById(current_audio_id).GetImageBitmap(context);
    }

    public static String GetTitle(Integer id_audio)
    {
        return FindAudioById(id_audio).GetTitle();
    }

    public static String GetCurrentTitle()
    {
        return FindAudioById(current_audio_id).GetTitle();
    }

    private static HashMap<Integer, AudioFile> LoadAllTracks()
    {
        HashMap<Integer, AudioFile> audio_list = new HashMap<>();

        Cursor cursor = MainActivity.database.query("tracks", null, null, null, null, null, null);
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

                audio_list.put(id, new AudioFile(id, title, artist, data, duration, image));
                id_audio_list.add(id);
            }
            while (cursor.moveToNext());
        }
        cursor.close();

        return audio_list;
    }
}
