package com.example.mobile_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_app.responses.Cryptocurrencies;
import com.example.mobile_app.responses.PortfolioCryptocurrencies;
import com.example.mobile_app.responses.PortfolioResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements RecyclerViewInterface{
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private TextView portfolioValue;
    private Button signOut;
    private ApiInterface apiInterface;
    private PortfolioRecycleAdapter adapter;
    private SearchView searchView;
    private List<PortfolioCryptocurrencies> cryptocurrenciesList = new ArrayList<>();
    private String accessToken;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        recyclerView = getView().findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(requireActivity(), 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PortfolioRecycleAdapter(new ArrayList<>(), requireActivity(), this);
        recyclerView.setAdapter(adapter);

        portfolioValue = getView().findViewById(R.id.portfolioValue);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("SecretsPref", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");

        signOut = getView().findViewById(R.id.signOut);
        signOut.setOnClickListener(view1 -> {
            sharedPreferences.edit().remove("access_token").apply();
            Intent newIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(newIntent);
        });
        fetchData();

    }

    private void filterList(String text) {
        List<PortfolioCryptocurrencies> filteredList = new ArrayList<>();
        for (PortfolioCryptocurrencies cryptocurrencies : cryptocurrenciesList) {
            if (cryptocurrencies.getCryptocurrency().toLowerCase().startsWith(text.toLowerCase())) {
                filteredList.add(cryptocurrencies);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(requireActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setPortfolioCryptocurrencies(filteredList);
        }
    }

    private void fetchData() {
        Call<PortfolioResponse> call = apiInterface.getPortfolio(accessToken);

        call.enqueue(new Callback<PortfolioResponse>() {
            @Override
            public void onResponse(Call<PortfolioResponse> call, Response<PortfolioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PortfolioCryptocurrencies> cryptocurrencies = response.body().getPortfolioCryptocurrencies();
                    adapter.addPortfolioCryptocurrencies(cryptocurrencies);
                    cryptocurrenciesList.addAll(cryptocurrencies);
                    portfolioValue.setText(response.body().getCurrentPortfolioValue().toString() + " $");
                }else{
                    Log.d("ADebugTag", "Response unsuccessful.");
                }

            }

            @Override
            public void onFailure(Call<PortfolioResponse> call, Throwable throwable) {
                Log.e("ADebugTag", "Network Error: " + throwable.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), Transaction.class);
        intent.putExtra("cryptoName", adapter.portfolioCryptocurrenciesList.get(position).getCryptocurrency());
        startActivity(intent);

    }
}
