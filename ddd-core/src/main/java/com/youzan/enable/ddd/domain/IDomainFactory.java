package com.youzan.enable.ddd.domain;

/**
 * 领域工厂
 * @author xueliang.sxl
 *
 */
public interface IDomainFactory<T extends Entity> {

	T create();

}
