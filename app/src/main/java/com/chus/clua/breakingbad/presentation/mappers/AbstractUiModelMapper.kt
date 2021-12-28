package com.chus.clua.breakingbad.presentation.mappers

import com.chus.clua.breakingbad.domain.models.DomainModel
import com.chus.clua.breakingbad.presentation.models.UiModel

abstract class AbstractUiModelMapper<in DM, out UM> :
    UiModelMapper<DM, UM> where DM : DomainModel, UM : UiModel {

    abstract override fun mapFromDomain(from: DM?): UM

    override fun mapFromDomainList(list: List<DM>?): List<UM> {
        list?.let { return it.map { dm -> mapFromDomain(dm) } }
        return emptyList()
    }

}