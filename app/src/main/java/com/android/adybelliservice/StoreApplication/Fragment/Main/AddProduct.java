package com.android.adybelliservice.StoreApplication.Fragment.Main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.adybelliservice.StoreApplication.Activity.FilterActivity;
import com.android.adybelliservice.StoreApplication.Activity.SelectCategoryActivity;
import com.android.adybelliservice.StoreApplication.Adapter.AddProduct.AddSizeAdapter;
import com.android.adybelliservice.StoreApplication.Adapter.AddProduct.AdditionalInformationAdapter;
import com.android.adybelliservice.StoreApplication.Adapter.AddProduct.ColorAdapter;
import com.android.adybelliservice.StoreApplication.Adapter.AddProduct.DefaultItemAdapter;
import com.android.adybelliservice.StoreApplication.Adapter.AddProduct.SelectedImagesAdapter;
import com.android.adybelliservice.StoreApplication.Api.APIClient;
import com.android.adybelliservice.StoreApplication.Api.ApiInterface;
import com.android.adybelliservice.StoreApplication.Common.ConfirmDialog;
import com.android.adybelliservice.StoreApplication.Common.FilterType;
import com.android.adybelliservice.StoreApplication.Common.ImageStatus;
import com.android.adybelliservice.StoreApplication.Common.RealPathUtil;
import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Model.AddProduct.AddProductBody;
import com.android.adybelliservice.StoreApplication.Model.AddProduct.AddResponse;
import com.android.adybelliservice.StoreApplication.Model.AddProduct.AddSize;
import com.android.adybelliservice.StoreApplication.Model.AddProduct.AdditionalInformation;
import com.android.adybelliservice.StoreApplication.Model.AddProduct.DefaultItem;
import com.android.adybelliservice.StoreApplication.Model.AddProduct.ImageResponse;
import com.android.adybelliservice.StoreApplication.Model.AddProduct.SelectedImage;
import com.android.adybelliservice.StoreApplication.Model.Filter.Brands;
import com.android.adybelliservice.StoreApplication.Model.Filter.Category;
import com.android.adybelliservice.StoreApplication.Model.Filter.Color;
import com.android.adybelliservice.StoreApplication.Model.Filter.GetAddInfo;
import com.android.adybelliservice.StoreApplication.Model.GBody;
import com.android.adybelliservice.R;
import com.android.adybelliservice.databinding.StoreAddProductSelectDialogBinding;
import com.android.adybelliservice.databinding.StoreFragmentAddProductBinding;
import com.flod.loadingbutton.LoadingButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;
import java.nio.channels.Selector;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;


public class AddProduct extends Fragment {


    private Context context;
    private StoreFragmentAddProductBinding binding;
    private ArrayList<AdditionalInformation> additionalInformations = new ArrayList<>();
    private int additionalCount = 1;
    private ArrayList<AddSize> addSizeArrayList = new ArrayList<>();
    private int sizeCount = 1;
    private ArrayList<SelectedImage> images = new ArrayList<>();
    public static AddProduct INSTANCE;
    private ArrayList<Brands> brands = new ArrayList<>();
    private ArrayList<Color> colors = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();
    private ApiInterface apiInterface;
    double price = 0.0;
    double discountPrice = 0.0;
    // Selected items
    public static ArrayList<String> selected_category = new ArrayList<>();
    public static String selected_brand = "-1";
    public static String selected_brand_title = "";
    public static String selected_color = "-1";
    public static String selected_color_title = "-1";

    private Integer uploadImagePos = 0;

    private ArrayList<ImageResponse> imageResponses=new ArrayList<>();


