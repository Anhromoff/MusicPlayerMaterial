package agolubeff.musicplayermaterial.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import agolubeff.musicplayermaterial.activity.MainActivity;
import agolubeff.musicplayermaterial.service.AudioPlayerService;

/**
 * Created by andre on 07.01.2018.
 */

public class Playlist
{
    private int id;
    private String name;
    private ArrayList<Integer> id_audio_list;

    private Stack<Integer> previous = new Stack<>();
    private Stack<Integer> next= new Stack<>();
    private int current_audio_id =0;
    private int current_audio_position=0;

    public Playlist(int id, String name)
    {
        this.id = id;
        this.name = name;

        if(id==PlaylistManager.ID_ALL_TRACKS) id_audio_list = AllTracks.GetIdAudioList();
        else id_audio_list = LoadTracksFromPlaylist(id);
    }

    public String GetName()
    {
        return this.name;
    }

    public int GetId()
    {
        return id;
    }

    public void EditName(String name)
    {
        this.name = name;
    }

    public void AddTrack(int id_track)
    {
        if(Contains(id_track)) return;

        id_audio_list.add(id_track);

        ContentValues cv = new ContentValues();

        cv.put("id_playlist", id);
        cv.put("id_track", id_track);

        MainActivity.database.insert("contains", null, cv);
    }

    public void DeleteTrack(int id_track)
    {
        id_audio_list.remove(id_track);
        MainActivity.database.delete("contains", "id_track = " + id_track, null);
    }

    private boolean IsInRange(int position)
    {
        return ((position >= 0)&&(position < id_audio_list.size()));
    }

    public boolean Contains(int id_track)
    {
        //return (FindAudioById(track.GetId()) != null);
        for (int i:id_audio_list)
        {
            if (i==id_track) return true;
        }
        return false;
    }

    /*public AudioFile FindAudioById(int id)
    {
       for(AudioFile audio : audio_list)
       {
           if (audio.GetId() == id) return audio;
       }
       return null;
    }*/

    public int GetCurrentAudioPosition()
    {
        return current_audio_position;
    }

    @Nullable
    public Integer FindAudioIdByPosition(int position)
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
    public Integer FindAudioPositionById(int id_audio)
    {
        for(int i=0; i<id_audio_list.size(); i++)
        {
            if(id_audio_list.get(i) == id_audio) return i;
        }
        return null;
    }

    public void InitStacks()
    {
        current_audio_id = AllTracks.GetCurrentAudioId();

        for (int i = 0; id_audio_list.get(i) != current_audio_id; i++)
        {
            previous.push(id_audio_list.get(i));
        }

        for (int i = id_audio_list.size() - 1; id_audio_list.get(i) != current_audio_id; i--)
        {
            next.push(id_audio_list.get(i));
        }
    }

    @Nullable
    public Integer GetNextAudioId()
    {
        if(next.empty())
        {
            return null;
        }

        current_audio_position ++;

        previous.push(current_audio_id);
        current_audio_id = next.peek();
        next.pop();
        return current_audio_id;
    }

    @Nullable
    public Integer GetPreviousAudioId()
    {
        if(previous.empty()) return null;

        current_audio_position --;

        next.push(current_audio_id);
        current_audio_id = previous.peek();
        previous.pop();
        return current_audio_id;
    }

    public int Size()
    {
        return id_audio_list.size();
    }

    /*private void LoadAllTracks()
    {
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

                audio_list.add(new AudioFile(id, title, artist, data, duration, image));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
    }*/

    private ArrayList<Integer> LoadTracksFromPlaylist(int id_playlist)
    {
        ArrayList<Integer> res = new ArrayList<>();

        String result = "";
        Cursor cursor = MainActivity.database.query("contains", new String[]{"id_track"}, "id_playlist = ?", new String[]{String.valueOf(id_playlist)}, null, null, null);
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

        cursor = MainActivity.database.query("tracks", new String[]{"id", "title", "artist", "data", "duration_seconds", "image"}, "id in (" + result + ")", null, null, null, null);

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


                res.add(id_track);
            }
            while (cursor.moveToNext());
        }

        return res;
    }

}
