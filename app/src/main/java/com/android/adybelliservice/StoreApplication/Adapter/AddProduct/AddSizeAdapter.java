package com.android.adybelliservice.StoreApplication.Adapter.AddProduct;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.StoreApplication.Activity.QRScanner;
import com.android.adybelliservice.StoreApplication.Model.AddProduct.AddSize;
import com.android.adybelliservice.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AddSizeAdapter extends RecyclerView.Adapter<AddSizeAdapter.ViewHolder> {
    private ArrayList<AddSize> arrayList=new ArrayList<>();
    private Context context;

    public AddSizeAdapter(ArrayList<AddSize> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.store_size_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        AddSize item=arrayList.get(holder.getAdapterPosition());

        holder.size.setText(item.getLabel());
        try {
            holder.count.setText(item.getStockQuantity()+"");
        } catch (Exception ex){
            ex.printStackTrace();
        }
        try {
            holder.barcode.setText(item.getBarcode()+"");
        } catch (Exception ex){
            ex.printStackTrace();
        }
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.size()>1) {
                    arrayList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            }
        });

        holder.count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    Integer c=Integer.parseInt(s.toString());
                    arrayList.get(holder.getAdapterPosition()).setStockQuantity(c);
                } catch (Exception ex){
                    ex.printStackTrace();
                    arrayList.get(holder.getAdapterPosition()).setStockQuantity(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.size.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayList.get(holder.getAdapterPosition()).setLabel(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.barcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayList.get(holder.getAdapterPosition()).setBarcode(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.barcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    holder.barcode.clearFocus();
                    holder.barcode.setInputType(InputType.TYPE_NULL);
                    holder.barcode.setFocusableInTouchMode(false);
                    return true;
                }
                return false;
            }
        });

        holder.barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.barcode.clearFocus();
                holder.barcode.setInputType(InputType.TYPE_NULL);
                holder.barcode.setFocusableInTouchMode(false);
                showPopupMenu(holder,holder.getAdapterPosition());
            }
        });
    }

    private void showPopupMenu(ViewHolder holder, int position) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(context, holder.barcode);
        //inflating menu from xml resource
        popup.inflate(R.menu.store_options_menu);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.scan:
                        QRScanner.barcodeEdit= holder.barcode;
                        context.startActivity(new Intent(context, QRScanner.class));
                        holder.barcode.clearFocus();
                        return true;
                    case R.id.write:
                        holder.barcode.setFocusableInTouchMode(true);
                        holder.barcode.setInputType(InputType.TYPE_CLASS_NUMBER);
                        holder.barcode.requestFocus();
//                        InputMethodManager imm = (InputMethodManager)  context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                        return true;
                    default:
                        return false;
                }
            }
        });
        //displaying the popup
        popup.show();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextInputLayout sizeContainer,countContainer;
        private TextInputEditText count,size,barcode;
        private ImageView remove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            remove=itemView.findViewById(R.id.remove);
            countContainer=itemView.findViewById(R.id.countContainer);
            sizeContainer=itemView.findViewById(R.id.sizeContainer);
            size=itemView.findViewById(R.id.size);
            count=itemView.findViewById(R.id.count);
            barcode=itemView.findViewById(R.id.barcode);
        }
    }
}
