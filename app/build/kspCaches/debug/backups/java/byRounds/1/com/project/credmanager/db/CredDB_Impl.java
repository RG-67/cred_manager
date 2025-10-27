package com.project.credmanager.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.project.credmanager.dao.UserCredDao;
import com.project.credmanager.dao.UserCredDao_Impl;
import com.project.credmanager.dao.UserDetailsDao;
import com.project.credmanager.dao.UserDetailsDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CredDB_Impl extends CredDB {
  private volatile UserCredDao _userCredDao;

  private volatile UserDetailsDao _userDetailsDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `UserDetails` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `userPhone` INTEGER NOT NULL, `password` TEXT NOT NULL, `deviceId` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `UserCred` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `generatedUserId` INTEGER NOT NULL, `userId` TEXT NOT NULL, `userPhone` INTEGER NOT NULL, `deviceId` TEXT NOT NULL, `title` TEXT NOT NULL, `userName` TEXT NOT NULL, `Password` TEXT NOT NULL, `Description` TEXT NOT NULL, `created_at` TEXT NOT NULL, `updated_at` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '3f7995ff919d3ffce9a235d91f038cb9')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `UserDetails`");
        db.execSQL("DROP TABLE IF EXISTS `UserCred`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUserDetails = new HashMap<String, TableInfo.Column>(5);
        _columnsUserDetails.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserDetails.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserDetails.put("userPhone", new TableInfo.Column("userPhone", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserDetails.put("password", new TableInfo.Column("password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserDetails.put("deviceId", new TableInfo.Column("deviceId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserDetails = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserDetails = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserDetails = new TableInfo("UserDetails", _columnsUserDetails, _foreignKeysUserDetails, _indicesUserDetails);
        final TableInfo _existingUserDetails = TableInfo.read(db, "UserDetails");
        if (!_infoUserDetails.equals(_existingUserDetails)) {
          return new RoomOpenHelper.ValidationResult(false, "UserDetails(com.project.credmanager.model.UserDetails).\n"
                  + " Expected:\n" + _infoUserDetails + "\n"
                  + " Found:\n" + _existingUserDetails);
        }
        final HashMap<String, TableInfo.Column> _columnsUserCred = new HashMap<String, TableInfo.Column>(11);
        _columnsUserCred.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserCred.put("generatedUserId", new TableInfo.Column("generatedUserId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserCred.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserCred.put("userPhone", new TableInfo.Column("userPhone", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserCred.put("deviceId", new TableInfo.Column("deviceId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserCred.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserCred.put("userName", new TableInfo.Column("userName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserCred.put("Password", new TableInfo.Column("Password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserCred.put("Description", new TableInfo.Column("Description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserCred.put("created_at", new TableInfo.Column("created_at", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserCred.put("updated_at", new TableInfo.Column("updated_at", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserCred = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserCred = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserCred = new TableInfo("UserCred", _columnsUserCred, _foreignKeysUserCred, _indicesUserCred);
        final TableInfo _existingUserCred = TableInfo.read(db, "UserCred");
        if (!_infoUserCred.equals(_existingUserCred)) {
          return new RoomOpenHelper.ValidationResult(false, "UserCred(com.project.credmanager.model.UserCred).\n"
                  + " Expected:\n" + _infoUserCred + "\n"
                  + " Found:\n" + _existingUserCred);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "3f7995ff919d3ffce9a235d91f038cb9", "15fe3626ac2fe975c875cd4156bf35d6");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "UserDetails","UserCred");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `UserDetails`");
      _db.execSQL("DELETE FROM `UserCred`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserCredDao.class, UserCredDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserDetailsDao.class, UserDetailsDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserCredDao userCredDao() {
    if (_userCredDao != null) {
      return _userCredDao;
    } else {
      synchronized(this) {
        if(_userCredDao == null) {
          _userCredDao = new UserCredDao_Impl(this);
        }
        return _userCredDao;
      }
    }
  }

  @Override
  public UserDetailsDao userDetailsDao() {
    if (_userDetailsDao != null) {
      return _userDetailsDao;
    } else {
      synchronized(this) {
        if(_userDetailsDao == null) {
          _userDetailsDao = new UserDetailsDao_Impl(this);
        }
        return _userDetailsDao;
      }
    }
  }
}
