package agolubeff.musicplayermaterial.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import agolubeff.musicplayermaterial.Globals;
import agolubeff.musicplayermaterial.activity.MainActivity;
import agolubeff.musicplayermaterial.database.DBHelper;
import agolubeff.musicplayermaterial.model.AllTracks;
import agolubeff.musicplayermaterial.model.AudioFile;
import agolubeff.musicplayermaterial.model.Playlist;
import agolubeff.musicplayermaterial.model.PlaylistManager;

public class AudioPlayerService extends Service
{
    private final IBinder ibinder = new MyBinder();

    private static MediaPlayer media_player;

    private MediaSessionManager media_session_manager;
    private MediaSessionCompat media_session_compat;
    private MediaControllerCompat.TransportControls transport_controls;


    public void onCreate()
    {
        super.onCreate();

        InitMediaPlayer();
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    private void InitMediaSession() throws RemoteException
    {
        if (media_session_manager != null) return; //mediaSessionManager exists

        media_session_manager = (MediaSessionManager) getSystemService(Context.MEDIA_SESSION_SERVICE);
        // Create a new MediaSession
        media_session_compat = new MediaSessionCompat(getApplicationContext(), "AudioPlayer");
        //Get MediaSessions transport controls
        transport_controls = media_session_compat.getController().getTransportControls();
        //set MediaSession -> ready to receive media commands
        media_session_compat.setActive(true);
        //indicate that the MediaSession handles transport control commands
        // through its MediaSessionCompat.Callback.
        media_session_compat.setFlags(MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        //Set mediaSession's MetaData
        UpdateMetaData();

        // Attach Callback to receive MediaSession updates
        media_session_compat.setCallback(new MediaSessionCompat.Callback()
        {
            // Implement callbacks
            @Override
            public void onPlay()
            {
                super.onPlay();

                Resume();
                //resumeMedia();
                //buildNotification(PlaybackStatus.PLAYING);
            }

            @Override
            public void onPause()
            {
                super.onPause();

                Pause();
                //pauseMedia();
                //buildNotification(PlaybackStatus.PAUSED);
            }

            @Override
            public void onSkipToNext()
            {
                super.onSkipToNext();

                //skipToNext();
                PlayNextAudio();
                UpdateMetaData();
                //buildNotification(PlaybackStatus.PLAYING);
            }

            @Override
            public void onSkipToPrevious()
            {
                super.onSkipToPrevious();

                //skipToPrevious();
                PlayPreviousAudio();
                UpdateMetaData();
                //buildNotification(PlaybackStatus.PLAYING);
            }

            @Override
            public void onStop()
            {
                super.onStop();
                //removeNotification();
                //Stop the service
                stopSelf();
            }

            @Override
            public void onSeekTo(long position)
            {
                super.onSeekTo(position);
            }
        });
    }

    private void UpdateMetaData()
    {
        AudioFile current_audio = AllTracks.GetCurrentAudio();
        //Bitmap albumArt = BitmapFactory.decodeResource(getResources(), R.drawable.image5); //replace with medias albumArt
        // Update the current metadata
        media_session_compat.setMetadata(new MediaMetadataCompat.Builder()
                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, current_audio.GetImageBitmap(getApplicationContext()))
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, current_audio.GetArtist())
                //.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, activeAudio.getAlbum())
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, current_audio.GetTitle())
                .build());
    }

    public static boolean IsPlaying()
    {
        return media_player.isPlaying();
    }

    public static int GetCurrentPosition()
    {
        return media_player.getCurrentPosition();
    }

    public static void PlayPreviousAudio(int id_playlist)
    {
        PlayNewAudio(PlaylistManager.FindPlaylistById(id_playlist).GetPreviousAudioId());
    }

    public static void PlayNextAudio(int id_playlist)
    {
        PlayNewAudio(PlaylistManager.FindPlaylistById(id_playlist).GetNextAudioId());
    }

    public static void PlayNewAudio(Integer id_new_audio)
    {
        if(id_new_audio == null)
        {
            return;
        }

        AudioFile new_audio = AllTracks.FindAudioById(id_new_audio);

        if(new_audio == null)
        {
            return;
        }

        //AudioPlayerState.ChangePlaylist(PlaylistManager.FindPlaylistById(id_playlist));
        AllTracks.ChangeAudio(id_new_audio);

        if(media_player.isPlaying())
        {
            media_player.stop();
        }
        try
        {
            media_player.reset();
            media_player.setDataSource(new_audio.GetData());
            media_player.prepare();
            media_player.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void PlayCurrentAudioAgain()
    {
        media_player.pause();
        //resume_position = 0;
        media_player.seekTo(0);
        media_player.start();
    }

    private void PlayMedia()
    {
        if (!media_player.isPlaying())
        {
            media_player.start();
        }
    }

    private void StopMedia()
    {
        if (media_player == null) return;
        if (media_player.isPlaying())
        {
            media_player.stop();
        }
    }

    public static void Pause()
    {
        if (media_player.isPlaying())
        {
            media_player.pause();
            //resume_position = AudioFile.ConvertDurationToSeconds(media_player.getCurrentPosition());

            AllTracks.Pause();
        }
    }

    public static void Resume()
    {
        if (!media_player.isPlaying())
        {
            //media_player.seekTo(resume_position*1000);
            media_player.start();
            AllTracks.Resume();
        }
    }

    public static void Resume(int position)
    {
        if (!media_player.isPlaying())
        {
            media_player.seekTo(position);
            media_player.start();
            AllTracks.Resume();
        }
    }

    private void InitMediaPlayer()
    {
        media_player = new MediaPlayer();
        media_player.reset();
        media_player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try
        {
            media_player.setDataSource(AllTracks.FindAudioById(AllTracks.GetCurrentAudioId()).GetData());
            media_player.prepare();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public class MyBinder extends Binder
    {
        AudioPlayerService GetService()
        {
            return AudioPlayerService.this;
        }
    }

    public IBinder onBind(Intent intent)
    {
        return ibinder;
    }



}
