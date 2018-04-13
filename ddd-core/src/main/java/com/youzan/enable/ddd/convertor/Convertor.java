package com.youzan.enable.ddd.convertor;

/**
 * Convertor are used to convert Objects among DTO, Domain Object(Entity) and Data Object.
 *
 * @author chuxiaofeng
 */
public interface Convertor<Dto, Entity, Do> {

    default Dto entityToDto(Entity entityObject){return null;}

    default Dto dataToDto(Do dataObject){return null;}

    default Entity dtoToEntity(Dto dto){return null;}

    default Entity dataToEntity(Do dataObject){return null;}

    default Do entityToData(Entity entityObject){return null;}

}

