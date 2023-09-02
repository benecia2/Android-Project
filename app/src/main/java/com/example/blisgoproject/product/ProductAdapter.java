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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product>productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public void updateProductList(List<Product> newProductList) {
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
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.upproditem, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.updateimgTxt.setText(product.getPname());
        //holder.updateimg.setImageResource(R.);

        String imgName = product.getPname2();
        int imgResource = holder.itemView.getContext()
                .getResources()
                .getIdentifier(imgName, "drawable", holder.itemView.getContext().getPackageName());
        if (imgResource != 0) {
            holder.updateimg.setImageResource(imgResource);
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView updateimg;
        TextView updateimgTxt;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            updateimg = itemView.findViewById(R.id.updateimg);
            updateimgTxt = itemView.findViewById(R.id.updateimgTxt);

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
