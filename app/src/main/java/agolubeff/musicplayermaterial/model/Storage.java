/*
package agolubeff.musicplayermaterial.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

*/
/**
 * Created by Andrey on 29.09.2017.
 *//*


public class Storage
{
    private final String STORAGE = " com.valdioveliu.valdio.audioplayer.STORAGE";
    private SharedPreferences preferences;
    private Context context;

    public Storage(Context context)
    {
        this.context = context;
    }

    public void SaveMusicList(MusicList music_list)
    {
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(music_list);
        editor.putString("audioArrayList", json);
        editor.apply();
    }

    public MusicList LoadMusicList()
    {
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("audioArrayList", null);
        //Type type = new TypeToken<MusicList>() {}.getType();
        //MusicList musicList = gson.fromJson(json, type);
        MusicList musicList = gson.fromJson(json, MusicList.class);
        return musicList;
    }

    public void SaveAudioIndex(int index)
    {
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("audioIndex", index);
        editor.apply();
    }

    public int LoadAudioIndex()
    {
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return preferences.getInt("audioIndex", -1);//return -1 if no data found
    }

    public void ClearMusicList()
    {
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public void SaveAudioFile(AudioFile audio_file, String key)
    {
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(audio_file);
        editor.putString(key, json);
        editor.apply();
    }

    public AudioFile LoadAudioFile(String key)
    {
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(key, null);
        AudioFile audio_file = gson.fromJson(json, AudioFile.class);
        return audio_file;
    }


}
*/
