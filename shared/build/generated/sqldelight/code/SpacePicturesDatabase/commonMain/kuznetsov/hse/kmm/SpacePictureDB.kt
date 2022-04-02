package kuznetsov.hse.kmm

import kotlin.String

public data class SpacePictureDB(
  public val platform: String,
  public val title: String,
  public val explanation: String,
  public val url: String,
  public val date: String
) {
  public override fun toString(): String = """
  |SpacePictureDB [
  |  platform: $platform
  |  title: $title
  |  explanation: $explanation
  |  url: $url
  |  date: $date
  |]
  """.trimMargin()
}
