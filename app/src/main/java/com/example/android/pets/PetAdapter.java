package com.example.android.pets;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.MyViewHolder> implements Filterable
{

    List<PetRecord> petRecordList;
    private List<PetRecord> petRecordListFull;
    private Context context;

    public PetAdapter(List<PetRecord> petRecordList, Context context) {
        this.petRecordList = petRecordList;
        this.context = context;
        petRecordListFull = new ArrayList<>(petRecordList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater =LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.design,viewGroup,false);
        return new MyViewHolder(view,petRecordList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        PetRecord petRecord =petRecordList.get(i);
        myViewHolder.pet_name.setText(petRecord.getPet_Name_Database());
        myViewHolder.pet_breed.setText(petRecord.getPet_Breed_Database());
        myViewHolder.pet_gender.setText(petRecord.getPet_Gender_Database());
        myViewHolder.pet_weight.setText(petRecord.getPet_Weight_Database());
        myViewHolder.pet_height.setText(petRecord.getPet_Height_Database());

    }

    @Override
    public int getItemCount() {
        return petRecordList.size();
    }

    @Override
    public Filter getFilter() {
        return petRecordFilter;
    }

    private Filter petRecordFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PetRecord> filtered_list = new ArrayList<>();
            if(constraint == null || constraint.length() ==0){
                filtered_list.addAll(petRecordListFull);
            }else {

                String filterpattern = constraint.toString().toLowerCase().trim();
                for (PetRecord record : petRecordListFull ){
                    if(record.getPet_Name_Database().toLowerCase().contains(filterpattern)){
                        filtered_list.add(record);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtered_list;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            petRecordList.clear();
            petRecordList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView pet_name,pet_breed,pet_weight,pet_height,pet_gender;
        CardView cardView;
        private List<PetRecord> list;

        public MyViewHolder(@NonNull View itemView, List<PetRecord> petRecordList) {
            super(itemView);
             pet_name = itemView.findViewById(R.id.pet_name_catalog);
             pet_breed = itemView.findViewById(R.id.pet_breed_catalog);
             pet_weight = itemView.findViewById(R.id.pet_weight_catalog);
             cardView = itemView.findViewById(R.id.card_view);
             cardView.setOnClickListener(this);
             pet_height = itemView.findViewById(R.id.pet_height_catalog);
             pet_gender = itemView.findViewById(R.id.pet_gender_catalog);
             this.list = petRecordList;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(),SinglePetRecord.class);
            intent.putExtra("LIST", (Serializable) list);
            intent.putExtra("position",getAdapterPosition());
            v.getContext().startActivity(intent);
        }
    }
}