package viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import ru.matrosov.vladimir.diplom.client.R;

public class UsersViewHolder extends RecyclerView.ViewHolder {
    TextView emailTextView;
    TextView nameTextView;
    TextView postTextView;
    ImageButton button;

    public UsersViewHolder(View itemView) {
        super(itemView);
        this.emailTextView = itemView.findViewById(R.id.user_email);
        this.nameTextView = itemView.findViewById(R.id.user_name);
        this.postTextView = itemView.findViewById(R.id.user_post);
        this.button = itemView.findViewById(R.id.send_message_user);
    }

    public void setEmail(String text) {

        emailTextView.setText(text);
    }

    public void setName(String text) {

        nameTextView.setText(text);
    }

    public void setPost(String text) {

        postTextView.setText(postTextView.getText() + text);
    }

    public void setImage(int image_id){
        button.setImageResource(image_id);
    }

    public void setClick(View.OnClickListener onClickListener){
        button.setOnClickListener(onClickListener);
    }
     public String getEmailText(){
        return emailTextView.getText().toString();
     }
}
