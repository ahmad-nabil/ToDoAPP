package com.ahmad.todoapp.dashboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmad.todoapp.R;
import com.ahmad.todoapp.databinding.FragmentVideoViewBinding;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class videoView extends DialogFragment {
String videoUri;
ExoPlayer exoPlayer;
FragmentVideoViewBinding videoViewBinding;
    public videoView(String videoUri) {

    this.videoUri=videoUri;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        videoViewBinding=FragmentVideoViewBinding.inflate(getLayoutInflater());
        exoPlayer=new SimpleExoPlayer.Builder(requireContext().getApplicationContext()).build();
        videoViewBinding.playerView.setPlayer(exoPlayer);
        Uri VideoUri=Uri.parse(videoUri);
        MediaItem mediaItem=MediaItem.fromUri(VideoUri);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.play();
        videoViewBinding.btnClose.setOnClickListener(v->{
            exoPlayer.release();
            dismiss();
        });

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return videoViewBinding.getRoot();
    }

}