package com.caiyuanzi.kcwc.toolslibrary.custom.common


import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableStringBuilder
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.security.MessageDigest

/**
 *
 *
 */
fun getMD5(string: String): String {
            var mbyte = string.toByteArray()
            var md5 = ""
            var sbString = StringBuffer()

            var messageDigest = MessageDigest.getInstance("MD5")
            messageDigest.update(mbyte)
            var temp = messageDigest.digest()

            for (b in temp) {
                sbString.append(Integer.toHexString(b.toInt() and 0xff))
            }
            md5 = sbString.toString()
            return md5
        }

/**
 * 获取assert中的json资源
 */
fun getJson(fileName:String,context: Context):String{
    var stringBuffer = StringBuffer()

    try {
        var assetManager:AssetManager = context.assets
        var bf= BufferedReader(InputStreamReader(assetManager.open(fileName)))
        var line :String =""
        while ( bf.readLine()!=null){
            line = bf.readLine()
            stringBuffer.append(line)
        }
    }catch (e:IOException){

    }

    return stringBuffer.toString()
}


/**
 * 设置文字可点击和颜色部分
 * @param context
 * @param headString 前端字符串
 * @param contentString 点击部分内容
 * @param endString 结尾部分
 * @param textClick 点击
 *
 */
fun<T> setTextSpanPart(headString: String,contentString:String,endString: String,textClick: T):SpannableStringBuilder{
    val style = SpannableStringBuilder()
    style.append(headString).append(contentString)
    endString?.let {
        style.append(it)
    }

    style.setSpan(textClick, headString.length, headString.length+contentString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    return  style
}


/**
 * 正常编码中一般只会用到 [dp]/[sp] ;
 * 其中[dp]/[sp] 会根据系统分辨率将输入的dp/sp值转换为对应的px
 */
val Float.dp: Float                 // [xxhdpi](360 -> 1080)
get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

val Int.dp: Int
get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()


val Float.sp: Float                 // [xxhdpi](360 -> 1080)
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)


val Int.sp: Int
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()


/**
 * 将数字转换为中文（ 仅包含1-9）
 * @param count  输入的数字
 */
 fun changeNumToHanzi( count :Int):String{

    val mString = arrayOf("一","二","三","四","五","六","七","八","九")

    return mString[count-1]

}
