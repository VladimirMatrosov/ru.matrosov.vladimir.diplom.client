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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

        ServerConnection serverConnection = new ServerConnection("http://192.168.0.102:8080", this.getContext());
        serverConnection.showingUsers(this::onResponse);
        return view;
    }

    @SuppressLint("ResourceType")
    void onResponse(ServerResponse response) {
        if (response.getStatus() == 0) {
            List<User> users = (ArrayList) response.getResponseObj();
            ScrollView scrollView = new ScrollView(this.getContext());
            RelativeLayout relativeLayout = new RelativeLayout(this.getContext());

            TextView textView = new TextView(this.getContext());
            textView.setText("Имя: , Фамилия: , Почта:, Должность: ");
            textView.setId(13);

            RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            textViewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            relativeLayout.addView(textView, textViewParams);

            ImageButton imageButton = new ImageButton(this.getContext());
            imageButton.setImageResource(R.drawable.ic_menu_chat);

            RelativeLayout.LayoutParams imageButParam = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            imageButParam.addRule(RelativeLayout.ALIGN_TOP, textView.getId());
            imageButParam.addRule(RelativeLayout.RIGHT_OF, textView.getId());
            relativeLayout.addView(imageButton, imageButParam);

            scrollView.addView(relativeLayout);

            Toast.makeText(this.getContext(), "Response:" + response.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getContext(), "В системе нет пользователей", Toast.LENGTH_LONG).show();
        }
    }
}
