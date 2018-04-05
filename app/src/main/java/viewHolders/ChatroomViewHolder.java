package viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import ru.matrosov.vladimir.diplom.client.R;

public class ChatroomViewHolder extends RecyclerView.ViewHolder {
    TextView textViewName;
    ImageButton button;

    public ChatroomViewHolder(View itemView) {
        super(itemView);
        this.textViewName = itemView.findViewById(R.id.chatroom_name);
        this.button = itemView.findViewById(R.id.send_message_chatroom);
    }

    public void setName(String text) {
        textViewName.setText(text);
    }

    public ImageButton getButton() {
        return button;
    }

    public TextView getTextViewName() {
        return textViewName;
    }
}
