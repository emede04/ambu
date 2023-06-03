package com.example.ambu.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDeDatosLocal extends SQLiteOpenHelper {
    private static final String DB_NAME = "AMBU";

    //Table name
    private static final String DB_TABLE_NAME = "USERS";

    //Database version must be >= 1
    private static final int DB_VERSION = 1;
    private final Context miContexto;
    //Columns
    private static final String USER_NAME_COLUMN = "CUSER";

    private static final String USER_PASSWORD_COLUMN = "CPASS";
    private static final String USER_ESTADO_COLUMN = "ESTADO";


    public BaseDeDatosLocal(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        miContexto = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + DB_TABLE_NAME + "("
                + USER_NAME_COLUMN + " TEXT ," + USER_PASSWORD_COLUMN + " TEXT, " + USER_ESTADO_COLUMN + " TEXT "+")";

        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        Log.d("base de datos", "tablas creadas");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Boolean verifica(String user, String password) {
        //comprueba primero si existe un registro con usuario.
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorpass;
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_TABLE_NAME + " WHERE " + USER_NAME_COLUMN + "= ?", new String[]{user});
        if (cursor.getCount() >= 1) {
            cursorpass = db.rawQuery("SELECT '" + user + "' FROM " + DB_TABLE_NAME + " WHERE " + USER_PASSWORD_COLUMN + " = ? ", new String[]{password});
            if (cursorpass.getCount() >= 1) {
                Log.d("base de datos verifico la existencia del user", "el usuario existe, entra, o se ha pulsado el boton de registrar por tanto no hace nada");
                cursor.close();

                return true;
            } else {
                Log.d("base de datos", "usuario existe, pero la contesañe es incorrecta");
                cursor.close();
                return false;
            }
        } else {
            cursor.close();

            Log.d("base de datos", "el usuario no existe");
            return false;
        }
    }


    public long insertMedico(String name, String pass) {

        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        // Contenedor clave,valor -> columna, valor de entrada registro
        ContentValues values = new ContentValues();

        values.put(USER_NAME_COLUMN, name);
        values.put(USER_PASSWORD_COLUMN, pass);

        String data = "medico";

        values.put(USER_ESTADO_COLUMN, data);
        System.out.println(name + ":"+ data);
        Log.d("base de datos", "usuario creado");
        Log.d("base de datos",name);
        result = db.insert(DB_TABLE_NAME, null, values);
        System.out.println(data);
        //cerramos las conexion
        db.close();

        return result;

    }

    public long insertUsuarios(String name, String pass) {

        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        // Contenedor clave,valor -> columna, valor de entrada registro
        ContentValues values = new ContentValues();

        values.put(USER_NAME_COLUMN, name);
        values.put(USER_PASSWORD_COLUMN, pass);

        String data = "";
        if(name.equals("md") || name.equals("admin")){
            data = "medico";
        }else{
            data = "paciente";

        }
        values.put(USER_ESTADO_COLUMN, data);
        System.out.println(name + ":"+ data);
        Log.d("base de datos", "usuario creado");
        Log.d("base de datos",name);
        result = db.insert(DB_TABLE_NAME, null, values);
        System.out.println(data);
        //cerramos las conexion
        db.close();

        return result;

    }

    public boolean verificaEstado(String txtUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor_estado;
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_TABLE_NAME + " WHERE " + USER_NAME_COLUMN + "= ?", new String[]{txtUser});
        cursor.moveToFirst();
          if (cursor.getCount() >= 1) {
              cursor_estado = db.rawQuery(" SELECT estado FROM  USERS  WHERE CUSER =? and ESTADO = ?", new String[]{txtUser,"paciente"});
                cursor_estado.moveToFirst();
              if (cursor_estado.getCount() ==1) {
                  Log.d("base de datos", "el usuario es un paciente");


                return true;
            } else {
                  Log.d("base de datos", "el usuario es un medico");

                return false;
            }

        } else {
            cursor.close();

            Log.d("base de datos", "el usuario no existe");
            return false;
        }


    }
}