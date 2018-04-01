package ru.matrosov.vladimir.diplom.client;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import data.User;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerResponse;


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
    void onResponse(ServerResponse response) {
        if (response.getStatus() == 0) {

            ArrayList<User> users = new ArrayList<>();
            User user = new User();
            user.setEmail("1");
            User user2 = new User();
            user2.setEmail("2");
            User user3 = new User();
            user3.setEmail("3");
            User user4  = new User();
            user4.setEmail("4");


            users.add(user);
            users.add(user2);

            users.add(user3);

            users.add(user4);


            RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);

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
                    holder.setName(users.get(position).getEmail());
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
        private TextView userTextView;

        public MyUserViewHolder(View itemView) {
            super(itemView);
            this.userTextView = itemView.findViewById(R.id.username);
        }

        void setName(String text) {
            userTextView.setText(text);
        }
    }
}
