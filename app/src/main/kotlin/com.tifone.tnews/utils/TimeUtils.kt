package com.tifone.tnews.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {
    companion object STATIC {
        fun getCurrentTimeStamp(): String {
            return (System.currentTimeMillis() / 1000).toString()
        }

        fun getTimeStampAgo(timeStamp: Long): String {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
            val result = format.format(timeStamp * 1000L)
            var date: Date? = null
            try {
                date = format.parse(result)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return timeAgo(date)
        }

        private fun timeAgo(date: Date?): String {
            val format = SimpleDateFormat("MM-dd HH:mm", Locale.CHINA)
            if (date != null) {
                val agoTimeMillis = Date(System.currentTimeMillis()).time - date.time
                val agoTimeMin = agoTimeMillis / 1000 / 60
                return if (agoTimeMin < 1) {
                    "刚刚"
                } else if (agoTimeMin <= 60) {
                    agoTimeMin.toString() + "分钟前"
                } else if (agoTimeMin <= 60 * 24) {
                    (agoTimeMin / 60).toString() + "小时前"
                } else if (agoTimeMin <= 60 * 24 * 2) {
                    (agoTimeMin / 60 / 24).toString() + "天前"
                } else {
                    format.format(date)
                }
            }
            return format.format(Date(0))
        }
    }
}