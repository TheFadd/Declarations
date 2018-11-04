package com.example.myapplication.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface HumanDAO {

    @Query("SELECT * FROM human")
    List<HumanEntity> allHumansBlocking();

    @Query("SELECT * FROM human WHERE favorite == 1")
    LiveData<List<HumanEntity>> allFavoriteHumans();

    @Query("SELECT * FROM human")
    Flowable<List<HumanEntity>> allHumansRx();

    @Query("UPDATE human SET favorite = 0")
    void removeAllFavorite();

    @Insert
    void insert(HumanEntity... items);

    @Insert
    void insert(List<HumanEntity> items);

    @Delete
    void delete(HumanEntity... humanEntities);

    @Update
    void update(HumanEntity... humanEntities);

    @Query("DELETE FROM human")
    void clearHumans();
}
