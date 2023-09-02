package com.example.blisgoproject.comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blisgoproject.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> commentList;

    //생성자
    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    //추가
    public void addComment(Comment comment){
        commentList.add(comment);
        notifyDataSetChanged();
    }

    //댓글 목록
    public void setComments(List<Comment> comments){
        commentList = comments;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.tv_comment.setText(comment.getContent());

        //삭제 버튼 클릭 이벤트 처리
        holder.comment_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteComment(comment.getId(), position, holder.itemView.getContext());
            }
        });
    }

    private  void deleteComment(Long id, int position, final Context context){
        CommentService commentService = CommentClient.getInstance().getCommentService();
        Call<Void>call = commentService.deleteComment(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    // 삭제 성공
                    Toast.makeText(context, "댓글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    commentList.remove(position);
                    notifyDataSetChanged();
                }else {
                    // 삭제 실패
                    Toast.makeText(context, "댓글 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // 오류 발생
                Toast.makeText(context, "댓글 삭제 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return commentList == null ? 0 : commentList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView tv_comment;
        AppCompatButton comment_delete;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            comment_delete = itemView.findViewById(R.id.comment_delete);
        }
    }
}
