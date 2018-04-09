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
import android.widget.EditText;
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
import static constants.IpAdress.IP_ADRESS;


public class ShowChatFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_chat, container, false);

        Intent intent = getActivity().getIntent();
        Integer idChat = intent.getIntExtra(ID_CHAT, 0);
        String email = intent.getStringExtra(EMAIL);
        ServerConnection serverConnection = new ServerConnection(IP_ADRESS, getContext());
        serverConnection.showingChat(idChat, email, this::onResponseShowChat);

        Button buttonAddUsersToChat = view.findViewById(R.id.addUserToChat);
        buttonAddUsersToChat.setOnClickListener(this::addUserToChat);

        Button buttonSendMessage = view.findViewById(R.id.send_new_message);
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextText = view.findViewById(R.id.new_message);
                String text = editTextText.getText().toString();
                serverConnection.sendingMessage(email, text, idChat, this::onResponseSendMessage);
            }

            void onResponseSendMessage(SendMessageResponse sendMessageResponse) {
                if (sendMessageResponse.getStatus() == 0){
                    ServerConnection serverConnection = new ServerConnection(IP_ADRESS, getContext());
                    serverConnection.showingChat(idChat, email, ShowChatFragment.this::onResponseShowChat);
                } else if (sendMessageResponse.getStatus() == -5) {
                    Toast.makeText(getContext(), "Сообщение не может быть пустым", Toast.LENGTH_LONG).show();
                } else if (sendMessageResponse.getStatus() == -1) {
                    Toast.makeText(getContext(), "Сообщение не удалось отправить", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button buttonSowUsers = view.findViewById(R.id.showUsersInChat);
        buttonSowUsers.setOnClickListener(this::showUsers);

        Button buttonLeaveChat = view.findViewById(R.id.leaveChat);
        buttonLeaveChat.setOnClickListener(this::leaveChat);
        return  view;
    }

    void leaveChat(View view) {
        Intent intent = getActivity().getIntent();
        Integer idChat = intent.getIntExtra(ID_CHAT, 0);
        String email = intent.getStringExtra(EMAIL);

        ServerConnection serverConnection = new ServerConnection(IP_ADRESS, getContext());
        serverConnection.leavingChat(idChat, email, this::onResponseLeaveChat);
    }

    void onResponseLeaveChat(LeaveChatResponse leaveChatResponse) {
        if (leaveChatResponse.getStatus() == 0) {
            FragmentManager fragmentManager = getFragmentManager();
            new FragmentSupports().replaceFragments(fragmentManager, "showChat_to_chatrooms", R.id.frame_main,
                    new ChatroomsFragment());
        } else  if(leaveChatResponse.getStatus() == -4) {
            Toast.makeText(getContext(), "Вы не состоите в данном чате", Toast.LENGTH_LONG).show();
        } else if (leaveChatResponse.getStatus() == -1) {
            Toast.makeText(getContext(), "Не удалось покинуть чат", Toast.LENGTH_LONG).show();
        }
    }

    void showUsers(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        new FragmentSupports().replaceFragments(fragmentManager, "showChats_to_showUsers", R.id.frame_main,
                new UsersByChatFragment());
    }

    void addUserToChat(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        new FragmentSupports().replaceFragments(fragmentManager, "showChat_to_addUser", R.id.frame_main,
                new AddUsersToChatFragment());
    }

    void onResponseShowChat(ShowChatResponse response) {
        if (response.getStatus() == 0) {
            ArrayList<User> users = (ArrayList<User>) response.getUsers();
            ArrayList<Message> messages = (ArrayList<Message>) response.getMessages();

            RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewShowChat);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false));
            RecyclerView.Adapter<ShowChatViewHolder> showChatViewHolderAdapter = new RecyclerView.Adapter<ShowChatViewHolder>() {
                @NonNull
                @Override
                public ShowChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.show_chat_cell_layout,
                            parent, false);
                    return new ShowChatViewHolder(view);
                }

                @Override
                public void onBindViewHolder(@NonNull ShowChatViewHolder holder, int position) {
                    holder.setData(messages.get(position).getDate().toString());
                    holder.setTextEdit(messages.get(position).getText());
                    holder.setName(users.get(position).getFirstName() + " " + users.get(position).getLastName());
                }

                @Override
                public int getItemCount() {
                    return messages.size();
                }
            };
            recyclerView.setAdapter(showChatViewHolderAdapter);
            showChatViewHolderAdapter.notifyDataSetChanged();
        }else if (response.getStatus() == -1) {
            Toast.makeText(getContext(), "Не удалось открыть чат", Toast.LENGTH_LONG).show();
        }else if (response.getStatus() == -4) {
            Toast.makeText(getContext(), "Вы не состоите в данном чате", Toast.LENGTH_LONG).show();
        }else if (response.getStatus() == -5) {
            Toast.makeText(getContext(), "В данном чате нет сообщений", Toast.LENGTH_LONG).show();
        }
    }
}
