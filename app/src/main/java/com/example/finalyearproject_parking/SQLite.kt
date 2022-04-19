package com.example.finalyearproject_parking

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class SQLite (context:Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "parking.db"
        private const val TBL_PARKING = "tbl_parking"
        private const val ID = "id"
        private const val STREET = "street"
        private const val CITY = "city"
        private const val POSTCODE = "postcode"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val createTblParking = ("CREATE TABLE " + TBL_PARKING + "("
                + ID + " INTEGER PRIMARY KEY," + STREET + " TEXT," + CITY + " TEXT,"
                + POSTCODE + " TEXT" + ")" )
        db?.execSQL(createTblParking)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_PARKING")
        onCreate(db)

    }
    fun insertParking(std: ParkingModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(STREET, std.street)
        contentValues.put(CITY, std.city)
        contentValues.put(POSTCODE, std.postcode)

        val success = db.insert(TBL_PARKING, null, contentValues)
        db.close()
        return success
    }

    fun getAllParking(): ArrayList<ParkingModel>{
        val stdList: ArrayList<ParkingModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_PARKING"
        val db = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var street: String
        var city: String
        var postcode: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                street = cursor.getString(cursor.getColumnIndex("street"))
                city = cursor.getString(cursor.getColumnIndex("city"))
                postcode = cursor.getString(cursor.getColumnIndex("postcode"))

                val std = ParkingModel(id = id, street = street, city = city, postcode = postcode)
                stdList.add(std)
            } while (cursor.moveToNext())

            }
            return stdList
        }

        fun updateParking(std: ParkingModel):Int {
            val db = this.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(ID, std.id)
            contentValues.put(STREET, std.street)
            contentValues.put(CITY, std.city)
            contentValues.put(POSTCODE, std.postcode)

            val success = db.update(TBL_PARKING, contentValues, "id" + std.id, null)
            db.close()
            return success





        }

    }
