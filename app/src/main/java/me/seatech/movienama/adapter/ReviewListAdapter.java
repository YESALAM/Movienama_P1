package me.seatech.movienama.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.seatech.movienama.R;
import me.seatech.movienama.schemas.Comment;

/**
 * Created by yesalam on 4/9/16.
 */
public class ReviewListAdapter extends BaseAdapter {

    private Context context ;
    List<Comment> comments ;

    public ReviewListAdapter(Context context,List<Comment> comments){
        this.context = context ;
        this.comments = comments ;
    }



    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View reviewLayout = inflater.inflate(R.layout.review_layout, parent, false);
        TextView author = (TextView) reviewLayout.findViewById(R.id.author_review) ;
        TextView content = (TextView) reviewLayout.findViewById(R.id.content_review) ;

        author.setText(comments.get(position).getAuthor());
        content.setText(comments.get(position).getContent());
        return reviewLayout;
    }
}
