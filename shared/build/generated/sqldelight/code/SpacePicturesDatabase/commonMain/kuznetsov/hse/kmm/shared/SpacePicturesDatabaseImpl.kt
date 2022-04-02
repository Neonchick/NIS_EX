package kuznetsov.hse.kmm.shared

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.`internal`.copyOnWriteList
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.collections.MutableList
import kotlin.reflect.KClass
import kuznetsov.hse.kmm.SpacePictureDB
import kuznetsov.hse.kmm.SpacePicturesDatabase
import kuznetsov.hse.kmm.SpacePicturesQueries

internal val KClass<SpacePicturesDatabase>.schema: SqlDriver.Schema
  get() = SpacePicturesDatabaseImpl.Schema

internal fun KClass<SpacePicturesDatabase>.newInstance(driver: SqlDriver): SpacePicturesDatabase =
    SpacePicturesDatabaseImpl(driver)

private class SpacePicturesDatabaseImpl(
  driver: SqlDriver
) : TransacterImpl(driver), SpacePicturesDatabase {
  public override val spacePicturesQueries: SpacePicturesQueriesImpl =
      SpacePicturesQueriesImpl(this, driver)

  public object Schema : SqlDriver.Schema {
    public override val version: Int
      get() = 1

    public override fun create(driver: SqlDriver): Unit {
      driver.execute(null, """
          |CREATE TABLE SpacePictureDB (
          |    platform TEXT NOT NULL,
          |    title TEXT NOT NULL,
          |    explanation TEXT NOT NULL,
          |    url TEXT NOT NULL,
          |    date TEXT NOT NULL
          |)
          """.trimMargin(), 0)
    }

    public override fun migrate(
      driver: SqlDriver,
      oldVersion: Int,
      newVersion: Int
    ): Unit {
    }
  }
}

private class SpacePicturesQueriesImpl(
  private val database: SpacePicturesDatabaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), SpacePicturesQueries {
  internal val selectAll: MutableList<Query<*>> = copyOnWriteList()

  internal val selectByPlatform: MutableList<Query<*>> = copyOnWriteList()

  internal val selectByTitle: MutableList<Query<*>> = copyOnWriteList()

  public override fun <T : Any> selectAll(mapper: (
    platform: String,
    title: String,
    explanation: String,
    url: String,
    date: String
  ) -> T): Query<T> = Query(-1544193696, selectAll, driver, "SpacePictures.sq", "selectAll",
      "SELECT * FROM SpacePictureDB") { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!
    )
  }

  public override fun selectAll(): Query<SpacePictureDB> = selectAll { platform, title, explanation,
      url, date ->
    SpacePictureDB(
      platform,
      title,
      explanation,
      url,
      date
    )
  }

  public override fun <T : Any> selectByPlatform(platform: String, mapper: (
    platform: String,
    title: String,
    explanation: String,
    url: String,
    date: String
  ) -> T): Query<T> = SelectByPlatformQuery(platform) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!
    )
  }

  public override fun selectByPlatform(platform: String): Query<SpacePictureDB> =
      selectByPlatform(platform) { platform_, title, explanation, url, date ->
    SpacePictureDB(
      platform_,
      title,
      explanation,
      url,
      date
    )
  }

  public override fun <T : Any> selectByTitle(title: String, mapper: (
    platform: String,
    title: String,
    explanation: String,
    url: String,
    date: String
  ) -> T): Query<T> = SelectByTitleQuery(title) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!
    )
  }

  public override fun selectByTitle(title: String): Query<SpacePictureDB> = selectByTitle(title) {
      platform, title_, explanation, url, date ->
    SpacePictureDB(
      platform,
      title_,
      explanation,
      url,
      date
    )
  }

  public override fun insertStats(
    platform: String,
    title: String,
    explanation: String,
    url: String,
    date: String
  ): Unit {
    driver.execute(1786794241, """
    |INSERT OR IGNORE INTO SpacePictureDB(platform, title, explanation, url, date)
    |VALUES (?,?, ?, ?, ?)
    """.trimMargin(), 5) {
      bindString(1, platform)
      bindString(2, title)
      bindString(3, explanation)
      bindString(4, url)
      bindString(5, date)
    }
    notifyQueries(1786794241, {database.spacePicturesQueries.selectByTitle +
        database.spacePicturesQueries.selectAll + database.spacePicturesQueries.selectByPlatform})
  }

  public override fun deleteAll(): Unit {
    driver.execute(-186927535, """DELETE FROM SpacePictureDB""", 0)
    notifyQueries(-186927535, {database.spacePicturesQueries.selectByTitle +
        database.spacePicturesQueries.selectAll + database.spacePicturesQueries.selectByPlatform})
  }

  public override fun deleteByTitle(title: String): Unit {
    driver.execute(1652205809, """DELETE FROM SpacePictureDB WHERE title = ?""", 1) {
      bindString(1, title)
    }
    notifyQueries(1652205809, {database.spacePicturesQueries.selectByTitle +
        database.spacePicturesQueries.selectAll + database.spacePicturesQueries.selectByPlatform})
  }

  private inner class SelectByPlatformQuery<out T : Any>(
    public val platform: String,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectByPlatform, mapper) {
    public override fun execute(): SqlCursor = driver.executeQuery(1835871563,
        """SELECT * FROM SpacePictureDB WHERE platform = ?""", 1) {
      bindString(1, platform)
    }

    public override fun toString(): String = "SpacePictures.sq:selectByPlatform"
  }

  private inner class SelectByTitleQuery<out T : Any>(
    public val title: String,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectByTitle, mapper) {
    public override fun execute(): SqlCursor = driver.executeQuery(-1714533248,
        """SELECT * FROM SpacePictureDB WHERE title = ?""", 1) {
      bindString(1, title)
    }

    public override fun toString(): String = "SpacePictures.sq:selectByTitle"
  }
}
