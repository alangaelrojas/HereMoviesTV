package com.alan.alantv

import android.net.Uri
import android.os.Bundle
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.MediaPlayerAdapter
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.leanback.widget.PlaybackControlsRow
import com.alan.core.data.wrappers.MovieEntity

/** Handles video playback with media controls. */
class PlaybackVideoFragment : VideoSupportFragment() {

    private lateinit var mTransportControlGlue: PlaybackTransportControlGlue<MediaPlayerAdapter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val (_, _, _, _, _, overview, _, _, _, title, video, _, _, _, _) =
                activity?.intent?.getSerializableExtra(DetailsActivity.MOVIE) as MovieEntity

        val glueHost = VideoSupportFragmentGlueHost(this@PlaybackVideoFragment)
        val playerAdapter = MediaPlayerAdapter(activity)
        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE)

        mTransportControlGlue = PlaybackTransportControlGlue(getActivity(), playerAdapter)
        mTransportControlGlue.host = glueHost
        mTransportControlGlue.title = title
        mTransportControlGlue.subtitle = overview
        mTransportControlGlue.playWhenPrepared()

        //playerAdapter.setDataSource(Uri.parse(video))
    }

    override fun onPause() {
        super.onPause()
        mTransportControlGlue.pause()
    }
}