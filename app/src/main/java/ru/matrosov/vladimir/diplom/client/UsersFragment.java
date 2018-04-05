package ru.matrosov.vladimir.diplom.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import data.User;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;
import ru.matrosov.vladimir.diplom.client.retrofit.ShowUsersResponse;


public class UsersFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        ServerConnection serverConnection = new ServerConnection(LoginActivity.server, this.getContext());
        serverConnection.showingUsers(this::onResponse);
        return view;
    }

    @SuppressLint("ResourceType")
    void onResponse(ShowUsersResponse response) {
        if (response.getStatus() == 0) {
            ArrayList<User> users = (ArrayList<User>) response.getUsers();

            RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewUsers);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            RecyclerView.Adapter<MyUserViewHolder> userViewHolderAdapter = new RecyclerView.Adapter<MyUserViewHolder>() {
                @NonNull
                @Override
                public MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.user_cell_layout, parent, false);
                    return new MyUserViewHolder(view);
                }

                @Override
                public void onBindViewHolder(@NonNull MyUserViewHolder holder, int position) {
                    holder.setEmail(users.get(position).getEmail());
                    holder.setName(users.get(position).getFirstName() + " " + users.get(position).getLastName());
                    holder.setPost(users.get(position).getPost());
                    holder.button.setImageResource(R.drawable.ic_email_black);
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
