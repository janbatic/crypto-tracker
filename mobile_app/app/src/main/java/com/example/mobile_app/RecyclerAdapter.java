package com.example.mobile_app;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.responses.Cryptocurrencies;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.myViewHolder> {
    public List<Cryptocurrencies> cryptocurrenciesList;
    private Context context;
    private final RecyclerViewInterface recyclerViewInterface;

    public RecyclerAdapter(List<Cryptocurrencies> cryptocurrenciesList, Context context,
                           RecyclerViewInterface recyclerViewInterface) {
        this.cryptocurrenciesList = cryptocurrenciesList;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    public void setCryptocurrenciesList(List<Cryptocurrencies> cryptocurrenciesList) {
        this.cryptocurrenciesList = cryptocurrenciesList;
        notifyDataSetChanged();
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new myViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Cryptocurrencies cryptocurrencies = cryptocurrenciesList.get(position);
        holder.cryptoName.setText(cryptocurrencies.getName());
        holder.cryptoPrice.setText(cryptocurrencies.getValue());
        holder.cryptoMarketCap.setText(cryptocurrencies.getMarket_cap());
    }

    @Override
    public int getItemCount() {
        return cryptocurrenciesList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView cryptoName;
        TextView cryptoPrice;
        TextView cryptoMarketCap;

        public myViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            cryptoName = itemView.findViewById(R.id.crypto_name);
            cryptoPrice = itemView.findViewById(R.id.crypto_price);
            cryptoMarketCap = itemView.findViewById(R.id.crypto_market_cap);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getBindingAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

    public void addCryptocurrencies(List<Cryptocurrencies> cryptocurrencies) {
        for(Cryptocurrencies cryptos : cryptocurrencies){
            cryptocurrenciesList.add(cryptos);
            notifyDataSetChanged();
        }

    }
}
