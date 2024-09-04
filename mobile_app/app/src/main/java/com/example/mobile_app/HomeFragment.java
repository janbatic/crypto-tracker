package com.example.mobile_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mobile_app.responses.CryptoInfoResponse;
import com.example.mobile_app.responses.Cryptocurrencies ;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements RecyclerViewInterface{

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private ApiInterface apiInterface;
    private RecyclerAdapter adapter;
    private SearchView searchView;
    private int page_number = 1;
    private int totalPages = 0;
    private List<Cryptocurrencies> cryptocurrenciesList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
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

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        adapter = new RecyclerAdapter(new ArrayList<>(), requireActivity(), this);
        recyclerView.setAdapter(adapter);
        fetchData();

    }
    private void filterList(String text) {
        List<Cryptocurrencies> filteredList = new ArrayList<>();
        for(Cryptocurrencies cryptocurrencies: cryptocurrenciesList){
            if(cryptocurrencies.getName().toLowerCase().startsWith(text.toLowerCase())){
                filteredList.add(cryptocurrencies);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(requireActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
        }else{
            adapter.setCryptocurrenciesList(filteredList);
        }
    }
    private void fetchData() {
        Call<CryptoInfoResponse> call = apiInterface.getCryptocurrencies(page_number);

        call.enqueue(new Callback<CryptoInfoResponse>() {
            @Override
            public void onResponse(Call<CryptoInfoResponse> call, Response<CryptoInfoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Cryptocurrencies> cryptocurrencies = response.body().getCryptocurrencies();
                    adapter.addCryptocurrencies(cryptocurrencies);
                    cryptocurrenciesList.addAll(cryptocurrencies);

                    if (page_number == 1) {
                        // Update total pages after the first request
                        totalPages = response.body().getNumPages();
                    }

                    // Check if there are more pages to fetch
                    if (page_number < totalPages) {
                        page_number++;
                        fetchData();  // Fetch the next page
                    } else {
                        Log.d("ADebugTag", "All pages loaded.");
                    }
                } else {
                    Log.d("ADebugTag", "Response unsuccessful.");
                }
            }

            @Override
            public void onFailure(Call<CryptoInfoResponse> call, Throwable throwable) {
                Log.e("ADebugTag", "Network Error: " + throwable.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), Transaction.class);
        intent.putExtra("cryptoName", adapter.cryptocurrenciesList.get(position).getName());
        startActivity(intent);

    }
}