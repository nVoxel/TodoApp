package com.voxeldev.todoapp.about.div

import android.content.Context
import com.yandex.div.internal.util.IOUtils
import org.json.JSONObject

/**
 * @author nvoxel
 */
internal class AssetReader(private val context: Context) {

    fun read(filename: String): JSONObject {
        val data = IOUtils.toString(context.assets.open(filename))
        return JSONObject(data)
    }
}
