package com.altafrazzaque.bookhub.model

import android.os.Parcel
import android.os.Parcelable

class Category (
    val query:String,
    val name:String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(query)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}

fun categoryBusiness() : Category = categoryList[2]
fun categoryTravel() : Category = categoryList[8]
fun categoryEducation() : Category = categoryList[5]
fun categoryComic() : Category = categoryList[9]

val categoryList = listOf(
    Category("categories:music", "Arts & entertainment"),
    Category( "categories:biography&autobiography", "Biography & memoirs"),
    Category( "categories:business, investing+business+investing", "Business & investing"),
    Category( "categories:childrens", "Children's"),
    Category( "categories:computers+technology, computers, technology", "Computers & technology"),
    Category( "categories:education", "Education"),
    Category( "categories:religion", "Religion & spirituality"),
    Category( "categories:romance", "Romance"),
    Category( "categories:travel", "Travel"),
    Category( "categories:comics&graphic novels", "Comics"),
    Category( "categories:cooking", "Cooking, food & wine"),
    Category( "categories:fiction", "Fiction & literary collections"),
    Category( "categories:language", "Foreign language & study aids"),
    Category( "categories:health", "Health, mind & body"),
    Category( "categories:history", "History"),
    Category( "categories:parenting", "Parenting & families"),
    Category( "categories:science,math", "Science & Math"),
    Category( "categories:science fiction+fantasy", "Sci-fi & fantasy"),
    Category( "categories:self-help", "Self-help"),
)
