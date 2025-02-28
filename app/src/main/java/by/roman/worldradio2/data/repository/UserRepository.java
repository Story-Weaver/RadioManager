package by.roman.worldradio2.data.repository;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import by.roman.worldradio2.data.dto.UserDTO;

public class UserRepository {
    private SQLiteDatabase db;

    public UserRepository(SQLiteDatabase db) {
        this.db = db;
    }
    public long addUser(UserDTO dto) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_UUID_USER, dto.getId());
        values.put(DatabaseHelper.COLUMN_LOGIN_USER, dto.getLogin());
        values.put(DatabaseHelper.COLUMN_PASSWORD_USER,dto.getPassword());
        return db.insert(DatabaseHelper.TABLE_USER, null, values);
    }
}
