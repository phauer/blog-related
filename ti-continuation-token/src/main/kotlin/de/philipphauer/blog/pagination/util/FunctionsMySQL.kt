package de.philipphauer.blog.pagination.util

import org.h2.util.StringUtils
import java.sql.Connection
import java.sql.SQLException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * https://github.com/h2database/h2database/blob/master/h2/src/main/org/h2/mode/FunctionsMySQL.java
 * This class implements some MySQL-specific functions.
 *
 * @author Jason Brittain
 * @author Thomas Mueller
 */
object FunctionsMySQL {

    /**
     * The date format of a MySQL formatted date/time.
     * Example: 2008-09-25 08:40:59
     */
    private val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

    /**
     * Format replacements for MySQL date formats.
     * See
     * http://dev.mysql.com/doc/refman/5.1/en/date-and-time-functions.html#function_date-format
     */
    private val FORMAT_REPLACE = arrayOf(
        "%a",
        "EEE",
        "%b",
        "MMM",
        "%c",
        "MM",
        "%d",
        "dd",
        "%e",
        "d",
        "%H",
        "HH",
        "%h",
        "hh",
        "%I",
        "hh",
        "%i",
        "mm",
        "%j",
        "DDD",
        "%k",
        "H",
        "%l",
        "h",
        "%M",
        "MMMM",
        "%m",
        "MM",
        "%p",
        "a",
        "%r",
        "hh:mm:ss a",
        "%S",
        "ss",
        "%s",
        "ss",
        "%T",
        "HH:mm:ss",
        "%W",
        "EEEE",
        "%w",
        "F",
        "%Y",
        "yyyy",
        "%y",
        "yy",
        "%%",
        "%"
    )

    /**
     * Register the functionality in the database.
     * Nothing happens if the functions are already registered.
     *
     * @param conn the connection
     */
    @Throws(SQLException::class)
    fun register(conn: Connection) {
        val init = arrayOf("UNIX_TIMESTAMP", "unixTimestamp", "FROM_UNIXTIME", "fromUnixTime", "DATE", "date")
        val stat = conn.createStatement()
        var i = 0
        while (i < init.size) {
            val alias = init[i]
            val method = init[i + 1]
            stat.execute(
                "CREATE ALIAS IF NOT EXISTS " + alias +
                        " FOR \"" + FunctionsMySQL::class.java!!.name + "." + method + "\""
            )
            i += 2
        }
    }

    /**
     * Get the seconds since 1970-01-01 00:00:00 UTC.
     * See
     * http://dev.mysql.com/doc/refman/5.1/en/date-and-time-functions.html#function_unix-timestamp
     *
     * @return the current timestamp in seconds (not milliseconds).
     */
    @JvmStatic
    fun unixTimestamp(): Int {
        return (System.currentTimeMillis() / 1000L).toInt()
    }

    /**
     * Get the seconds since 1970-01-01 00:00:00 UTC of the given timestamp.
     * See
     * http://dev.mysql.com/doc/refman/5.1/en/date-and-time-functions.html#function_unix-timestamp
     *
     * @param timestamp the timestamp
     * @return the current timestamp in seconds (not milliseconds).
     */
    @JvmStatic
    fun unixTimestamp(timestamp: java.sql.Timestamp): Int {
        return (timestamp.time / 1000L).toInt()
    }

    /**
     * See
     * http://dev.mysql.com/doc/refman/5.1/en/date-and-time-functions.html#function_from-unixtime
     *
     * @param seconds The current timestamp in seconds.
     * @return a formatted date/time String in the format "yyyy-MM-dd HH:mm:ss".
     */
    @JvmStatic
    fun fromUnixTime(seconds: Int): String {
        val formatter = SimpleDateFormat(
            DATE_TIME_FORMAT,
            Locale.ENGLISH
        )
        return formatter.format(Date(seconds * 1000L))
    }

    /**
     * See
     * http://dev.mysql.com/doc/refman/5.1/en/date-and-time-functions.html#function_from-unixtime
     *
     * @param seconds The current timestamp in seconds.
     * @param format The format of the date/time String to return.
     * @return a formatted date/time String in the given format.
     */
    @JvmStatic
    fun fromUnixTime(seconds: Int, format: String): String {
        var format = format
        format = convertToSimpleDateFormat(format)
        val formatter = SimpleDateFormat(format, Locale.ENGLISH)
        return formatter.format(Date(seconds * 1000L))
    }

    private fun convertToSimpleDateFormat(format: String): String {
        var format = format
        val replace = FORMAT_REPLACE
        var i = 0
        while (i < replace.size) {
            format = StringUtils.replaceAll(format, replace[i], replace[i + 1])
            i += 2
        }
        return format
    }

    /**
     * See
     * http://dev.mysql.com/doc/refman/5.1/en/date-and-time-functions.html#function_date
     * This function is dependent on the exact formatting of the MySQL date/time
     * string.
     *
     * @param dateTime The date/time String from which to extract just the date
     * part.
     * @return the date part of the given date/time String argument.
     */
    @JvmStatic
    fun date(dateTime: String?): String? {
        if (dateTime == null) {
            return null
        }
        val index = dateTime!!.indexOf(' ')
        return if (index != -1) {
            dateTime!!.substring(0, index)
        } else dateTime
    }

}