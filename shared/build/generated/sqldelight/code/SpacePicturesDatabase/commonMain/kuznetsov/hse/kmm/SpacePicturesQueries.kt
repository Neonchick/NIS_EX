package kuznetsov.hse.kmm

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Any
import kotlin.String
import kotlin.Unit

public interface SpacePicturesQueries : Transacter {
  public fun <T : Any> selectAll(mapper: (
    platform: String,
    title: String,
    explanation: String,
    url: String,
    date: String
  ) -> T): Query<T>

  public fun selectAll(): Query<SpacePictureDB>

  public fun <T : Any> selectByPlatform(platform: String, mapper: (
    platform: String,
    title: String,
    explanation: String,
    url: String,
    date: String
  ) -> T): Query<T>

  public fun selectByPlatform(platform: String): Query<SpacePictureDB>

  public fun <T : Any> selectByTitle(title: String, mapper: (
    platform: String,
    title: String,
    explanation: String,
    url: String,
    date: String
  ) -> T): Query<T>

  public fun selectByTitle(title: String): Query<SpacePictureDB>

  public fun insertStats(
    platform: String,
    title: String,
    explanation: String,
    url: String,
    date: String
  ): Unit

  public fun deleteAll(): Unit

  public fun deleteByTitle(title: String): Unit
}
