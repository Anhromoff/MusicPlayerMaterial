package agolubeff.musicplayermaterial;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.FileDescriptor;

/**
 * Created by Andrey on 02.10.2017.
 */

public class Globals
{
    public final static String ACTION_START_SERVICE = "StartService";
    public final static String ACTION_PLAY_NEXT_AUDIO = "PlayNextAudio";
    public final static String ACTION_PLAY_PREVIOUS_AUDIO = "PlayPreviousAudio";
    public final static String ACTION_PLAY_NEW_AUDIO = "PlayNewAudio";
    public final static String ACTION_RESUME = "Resume";
    public final static String ACTION_PAUSE = "Pause";
    public final static String ACTION_SEEKBAR_SET_POSITION = "SeekbarSetPosition";
    public final static String ACTION_SET_RESUME_POSITION = "SetResumePosition";
    public final static String ACTION_ERROR = "Error";
    public final static String ACTION_SHUFFLE = "Shuffle";
    public final static String ACTION_SORT = "Sort";
    public final static String ACTION_LOOP = "Loop";
    public final static String ACTION_CONTINUE = "Continue";
    public final static String NEW_POSITION = "NewPosition";
    public final static String AUDIO_ID = "AudioId";
    public final static String PLAYLIST_ID = "PlaylistId";
    public final static String ACTION_NAME = "ActionName";
    public final static String ERROR_TEXT = "ErrorText";
    public final static String PACKAGE_NAME = "agolubeff.mymusicplayer";

    public final static int FRAGMENT_STATISTICS = 0;
    public final static int FRAGMENT_MYMUSIC = 1;
    public final static int FRAGMENT_EQUALIZER = 2;
    public final static int FRAGMENT_SETTINGS = 3;

    public final static String DATABASE_NAME = "MyDB";
    public final static String log_tag = "MyLogs";



}
