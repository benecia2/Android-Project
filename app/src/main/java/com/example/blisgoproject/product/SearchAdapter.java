package com.example.blisgoproject.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blisgoproject.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Product>productList;

    public SearchAdapter(List<Product> productList) {
        this.productList = productList;
    }

    //리스트 출력
    public void searchList(List<Product> newProductList) {
        productList.clear();
        productList.addAll(newProductList);
        notifyDataSetChanged();
    }

    //인터페이스
    public interface ItemClickListener {
        void ItemClick(int pos);
    }
    private ItemClickListener itemClickListener;
    //setter
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    //Main에 객체 전달
    public Product getItem(int position) {
        return productList.get(position);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.searchitem, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Product product =productList.get(position);
        holder.productname.setText(product.getPname());

        String imgName = product.getPname2();
        int imgResource = holder.itemView.getContext()
                .getResources()
                .getIdentifier(imgName, "drawable", holder.itemView.getContext().getPackageName());
        if (imgResource != 0) {
            holder.productimg.setImageResource(imgResource);
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }


    class SearchViewHolder extends RecyclerView.ViewHolder {

        ImageView productimg;
        TextView productname;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            productimg = itemView.findViewById(R.id.productimg);
            productname = itemView.findViewById(R.id.productname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    itemClickListener.ItemClick(position);
                }
            });
        }
    }

}
