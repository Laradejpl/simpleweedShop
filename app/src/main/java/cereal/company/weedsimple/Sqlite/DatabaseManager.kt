package cereal.company.weedsimple.Sqlite


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseManager (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,
    DATABASE_VERSION) {



    companion object {

        //general database
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "product.db"
        //product
        private const val TABLE_PRODUCTS = "product"
        private const val ID_PRODUCTS  = "ID"
        private const val TITLE = "titre"
        private const val POSTER_PATH = "poster_path"
        private const val EMAIL_USER = "emailUser"
        private const val PRICE_PRODUCT = "price_product"





    }

    override fun onCreate(db: SQLiteDatabase?) {
        //movies product
        val createProductDB = "CREATE TABLE $TABLE_PRODUCTS ($ID_PRODUCTS INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$TITLE TEXT," +
                "$POSTER_PATH TEXT," +
                "$PRICE_PRODUCT INTEGER,"+
                "$EMAIL_USER TEXT )"



        db?.execSQL(createProductDB)


    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {


        db?.execSQL(" DROP TABLE IF EXISTS $TABLE_PRODUCTS")

    }




    fun insertProductDataBase (title : String?,  posterePath:String? , emailUser: String? ,price_product: Int) : Boolean{

        val db: SQLiteDatabase = this.writableDatabase
        val cv  = ContentValues()
        cv.put(TITLE, title)
        cv.put(POSTER_PATH, posterePath)
        cv.put(EMAIL_USER,emailUser)
        cv.put(PRICE_PRODUCT,price_product)
        val result = db.insert(TABLE_PRODUCTS,null,cv)
        val error : Long = -1
        return result != error


    }


    fun deleteFavorite (title: String?): Boolean{

        val db : SQLiteDatabase = this.writableDatabase




        var sql = "SELECT * FROM $TABLE_PRODUCTS WHERE $TITLE =?"

        var cursor = db.rawQuery(sql, arrayOf(title))


        if (cursor.count >0){


            var result = db.delete(TABLE_PRODUCTS, "$TITLE =? ", arrayOf(title))

            return true
        }

        return false

    }


    fun getUserFavs (emailUser: String?) : Cursor {

        var db :SQLiteDatabase = this.writableDatabase

        var userEmail = emailUser

        val sql = "SELECT * FROM $TABLE_PRODUCTS WHERE emailUser=?"

        return db.rawQuery(sql, arrayOf(userEmail))

    }

    fun getUserFavs (emailUser: String?, title: String?) : Cursor {

        var db :SQLiteDatabase = this.writableDatabase

        var userEmail = emailUser

        val sql = "SELECT * FROM $TABLE_PRODUCTS WHERE userEmail=? AND $TITLE =?"

        return db.rawQuery(sql, arrayOf(userEmail,title))



    }



    fun checkFavoris (emailUser: String?, title: String?): Boolean {

        var db :SQLiteDatabase = this.writableDatabase
        var userEmail = emailUser

        var sql = "SELECT $TITLE FROM $TABLE_PRODUCTS WHERE $TITLE =? AND emailUser =?"

        val cursor =db.rawQuery(sql, arrayOf(title,userEmail))

        if (cursor.count > 0){

            return true

        }
        return false
    }




}

