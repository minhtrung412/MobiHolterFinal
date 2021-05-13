package com.example.mobiholterfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiholterfinal.HistoryView;
import com.example.mobiholterfinal.R;
import com.example.mobiholterfinal.data.DBManager;
import com.example.mobiholterfinal.model.UserData;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private List<UserData> userDataList;

    String data_receive_1, data_receive_2, data_receive_3, data_receive_time;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView tv_name,tv_time, textViewOptions;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            textViewOptions = itemView.findViewById(R.id.textViewOptions);

            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            menu.add(this.getAdapterPosition(), 1, 0,"Delete");
            menu.add(this.getAdapterPosition(), 2, 1,"View");

        }

    }

    public CustomAdapter(Context context, List<UserData> userDataList){
        this.userDataList= userDataList;
        this.context = context;
        ;
    }


    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.history_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)  {
        final UserData userData = userDataList.get(position)  ;
        holder.tv_name.setText(String.valueOf(userData.getmName()));
        holder.tv_time.setText(String.valueOf(userData.getmTime()));
        holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.menu_delete:
                                DBManager dbManager= new DBManager(context);
                                userDataList.remove(position);
                                dbManager.deleteUserData(userData.getmID(),context);
                                notifyDataSetChanged();
                                return false;
                            case R.id.menu_view:
                                DBManager dbManager1 = new DBManager(context);
                                Cursor cursor = dbManager1.getData(userData.getmID(),context);
                                if (cursor.getCount() == 0){
                                } else {
                                    while(cursor.moveToNext()){
                                        data_receive_1 = cursor.getString(3);
                                        data_receive_2 = cursor.getString(4);
                                        data_receive_3 = cursor.getString(5);
                                        data_receive_time = cursor.getString(6);
                                    }
                                }
                                Intent intent = new Intent(context, HistoryView.class);
                                intent.putExtra("Data1", data_receive_1);
                                intent.putExtra("Data2", data_receive_2);
                                intent.putExtra("Data3", data_receive_3);
                                intent.putExtra("DataTime", data_receive_time);

                                v.getContext().startActivity(intent);

                        }

                        return false;

                    }
                });
                popupMenu.show();



            }
        });


    }







    @Override
    public int getItemCount() {
        return userDataList.size();
    }



}
