package com.youzan.enable.ddd.convertor;

/**
 * Convertor  are used to convert Objects among Client Object, Domain Object and Data Object.
 *
 * @author fulan.zjf on 2017/12/16.
 */
public interface Convertor<C, E, D> {

    default C entityToClient(E entityObject){return null;}

    default C dataToClient(D dataObject){return null;}

    default E clientToEntity(C clientObject){return null;}

    default E dataToEntity(D dataObject){return null;}

    default D entityToData(E entityObject){return null;}

}

