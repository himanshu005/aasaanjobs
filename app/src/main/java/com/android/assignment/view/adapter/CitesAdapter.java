package com.android.assignment.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.assignment.R;
import com.android.assignment.service.model.Objects;

import java.util.ArrayList;
import java.util.List;


public class CitesAdapter extends RecyclerView.Adapter<CitesAdapter.MyViewHolder> implements Filterable {

    private static final String TAG = CitesAdapter.class.getName();
    private final CitesAdapterListener mListener;
    private List<Objects> mObjectsList;
    private List<Objects> mObjectsFilterList;

    public CitesAdapter(List<Objects> objectsList, CitesAdapterListener listener) {
        this.mListener = listener;
        this.mObjectsList = objectsList;
        this.mObjectsFilterList = objectsList;
    }

    public void setProjectList(List<Objects> projectList) {
        this.mObjectsList = projectList;
        this.mObjectsFilterList = projectList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Objects objects = mObjectsFilterList.get(position);
        Log.v(TAG, "objects "+ objects);
        holder.tvCity.setText(objects.getName());
    }

    @Override
    public int getItemCount() {
        return mObjectsFilterList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {

                    mObjectsFilterList = mObjectsList;
                } else {
                    List<Objects> filteredList = new ArrayList<>();
                    for (Objects row : mObjectsList) {
                      if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    mObjectsFilterList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mObjectsFilterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mObjectsFilterList = (ArrayList<Objects>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCity;
        public LinearLayout ll;

        MyViewHolder(View view) {
            super(view);
            tvCity = view.findViewById(R.id.tvCity);
            ll = view.findViewById(R.id.ll);
            view.setOnClickListener(v -> mListener.onCitesSelected(mObjectsFilterList.get(getAdapterPosition())));
        }

    }
    public interface CitesAdapterListener {
        void onCitesSelected(Objects contact);
    }
}