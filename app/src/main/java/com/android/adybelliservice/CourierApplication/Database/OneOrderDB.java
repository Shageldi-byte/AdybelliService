package com.android.adybelliservice.CourierApplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OneOrderDB extends SQLiteOpenHelper {
    private static final String DBNAME="oneOrder";
    private static final String TBNAME="oneOrder";
    public OneOrderDB(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE "+TBNAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                 "order_id TEXT," +
                 "status TEXT," +
                 "user_id TEXT," +
                 "total TEXT," +
                 "shipping_price TEXT," +
                 "delivery_day TEXT," +
                 "payment_method TEXT," +
                 "address TEXT," +
                 "createdAt TEXT," +
                 "updatedAt TEXT,"+
                 "fullname TEXT,"+
                 "email TEXT,"+
                 "phone TEXT"+
                 ")");
    }

    public boolean insert(String order_id,
                          String status,
                          String user_id,
                          String total,
                          String shipping_price,
                          String delivery_day,
                          String payment_method,
                          String address,
                          String createdAt,
                          String updatedAt,
                          String name,
                          String email,
                          String phone
    ){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("order_id",order_id);
        contentValues.put("status",status);
        contentValues.put("user_id",user_id);
        contentValues.put("total",total);
        contentValues.put("shipping_price",shipping_price);
        contentValues.put("delivery_day",delivery_day);
        contentValues.put("payment_method",payment_method);
        contentValues.put("address",address);
        contentValues.put("createdAt",createdAt);
        contentValues.put("updatedAt",updatedAt);
        contentValues.put("fullname",name);
        contentValues.put("email",email);
        contentValues.put("phone",phone);
        long result=db.insert(TBNAME,null,contentValues);
        if(result==-1){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TBNAME);
        onCreate(db);
    }

    public Cursor getAll(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TBNAME+" ORDER BY ID DESC",null);
        return cursor;
    }

    public Cursor getSelect(String status){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TBNAME+" WHERE status='"+status+"' ORDER BY ID DESC",null);
        return cursor;
    }

    public Cursor getById(String order_id){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TBNAME+" WHERE order_id='"+order_id+"' ORDER BY ID DESC",null);
        return cursor;
    }

    public Cursor getSearch(String status,String orderId){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TBNAME+" WHERE status='"+status+"' AND order_id='"+orderId+"' ORDER BY ID DESC",null);
        return cursor;
    }

    public Cursor customSelect(String  sql){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        return cursor;
    }



    public boolean updateStatus(String order_id,String status){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("status",status);
        db.update(TBNAME,contentValues,"order_id=?",new String[]{order_id});
        return true;
    }



    public Cursor getLastInsertId(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT ID FROM "+TBNAME+" ORDER BY ID DESC LIMIT 1", null);
        return cursor;
    }



    public Integer deleteData(String order_id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TBNAME,"order_id=?",new String[]{order_id});
    }

    public Integer deleteByStatus(String status){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TBNAME,"status=?",new String[]{status});
    }

    public void truncate(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+TBNAME);
        onCreate(db);
    }
}
