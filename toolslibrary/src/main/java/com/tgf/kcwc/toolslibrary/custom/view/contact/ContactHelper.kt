package com.tgf.kcwc.toolslibrary.custom.view.contact


import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tgf.kcwc.toolslibrary.custom.common.getMD5
import com.caiyuanzi.utiltools.model.ContactEntity

class ContactHelper {

    /**
     * 获取 通讯录的列表
     */
    @SuppressLint("Range")
    private fun getContacts(activity: AppCompatActivity): ArrayList<ContactEntity> {
        val projectionString = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME
        )

        val projectionString2 = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        var contacts = ArrayList<ContactEntity>()
        activity.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projectionString,
            null,
            null,
            ContactsContract.Contacts.SORT_KEY_PRIMARY
        )?.use {
            while (it!!.moveToNext()) {
                var mPersonMsg: ContactEntity = ContactEntity("","","","","", isFirst = false)
                var contactId: String = it.getString(it.getColumnIndex(ContactsContract.Contacts._ID))
                var name: String = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                   activity.contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    projectionString2,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                    null,
                    null
                )?.use { it1 ->
                    while (it1!!.moveToNext()) {
                        mPersonMsg.name = name
                        mPersonMsg.userId = contactId

                        var phoneNum: String =
                            it1.getString(it1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                        phoneNum = phoneNum.replace("-", "")
                        phoneNum = phoneNum.replace(" ", "")
                        if (phoneNum != null) {
                            mPersonMsg.phone = phoneNum
                        }
                        if (mPersonMsg.phone != null && mPersonMsg.phone != "") {
                            contacts.add(mPersonMsg)
                        }
                    }
                    it1.close()
                }
            }
            it.close()
            for (i in 0 until contacts.size){
                Log.i("11","1=====>: 姓名： ${contacts[i].name}  电话： ${contacts[i].phone} 首字母${contacts[i].firstWord} --isFirst:${contacts[i].isFirst}")
            }
        }
        return contacts
    }

    /**
     * 获取通讯录数据库版本号是否有更新
     */
    @SuppressLint("Range")
    private fun isUpdateVersion(activity: AppCompatActivity):Boolean{
        var versionString = StringBuffer()
        var vesion:String =""
        val projectionString = arrayOf(
            "version"
        )
        activity.contentResolver.query(ContactsContract.RawContacts.CONTENT_URI,
                projectionString,
                null,
                null,
                null)?.use {
                while (it.moveToNext()) {
                    vesion = it.getString(it.getColumnIndex(ContactsContract.RawContacts.VERSION))
                    versionString.append(vesion)
                }
                it.close()
                Log.i("222", "--->版本号${versionString}")

                //转换为md5格式
                var mMd5 = getMD5(versionString.toString())
                Log.i("22222", "------>mMd5${mMd5}")
                //判断是否有更新
                var sharedPreferences = activity.getSharedPreferences("contactData", Context.MODE_PRIVATE)
                var preMd5 = sharedPreferences.getString("md5", "")
                Log.i("22222", "------>preMd5${preMd5}")
                if (mMd5 != preMd5) {//表示数据库有更新,把最新的值存到sharedPreferences
                    Log.i("22222", "------>数据库有更新")
                    var sharedPreferences2 = activity.getSharedPreferences("contactData", Context.MODE_PRIVATE)
                    var editor = sharedPreferences2.edit()
                    editor.putString("md5", mMd5)
                    editor.commit()
                    return  true
                } else {
                    Log.i("22222", "------>数据库没更新")
                    return  false
                }

            }
         return false
        }


}