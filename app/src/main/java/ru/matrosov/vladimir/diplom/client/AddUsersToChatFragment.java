package ru.matrosov.vladimir.diplom.client;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import data.User;
import ru.matrosov.vladimir.diplom.client.retrofit.AddChatResponse;
import ru.matrosov.vladimir.diplom.client.retrofit.AddUserToChatResponse;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;
import ru.matrosov.vladimir.diplom.client.retrofit.ShowUsersResponse;


public class AddUsersToChatFragment extends Fragment {

    public static final String ID_CHAT = "idChat";
    public static final String EMAIL_ADD_USER = "email_add_user";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_users_to_chat, container, false);

        ServerConnection serverConnection = new ServerConnection(LoginActivity.server, this.getContext());
        serverConnection.showingUsers(this::onResponse);
        return view;
    }

    void onResponse(ShowUsersResponse response) {
        if (response.getStatus() == 0) {
            ArrayList<User> users = (ArrayList<User>) response.getUsers();

            RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewAddUserToChat);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            RecyclerView.Adapter<MyUserViewHolder> userViewHolderAdapter =
                    new RecyclerView.Adapter<MyUserViewHolder>() {
                @NonNull
                @Override
                public MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.user_cell_layout,
                            parent, false);
                    return new MyUserViewHolder(view);
                }

                @Override
                public void onBindViewHolder(@NonNull MyUserViewHolder holder, int position) {
                    holder.setEmail(users.get(position).getEmail());
                    holder.setName(users.get(position).getFirstName() + " " + users.get(position).getLastName());
                    holder.setPost(users.get(position).getPost());
                    holder.button.setImageResource(R.drawable.ic_person_add_black);
                    holder.button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = getActivity().getIntent();
                            Integer idChat = intent.getIntExtra(ID_CHAT, 0);
                            ServerConnection serverConnection = new ServerConnection(LoginActivity.server, getContext());
                            serverConnection.addingUserToChat(idChat, holder.emailTextView.getText().toString(),
                                    this::onResponseAddUserToChat);
                        }

                        void onResponseAddUserToChat(AddUserToChatResponse response) {
                            if (response.getStatus() == 0) {
                                Toast.makeText(getContext(), "Пользователь добавлен в чат", Toast.LENGTH_LONG).show();
                                Intent intent = getActivity().getIntent();
                                intent.putExtra(ID_CHAT, response.getChatroom().getChatroomID());
                            }else
                                Toast.makeText(getContext(), "Не удалось добавить пользователя в чат", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                 @Override
                public int getItemCount() {
                    return users.size();
                }
            };
            recyclerView.setAdapter(userViewHolderAdapter);

            userViewHolderAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this.getContext(), "В системе нет пользователей", Toast.LENGTH_LONG).show();
        }
    }

    class MyUserViewHolder extends RecyclerView.ViewHolder {
        private TextView emailTextView;
        private TextView nameTextView;
        private TextView postTextView;
        private ImageButton button;

        public MyUserViewHolder(View itemView) {
            super(itemView);
            this.emailTextView = itemView.findViewById(R.id.user_email);
            this.nameTextView = itemView.findViewById(R.id.user_name);
            this.postTextView = itemView.findViewById(R.id.user_post);
            this.button = itemView.findViewById(R.id.send_message_user);
        }

        void setEmail(String text) {
            emailTextView.setText(text);
        }

        void setName(String text) {
            nameTextView.setText(text);
        }

        void setPost(String text) {
            postTextView.setText(postTextView.getText() + text);
        }
    }
}

