package com.chus.clua.breakingbad.presentation.mappers

/**
 *
 * Interface for [DomainModel] to [UiModel] mappers.
 * It transforms domain models into ui models
 *
 * @param <DM> the domain model input type
 * @param <UM> the ui model output type
 */
interface UiModelMapper<in DM, out UM> {

    fun mapFromDomain(from: DM?): UM

    fun mapFromDomainList(list: List<DM>?): List<UM>

}