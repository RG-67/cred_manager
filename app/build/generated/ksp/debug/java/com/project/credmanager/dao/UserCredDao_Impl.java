package com.project.credmanager.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.project.credmanager.model.UserCred;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserCredDao_Impl implements UserCredDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserCred> __insertionAdapterOfUserCred;

  private final EntityDeletionOrUpdateAdapter<UserCred> __deletionAdapterOfUserCred;

  private final EntityDeletionOrUpdateAdapter<UserCred> __updateAdapterOfUserCred;

  public UserCredDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserCred = new EntityInsertionAdapter<UserCred>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `UserCred` (`id`,`generatedUserId`,`userId`,`userPhone`,`deviceId`,`title`,`userName`,`Password`,`Description`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserCred entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getGeneratedUserId());
        statement.bindString(3, entity.getUserId());
        statement.bindLong(4, entity.getUserPhone());
        statement.bindString(5, entity.getDeviceId());
        statement.bindString(6, entity.getTitle());
        statement.bindString(7, entity.getUserName());
        statement.bindString(8, entity.getPassword());
        statement.bindString(9, entity.getDescription());
        statement.bindString(10, entity.getCreatedAt());
        statement.bindString(11, entity.getUpdateAt());
      }
    };
    this.__deletionAdapterOfUserCred = new EntityDeletionOrUpdateAdapter<UserCred>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `UserCred` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserCred entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfUserCred = new EntityDeletionOrUpdateAdapter<UserCred>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `UserCred` SET `id` = ?,`generatedUserId` = ?,`userId` = ?,`userPhone` = ?,`deviceId` = ?,`title` = ?,`userName` = ?,`Password` = ?,`Description` = ?,`created_at` = ?,`updated_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserCred entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getGeneratedUserId());
        statement.bindString(3, entity.getUserId());
        statement.bindLong(4, entity.getUserPhone());
        statement.bindString(5, entity.getDeviceId());
        statement.bindString(6, entity.getTitle());
        statement.bindString(7, entity.getUserName());
        statement.bindString(8, entity.getPassword());
        statement.bindString(9, entity.getDescription());
        statement.bindString(10, entity.getCreatedAt());
        statement.bindString(11, entity.getUpdateAt());
        statement.bindLong(12, entity.getId());
      }
    };
  }

  @Override
  public Object insertUserCred(final UserCred userCred,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserCred.insert(userCred);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCred(final UserCred userCred, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfUserCred.handle(userCred);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateUserCred(final UserCred userCred,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUserCred.handle(userCred);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<UserCred>> getAllCred(final int generatedUserId, final String userId) {
    final String _sql = "SELECT * FROM UserCred WHERE generatedUserId = ? AND userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, generatedUserId);
    _argIndex = 2;
    _statement.bindString(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"UserCred"}, false, new Callable<List<UserCred>>() {
      @Override
      @Nullable
      public List<UserCred> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfGeneratedUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedUserId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfUserPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "userPhone");
          final int _cursorIndexOfDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "Password");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "Description");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdateAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<UserCred> _result = new ArrayList<UserCred>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserCred _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpGeneratedUserId;
            _tmpGeneratedUserId = _cursor.getInt(_cursorIndexOfGeneratedUserId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final long _tmpUserPhone;
            _tmpUserPhone = _cursor.getLong(_cursorIndexOfUserPhone);
            final String _tmpDeviceId;
            _tmpDeviceId = _cursor.getString(_cursorIndexOfDeviceId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpUserName;
            _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            final String _tmpPassword;
            _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            final String _tmpUpdateAt;
            _tmpUpdateAt = _cursor.getString(_cursorIndexOfUpdateAt);
            _item = new UserCred(_tmpId,_tmpGeneratedUserId,_tmpUserId,_tmpUserPhone,_tmpDeviceId,_tmpTitle,_tmpUserName,_tmpPassword,_tmpDescription,_tmpCreatedAt,_tmpUpdateAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
