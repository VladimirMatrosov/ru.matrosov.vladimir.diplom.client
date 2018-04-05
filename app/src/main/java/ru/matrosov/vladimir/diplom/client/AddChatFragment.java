package ru.matrosov.vladimir.diplom.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import ru.matrosov.vladimir.diplom.client.retrofit.AddChatResponse;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;

import static constants.IntentParameters.EMAIL;
import static constants.IntentParameters.ID_CHAT;
import static constants.IpAdress.IP_ADRESS;


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
        String email = intent.getStringExtra(EMAIL);

        LinearLayout linearLayout = getView().findViewById(R.id.add_chat_frame);
        EditText editTextNameChat = linearLayout.findViewById(R.id.name_chat);
        String nameChat = editTextNameChat.getText().toString();

        ServerConnection serverConnection = new ServerConnection(IP_ADRESS, this.getContext());
        serverConnection.addingChat(email, nameChat, this::onResponseAddChat);
    }

    void onResponseAddChat(AddChatResponse response) {
        if (response.getStatus() == 0) {
            Intent intent = getActivity().getIntent();
            Integer idChat = response.getChatroom().getChatroomID();
            intent.putExtra(ID_CHAT, idChat);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_main, new ShowChatFragment());
            fragmentTransaction.commit();
        } else {
            Toast.makeText(this.getContext(), "Введите имя чата", Toast.LENGTH_LONG).show();
        }
    }
}
