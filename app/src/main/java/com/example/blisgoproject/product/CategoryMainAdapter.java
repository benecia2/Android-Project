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

public class CategoryMainAdapter extends RecyclerView.Adapter<CategoryMainAdapter.CategoryMainViewHolder> {

    private List<Product>productList;

    public CategoryMainAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public void cgList(List<Product> newProductList) {
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
    public CategoryMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.main_category_item, parent, false);
        return new CategoryMainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryMainViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.cgimgTxt.setText(product.getPname());
        String productName = product.getPname();
        if (productName.length() > 6) {
            holder.cgimgTxt.setText(productName.substring(0, 3) + "\n" + productName.substring(3));
        } else {
            holder.cgimgTxt.setText(productName);
        }

        String imgName = product.getPname2();
        int imgResource = holder.itemView.getContext()
                .getResources()
                .getIdentifier(imgName, "drawable", holder.itemView.getContext().getPackageName());
        if (imgResource != 0) {
            holder.cgimg.setImageResource(imgResource);
        } else {

        }

    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    class CategoryMainViewHolder extends RecyclerView.ViewHolder {

        ImageView cgimg;
        TextView cgimgTxt;

        public CategoryMainViewHolder(@NonNull View itemView) {
            super(itemView);
            cgimg = itemView.findViewById(R.id.cgimg);
            cgimgTxt = itemView.findViewById(R.id.cgimgTxt);

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
