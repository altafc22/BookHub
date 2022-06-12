package com.altafrazzaque.bookhub.ui.main.adapter.comaparators

import com.altafrazzaque.bookhub.model.api.VolumeInfo

class TitleComparator(private val isAsc: Boolean) : Comparator<VolumeInfo>{
    override fun compare(left: VolumeInfo?, right: VolumeInfo?): Int {
        if(isAsc){
            if (left == null) {
                return if (right == null) 0 else -1
            }
            return if (right == null) {
                1
            } else left.title.compareTo(right.title)
        }
        else
        {
            if (left == null) {
                return if (right == null) 0 else -1
            }
            return if (right == null) {
                1
            } else right.title.compareTo(left.title)
        }
    }

}

class AuthorComparator(private val isAsc: Boolean) : Comparator<VolumeInfo>{
    override fun compare(left: VolumeInfo?, right: VolumeInfo?): Int {
        val authorLeft = if(!left?.authors.isNullOrEmpty()) left?.authors?.get(0) else ""
        val authorRight = if(!right?.authors.isNullOrEmpty()) right?.authors?.get(0) else ""

        if(isAsc){
            if (authorLeft == null) {
                return if (authorRight == null) 0 else -1
            }
            return if (authorRight == null) {
                1
            } else authorLeft.compareTo(authorRight)
        }
        else
        {
            if (authorLeft == null) {
                return if (authorRight == null) 0 else -1
            }
            return if (authorRight == null) {
                1
            } else authorRight.compareTo(authorLeft)
        }
    }
}