package com.example.artmind.model;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Button;

import com.example.artmind.R;

/**
 * Model to load, play & pause music
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class MusicPlayerModel {

    private MediaPlayer mediaPlayer;
    private boolean musicPaused;

    /**
     * Constructor method for Music Player Model
     *
     * @param context    find help fragment's context
     * @param musicResId music resource ID from resource folder
     */
    public MusicPlayerModel(Context context, int musicResId) {
        mediaPlayer = MediaPlayer.create(context, musicResId);
        mediaPlayer.setLooping(true);
    }

    /**
     * Start music player
     */
    public void startMusic() {
        if (mediaPlayer == null) {
            return;
        }

        if (musicPaused) {
            // If music was paused, resume it
            mediaPlayer.start();
            musicPaused = false;
        } else {
            // If music was not paused, start it
            mediaPlayer.seekTo(0);  // Start from the beginning
            mediaPlayer.start();
        }
    }

    /**
     * Paues music player
     */
    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            musicPaused = true;
        }
    }

    /**
     * Stop music player
     */
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * Toggle mute button
     *
     * @param button  view button to mute/unmute
     * @param isMuted check if the music is muted or not previously
     * @return boolean
     */
    public boolean toggleMute(Button button, boolean isMuted) {
        if (isMuted) {
            // If music is paused or stopped, start it
            startMusic();
            button.setText(R.string.mute);
            isMuted = false;
        } else {
            // If music is playing, pause it
            pauseMusic();
            button.setText(R.string.unmute);
            isMuted = true;
        }
        return isMuted;
    }

    /**
     * Getter method for Media Player
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}

