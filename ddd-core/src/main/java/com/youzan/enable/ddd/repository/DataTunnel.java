package com.youzan.enable.ddd.repository;

import java.util.List;

/**
 * Data Tunnel is the real Data CRUD Operator may interact with DB, service, cache etc...
 * 
 * @author fulan.zjf 2017年10月27日 上午10:34:17
 */
public interface DataTunnel<T extends DataObject> {

    T create(T dataObject);

    default void delete(T dataObject) {};

    default void update(T dataObject){};

    T get(String id);

    List<T> getByEntity(T entity);
}