    public AddProduct() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = StoreFragmentAddProductBinding.inflate(inflater, container, false);
        context = getContext();
        INSTANCE = this;
        setFonts(context, binding.root);
        additionalInformations.add(new AdditionalInformation(additionalCount, "", ""));
        addSizeArrayList.add(new AddSize("", 1,""));
        getAddInfo();
        setViewFonts();
        setAdditional();
        setSizes();
        return binding.getRoot();
    }

    private void getAddInfo() {
        binding.indicator.setVisibility(View.VISIBLE);
        binding.con2.setAlpha(0.2f);
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GBody<GetAddInfo>> call = apiInterface.getAddInfo("5c249d738fc4a4ae6f2844c0b744e06a", "Bearer " + Utils.getSharedPreference(context, "token"));
        call.enqueue(new Callback<GBody<GetAddInfo>>() {
            @Override
            public void onResponse(Call<GBody<GetAddInfo>> call, Response<GBody<GetAddInfo>> response) {
                if (response.isSuccessful()) {
                    binding.indicator.setVisibility(View.GONE);
                    binding.con2.setAlpha(1.0f);
                    setEnabled(true);
                    setListener();
                    if (response.body().getBody() != null) {
                        if (response.body().getBody().getBrands() != null && response.body().getBody().getBrands().size() > 0) {
                            brands = response.body().getBody().getBrands();
                        }
                        if (response.body().getBody().getColors() != null && response.body().getBody().getColors().size() > 0) {
                            colors = response.body().getBody().getColors();
                        }
                        if (response.body().getBody().getCategories() != null && response.body().getBody().getCategories().size() > 0) {
                            categories = response.body().getBody().getCategories();
                        }
                    }
                } else {
                    setEnabled(false);
                    try {
                        String msg = Utils.checkMessage(response.body().getMessage(), context);
                        Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_LONG).show();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Snackbar.make(binding.getRoot(), getResources().getString(R.string.something_went_wrong), Snackbar.LENGTH_LONG).show();
                    }
                    showTryAgain();
                }
                binding.swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<GBody<GetAddInfo>> call, Throwable t) {
                Snackbar.make(binding.getRoot(), t.getMessage(), Snackbar.LENGTH_LONG).show();
                showTryAgain();
                setEnabled(false);
                binding.swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void showTryAgain() {
        ConfirmDialog confirmDialog = new ConfirmDialog(context);
        confirmDialog.setCancelText(context.getResources().getString(R.string.cancel));
        confirmDialog.setOkText(context.getResources().getString(R.string.try_again));
        confirmDialog.setMessage(context.getResources().getString(R.string.cant_get));
        confirmDialog.setTitle(context.getResources().getString(R.string.attention));
        confirmDialog.setConfirmDialogListener(new ConfirmDialog.ConfirmDialogListener() {
            @Override
            public void onOkPressed() {
                confirmDialog.dismiss();
                getAddInfo();
            }

            @Override
            public void onCancelPressed() {
                confirmDialog.dismiss();
            }
        });
        confirmDialog.show();
    }

    private void setEnabled(Boolean isEnabled) {
        for (int i = 0; i < binding.con2.getChildCount(); i++) {
            View view = binding.con2.getChildAt(i);
            view.setEnabled(isEnabled); // Or whatever you want to do with the view.
        }
    }

    private void setSizes() {
        binding.sizeRec.setAdapter(new AddSizeAdapter(addSizeArrayList, context));
        binding.sizeRec.setLayoutManager(new LinearLayoutManager(context));
    }

    private void setAdditional() {
        binding.additionalInformationRec.setAdapter(new AdditionalInformationAdapter(additionalInformations, context));
        binding.additionalInformationRec.setLayoutManager(new LinearLayoutManager(context));
    }

    public RecyclerView getImageRecycler() {
        return binding.imgRec;
    }

    public RelativeLayout getImageCon() {
        return binding.imgCon;
    }


    private void setListener() {
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAddInfo();
            }
        });
        binding.addAdditionalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additionalCount++;
                additionalInformations.add(new AdditionalInformation(additionalCount, "", ""));
                try {
                    binding.additionalInformationRec.getAdapter().notifyDataSetChanged();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        binding.addSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeCount++;
                addSizeArrayList.add(new AddSize("", 1,""));
                try {
                    binding.sizeRec.getAdapter().notifyDataSetChanged();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        binding.addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 12);
                    Snackbar.make(binding.root, R.string.set_permission, Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }
                            })
                            .show();
                    return;

                }
                startGallery();

            }
        });

        binding.selectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCategoryActivity.filters = categories;
                Intent intent = new Intent(context, SelectCategoryActivity.class);
                someActivityResultLauncher.launch(intent);
            }
        });


//        binding.color.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    binding.color.requestFocus();
//                }
//            }
//        });

        binding.color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(context.getResources().getString(R.string.color), colors, FilterType.COLOR);
            }
        });

