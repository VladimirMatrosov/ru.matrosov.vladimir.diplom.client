package ru.matrosov.vladimir.diplom.client;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import data.Chatroom;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;
import ru.matrosov.vladimir.diplom.client.retrofit.ShowChatroomsResponse;
import viewHolders.ChatroomViewHolder;

import static constants.IntentParameters.EMAIL;
import static constants.IntentParameters.ID_CHAT;
import static constants.IpAdress.IP_ADRESS;

public class ChatroomsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatrooms, container, false);

        Intent intent = this.getActivity().getIntent();
        String email = intent.getStringExtra(EMAIL);

        Button buttonNewChat = view.findViewById(R.id.new_chat);
        buttonNewChat.setOnClickListener(this::newChat);

        ServerConnection serverConnection = new ServerConnection(IP_ADRESS, this.getContext());
        serverConnection.showingChatrooms(email, this::onResponseShowChatrooms);
        return view;
    }

    void newChat(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        new FragmentSupports().replaceFragments(fragmentManager, "chatrooms to addChat", R.id.frame_main,
                new AddChatFragment());
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
                                    intent.putExtra(ID_CHAT, chatrooms.get(position).getChatroomID());
                                    FragmentManager fragmentManager = getFragmentManager();
                                    new FragmentSupports().replaceFragments(fragmentManager, "chatrooms_to_showChat",
                                            R.id.frame_main, new ShowChatFragment());
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
            Toast.makeText(getContext(), R.string.no_user_with_email, Toast.LENGTH_LONG).show();
        } else if (response.getStatus() == -5) {
            TextView textView = getView().findViewById(R.id.textViewNull);
            textView.setText(R.string.not_have_chats);
        }
    }

}
