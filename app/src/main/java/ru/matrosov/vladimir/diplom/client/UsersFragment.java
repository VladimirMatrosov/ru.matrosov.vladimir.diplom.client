package ru.matrosov.vladimir.diplom.client;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import data.User;
import ru.matrosov.vladimir.diplom.client.retrofit.OpenChatResponse;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;
import ru.matrosov.vladimir.diplom.client.retrofit.ShowUsersResponse;
import viewHolders.UsersViewHolder;

import static constants.IntentParameters.EMAIL;
import static constants.IntentParameters.ID_CHAT;
import static constants.IpAdress.IP_ADRESS;


public class UsersFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setTitle("Список пользователей");

        ServerConnection serverConnection = new ServerConnection(IP_ADRESS, this.getContext());
        serverConnection.showingUsers(this::onResponse);
        return view;
    }

    @SuppressLint("ResourceType")
    void onResponse(ShowUsersResponse response) {
        if (response.getStatus() == 0) {
            ArrayList<User> users = (ArrayList<User>) response.getUsers();

            RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewUsers);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            RecyclerView.Adapter<UsersViewHolder> userViewHolderAdapter = new RecyclerView.Adapter<UsersViewHolder>() {
                @NonNull
                @Override
                public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.user_cell_layout, parent, false);
                    return new UsersViewHolder(view);
                }

                @Override
                public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
                    holder.setEmail(users.get(position).getEmail());
                    holder.setName(users.get(position).getFirstName() + " " + users.get(position).getLastName());
                    holder.setPost(users.get(position).getPost());
                    holder.setImage(R.drawable.ic_email_black);
                    holder.setClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = getActivity().getIntent();
                            String email = intent.getStringExtra(EMAIL);
                            String email_user = users.get(position).getEmail();

                            ServerConnection serverConnection = new ServerConnection(IP_ADRESS, getContext());
                            serverConnection.openingChat(email, email_user, this::onRaponseOpenChat);
                        }

                        void onRaponseOpenChat(OpenChatResponse openChatResponse) {
                            if (openChatResponse.getStatus() == 0) {
                                Intent intent = getActivity().getIntent();
                                intent.putExtra(ID_CHAT, openChatResponse.getChatroom().getChatroomID());
                                FragmentManager fragmentManager = getFragmentManager();
                                new FragmentSupports().replaceFragments(fragmentManager, "users_to_chat",
                                        R.id.frame_main, new ShowChatFragment());
                            } else if (openChatResponse.getStatus() == -3) {
                                Toast.makeText(getContext(), R.string.error_add_chat,
                                        Toast.LENGTH_LONG).show();
                            } else if (openChatResponse.getStatus() == -1) {
                                Toast.makeText(getContext(), R.string.can_not_open_chat,
                                        Toast.LENGTH_LONG).show();
                            }
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
            Toast.makeText(this.getContext(), R.string.system_has_not_users, Toast.LENGTH_LONG).show();
        }
    }
}
