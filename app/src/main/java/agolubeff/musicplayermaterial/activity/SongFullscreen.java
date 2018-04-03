package agolubeff.musicplayermaterial.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import agolubeff.musicplayermaterial.Globals;
import agolubeff.musicplayermaterial.R;
import agolubeff.musicplayermaterial.adapter.SongFullscreenViewPagerAdapter;
import agolubeff.musicplayermaterial.model.AllTracks;
import agolubeff.musicplayermaterial.model.AudioFile;
import agolubeff.musicplayermaterial.model.Playlist;
import agolubeff.musicplayermaterial.model.PlaylistManager;
import agolubeff.musicplayermaterial.service.AudioPlayerService;

public class SongFullscreen extends AppCompatActivity
{
    int id_playlist;

    FragmentPagerAdapter adapter;
    Toolbar toolbar;

    TextView title;
    TextView artist;
    ViewPager view_pager;
    TextView current_position;
    TextView duration;
    SeekBar seek_bar;
    ImageButton shuffle;
    ImageButton previous;
    ImageButton play_pause;
    ImageButton next;
    ImageButton loop;

    private static boolean thread_running = true;
    private int resume_position;

    /*View.OnClickListener loop_click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
if(AudioPlayerState.IsLooped())
            {
                AudioPlayerState.Continue();
                UpdateUIContinue();
            }
            else
            {
                AudioPlayerState.Loop();
                UpdateUILoop();
            }

        }
    };

    View.OnClickListener shuffle_click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
if(AudioPlayerState.IsShuffled())
            {
                AudioPlayerState.Sort();

                //UpdateUISort();
            }
            else
            {
                AudioPlayerState.Shuffle();
                //UpdateUIShuffle();
            }
            PlayNextAudio();

        }
    };

    SeekBar.OnSeekBarChangeListener seek_bar_interaction = new SeekBar.OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            if(fromUser)
            {
AudioFile current_audio_file = AudioPlayerState.GetCurrentAudio();
                current_audio_file.resume_position = progress;

                current_position.setText(AudioFile.ConvertDurationToString(current_audio_file.resume_position));
                seekBar.setProgress(current_audio_file.resume_position);

                Intent intent = new Intent(seekBar.getContext(), AudioPlayerService.class);
                intent.putExtra(Globals.ACTION_NAME, Globals.ACTION_SET_RESUME_POSITION);
                intent.putExtra(Globals.NEW_POSITION, current_audio_file.resume_position);
                startService(intent);


                AudioPlayerState.SetCurrentPosition(progress);
                current_position.setText(AudioFile.ConvertDurationToString(progress));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {
            Pause();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            Resume();
        }
    };

    View.OnTouchListener view_pager_interaction = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            return true;
        }
    };*/

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_fullscreen);

        id_playlist = getIntent().getIntExtra(Globals.PLAYLIST_ID, -100);
        PlaylistManager.FindPlaylistById(id_playlist).InitStacks();

        InitToolbar();
        InitViewPager();
        InitItems();

        thread.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.song_fullscreen_toolbar_menu, menu);
        return true;
    }

    private void InitToolbar()
    {
        toolbar = (Toolbar)findViewById(R.id.song_fullscreen_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    private void InitViewPager()
    {
        view_pager = (ViewPager) findViewById(R.id.song_fullscreen_view_pager);
        adapter = new SongFullscreenViewPagerAdapter(getSupportFragmentManager(), getApplicationContext(), id_playlist);
        view_pager.setAdapter(adapter);
        //view_pager.setOnTouchListener(view_pager_interaction);
    }

    private void Resume()
    {
        AudioPlayerService.Resume();
        thread_running = true;
        play_pause.setImageResource(R.drawable.ic_pause);
    }

    private void Resume(int position)
    {
        AudioPlayerService.Resume(position);
        thread_running = true;
        play_pause.setImageResource(R.drawable.ic_pause);
    }

    private void Pause()
    {
        AudioPlayerService.Pause();
        thread_running = false;
        play_pause.setImageResource(R.drawable.ic_play);
    }

    private void Next()
    {
        AudioPlayerService.PlayNextAudio(id_playlist);
        thread_running = true;
        UpdateUIPlayNewAudio();
    }

    private void Previous()
    {
        AudioPlayerService.PlayPreviousAudio(id_playlist);
        thread_running = true;
        UpdateUIPlayNewAudio();
    }

    private void InitItems()
    {
        title = (TextView)findViewById(R.id.fullscreen_title);
        artist = (TextView)findViewById(R.id.fullscreen_artist);
        current_position = (TextView)findViewById(R.id.fullscreen_current_position);
        duration = (TextView)findViewById(R.id.fullscreen_duration);
        seek_bar = (SeekBar)findViewById(R.id.fullscreen_seek_bar);
        shuffle = (ImageButton) findViewById(R.id.shuffle);
        previous = (ImageButton)findViewById(R.id.previous);
        play_pause = (ImageButton)findViewById(R.id.play_pause);
        next = (ImageButton)findViewById(R.id.next);
        loop = (ImageButton) findViewById(R.id.loop);

        AudioFile current_audio = AllTracks.FindAudioById(AllTracks.GetCurrentAudioId());
        title.setText(current_audio.GetTitle());
        artist.setText(current_audio.GetArtist());
        current_position.setText(AudioFile.ConvertDurationToString(0));
        duration.setText(current_audio.GetDurationString());
        seek_bar.setMax(current_audio.GetDurationSeconds());
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if(fromUser)
                {
                    resume_position = progress*1000;
                    current_position.setText(AudioFile.ConvertDurationToString(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                Pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                Resume(resume_position);
            }
        });

        //shuffle.setOnClickListener(shuffle_click);
        previous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Previous();
            }
        });

        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Next();
            }
        });

        if(current_audio.IsPlaying()) play_pause.setImageResource(R.drawable.ic_pause);
        else play_pause.setImageResource(R.drawable.ic_play);
        play_pause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(AudioPlayerService.IsPlaying()) Pause();
                else Resume();
            }
        });
        //loop.setOnClickListener(loop_click);
        //seek_bar.setOnSeekBarChangeListener(seek_bar_interaction);

        //if(AudioPlayerState.IsLooped()) UpdateUILoop();
        //if(AudioPlayerState.IsShuffled()) UpdateUIShuffle();
    }


    private void UpdateUILoop()
    {
        loop.setColorFilter(getResources().getColor(R.color.colorSecondary), PorterDuff.Mode.SRC_ATOP);
    }

    private void UpdateUIContinue()
    {
        loop.setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
    }

    private void UpdateUISort()
    {
        shuffle.setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
    }

    private void UpdateUIShuffle()
    {
        shuffle.setColorFilter(getResources().getColor(R.color.colorSecondary), PorterDuff.Mode.SRC_ATOP);
    }

    private void UpdateUIPlayNewAudio()
    {
        //UpdateUIResume();
        play_pause.setImageResource(R.drawable.ic_pause);

        AudioFile current_audio_file = AllTracks.GetCurrentAudio();

        title.setText(current_audio_file.GetTitle());
        artist.setText(current_audio_file.GetArtist());
        duration.setText(current_audio_file.GetDurationString());
        current_position.setText(AudioFile.ConvertDurationToString(0));
        seek_bar.setMax(current_audio_file.GetDurationSeconds());

        view_pager.setCurrentItem(PlaylistManager.FindPlaylistById(id_playlist).GetCurrentAudioPosition());
        //view_pager.setCurrentItem(1);
    }

    Handler handler = new Handler();
    public Thread thread = new Thread(new Runnable()
    {
        public void run()
        {
            while(true)
            {
                try
                {
                    TimeUnit.MILLISECONDS.sleep(100);
                    handler.post(UpdateTime);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    });

    private Runnable UpdateTime = new Runnable()
    {
        public void run()
        {
            AudioFile current_audio = AllTracks.GetCurrentAudio();

            if((current_audio != null)&&(thread_running))
            {
                int resume_position = AudioFile.ConvertDurationToSeconds(AudioPlayerService.GetCurrentPosition());
                handler.postDelayed(UpdateTime, 1000);

                seek_bar.setProgress(resume_position);
                current_position.setText(AudioFile.ConvertDurationToString(resume_position));

                if(resume_position == current_audio.GetDurationSeconds())
                {
                    /*if(AudioPlayerState.IsLooped())
                    {
                        AudioPlayerService.PlayCurrentAudioAgain();
                        seek_bar.setProgress(0);
                        current_position.setText(AudioFile.ConvertDurationToString(0));
                    }*/
                    //Next();
                }
            }
        }
    };
}
