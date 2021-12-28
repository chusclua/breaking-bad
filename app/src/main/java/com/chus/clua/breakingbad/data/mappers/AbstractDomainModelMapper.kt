package com.chus.clua.breakingbad.data.mappers

import com.chus.clua.breakingbad.data.models.RemoteModel
import com.chus.clua.breakingbad.domain.models.DomainModel


abstract class AbstractDomainModelMapper<in RM, out DM> :
    DomainModelMapper<RM, DM> where RM : RemoteModel, DM : DomainModel {

    abstract override fun mapFromRemote(from: RM?): DM

    override fun mapFromRemoteList(list: List<RM>?): List<DM> {
        list?.let { return it.map { rm -> mapFromRemote(rm) } }
        return emptyList()
    }

}