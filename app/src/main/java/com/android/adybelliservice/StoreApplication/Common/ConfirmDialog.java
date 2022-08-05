package com.android.adybelliservice.StoreApplication.Common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.android.adybelliservice.databinding.StoreConfirmDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ConfirmDialog {
    private String title;
    private String message;
    private String okText;
    private String cancelText;
    private Context context;
    private BottomSheetDialog bottomSheetDialog;
    private StoreConfirmDialogBinding binding;
    public interface ConfirmDialogListener{
        void onOkPressed();
        void onCancelPressed();
    }
    public ConfirmDialog(Context context) {
        this.context = context;
        binding=StoreConfirmDialogBinding.inflate(LayoutInflater.from(context));
        createBottomSheet();
    }

    private void createBottomSheet() {
        bottomSheetDialog=new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(binding.getRoot());
        binding.ok.setTypeface(Utils.getMediumFont(context));
        binding.cancel.setTypeface(Utils.getMediumFont(context));
        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setLoading(false);
    }

    public Button getCancelButton(){
        return binding.cancel;
    }

    public Button getOkButton(){
        return binding.ok;
    }

    public BottomSheetDialog getDialog(){
        return bottomSheetDialog;
    }

    public void setTitle(String title){
        this.title=title;
        binding.title.setVisibility(View.VISIBLE);
    }
    public void setMessage(String message){
        this.message=message;
        binding.message.setVisibility(View.VISIBLE);
    }

    public void setOkText(String okText){
        this.okText=okText;
        binding.ok.setVisibility(View.VISIBLE);
    }

    public void setCancelText(String cancelText){
        this.cancelText=cancelText;
        binding.cancel.setVisibility(View.VISIBLE);
    }

    public void setLoading(boolean loading){
        if(loading){
            binding.ok.setText("");
            binding.loadingButton.setVisibility(View.VISIBLE);
        } else {
            binding.ok.setText(okText);
            binding.loadingButton.setVisibility(View.GONE);
        }
    }

    public void show(){
        binding.title.setText(title);
        binding.message.setText(message);
        binding.ok.setText(okText);
        binding.cancel.setText(cancelText);
        bottomSheetDialog.show();
    }

    public void setConfirmDialogListener(ConfirmDialogListener listener){
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancelPressed();
            }
        });
        binding.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOkPressed();
            }
        });
    }

    public void dismiss(){
        bottomSheetDialog.dismiss();
    }



}
