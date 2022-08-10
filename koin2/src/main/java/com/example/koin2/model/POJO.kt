package com.example.koin2.model

class POJO {

    private var name: String? = null
    private var realName: String? = null
    private var team: String? = null
    private var firstApperance: Int? = 0
    private var createdBy: String? = null
    private var publisher: String? = null
    private var imageUrl: String? = null
    private var bio: String? = null

    fun POJO(name: String?, realName: String?, team:String?,
             firstApperance: Int?, createdBy: String?, publisher:String?,
             imageUrl: String?, bio: String?) {
        this.name = name
        this.realName = realName
        this.team = team
        this.firstApperance = firstApperance
        this.createdBy = createdBy
        this.publisher = publisher
        this.imageUrl = imageUrl
        this.bio = bio
    }

    fun getName(): String?{
       return name
    }

    fun getRealName() : String? {
        return realName
    }

    fun getTeam() : String? {
        return team
    }

    fun getFirstApperance() : Int? {
        return firstApperance
    }

    fun getCreatedBy() : String? {
        return createdBy
    }

    fun getPublisher() : String? {
        return publisher
    }

    fun getImgeUrl() : String? {
        return imageUrl
    }

    fun getBio() : String? {
        return bio
    }
}