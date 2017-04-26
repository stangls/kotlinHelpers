package io.github.stangls

import java.net.URLDecoder
import java.net.URLEncoder

/**
 * Created by stefan on 18.04.17.
 */
fun String.urlencode(): String = URLEncoder.encode(this,"UTF-8")
fun String.urldecode(): String = URLDecoder.decode(this,"UTF-8")