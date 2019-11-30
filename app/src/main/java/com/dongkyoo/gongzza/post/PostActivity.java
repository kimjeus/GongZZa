package com.dongkyoo.gongzza.post;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dongkyoo.gongzza.BR;
import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.databinding.ActivityPostBinding;
import com.dongkyoo.gongzza.dtos.PostDto;
import com.dongkyoo.gongzza.vos.Config;
import com.dongkyoo.gongzza.vos.User;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPostBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_post);

        User me = getIntent().getParcelableExtra(Config.USER);
        PostDto postDto = getIntent().getParcelableExtra(Config.POST);

        PostViewModel postViewModel = new PostViewModel(getApplication(), postDto, me);
        binding.setViewModel(postViewModel);

        postViewModel.isMember.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.enrollButton.setVisibility(View.INVISIBLE);
                    binding.leaveButton.setVisibility(View.VISIBLE);
                } else {
                    binding.enrollButton.setVisibility(View.VISIBLE);
                    binding.leaveButton.setVisibility(View.INVISIBLE);
                }
                binding.setViewModel(postViewModel);
            }
        });

        binding.enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
                builder.setTitle(R.string.enroll_confirm_title)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                postViewModel.enroll();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
                builder.show();
            }
        });

        binding.leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
                builder.setTitle(R.string.leave_confirm_title)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                postViewModel.leave();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
                builder.show();
            }
        });
    }
}
