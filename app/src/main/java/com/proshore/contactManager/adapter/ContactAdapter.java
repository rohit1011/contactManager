package com.proshore.contactManager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.proshore.contactManager.R;
import com.proshore.contactManager.model.ContactModel;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{
    private Context mContext;
    private List<ContactModel> contactModels;

    public ContactAdapter(Context mContext, List<ContactModel> contactModels, onContactClickListner clickDelete, onContactClickListner clickEdit) {
        this.mContext = mContext;
        this.contactModels = contactModels;
        this.clickDelete = clickDelete;
        this.clickEdit = clickEdit;
    }

    private onContactClickListner clickDelete;
    private onContactClickListner clickEdit;


    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contactlists,viewGroup,false);
        final ContactAdapter.ViewHolder viewHolder= new ContactAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactAdapter.ViewHolder viewHolder, int i) {
        viewHolder.name.setText(contactModels.get(i).getContactName());
        viewHolder.contact.setText(contactModels.get(i).getContactNumber());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEdit.onContactClick(v,viewHolder.getAdapterPosition());
            }
        }        );
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDelete.onContactClick(v,viewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (contactModels.size()!=0){
            return contactModels.size();
        }
        else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,contact;
        ImageView edit,delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            contact = itemView.findViewById(R.id.number);

            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }
    public interface onContactClickListner{
        void onContactClick(View v, int pos);
    }
}
