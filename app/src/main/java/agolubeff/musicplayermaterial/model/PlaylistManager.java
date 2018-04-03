package agolubeff.musicplayermaterial.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import agolubeff.musicplayermaterial.activity.MainActivity;
import agolubeff.musicplayermaterial.service.AudioPlayerService;

/**
 * Created by Andrey on 12.01.2018.
 */

public class PlaylistManager
{
    public static final int ID_ALL_TRACKS = -1;

    private static Playlist all_tracks = new Playlist(ID_ALL_TRACKS, "All tracks");
    private static ArrayList<Playlist> playlists = new ArrayList<>();

    public PlaylistManager()
    {

    }

    public static void AddNewPlaylist(String name)
    {
        int id = GetIDForNewPlaylist();

        ContentValues cv = new ContentValues();

        cv.put("id", id);
        cv.put("name", name);

        MainActivity.database.insert("playlists", null, cv);

        playlists.add(new Playlist(id, name));
    }

    public static void EditPlaylistName(String new_name, int position)
    {
        FindPlaylistByPosition(position).EditName(new_name);
        MainActivity.database.execSQL("update playlists set name = \'" + new_name + "\' where id = " + FindPlaylistByPosition(position).GetId() + ";");
    }

    public static int Size()
    {
        return playlists.size();
    }

    public static String[] GetPlaylistNames()
    {
        String[] names = new String[playlists.size()];
        for(int i=0; i<playlists.size(); i++) names[i] = playlists.get(i).GetName();
        return names;
    }

    /*public static int GetAllTracksSize()
    {
        return all_tracks.Size();
    }*/

    public static int GetPlaylistSize(int id_playlist)
    {
        return FindPlaylistById(id_playlist).Size();
    }

    public static Playlist FindPlaylistByPosition(int position)
    {
        return playlists.get(position);
    }

    public static Playlist FindPlaylistById(int id)
    {
        if(id == ID_ALL_TRACKS) return all_tracks;

        for (Playlist playlist : playlists)
        {
            if(playlist.GetId() == id) return playlist;
        }
        return null;
    }

    /*public static AudioFile FindAudioById(int id_audio)
    {
        return all_tracks.FindAudioById(id_audio);
    }*/

    public static int FindAudioIdByPosition(int position, int id_playlist)
    {
        return FindPlaylistById(id_playlist).FindAudioIdByPosition(position);
    }

    public static void DeletePlaylist(Playlist playlist)
    {
        MainActivity.database.execSQL("delete from playlists where id = \'" + playlist.GetId() + "\';");
        MainActivity.database.execSQL("delete from contains where id_playlist = \'" + playlist.GetId() + "\';");

        playlists.remove(playlist);
    }

    public static void LoadPlaylistsFromDB()
    {
        Cursor cursor = MainActivity.database.query("playlists", null, null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do
            {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));

                playlists.add(new Playlist(id, name));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
    }

    private static int GetIDForNewPlaylist()
    {
        Cursor c = MainActivity.database.rawQuery("Select max(id) from playlists", null);
        if(c.moveToFirst())
        {
            return c.getInt(0) + 1;
        }
        else return 0;
    }

    public static void AddTrackToPlaylist(int id_track, int id_playlist)
    {
        FindPlaylistById(id_playlist).AddTrack(id_track);
    }

    /*public static AudioFile FindAudioByPositionFromAllTracks(int position)
    {
        try
        {
            return FindPlaylistById(ID_ALL_TRACKS).FindAudioByPosition(position);
        }
        catch (NullPointerException e)
        {
            return null;
        }
    }*/
}
