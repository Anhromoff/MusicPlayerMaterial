package agolubeff.musicplayermaterial.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import agolubeff.musicplayermaterial.Globals;
import agolubeff.musicplayermaterial.MyRecyclerViewOnItemClickListener;
import agolubeff.musicplayermaterial.R;
import agolubeff.musicplayermaterial.adapter.RecyclerAdapter;
import agolubeff.musicplayermaterial.database.DBHelper;
import agolubeff.musicplayermaterial.fragment.Equalizer;
import agolubeff.musicplayermaterial.fragment.MyMusic;
import agolubeff.musicplayermaterial.fragment.Settings;
import agolubeff.musicplayermaterial.fragment.Statistics;
import agolubeff.musicplayermaterial.model.ActionModeCallback;
import agolubeff.musicplayermaterial.model.AllTracks;
import agolubeff.musicplayermaterial.model.AudioFile;
import agolubeff.musicplayermaterial.model.Playlist;
import agolubeff.musicplayermaterial.model.PlaylistManager;
import agolubeff.musicplayermaterial.service.AudioPlayerService;

public class MainActivity extends AppCompatActivity
{
    BottomNavigationView bottom_navigation;
    Toolbar toolbar;
    ImageView album_art;
    ImageButton play_pause;
    ImageButton next;
    TextView title;

    public RecyclerAdapter recycler_adapter;
    ActionModeCallback action_mode_callback;

    public static DBHelper db_helper;
    public static SQLiteDatabase database;

    MyRecyclerViewOnItemClickListener audio_click = new MyRecyclerViewOnItemClickListener()
    {
        @Override
        public void OnItemClick(View v, final int position)
        {
            recycler_adapter.ClickAudio(position);
            Integer id_new_audio = AllTracks.FindAudioIdByPosition(position);

            if (action_mode_callback.action_mode == null)
            {
                if(AllTracks.IsPlaying(id_new_audio))
                {
                    AudioPlayerService.Pause();
                    play_pause.setImageResource(R.drawable.ic_play);
                }
                else
                {
                    int id_current_audio = AllTracks.GetCurrentAudioId();
                    if(AllTracks.IsStarted())
                    {
                        if(id_new_audio == id_current_audio)
                        {
                            AudioPlayerService.Resume();
                            play_pause.setImageResource(R.drawable.ic_pause);
                        }
                        else
                        {
                            AudioPlayerService.PlayNewAudio(id_new_audio);

                            Bitmap image = AllTracks.GetImageBitmap(id_new_audio, getApplicationContext());
                            if(image != null) album_art.setImageBitmap(image);
                            else album_art.setImageResource(R.drawable.ic_no_album_art);

                            title.setText(AllTracks.GetTitle(id_new_audio));
                            play_pause.setImageResource(R.drawable.ic_pause);
                        }
                    }
                    else
                    {
                        AudioPlayerService.PlayNewAudio(id_new_audio);

                        Bitmap image = AllTracks.GetImageBitmap(id_new_audio, getApplicationContext());
                        if(image != null) album_art.setImageBitmap(image);
                        else album_art.setImageResource(R.drawable.ic_no_album_art);

                        title.setText(AllTracks.GetTitle(id_new_audio));
                        play_pause.setImageResource(R.drawable.ic_pause);
                    }

                    startActivity(new Intent(MainActivity.this, SongFullscreen.class).putExtra(Globals.PLAYLIST_ID, PlaylistManager.ID_ALL_TRACKS));
                }

            }
            else action_mode_callback.ToggleSelection(position);
        }

        @Override
        public void OnItemLongClick(View v, int position)
        {
            if (action_mode_callback.action_mode == null)
            {
                action_mode_callback.action_mode = startActionMode(action_mode_callback);
                action_mode_callback.ToggleSelection(position);
            }
        }
    };
    BottomNavigationView.OnNavigationItemSelectedListener bottom_navigation_interaction = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.action_statistics:
                    ChangeFragment(Globals.FRAGMENT_STATISTICS);
                    break;
                case R.id.action_my_music:
                    ChangeFragment(Globals.FRAGMENT_MYMUSIC);
                    break;
                case R.id.action_equalizer:
                    ChangeFragment(Globals.FRAGMENT_EQUALIZER);
                    break;
                case R.id.action_settings:
                    ChangeFragment(Globals.FRAGMENT_SETTINGS);
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitDatabase();
        InitAdapter();
        InitItems();
        StartService();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        title.setText(AllTracks.GetCurrentTitle());

        Bitmap image = AllTracks.GetCurrentImageBitmap(this);
        if(image != null) album_art.setImageBitmap(image);
        else album_art.setImageResource(R.drawable.ic_no_album_art);

