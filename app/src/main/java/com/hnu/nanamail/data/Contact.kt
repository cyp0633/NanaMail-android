package com.hnu.nanamail.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 联系人
 * @property uuid 联系人标识符
 * @property name 联系人姓名
 * @property address 联系人邮箱
 * @property description 联系人描述
 */
@Entity
data class Contact (
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    val uuid: String = "",
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "address")
    val address: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
)
