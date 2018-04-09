package viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.matrosov.vladimir.diplom.client.R;

public class ShowChatViewHolder extends RecyclerView.ViewHolder {
    TextView textViewData;
    TextView textViewText;
    TextView textViewNameUser;

    public ShowChatViewHolder(View itemView) {
        super(itemView);
        textViewData = itemView.findViewById(R.id.message_data);
        textViewText = itemView.findViewById(R.id.message_text);
        textViewNameUser = itemView.findViewById(R.id.message_user);
    }

    public void setData(String text) {
        textViewData.setText("Дата отправки: " + text);
    }

    public void setTextEdit(String text) {
        textViewText.setText(text);
    }

    public void setName(String text) {
        textViewNameUser.setText("Пользователь: " + text);
    }
}
