package ru.matrosov.vladimir.diplom.client;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import ru.matrosov.vladimir.diplom.client.retrofit.AddChatResponse;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;


public class AddChatFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_chat, container, false);

        Button buttonAddChat = view.findViewById(R.id.add_chat);
        buttonAddChat.setOnClickListener(this::addChat);
        return view;
    }

    void addChat(View view) {
        Intent intent = this.getActivity().getIntent();
        String email = intent.getStringExtra(LoginActivity.EMAIL);

        LinearLayout linearLayout = getView().findViewById(R.id.add_chat_frame);
        EditText editTextNameChat = linearLayout.findViewById(R.id.name_chat);
        String nameChat = editTextNameChat.getText().toString();

        ServerConnection serverConnection = new ServerConnection(LoginActivity.server, this.getContext());
        serverConnection.addingChat(email, nameChat, this::onResponseAddChat);
    }

    void onResponseAddChat(AddChatResponse response) {
        if (response.getStatus() == 0) {
            Toast.makeText(this.getContext(), response.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getContext(), "Введите имя чата", Toast.LENGTH_LONG).show();
        }
    }
}
