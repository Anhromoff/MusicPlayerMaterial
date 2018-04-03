/*
package agolubeff.musicplayermaterial.model;

*/
/**
 * Created by Andrey on 20.02.2018.
 *//*


public class AudioPlayerState
{
    //private static Playlist current_playlist = PlaylistManager.FindPlaylistById(PlaylistManager.ID_ALL_TRACKS);
    //private static AudioFile current_audio = AllTracks.FindAudioById(current_playlist.GetNextAudioId());
    //private static AudioFile current_audio = AllTracks.FindAudioByPosition(0);
    //rivate static AudioFile previous_audio;
    private static int current_position = 0;
    private static boolean is_looped = false;
    private static boolean is_shuffled = false;
    //private static boolean is_started = false;

    public AudioPlayerState()
    {

    }

    */
/*public static void Pause()
    {
        current_audio.is_playing = false;
        current_audio.is_focused = true;
    }

    public static void Resume()
    {
        current_audio.is_playing = true;
        current_audio.is_focused = true;
    }

    public static void ChangeAudio(AudioFile new_audio)
    {
        is_started = true;

        previous_audio = current_audio;
        current_audio = new_audio;

        if(previous_audio != null)
        {
            previous_audio.is_focused = false;
            if (previous_audio.is_playing) previous_audio.is_playing = false;
        }

        current_audio.is_playing = true;
        current_audio.is_focused = true;
    }*//*


    */
/*public static void ChangePlaylist(Playlist new_playlist)
    {
        if(current_playlist.GetId() != new_playlist.GetId())
        {
            current_playlist = new_playlist;
            ChangeAudio(AllTracks.FindAudioById(new_playlist.GetNextAudioId()));
        }
    }*//*


    */
/*public static int GetCurrentAudioPosition()
    {
        return current_playlist.FindAudioPositionById(current_audio.GetId());
    }

    public static int GetPreviousAudioPosition()
    {
        return current_playlist.FindAudioPositionById(previous_audio.GetId());
    }*//*


    public static void Loop()
    {
        is_looped = true;
    }

    public static void Continue()
    {
        is_looped = false;
    }

    */
/*public static void Sort()
    {
        current_playlist.Sort();
        ChangeAudio(AllTracks.FindAudioById(current_playlist.GetAudioIdByOrder(0)));
        is_shuffled = false;
    }

    public static void Shuffle()
    {
        current_playlist.Shuffle();
        ChangeAudio(AllTracks.FindAudioById(current_playlist.GetAudioIdByOrder(0)));
        is_shuffled = true;
    }*//*


    */
/*public static boolean IsStarted()
    {
        return is_started;
    }*//*


    public static boolean IsLooped()
    {
        return is_looped;
    }

    public static boolean IsShuffled()
    {
        return is_shuffled;
    }

    */
/*public static AudioFile GetCurrentAudio()
    {
        return current_audio;
    }

    public static AudioFile GetPreviousAudio()
    {
        return previous_audio;
    }*//*


    */
/*public static Playlist GetCurrentPlaylist()
    {
        return current_playlist;
    }*//*


    public static int GetCurrentPosition()
    {
        return current_position;
    }

    public static void SetCurrentPosition(int new_position)
    {
        current_position = new_position;
    }

}
*/
