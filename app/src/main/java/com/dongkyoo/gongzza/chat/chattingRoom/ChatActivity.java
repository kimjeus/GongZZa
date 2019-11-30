package com.dongkyoo.gongzza.chat.chattingRoom;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.dtos.PostChatDto;
import com.dongkyoo.gongzza.vos.Config;
import com.dongkyoo.gongzza.vos.Participant;
import com.dongkyoo.gongzza.vos.User;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    public static boolean IS_SHOWN = false;

    private User me;
    private static PostChatDto postChatDto;
    private ChatAdapter adapter;
    private ChatViewModel viewModel;

    public static PostChatDto getPostChatDto() {
        return postChatDto;
    }

    private DrawerLayout drawerLayout;
    private BroadcastReceiver chatMessageReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        int postId = getIntent().getIntExtra(Config.POST, -1);
        if (postId == -1) {
            me = getIntent().getParcelableExtra(Config.USER);
            postChatDto = getIntent().getParcelableExtra(Config.POST);
            viewModel = new ChatViewModel(this, postChatDto, me);
            initView();
        } else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String userId = sharedPreferences.getString(Config.USER_ID, null);
            String password = sharedPreferences.getString(Config.PASSWORD, null);

            if (userId != null && password != null) {
                viewModel = new ChatViewModel(this, postId, userId);
                viewModel.baseInfoState.observe(this, new Observer<ChatBaseInfo>() {
                    @Override
                    public void onChanged(ChatBaseInfo baseInfo) {
                        if (baseInfo.state == 200) {
                            me = baseInfo.me;
                            postChatDto = baseInfo.postChatDto;
                            initView();
                        }
                        else {
                            Toast.makeText(ChatActivity.this, "데이터 로딩에 실패했습니다.", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
            }
        }
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(postChatDto.getTitle());

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        TextView textView = findViewById(R.id.nav_header_title_textView);
        textView.setText(postChatDto.getTitle());

        ListView participantListView = findViewById(R.id.participants_listView);
        viewModel.participantList.observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> participants) {
                List<String> participantList = new ArrayList<>();
                for (Participant participant : participants) {
                    participantList.add(participant.getUser().getName());
                }
                participantListView.setAdapter(new ArrayAdapter<String>(ChatActivity.this, android.R.layout.simple_list_item_1, participantList));
            }
        });

        toolbar.inflateMenu(R.menu.chat_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.chat_navigation_menu) {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
                return false;
            }
        });

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
        drawerLayout.addDrawerListener(drawerToggle);
        ImageView imageView = navigationView.findViewById(R.id.navigation_quit_imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ChatActivity.this)
                        .setTitle("확인")
                        .setMessage("정말 나가시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                }
            });

        RecyclerView recyclerView = findViewById(R.id.chat_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
//        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        adapter = new ChatAdapter(this, this.postChatDto, this.me);
        recyclerView.setAdapter(adapter);

        ImageButton sendButton = findViewById(R.id.send_imageButton);
        EditText chatEditText = findViewById(R.id.chat_editText);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = chatEditText.getText().toString();
                viewModel.sendChat(content);
                chatEditText.setText("");
            }
        });

        viewModel.chatState.observe(this, new Observer<ChatState>() {
            @Override
            public void onChanged(ChatState chatState) {
                switch (chatState.state) {
                    case ChatState.CREATE:
                        adapter.notifyItemInserted(postChatDto.getChatLogList().size() - 1);
                        recyclerView.smoothScrollToPosition(postChatDto.getChatLogList().size() - 1);
                        break;

                    case ChatState.MODIFY:
                        adapter.notifyItemChanged(chatState.position);
                        break;

                    case ChatState.DELETE:
                        adapter.notifyItemRemoved(chatState.position);
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        IS_SHOWN = true;

        if (chatMessageReceiver == null) {
            chatMessageReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getIntExtra(Config.POST, -1) == postChatDto.getId()) {
                        String senderId = intent.getStringExtra(Config.USER);
                        String message = intent.getStringExtra(Config.MESSAGE);
                        int id = intent.getIntExtra(Config.CHAT_ID, 0);
                        viewModel.receiveChat(id, senderId, message);
                    }
                }
            };
        }

        IntentFilter intentFilter = new IntentFilter(getString(R.string.receive_message));
        registerReceiver(chatMessageReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        IS_SHOWN = false;

        if (chatMessageReceiver != null) {
            unregisterReceiver(chatMessageReceiver);
            chatMessageReceiver = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
            return;
        }
        super.onBackPressed();
    }
}
