package io.github.stangls.jsonHelper

import allIndexed
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by stefan on 21.04.17.
 */


fun equals(x: Any, y: Any) : Boolean =
    when(x) {
        is JSONObject -> equals(x,y as JSONObject)
        is JSONArray -> equals(x,y as JSONArray)
        else -> x == y
    }

fun equals(expected: JSONObject, result: JSONObject) =
    expected.keySet().sorted() == result.keySet().sorted() &&
    expected.keys().asSequence().toList().all {
        equals(expected[it],result[it])
    }

fun equals(expected: JSONArray, result: JSONArray) =
    expected.length() == result.length() &&
    expected.allIndexed { idx, x ->
        equals(x, result[idx])
    }

