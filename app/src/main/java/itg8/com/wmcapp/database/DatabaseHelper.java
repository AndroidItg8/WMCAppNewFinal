package itg8.com.wmcapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import itg8.com.wmcapp.board.model.NoticeBoardModel;
import itg8.com.wmcapp.board.model.TempNoticeBoardModel;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.complaint.model.ComplaintCategoryModel;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;

/**
 * Created by Android itg 8 on 10/25/2017.
 */



public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME    ="ormlite.db";
    private static final int    DATABASE_VERSION = 6;

    private Dao<CityModel, Integer> mDAOCity = null;
    private Dao<TempComplaintModel, Integer> mDAOComplaint;
    private Dao<ComplaintCategoryModel, Integer> mDAOCategoryComplaint;
    private Dao<TempNoticeBoardModel, Integer> mDAONB= null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, CityModel.class);
            TableUtils.createTable(connectionSource, TempComplaintModel.class);
            TableUtils.createTable(connectionSource, TempNoticeBoardModel.class);
            TableUtils.createTable(connectionSource, ComplaintCategoryModel.class);
            Logs.d("DatabaseHelper : onCreate");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, CityModel.class, true);
            TableUtils.dropTable(connectionSource, TempComplaintModel.class, true);
            TableUtils.dropTable(connectionSource, TempNoticeBoardModel.class, true);
            TableUtils.dropTable(connectionSource, ComplaintCategoryModel.class, true);
            Logs.d("DatabaseHelper : onUpgrade");
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Dao<CityModel, Integer> getmDAOCity() throws SQLException {
        if (mDAOCity == null) {
            mDAOCity = getDao(CityModel.class);
        }

        return mDAOCity;
    }

    public Dao<TempNoticeBoardModel, Integer> getmDAONoticeBoard() throws SQLException {
        if (mDAONB == null) {
            mDAONB = getDao(TempNoticeBoardModel.class);
        }

        return mDAONB;
    }
    public Dao<TempComplaintModel, Integer> getmDAOComplaint() throws SQLException {
        if (mDAOComplaint == null) {
            mDAOComplaint = getDao(TempComplaintModel.class);
        }
        return mDAOComplaint;
    }
    public Dao<ComplaintCategoryModel, Integer> getmDAOCategoryComplaint() throws SQLException {
        if (mDAOCategoryComplaint == null) {
            Logs.d("DatabaseHelper : getmDAOCategoryComplaint");

            mDAOCategoryComplaint = getDao(ComplaintCategoryModel.class);
        }
        return mDAOCategoryComplaint;
    }

    @Override
    public void close() {
        mDAOCity = null;
        super.close();
    }

}
