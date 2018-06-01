package ru.matrosov.vladimir.diplom.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import data.Message;
import data.User;
import ru.matrosov.vladimir.diplom.client.retrofit.LeaveChatResponse;
import ru.matrosov.vladimir.diplom.client.retrofit.SendMessageResponse;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;
import ru.matrosov.vladimir.diplom.client.retrofit.ShowChatResponse;
import viewHolders.ShowChatViewHolder;

import static constants.IntentParameters.EMAIL;
import static constants.IntentParameters.ID_CHAT;
import static constants.IntentParameters.MESSAGES_SIZE;
import static constants.IpAdress.IP_ADRESS;

public class ShowChatFragment extends Fragment {

    private static final String TAG = "ShowChatFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_show_chat, container, false);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setTitle("Сообщения");

        Intent intent = getActivity().getIntent();
        Integer idChat = intent.getIntExtra(ID_CHAT, 0);
        String email = intent.getStringExtra(EMAIL);
        ServerConnection serverConnection = new ServerConnection(IP_ADRESS, getContext());
        serverConnection.showingChat(idChat, email, this::onResponseShowChat);

        EditText editTextText = view.findViewById(R.id.new_message);

        ImageButton buttonSendMessage = view.findViewById(R.id.send_new_message);
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                String text = editTextText.getText().toString();
                editTextText.setText("");
                serverConnection.sendingMessage(email, text, idChat, this::onResponseSendMessage);
            }

            void onResponseSendMessage(SendMessageResponse sendMessageResponse) {
                if (sendMessageResponse.getStatus() == 0) {
                    ServerConnection serverConnection = new ServerConnection(IP_ADRESS, getContext());
                    serverConnection.showingChat(idChat, email, ShowChatFragment.this::onResponseShowChat);
                } else if (sendMessageResponse.getStatus() == -5) {
                    Toast.makeText(getContext(), R.string.message_null, Toast.LENGTH_LONG).show();
                } else if (sendMessageResponse.getStatus() == -1) {
                    Toast.makeText(getContext(), R.string.can_not_send_mess, Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    void onResponseShowChat(ShowChatResponse response) {
        if (response.getStatus() == 0) {
            ArrayList<User> users = (ArrayList<User>) response.getUsers();
            ArrayList<Message> messages = (ArrayList<Message>) response.getMessages();

            RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewShowChat);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            RecyclerView.Adapter<ShowChatViewHolder> showChatViewHolderAdapter = new RecyclerView.Adapter<ShowChatViewHolder>() {
                @NonNull
                @Override
                public ShowChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.show_chat_cell_layout, parent,
                            false);
                    return new ShowChatViewHolder(view);
                }

                @Override
                public void onBindViewHolder(@NonNull ShowChatViewHolder holder, int position) {
                    holder.setData(messages.get(messages.size() - 1 - position).getDate().toString());
                    holder.setTextEdit(messages.get(messages.size() - 1 - position).getText());
                    holder.setName(users.get(messages.size() - 1 - position).getFirstName() + " " +
                            users.get(messages.size() - 1 - position).getLastName());
                }

                @Override
                public int getItemCount() {
                    return messages.size();
                }
            };

            recyclerView.setAdapter(showChatViewHolderAdapter);
            showChatViewHolderAdapter.notifyDataSetChanged();

            Intent intent = getActivity().getIntent();

            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.intentShowChat.setPackage(getContext());
            serviceStoping(mainActivity);
            mainActivity.intentShowChat.setData(intent.getIntExtra(ID_CHAT, 0),
                    intent.getStringExtra(EMAIL), messages.size());

            getContext().startService(mainActivity.intentShowChat.getIntent());
            mainActivity.mBound = true;
            getContext().bindService(mainActivity.intentShowChat.getIntent(), mainActivity.mConnection,
                    Context.BIND_AUTO_CREATE);


        } else if (response.getStatus() == -1) {
            Toast.makeText(getContext(), R.string.can_not_open_chat, Toast.LENGTH_LONG).show();
        } else if (response.getStatus() == -4) {
            Toast.makeText(getContext(), R.string.has_not_this_chat, Toast.LENGTH_LONG).show();
        } else if (response.getStatus() == -5) {
            Toast.makeText(getContext(), R.string.chat_has_not_mess, Toast.LENGTH_LONG).show();

            Intent intent = getActivity().getIntent();

            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.intentShowChat.setPackage(getContext());
            serviceStoping(mainActivity);
            mainActivity.intentShowChat.setData(intent.getIntExtra(ID_CHAT, 0),
                    intent.getStringExtra(EMAIL), 0);

            getContext().startService(mainActivity.intentShowChat.getIntent());
            mainActivity.mBound = true;
            getContext().bindService(mainActivity.intentShowChat.getIntent(), mainActivity.mConnection,
                    Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getFragmentManager();
        int id = item.getItemId();
        if (id == R.id.action_showUsers) {
            new FragmentSupports().replaceFragments(fragmentManager, "showChat_showUsers",
                    R.id.frame_main, new UsersByChatFragment());
        } else if (id == R.id.action_addUserToChat) {
            new FragmentSupports().replaceFragments(fragmentManager, "showChat_addUser",
                    R.id.frame_main, new AddUsersToChatFragment());
        } else if (id == R.id.action_leave_chat) {
            Intent intent = getActivity().getIntent();
            Integer idChat = intent.getIntExtra(ID_CHAT, 0);
            String email = intent.getStringExtra(EMAIL);

            ServerConnection serverConnection = new ServerConnection(IP_ADRESS, getContext());
            serverConnection.leavingChat(idChat, email, this::onResponseLeaveChat);
        }

        return true;
    }

    void onResponseLeaveChat(LeaveChatResponse leaveChatResponse) {
        if (leaveChatResponse.getStatus() == 0) {
            FragmentManager fragmentManager = getFragmentManager();
            new FragmentSupports().replaceFragments(fragmentManager, "showChat_to_chatrooms",
                    R.id.frame_main, new ChatroomsFragment());
        } else if (leaveChatResponse.getStatus() == -4) {
            Toast.makeText(getContext(), R.string.not_have_chats, Toast.LENGTH_LONG).show();
        } else if (leaveChatResponse.getStatus() == -1) {
            Toast.makeText(getContext(), R.string.error_leave_chat, Toast.LENGTH_LONG).show();
        }
    }

    public void serviceStoping(MainActivity mainActivity){
        if (mainActivity.mBound == true) {
            getContext().unbindService(mainActivity.mConnection);
            getContext().stopService(mainActivity.intentShowChat.getIntent());
            mainActivity.mBound = false;
        }
    }

}
