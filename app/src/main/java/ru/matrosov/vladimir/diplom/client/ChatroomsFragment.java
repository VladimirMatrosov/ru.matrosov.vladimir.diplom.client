package ru.matrosov.vladimir.diplom.client;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import data.Chatroom;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;
import ru.matrosov.vladimir.diplom.client.retrofit.ShowChatroomsResponse;
import viewHolders.ChatroomViewHolder;

public class ChatroomsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatrooms, container, false);

        Intent intent = this.getActivity().getIntent();
        String email = intent.getStringExtra(LoginActivity.EMAIL);

        Button buttonNewChat = view.findViewById(R.id.new_chat);
        buttonNewChat.setOnClickListener(this::newChat);

        ServerConnection serverConnection = new ServerConnection(LoginActivity.server, this.getContext());
        serverConnection.showingChatrooms(email, this::onResponseShowChatrooms);
        return view;
    }

    void newChat(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddChatFragment addChatFragment = new AddChatFragment();
        fragmentTransaction.replace(R.id.frame_main, addChatFragment);
        fragmentTransaction.commit();
    }

    void onResponseShowChatrooms(ShowChatroomsResponse response) {
        if (response.getStatus() == 0) {
            RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewChatrooms);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false));

            ArrayList<Chatroom> chatrooms = (ArrayList<Chatroom>) response.getChatrooms();

            RecyclerView.Adapter<ChatroomViewHolder> chatroomViewHolderAdapter =
                    new RecyclerView.Adapter<ChatroomViewHolder>() {
                        @NonNull
                        @Override
                        public ChatroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(getContext()).inflate(R.layout.chatrooms_cell_layout,
                                    parent, false);
                            return new ChatroomViewHolder(view);
                        }

                        @Override
                        public void onBindViewHolder(@NonNull ChatroomViewHolder holder, int position) {
                            holder.getTextViewName().setText(chatrooms.get(position).getName());
                            holder.getButton().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = getActivity().getIntent();
                                    intent.putExtra(AddUsersToChatFragment.ID_CHAT, chatrooms.get(position).getChatroomID());
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.frame_main, new ShowChatFragment());
                                    fragmentTransaction.commit();
                                }
                            });
                        }

                        @Override
                        public int getItemCount() {
                            return chatrooms.size();
                        }
                    };

            recyclerView.setAdapter(chatroomViewHolderAdapter);
            chatroomViewHolderAdapter.notifyDataSetChanged();
        } else if (response.getStatus() == -1) {
            Toast.makeText(getContext(), "Пользователя с таким email нет", Toast.LENGTH_LONG).show();
        } else if (response.getStatus() == -5) {
            TextView textView = getView().findViewById(R.id.textViewNull);
            textView.setText("Вы не состоите ни в одном диалоге");
        }
    }

}
