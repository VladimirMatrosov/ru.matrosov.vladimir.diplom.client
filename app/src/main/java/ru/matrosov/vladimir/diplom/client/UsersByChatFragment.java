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
import android.widget.Toast;

import java.util.ArrayList;

import data.User;
import ru.matrosov.vladimir.diplom.client.retrofit.GetUsersByChatResponse;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;
import viewHolders.UsersByChatViewHolder;

import static constants.IntentParameters.ID_CHAT;
import static constants.IpAdress.IP_ADRESS;


public class UsersByChatFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_by_chat, container, false);

        Intent intent = getActivity().getIntent();
        Integer idChat = intent.getIntExtra(ID_CHAT, 0);

        ServerConnection serverConnection = new ServerConnection(IP_ADRESS, getContext());
        serverConnection.gettingUsersByChat(idChat, this::onResponseUsersByChat);

        Button button = view.findViewById(R.id.return_chatroom);
        button.setOnClickListener(this::returnChatroom);
        return view;
    }

    void returnChatroom(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        new FragmentSupports().replaceFragments(fragmentManager, "usersChat_to_showChat", R.id.frame_main,
                new ShowChatFragment());
    }

    void onResponseUsersByChat(GetUsersByChatResponse getUsersByChatResponse) {
        if (getUsersByChatResponse.getStatus() == 0) {
            ArrayList<User> users = getUsersByChatResponse.getUsers();

            RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewUsersByChat);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            RecyclerView.Adapter<UsersByChatViewHolder> usersByChatViewHolderAdapter = new RecyclerView.Adapter<UsersByChatViewHolder>() {
                @NonNull
                @Override
                public UsersByChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.users_by_chat_cell_layout,
                            parent, false);
                    return new UsersByChatViewHolder(view);
                }

                @Override
                public void onBindViewHolder(@NonNull UsersByChatViewHolder holder, int position) {
                    holder.setEmail(users.get(position).getEmail());
                    holder.setName(users.get(position).getFirstName() + " " + users.get(position).getLastName());
                    holder.setPost(users.get(position).getPost());
                }

                @Override
                public int getItemCount() {
                    return users.size();
                }
            };
            recyclerView.setAdapter(usersByChatViewHolderAdapter);

            usersByChatViewHolderAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(getContext(), R.string.error_show_users, Toast.LENGTH_LONG).show();
        }
    }
}
