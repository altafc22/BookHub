package com.altafrazzaque.bookhub.model.api
import android.os.Parcel
import android.os.Parcelable
import com.altafrazzaque.bookhub.model.Category
import com.google.gson.annotations.SerializedName

data class CategoryResult(
    val category: Category,
    val books: Books,
)

data class Books(
    var catTitle : String = "",
    @SerializedName("items")
    val items: List<Item> = listOf(),
    @SerializedName("kind")
    val kind: String = "",
    @SerializedName("totalItems")
    val totalItems: Int = 0
)

data class Item(
    @SerializedName("id")
    val id: String,
    @SerializedName("accessInfo")
    val accessInfo: AccessInfo?,
    @SerializedName("etag")
    val etag: String = "",
    @SerializedName("kind")
    val kind: String = "",
    @SerializedName("saleInfo")
    val saleInfo: SaleInfo?,
    @SerializedName("searchInfo")
    val searchInfo: SearchInfo?,
    @SerializedName("selfLink")
    val selfLink: String = "",
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readParcelable(AccessInfo::class.java.classLoader),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readParcelable(SaleInfo::class.java.classLoader),
        parcel.readParcelable(SearchInfo::class.java.classLoader),
        parcel.readString().toString(),
        parcel.readParcelable(VolumeInfo::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(accessInfo, flags)
        parcel.writeString(etag)
        parcel.writeString(kind)
        parcel.writeParcelable(saleInfo, flags)
        parcel.writeParcelable(searchInfo, flags)
        parcel.writeString(selfLink)
        parcel.writeParcelable(volumeInfo, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}

data class AccessInfo(
    @SerializedName("accessViewStatus")
    val accessViewStatus: String = "",
    @SerializedName("country")
    val country: String = "",
    @SerializedName("embeddable")
    val embeddable: Boolean = false,
    @SerializedName("epub")
    val epub: Epub? = Epub(),
    @SerializedName("pdf")
    val pdf: Pdf? = Pdf(),
    @SerializedName("publicDomain")
    val publicDomain: Boolean = false,
    @SerializedName("quoteSharingAllowed")
    val quoteSharingAllowed: Boolean = false,
    @SerializedName("textToSpeechPermission")
    val textToSpeechPermission: String = "",
    @SerializedName("viewability")
    val viewability: String = "",
    @SerializedName("webReaderLink")
    val webReaderLink: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(Epub::class.java.classLoader),
        parcel.readParcelable(Pdf::class.java.classLoader),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(accessViewStatus)
        parcel.writeString(country)
        parcel.writeByte(if (embeddable) 1 else 0)
        parcel.writeParcelable(epub, flags)
        parcel.writeParcelable(pdf, flags)
        parcel.writeByte(if (publicDomain) 1 else 0)
        parcel.writeByte(if (quoteSharingAllowed) 1 else 0)
        parcel.writeString(textToSpeechPermission)
        parcel.writeString(viewability)
        parcel.writeString(webReaderLink)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccessInfo> {
        override fun createFromParcel(parcel: Parcel): AccessInfo {
            return AccessInfo(parcel)
        }

        override fun newArray(size: Int): Array<AccessInfo?> {
            return arrayOfNulls(size)
        }
    }
}

data class SaleInfo(
    @SerializedName("buyLink")
    val buyLink: String? = "",
    @SerializedName("country")
    val country: String = "",
    @SerializedName("isEbook")
    val isEbook: Boolean = false,
    @SerializedName("saleability")
    val saleability: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(buyLink)
        parcel.writeString(country)
        parcel.writeByte(if (isEbook) 1 else 0)
        parcel.writeString(saleability)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SaleInfo> {
        override fun createFromParcel(parcel: Parcel): SaleInfo {
            return SaleInfo(parcel)
        }

        override fun newArray(size: Int): Array<SaleInfo?> {
            return arrayOfNulls(size)
        }
    }
}

data class SearchInfo(
    @SerializedName("textSnippet")
    val textSnippet: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(textSnippet)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchInfo> {
        override fun createFromParcel(parcel: Parcel): SearchInfo {
            return SearchInfo(parcel)
        }

        override fun newArray(size: Int): Array<SearchInfo?> {
            return arrayOfNulls(size)
        }
    }
}

class VolumeInfo(
    @SerializedName("id")
    var id: String,
    @SerializedName("allowAnonLogging")
    val allowAnonLogging: Boolean = false,
    @SerializedName("authors")
    val authors: List<String>? = listOf(),
    @SerializedName("canonicalVolumeLink")
    val canonicalVolumeLink: String = "",
    @SerializedName("categories")
    val categories: List<String>? = listOf(),
    @SerializedName("contentVersion")
    val contentVersion: String = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("imageLinks")
    val imageLinks: ImageLinks? = ImageLinks(),
    @SerializedName("infoLink")
    val infoLink: String = "",
    @SerializedName("language")
    val language: String = "",
    @SerializedName("maturityRating")
    val maturityRating: String = "",
    @SerializedName("pageCount")
    val pageCount: Int? = 0,
    @SerializedName("previewLink")
    val previewLink: String = "",
    @SerializedName("printType")
    val printType: String = "",
    @SerializedName("publishedDate")
    val publishedDate: String = "",
    @SerializedName("publisher")
    val publisher: String? = "",
    @SerializedName("subtitle")
    val subtitle: String? = "",
    @SerializedName("title")
    val title: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.createStringArrayList(),
        parcel.readString().toString(),
        parcel.createStringArrayList(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readParcelable(ImageLinks::class.java.classLoader),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeByte(if (allowAnonLogging) 1 else 0)
        parcel.writeStringList(authors)
        parcel.writeString(canonicalVolumeLink)
        parcel.writeStringList(categories)
        parcel.writeString(contentVersion)
        parcel.writeString(description)
        parcel.writeParcelable(imageLinks, flags)
        parcel.writeString(infoLink)
        parcel.writeString(language)
        parcel.writeString(maturityRating)
        parcel.writeValue(pageCount)
        parcel.writeString(previewLink)
        parcel.writeString(printType)
        parcel.writeString(publishedDate)
        parcel.writeString(publisher)
        parcel.writeString(subtitle)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VolumeInfo> {
        override fun createFromParcel(parcel: Parcel): VolumeInfo {
            return VolumeInfo(parcel)
        }

        override fun newArray(size: Int): Array<VolumeInfo?> {
            return arrayOfNulls(size)
        }
    }
}


data class Epub(
    @SerializedName("acsTokenLink")
    val acsTokenLink: String? = "",
    @SerializedName("isAvailable")
    val isAvailable: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(acsTokenLink)
        parcel.writeByte(if (isAvailable) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Epub> {
        override fun createFromParcel(parcel: Parcel): Epub {
            return Epub(parcel)
        }

        override fun newArray(size: Int): Array<Epub?> {
            return arrayOfNulls(size)
        }
    }
}

data class Pdf(
    @SerializedName("isAvailable")
    val isAvailable: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readByte() != 0.toByte())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (isAvailable) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pdf> {
        override fun createFromParcel(parcel: Parcel): Pdf {
            return Pdf(parcel)
        }

        override fun newArray(size: Int): Array<Pdf?> {
            return arrayOfNulls(size)
        }
    }
}


data class RetailPriceX(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("currencyCode")
    val currencyCode: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(amount)
        parcel.writeString(currencyCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RetailPriceX> {
        override fun createFromParcel(parcel: Parcel): RetailPriceX {
            return RetailPriceX(parcel)
        }

        override fun newArray(size: Int): Array<RetailPriceX?> {
            return arrayOfNulls(size)
        }
    }
}

data class ListPriceX(
    @SerializedName("amountInMicros")
    val amountInMicros: Int = 0,
    @SerializedName("currencyCode")
    val currencyCode: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(amountInMicros)
        parcel.writeString(currencyCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListPriceX> {
        override fun createFromParcel(parcel: Parcel): ListPriceX {
            return ListPriceX(parcel)
        }

        override fun newArray(size: Int): Array<ListPriceX?> {
            return arrayOfNulls(size)
        }
    }
}

data class RetailPrice(
    @SerializedName("amountInMicros")
    val amountInMicros: Int = 0,
    @SerializedName("currencyCode")
    val currencyCode: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(amountInMicros)
        parcel.writeString(currencyCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RetailPrice> {
        override fun createFromParcel(parcel: Parcel): RetailPrice {
            return RetailPrice(parcel)
        }

        override fun newArray(size: Int): Array<RetailPrice?> {
            return arrayOfNulls(size)
        }
    }
}


data class ImageLinks(
    @SerializedName("smallThumbnail")
    val smallThumbnail: String = "",
    @SerializedName("thumbnail")
    val thumbnail: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(smallThumbnail)
        parcel.writeString(thumbnail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageLinks> {
        override fun createFromParcel(parcel: Parcel): ImageLinks {
            return ImageLinks(parcel)
        }

        override fun newArray(size: Int): Array<ImageLinks?> {
            return arrayOfNulls(size)
        }
    }
}

data class IndustryIdentifier(
    @SerializedName("identifier")
    val identifier: String = "",
    @SerializedName("type")
    val type: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(identifier)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IndustryIdentifier> {
        override fun createFromParcel(parcel: Parcel): IndustryIdentifier {
            return IndustryIdentifier(parcel)
        }

        override fun newArray(size: Int): Array<IndustryIdentifier?> {
            return arrayOfNulls(size)
        }
    }
}

data class PanelizationSummary(
    @SerializedName("containsEpubBubbles")
    val containsEpubBubbles: Boolean = false,
    @SerializedName("containsImageBubbles")
    val containsImageBubbles: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (containsEpubBubbles) 1 else 0)
        parcel.writeByte(if (containsImageBubbles) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PanelizationSummary> {
        override fun createFromParcel(parcel: Parcel): PanelizationSummary {
            return PanelizationSummary(parcel)
        }

        override fun newArray(size: Int): Array<PanelizationSummary?> {
            return arrayOfNulls(size)
        }
    }
}

data class ReadingModes(
    @SerializedName("image")
    val image: Boolean = false,
    @SerializedName("text")
    val text: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (image) 1 else 0)
        parcel.writeByte(if (text) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReadingModes> {
        override fun createFromParcel(parcel: Parcel): ReadingModes {
            return ReadingModes(parcel)
        }

        override fun newArray(size: Int): Array<ReadingModes?> {
            return arrayOfNulls(size)
        }
    }
}