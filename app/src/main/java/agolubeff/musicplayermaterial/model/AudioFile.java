package agolubeff.musicplayermaterial.model;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.FileDescriptor;

import agolubeff.musicplayermaterial.activity.MainActivity;

/**
 * Created by Andrey on 06.09.2017.
 */

public class AudioFile
{
    private int id;
    private String title;
    private String artist;
    private String data;
    private int duration_seconds;
    private String duration_string;
    //private Bitmap image;
    private long image_long;

    private boolean is_playing = false;
    private boolean is_focused = false;
    //public int resume_position = 0;

    public AudioFile(int id, String title, String artist, String data, long duration, long image_long)
    {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.data = data;
        this.duration_seconds = ConvertDurationToSeconds(duration);
        this.duration_string = ConvertDurationToString(duration_seconds);
        this.image_long = image_long;
    }

    public int GetId()
    {
        return id;
    }
    public String GetTitle()
    {
        return title;
    }
    public String GetTruncatedTitle(int symbol_amount)
    {
        if(title.length() <= symbol_amount) return title;
        else return title.substring(0, symbol_amount-3) + "..";
    }
    public String GetArtist()
    {
        return artist;
    }
    public String GetTruncatedArtist(int symbol_amount)
    {
        if(artist.length() <= symbol_amount) return artist;
        else return artist.substring(0, symbol_amount-3) + "..";
    }
    public String GetData()
    {
        return data;
    }
    public int GetDurationSeconds()
    {
        return duration_seconds;
    }
    public String GetDurationString()
    {
        return duration_string;
    }
    public long GetImageLong()
    {
        return image_long;
    }
    public Bitmap GetImageBitmap(Context context)
    {
        return LongToBitmap(image_long, context);
    }

    public boolean IsPlaying()
    {
        return is_playing;
    }

    public boolean IsFocused()
    {
        return is_focused;
    }

    public void Play()
    {
        is_playing = true;
    }

    public void Focuse()
    {
        is_focused = true;
    }

    public void Stop()
    {
        is_playing = false;
    }

    public void Defocuse()
    {
        is_focused = false;
    }

    public static String ConvertDurationToString(int duration)
    {
        String res = "";

        int hours = (duration / (60*60));
        int minutes = (duration % (60*60)) / (60);
        int seconds = (duration % (60*60)) % (60);

        if(hours > 0) res = hours + ":";

        res += minutes + ":";

        if(seconds < 10) res += "0" + seconds;
        else res += "" + seconds;

        return res;
    }

    public static int ConvertDurationToSeconds(long duration)
    {
        return (int)(duration/1000);
    }

    public static Bitmap LongToBitmap(Long image_long, Context context)
    {
        ContentResolver content_resolver = context.getContentResolver();
        Bitmap image_bitmap = null;
        try
        {
            final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");

            Uri uri = ContentUris.withAppendedId(sArtworkUri, image_long);

            ParcelFileDescriptor pfd = content_resolver.openFileDescriptor(uri, "r");

            if (pfd != null)
            {
                FileDescriptor fd = pfd.getFileDescriptor();
                image_bitmap = BitmapFactory.decodeFileDescriptor(fd);
            }
        }
        catch (Exception e)
        {
        }
        return image_bitmap;
    }
}
