package com.example.mobile_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.responses.PortfolioCryptocurrencies;

import java.util.List;

public class PortfolioRecycleAdapter extends RecyclerView.Adapter<PortfolioRecycleAdapter.myViewHolder> {
        public List<PortfolioCryptocurrencies> portfolioCryptocurrenciesList;
        private Context context;
        private final RecyclerViewInterface recyclerViewInterface;

        public PortfolioRecycleAdapter(List<PortfolioCryptocurrencies> portfolioCryptocurrenciesList, Context context,
                               RecyclerViewInterface recyclerViewInterface) {
            this.portfolioCryptocurrenciesList = portfolioCryptocurrenciesList;
            this.context = context;
            this.recyclerViewInterface = recyclerViewInterface;
        }
        public void setPortfolioCryptocurrencies(List<PortfolioCryptocurrencies> portfolioCryptocurrenciesList) {
            this.portfolioCryptocurrenciesList = portfolioCryptocurrenciesList;
            notifyDataSetChanged();
        }

        @Override
        public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_portfolio, parent, false);
            return new myViewHolder(view, recyclerViewInterface);
        }

        @Override
        public void onBindViewHolder(myViewHolder holder, int position) {
            PortfolioCryptocurrencies cryptocurrencies = portfolioCryptocurrenciesList.get(position);
            holder.cryptocurrency.setText(cryptocurrencies.getCryptocurrency());
            holder.amount.setText(cryptocurrencies.getAmount());
        }

        @Override
        public int getItemCount() {
            return portfolioCryptocurrenciesList.size();
        }

        public static class myViewHolder extends RecyclerView.ViewHolder{
            TextView cryptocurrency;
            TextView amount;

            public myViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
                super(itemView);
                cryptocurrency = itemView.findViewById(R.id.cryptocurrency);
                amount = itemView.findViewById(R.id.amount);
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

        public void addPortfolioCryptocurrencies(List<PortfolioCryptocurrencies> cryptocurrencies) {
            for(PortfolioCryptocurrencies cryptos : cryptocurrencies){
                portfolioCryptocurrenciesList.add(cryptos);
                notifyDataSetChanged();
            }

        }
    }
