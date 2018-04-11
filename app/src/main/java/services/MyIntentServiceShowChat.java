package services;

import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Binder;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.ArrayList;

import data.Message;
import ru.matrosov.vladimir.diplom.client.R;
import ru.matrosov.vladimir.diplom.client.ShowChatFragment;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;
import ru.matrosov.vladimir.diplom.client.retrofit.ShowChatResponse;

import static constants.IntentParameters.EMAIL;
import static constants.IntentParameters.ID_CHAT;
import static constants.IntentParameters.MESSAGES_SIZE;
import static constants.IntentParameters.M_BOUND;
import static constants.IpAdress.IP_ADRESS;

public class MyIntentServiceShowChat extends IntentService {
    Integer idChat;
    String email;
    int messages_size;
    FragmentManager fragmentManager;
    Context context;
    int listSize;

    private final IBinder mBind = new ShowChatBinder();
    private final String TAG = "IntentServiceLogs";

    public MyIntentServiceShowChat() {
        super("MyIntentServiceShowChat");
    }

    public class ShowChatBinder extends Binder {
        public MyIntentServiceShowChat getService() {
            return MyIntentServiceShowChat.this;
        }

        public void setFragmentManager(FragmentManager fragmentManager1) {
            fragmentManager = fragmentManager1;
        }

        public void setContext(Context context1) {
            context = context1;
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBind;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            this.idChat = intent.getIntExtra(ID_CHAT, 0);
            Log.w(TAG, "idChat: " + idChat);
            this.email = intent.getStringExtra(EMAIL);
            Log.w(TAG, "email: " + email);
            this.messages_size = intent.getIntExtra(MESSAGES_SIZE, 0);
            Log.w(TAG, "size: " + messages_size);

            int i = 0;
            listSize = messages_size;
            while (messages_size == listSize) {
                synchronized (this) {
                    try {
                        wait(5000);
                        new ServerConnection(IP_ADRESS, context).showingChat(idChat, email, this::onResponse);
                        Log.w(TAG, "I = " + i);
                        i++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void onResponse(ShowChatResponse response) {
        if (response.getStatus() == 0) {
            ArrayList<Message> messages = (ArrayList<Message>) response.getMessages();
            listSize = messages.size();
            if (messages.size() != messages_size) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_main, new ShowChatFragment());
                fragmentTransaction.commit();
            }
        }
    }
}
