package com.example.artmind.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.artmind.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MediaPlayer.class)
public class MusicPlayerModelTest {

    @Mock
    Context mockContext;

    @Mock
    MediaPlayer mockMediaPlayer;

    private MusicPlayerModel musicPlayerModel;

    @Before
    public void setUp() {
        Mockito.when(mockContext.getApplicationContext()).thenReturn(mockContext);

        PowerMockito.mockStatic(MediaPlayer.class);
        PowerMockito.when(MediaPlayer.create(mockContext, R.raw.timer_bg_music)).thenReturn(mockMediaPlayer);

        musicPlayerModel = new MusicPlayerModel(mockContext, R.raw.timer_bg_music);
    }

    @Test
    public void startMusic_whenMediaPlayerNotNull_shouldStart() {
        musicPlayerModel.startMusic();
        verify(mockMediaPlayer).start();
        // Ensure mediaPlayer is not null after starting
        assertNotNull(musicPlayerModel.getMediaPlayer());
    }

    @Test
    public void pauseMusic_whenMediaPlayerNotNullAndPlaying_shouldPause() {
        Mockito.when(mockMediaPlayer.isPlaying()).thenReturn(true);

        musicPlayerModel.pauseMusic();
        verify(mockMediaPlayer).pause();
        // Ensure mediaPlayer is not null even after pausing
        assertNotNull(musicPlayerModel.getMediaPlayer());
    }

    @Test
    public void stopMusic_whenMediaPlayerNotNull_shouldRelease() {
        musicPlayerModel.stopMusic();
        verify(mockMediaPlayer).release();
        // Ensure mediaPlayer is null after stopping
        assertNull(musicPlayerModel.getMediaPlayer());
    }
}
