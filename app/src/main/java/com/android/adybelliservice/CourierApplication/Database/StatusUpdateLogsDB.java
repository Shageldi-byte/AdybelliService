package com.android.adybelliservice.CourierApplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StatusUpdateLogsDB extends SQLiteOpenHelper {
    private static final String DBNAME="updateLogs";
    private static final String TBNAME="updateLogs";
    public StatusUpdateLogsDB(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE "+TBNAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                 "od_id TEXT," +
                 "status TEXT," +
                 "delivery_id TEXT" +
                 ")");
    }

    public boolean insert(String od_id,
                          String status,
                          String delivery_id

    ){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("od_id",od_id);
        contentValues.put("status",status);
        contentValues.put("delivery_id",delivery_id);
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

    public Cursor getSearch(String status,String od_id){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TBNAME+" WHERE status='"+status+"' AND od_id='"+od_id+"' ORDER BY ID DESC",null);
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