        if(AllTracks.CurrentIsPlaying()) play_pause.setImageResource(R.drawable.ic_pause);
        else play_pause.setImageResource(R.drawable.ic_play);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private void InitDatabase()
    {
        db_helper = new DBHelper(this);
        database = db_helper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        ContentResolver content_resolver = getContentResolver();
        int id = 0;
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        //String sortOrder = MediaStore.Audio.Media.TITLE;
        Cursor cursor = content_resolver.query(uri, null, selection, null, null);

        if (cursor != null && cursor.getCount() > 0)
        {
            while (cursor.moveToNext())
            {
                cv.put("id", id);
                cv.put("title", cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                cv.put("artist", cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                cv.put("data", cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                cv.put("duration_seconds", cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
                cv.put("image", cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));

                long lol = database.insert("tracks", null, cv);
                Log.d(Globals.log_tag, "row inserted, ID = " + lol);

                id++;
            }
            cursor.close();
        }
    }

    private void InitItems()
    {
        bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(bottom_navigation_interaction);
        bottom_navigation.setSelectedItemId(R.id.action_my_music);

        toolbar = (Toolbar)findViewById(R.id.current_track_view);
        toolbar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, SongFullscreen.class).putExtra(Globals.PLAYLIST_ID, PlaylistManager.ID_ALL_TRACKS));
            }
        });

        //AudioFile audio_file = AllTracks.FindAudioByPosition(0);

        album_art = (ImageView) findViewById(R.id.bottom_toolbar_album_art);
        Bitmap image = AllTracks.GetCurrentImageBitmap(this);
        if(image != null) album_art.setImageBitmap(image);
        else album_art.setImageResource(R.drawable.ic_no_album_art);

        play_pause=(ImageButton)findViewById(R.id.bottom_toolbar_play_pause);
        play_pause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = AllTracks.GetCurrentAudioPosition();
                recycler_adapter.ClickAudio(position);

                if(AllTracks.CurrentIsPlaying())
                {
                    AudioPlayerService.Pause();
                    //recycler_adapter.notifyItemChanged(position);
                    play_pause.setImageResource(R.drawable.ic_play);
                }
                else
                {
                    AudioPlayerService.Resume();
                    //recycler_adapter.notifyItemChanged(position);
                    play_pause.setImageResource(R.drawable.ic_pause);
                }
            }
        });

        next=(ImageButton)findViewById(R.id.bottom_toolbar_next);
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int current_position = AllTracks.GetCurrentAudioPosition();
                int next_position = current_position+1;

                //AudioFile next_audio = AllTracks.FindAudioByPosition(next_position);
                Integer id_next_audio = AllTracks.FindAudioIdByPosition(next_position);

                if(id_next_audio != null)
                {
                    AudioPlayerService.PlayNewAudio(id_next_audio);
                    //recycler_adapter.notifyItemChanged(current_position);
                    //recycler_adapter.notifyItemChanged(next_position);
                    recycler_adapter.ClickAudio(next_position);

                    Bitmap image = AllTracks.GetImageBitmap(id_next_audio, getApplicationContext());
                    if(image != null) album_art.setImageBitmap(image);
                    else album_art.setImageResource(R.drawable.ic_no_album_art);

                    title.setText(AllTracks.GetTitle(id_next_audio));
                    play_pause.setImageResource(R.drawable.ic_pause);
                }
            }
        });

        title = (TextView)findViewById(R.id.bottom_toolbar_title);
        title.setText(AllTracks.GetCurrentTitle());
    }

    private void InitAdapter()
    {
        recycler_adapter = new RecyclerAdapter(getApplication(), PlaylistManager.ID_ALL_TRACKS, audio_click);
        action_mode_callback = new ActionModeCallback(recycler_adapter, this);
    }

    private void ChangeFragment(int position)
    {

        Fragment new_fragment = null;

       switch (position)
       {
           case Globals.FRAGMENT_STATISTICS:
               new_fragment=Statistics.NewInstance();
               break;
           case Globals.FRAGMENT_MYMUSIC:
               new_fragment = MyMusic.NewInstance();
               break;
           case Globals.FRAGMENT_EQUALIZER:
               new_fragment = Equalizer.NewInstance();
               break;
           case Globals.FRAGMENT_SETTINGS:
               new_fragment = Settings.NewInstance();
               break;
       }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new_fragment).commit();
    }

    private void StartService()
    {
        Intent serviceIntent = new Intent(this, AudioPlayerService.class);
        serviceIntent.putExtra(Globals.ACTION_NAME, Globals.ACTION_START_SERVICE);
        startService(serviceIntent);
    }
}
