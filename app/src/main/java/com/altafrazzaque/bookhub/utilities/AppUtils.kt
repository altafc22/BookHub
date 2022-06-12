package com.altafrazzaque.bookhub.utilities

object AppUtils {
    fun getAuthors(authors: List<String>) : String {
        var string = " "
        for(author in authors){
            string += "$author, "
        }
        if(string.length>2) string = string.dropLast(2)
        if(string.isNotEmpty() && string.length>3) string ="By$string"
        return string
    }
}