//        binding.brand.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    binding.brand.requestFocus();
//                }
//            }
//        });

        binding.brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(context.getResources().getString(R.string.brand), brands, FilterType.DEFAULT);
            }
        });


        binding.okMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                boolean isOkDesc=true;
////                for(AdditionalInformation additionalInformation:additionalInformations){
////                    if(additionalInformation.getText().trim().isEmpty() || additionalInformation.getText_ru().trim().isEmpty()){
////                        isOkDesc=false;
////                    }
////                }
////                if(!isOkDesc){
////                    Toast.makeText(context, context.getResources().getString(R.string.add_additional_info_must), Toast.LENGTH_SHORT).show();
////                    return;
////                }

                boolean isOkSize=true;
                for(AddSize addSize:addSizeArrayList){
                    if(addSize.getLabel().trim().isEmpty() || addSize.getBarcode().trim().isEmpty()){
                        isOkSize=false;
                    }
                }
                if(!isOkSize){
                    Toast.makeText(context, context.getResources().getString(R.string.add_size_must), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selected_category.size()<=0){
                    Toast.makeText(context, context.getResources().getString(R.string.select_must_category), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(binding.productName.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, context.getResources().getString(R.string.must_name), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(binding.productNameRu.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, context.getResources().getString(R.string.must_name), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(images.size()<=0){
                    Toast.makeText(context, context.getResources().getString(R.string.must_images), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(binding.price.getText().toString().trim().isEmpty() || binding.price.getText().toString().length()<=0){
                    Toast.makeText(context, context.getResources().getString(R.string.must_price), Toast.LENGTH_SHORT).show();
                    return;
                }

                try{
                    price=Double.parseDouble(binding.price.getText().toString());
                } catch (Exception ex){
                    ex.printStackTrace();
                }


                try{
                    discountPrice=Double.parseDouble(binding.discountPrice.getText().toString());
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                imageResponses.clear();
                binding.okMaterial.setVisibility(View.GONE);
                binding.loadingButton.setVisibility(View.VISIBLE);
                uploadImage(images.get(uploadImagePos).getUri());
            }
        });
    }

    private void uploadImage(Uri uri) {
        binding.indicator.setVisibility(View.VISIBLE);
//        binding.con2.setAlpha(0.2f);
        setImageStatus(uploadImagePos, ImageStatus.IN_PROGRESS);

        File file = new File(RealPathUtil.getRealPath(context, uri));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GBody<ImageResponse>> call = apiInterface.addProductImage(filePart, "Bearer " + Utils.getSharedPreference(context, "token"));
        call.enqueue(new Callback<GBody<ImageResponse>>() {
            @Override
            public void onResponse(Call<GBody<ImageResponse>> call, Response<GBody<ImageResponse>> response) {
                if (response.isSuccessful()) {
                    Log.e("Res image", response.body().getBody().getSmall());
                    setImageStatus(uploadImagePos, ImageStatus.SUCCESS);
                } else {
                    setImageStatus(uploadImagePos, ImageStatus.ERROR);
                    Toast.makeText(context, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
                imageResponses.add(response.body().getBody());
                uploadImagePos++;
                if (uploadImagePos < images.size() && images.get(uploadImagePos).getType() != 2) {
                    uploadImage(images.get(uploadImagePos).getUri());
                } else {
                    addProduct();
                }
                binding.indicator.setVisibility(View.GONE);
                binding.con2.setAlpha(1f);
            }

            @Override
            public void onFailure(Call<GBody<ImageResponse>> call, Throwable t) {
                Log.e("Error", t.getMessage());
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.indicator.setVisibility(View.GONE);
                binding.con2.setAlpha(1f);
                setImageStatus(uploadImagePos, ImageStatus.ERROR);
                if (uploadImagePos <= images.size()) {
                    uploadImagePos++;
                    uploadImage(images.get(uploadImagePos).getUri());
                }
            }
        });
    }

    private void addProduct() {

        apiInterface=APIClient.getClient().create(ApiInterface.class);
        StringBuilder categories = new StringBuilder();
        for(String str:selected_category){
            if(str.equals(selected_category.get(selected_category.size()-1))){
                categories.append(str);
            } else {
                categories.append(str).append(",");
            }
        }
        AddProductBody body=new AddProductBody(categories.toString(),
                binding.productName.getText().toString(),
                binding.productNameRu.getText().toString(),
                price,
                discountPrice,
                "store-"+Utils.getSharedPreference(context,"user_id"),
                selected_brand,
                selected_color,
                additionalInformations,
                addSizeArrayList,
                imageResponses
                );
        Call<GBody<AddResponse>> call= apiInterface.addProduct("5c249d738fc4a4ae6f2844c0b744e06a","Bearer "+Utils.getSharedPreference(context,"token"),body);
        call.enqueue(new Callback<GBody<AddResponse>>() {
            @Override
            public void onResponse(Call<GBody<AddResponse>> call, Response<GBody<AddResponse>> response) {
                MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(context);
                if(response.isSuccessful()){
                   alertDialogBuilder.setTitle(R.string.successfully);
                   alertDialogBuilder.setMessage(R.string.product_success);


                    binding.loadingButton.setVisibility(View.GONE);
                    binding.okMaterial.setVisibility(View.VISIBLE);

                    clearValues();
                } else {
                    alertDialogBuilder.setTitle(R.string.something_went_wrong);
                    alertDialogBuilder.setMessage("Code: "+response.code());
                    binding.loadingButton.setVisibility(View.GONE);
                    binding.okMaterial.setVisibility(View.VISIBLE);
                }
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();

                uploadImagePos=0;
            }

            @Override
            public void onFailure(Call<GBody<AddResponse>> call, Throwable t) {
                MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(context);
                alertDialogBuilder.setTitle(R.string.something_went_wrong);
                alertDialogBuilder.setMessage(t.getMessage());
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();
                binding.loadingButton.setVisibility(View.GONE);
                binding.okMaterial.setVisibility(View.VISIBLE);
                uploadImagePos=0;
            }
        });

    }

    private void clearValues() {
        binding.productNameRu.setText("");
        binding.productName.setText("");
        binding.price.setText("0");
        binding.discountPrice.setText("0");
        selected_brand="-1";
        binding.brand.setText("");
        binding.color.setText("");
        selected_color="-1";
        selected_category.clear();
        additionalInformations.clear();
        addSizeArrayList.clear();
        images.clear();
        additionalInformations.add(new AdditionalInformation(additionalCount, "", ""));
        addSizeArrayList.add(new AddSize("", 1,""));
        binding.selectedCategory.setText("0");
        setAdditional();
        setSizes();
        binding.imgCon.setVisibility(View.GONE);
        binding.imgRec.setVisibility(View.VISIBLE);
        images.add(new SelectedImage(null, 2, ImageStatus.DEFAULT));
        binding.imgRec.setAdapter(new SelectedImagesAdapter(images, context));
        binding.imgRec.setLayoutManager(new GridLayoutManager(context, 3));
    }

    private Bitmap getBitmap(int drawableRes) {
        Drawable drawable = context.getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private void setImageStatus(int i, String status) {
        try {
            images.get(i).setStatus(status);
            binding.imgRec.getAdapter().notifyDataSetChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public void startGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }

    public ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 132) {
                        // There are no request codes
                        binding.selectedCategory.setText(selected_category.size() + "");

                    } else if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        if (data.getClipData() != null) {
                            if (images.size() > 0 && images.get(images.size() - 1).getType() == 2) {
                                images.remove(images.size() - 1);
                            }
                            int count = data.getClipData().getItemCount();
                            for (int i = 0; i < count; i++) {
                                Uri imageUri = data.getClipData().getItemAt(i).getUri();
                                images.add(new SelectedImage(imageUri, 1, ImageStatus.DEFAULT));
                                Log.e("URI", imageUri.toString());

                            }
                            if (count > 0) {
                                binding.imgCon.setVisibility(View.GONE);
                                binding.imgRec.setVisibility(View.VISIBLE);
                                images.add(new SelectedImage(null, 2, ImageStatus.DEFAULT));
                                binding.imgRec.setAdapter(new SelectedImagesAdapter(images, context));
                                binding.imgRec.setLayoutManager(new GridLayoutManager(context, 3));
                            }
                        } else {
                            if (images.size() > 0 && images.get(images.size() - 1).getType() == 2) {
                                images.remove(images.size() - 1);
                            }
                            Uri uri = data.getData();
                            images.add(new SelectedImage(uri, 1, ImageStatus.DEFAULT));
                            images.add(new SelectedImage(null, 2, ImageStatus.DEFAULT));
                            binding.imgCon.setVisibility(View.GONE);
                            binding.imgRec.setVisibility(View.VISIBLE);
                            binding.imgRec.setAdapter(new SelectedImagesAdapter(images, context));
                            binding.imgRec.setLayoutManager(new GridLayoutManager(context, 3));
                        }
                    }
                }
            });

    private void setViewFonts() {
        binding.addAdditionalInformation.setTypeface(Utils.getRegularFont(context));
        binding.okMaterial.setTypeface(Utils.getMediumFont(context));
    }

    private void setFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                if (v instanceof TextInputLayout) {
                    ((TextInputLayout) v).setTypeface(Utils.getRegularFont(context));
                }
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    setFonts(context, child);
                }
            } else if (v instanceof TextInputEditText) {
                ((TextInputEditText) v).setTypeface(Utils.getRegularFont(context));

            }
        } catch (Exception e) {
        }
    }

    private <T> void showDialog(String title, ArrayList<T> arrayList, String type) {
        Dialog dialog = new Dialog(context);
        StoreAddProductSelectDialogBinding selectDialogBinding = StoreAddProductSelectDialogBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(selectDialogBinding.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        selectDialogBinding.title.setText(title);
        selectDialogBinding.search.setTypeface(Utils.getRegularFont(context));
        selectDialogBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (type.equals(FilterType.DEFAULT)) {
            selectDialogBinding.rec.setAdapter(new DefaultItemAdapter((ArrayList<Brands>) arrayList, context));
            selectDialogBinding.rec.setLayoutManager(new LinearLayoutManager(context));
        }
        if (type.equals(FilterType.COLOR)) {
            selectDialogBinding.rec.setAdapter(new ColorAdapter((ArrayList<Color>) arrayList, context));
            selectDialogBinding.rec.setLayoutManager(new GridLayoutManager(context, 4));
        }

        selectDialogBinding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    if (type.equals(FilterType.DEFAULT)) {
                        selectDialogBinding.rec.setAdapter(new DefaultItemAdapter((ArrayList<Brands>) arrayList, context));
                        selectDialogBinding.rec.setLayoutManager(new LinearLayoutManager(context));
                    }
                    if (type.equals(FilterType.COLOR)) {
                        selectDialogBinding.rec.setAdapter(new ColorAdapter((ArrayList<Color>) arrayList, context));
                        selectDialogBinding.rec.setLayoutManager(new GridLayoutManager(context, 4));
                    }
                } else {

                    if (type.equals(FilterType.DEFAULT)) {
                        ArrayList<Brands> searchArray=new ArrayList<>();
                        for(Brands brand:(ArrayList<Brands>) arrayList){
                            if(brand.getTitle().toUpperCase().contains(s.toString().toUpperCase())){
                                searchArray.add(brand);
                            }
                        }
                        Log.e("Size",s.toString().toUpperCase());
                        selectDialogBinding.rec.setAdapter(new DefaultItemAdapter(searchArray, context));
                        selectDialogBinding.rec.setLayoutManager(new LinearLayoutManager(context));
                    }
                    if (type.equals(FilterType.COLOR)) {
                        ArrayList<Color> searchArray=new ArrayList<>();
                        for(Color color:(ArrayList<Color>) arrayList){
                            String tm=color.getColor()+"";
                            String ru=color.getColor_ru()+"";
                            if(tm.toUpperCase().contains(s.toString().toUpperCase()) || ru.toUpperCase().contains(s.toString().toUpperCase())){
                                searchArray.add(color);
                            }
                        }
                        Log.e("Size",searchArray.size()+"");
                        selectDialogBinding.rec.setAdapter(new ColorAdapter(searchArray, context));
                        selectDialogBinding.rec.setLayoutManager(new GridLayoutManager(context, 4));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(!selected_brand.equals("-1")){
                    binding.brand.setText(selected_brand_title);
                }

                if(!selected_color.equals("-1")){
                    binding.color.setText(selected_color_title);
                }
            }
        });

        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialog.show();
    }
}