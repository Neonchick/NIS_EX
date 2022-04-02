package kuznetsov.hse.kmm

import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver
import kuznetsov.hse.kmm.shared.newInstance
import kuznetsov.hse.kmm.shared.schema

public interface SpacePicturesDatabase : Transacter {
  public val spacePicturesQueries: SpacePicturesQueries

  public companion object {
    public val Schema: SqlDriver.Schema
      get() = SpacePicturesDatabase::class.schema

    public operator fun invoke(driver: SqlDriver): SpacePicturesDatabase =
        SpacePicturesDatabase::class.newInstance(driver)
  }
}
