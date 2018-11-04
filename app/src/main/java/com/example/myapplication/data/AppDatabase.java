package com.example.myapplication.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {HumanEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HumanDAO humanDAO();
}
