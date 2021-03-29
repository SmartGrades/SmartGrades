package kz.tech.smartgrades.root.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface IFamilyDao {

  /*  @Query("SELECT * FROM TableFamilyList")
    Single<List<TableFamilyList>> getAll();

    @Query("SELECT * FROM TableFamilyList WHERE id = :id")
    Single<TableFamilyList> getById(long id);*/

  //  @Insert(onConflict = OnConflictStrategy.REPLACE)
   // void insert(List<TableFamilyList> news);

  /*  @Update
    void update(TableFamilyList news);

    @Delete
    void delete(List<TableFamilyList> news);*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFamilyRoom(TableFamilyRoom familyRoom);

    @Query("SELECT * FROM familyRoom")
    Single<List<TableFamilyRoom>> getAllFamilyRoomList();

    @Query("DELETE FROM familyRoom")
    void deleteAllFamilyRoom();




    @Query("SELECT * FROM familyList")
    Single<List<TableFamilyList>> getFamilyList();

    @Query("SELECT * FROM familyList WHERE zid = :zid")
    Single<TableFamilyList> getFamilyListByID(String zid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFamilyList(List<TableFamilyList> familyList);


    @Query("DELETE FROM familyList")
    void deleteAllFamilyList();



    //@Query("SELECT COUNT(*) FROM familyList")
    //Single<Integer> count();

    /*
     @Delete
    void delete(List<BestStories> list);

    @Query("DELETE FROM best")
    void deleteAll();
     */


    ///////////////                      ///////////////////

}
