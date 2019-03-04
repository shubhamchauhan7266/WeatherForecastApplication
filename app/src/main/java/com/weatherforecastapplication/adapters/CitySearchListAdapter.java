package com.weatherforecastapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weatherforecastapplication.R;
import com.weatherforecastapplication.database.entity.CityDetails;

import java.util.ArrayList;


/**
 * This adapter class is used for City list which come when we search list.
 *
 * @author Shubham Chauhan
 */
public class CitySearchListAdapter extends RecyclerView.Adapter<CitySearchListAdapter.ViewHolder> {

    private ArrayList<CityDetails> mCityList;
    private ICitySearchListAdapterCallBack mICitySearchListCallBack;

    public CitySearchListAdapter(Context context, ArrayList<CityDetails> cityList) {
        mICitySearchListCallBack = (ICitySearchListAdapterCallBack) context;
        mCityList = cityList;
    }

    /**
     * Method is used to set city list
     *
     * @param cityList a list of city
     */
    public void setCityList(ArrayList<CityDetails> cityList) {
        mCityList = cityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_details_item, parent, false);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        viewHolder.tvCityName.setText(mCityList.get(position).name);

        viewHolder.tvCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mICitySearchListCallBack.onCityClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCityList != null ? mCityList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCityName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCityName = itemView.findViewById(R.id.tv_city_name);
        }
    }

    public interface ICitySearchListAdapterCallBack{
        void onCityClick(int position);
    }
}
