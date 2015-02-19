package madalosso.dividedespesa.SqlAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import madalosso.dividedespesa.classes.Viagem;

/**
 * Created by madal_000 on 18-Feb-15.
 */
public class BdHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "app_MEU";
    // Books table name
    private static final String TABLE_VIAGEM = "VIAGENS_HOME";
    private static final String DATABASE_CREATE = "CREATE TABLE "+TABLE_VIAGEM+"( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome TEXT, " +
            "destino TEXT )";

    public BdHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        Log.d("addViagem", "deletando TABELA");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_VIAGEM);

        // create fresh books table
        onCreate(db);
    }


    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NOME = "nome";
    private static final String KEY_DESTINO = "destino";

    private static final String[] COLUMNS = {KEY_ID, KEY_NOME, KEY_DESTINO};

    public void addViagem(Viagem viagem) {
        //for logging
        Log.d("addViagem", viagem.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NOME, viagem.getNome()); // get title
        values.put(KEY_DESTINO, viagem.getDestino()); // get author

        // 3. insert
        db.insert(TABLE_VIAGEM, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Viagem getViagem(int id) {

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_VIAGEM, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[]{String.valueOf(id)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build viagem object
        Viagem viagem = new Viagem();
        viagem.setId(Integer.parseInt(cursor.getString(0)));
        viagem.setNome(cursor.getString(1));
        viagem.setDestino(cursor.getString(2));

        Log.d("getViagem(" + id + ")", viagem.toString());

        return viagem;
    }

    public ArrayList<Viagem> getAllViagens() {
        List<Viagem> viagens = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_VIAGEM;

        Log.d("getAllBooks()", query);
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Viagem book = null;
        if (cursor.moveToFirst()) {
            do {
                Viagem viagem = new Viagem();
                viagem.setId(Integer.parseInt(cursor.getString(0)));
                viagem.setNome(cursor.getString(1));
                viagem.setDestino(cursor.getString(2));

//                Log.d("getAllBooks()","id: "+ viagem.getId());
//                Log.d("getAllBooks()","nome: "+ viagem.getNome());
//                Log.d("getAllBooks()","destino: "+ viagem.getDestino());
                // Add book to books
                viagens.add(viagem);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()","size: "+ viagens.size());

        ArrayList<Viagem> retorno = new ArrayList<>();
        retorno.addAll(viagens);
        // return books
        return retorno;
    }

    public int updateViagem(Viagem viagem) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value

        ContentValues values = new ContentValues();
        values.put(KEY_NOME, viagem.getNome()); // get title
        values.put(KEY_DESTINO, viagem.getDestino()); // get author

        // 3. updating row
        int i = db.update(TABLE_VIAGEM, //table
                values, // column/value
                KEY_ID + " = ?", // selections
                new String[]{String.valueOf(viagem.getId())}); //selection args

        // 4. close
        db.close();

        return i;

    }

    public void deleteViagem(Viagem viagem) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("deleteBook", "id:"+viagem.getId());

        // 2. delete
        db.delete(TABLE_VIAGEM, //table name
                KEY_ID + " = ?",  // selections
                new String[]{String.valueOf(viagem.getId())}); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteBook", viagem.toString());

    }

}
