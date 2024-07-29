package com.example.flowershop.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flowershop.Class.Product;
import com.example.flowershop.Interface.IClickItemProductListener;
import com.example.flowershop.R;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context mContext;
    private List<Product> mProducts;
    private IClickItemProductListener iClickItemProductListener;
    public ProductAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<Product> list, IClickItemProductListener mListener) {
        this.mProducts = list;
        this.iClickItemProductListener = mListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mProducts.get(position);
        if(product == null) {
            return;
        }
        holder.tvNameProduct.setText(product.getName());
        // Tạo instance của lớp DecimalFormat với định dạng tiền tệ Việt Nam
        DecimalFormat f = new DecimalFormat("###,###,###đ");
        holder.tvPriceProduct.setText(f.format(product.getPrice()));
        Uri uri = Uri.parse(product.getImagePath());
        Glide.with(holder.imvProduct.getContext()).load(uri).into(holder.imvProduct);

        holder.layoutItemProduct.setOnClickListener(v -> {
            iClickItemProductListener.onClickItemProduct(product);
        });

        holder.tvAddCart.setOnClickListener(v -> {
            iClickItemProductListener.onClickAddToCart(product);
        });
    }


    @Override
    public int getItemCount() {
        if(mProducts != null) {
            return mProducts.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imvProduct;
        TextView tvNameProduct, tvPriceProduct, tvAddCart;
        CardView layoutItemProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imvProduct = itemView.findViewById(R.id.img_product);
            tvNameProduct = itemView.findViewById(R.id.tv_nameProduct);
            tvPriceProduct = itemView.findViewById(R.id.tv_priceProduct);
            layoutItemProduct = itemView.findViewById(R.id.layout_item_product);
            tvAddCart = itemView.findViewById(R.id.tv_addCart);
        }
    }
}
