package viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.matrosov.vladimir.diplom.client.R;

public class UsersByChatViewHolder extends RecyclerView.ViewHolder{
    TextView textViewEmail;
    TextView textViewName;
    TextView textViewPost;

    public UsersByChatViewHolder(View itemView) {
        super(itemView);
        textViewEmail = itemView.findViewById(R.id.user_email);
        textViewName = itemView.findViewById(R.id.user_name);
        textViewPost = itemView.findViewById(R.id.user_post);
    }

    public void setEmail(String email) {
        textViewEmail.setText(email);
    }

    public void  setName(String name){
        textViewName.setText(name);
    }

    public void setPost(String post){
        textViewPost.setText(textViewPost.getText() + " " + post);
    }
}
