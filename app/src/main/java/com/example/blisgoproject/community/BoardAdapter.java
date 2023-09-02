package com.example.blisgoproject.community;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blisgoproject.R;

import java.util.ArrayList;
import java.util.List;


public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {
    private List<Board> boardList;
    private OnItemClickListener onItemClickListener;




    //커뮤니티 검색
    public void setFilteredList(List<Board> filteredList) {
        this.boardList = filteredList;
        notifyDataSetChanged();
    }

    // 인터페이스 정의
    public interface OnItemClickListener{
        void onItemClick(Board board);
    }

    // 외부에서 리스너 설정하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }



    //생성자, 아이템 개수 설정
    public BoardAdapter( List<Board> boardList) {
        this.boardList = boardList;
    }



    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.community_item, parent, false);
        return new BoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        Board board = boardList.get(position);


        holder.tv_title.setText(board.getTitle());
        holder.tv_category.setText(board.getCategory());
        holder.tv_name.setText(board.getName());
        holder.tv_content.setText(board.getContent());
        holder.tv_date.setText(board.getDate());


        holder.tv_content.setVisibility(View.GONE); //화면에 숨김처리

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(board);
                }
            }
        });

    }

    //아이템 개수 설정
    @Override
    public int getItemCount() {
     return  boardList == null?0: boardList.size();
    }


    public class BoardViewHolder extends RecyclerView.ViewHolder {
            TextView tv_title, tv_category, tv_name, tv_content, tv_date;
        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_category = itemView.findViewById(R.id.tv_category);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_date = itemView.findViewById(R.id.tv_date);

        }
    }


}
