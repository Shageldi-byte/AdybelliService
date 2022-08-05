package com.android.adybelliservice.CourierApplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BoxDB extends SQLiteOpenHelper {
    private static final String DBNAME="box";
    private static final String TBNAME="box";
    public BoxDB(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE "+TBNAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                 "order_id TEXT," +
                 "name TEXT," +
                 "price TEXT," +
                 "size TEXT," +
                 "count TEXT," +
                 "status TEXT," +
                 "image TEXT," +
                 "delivery_id TEXT," +
                 "od_id TEXT)");
    }

    public boolean insert(String order_id,
                          String name,
                          String price,
                          String size,
                          String count,
                          String status,
                          String image,
                          String delivery_id,
                          String od_id
    ){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("order_id",order_id);
        contentValues.put("name",name);
        contentValues.put("price",price);
        contentValues.put("size",size);
        contentValues.put("count",count);
        contentValues.put("status",status);
        contentValues.put("image",image);
        contentValues.put("delivery_id",delivery_id);
        contentValues.put("od_id",od_id);
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



    public boolean updateStatus(String od_id,String status){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("status",status);
        db.update(TBNAME,contentValues,"od_id=?",new String[]{od_id});
        return true;
    }



    public Cursor getLastInsertId(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT ID FROM "+TBNAME+" ORDER BY ID DESC LIMIT 1", null);
        return cursor;
    }



    public Integer deleteData(String od_id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TBNAME,"od_id=?",new String[]{od_id});
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
