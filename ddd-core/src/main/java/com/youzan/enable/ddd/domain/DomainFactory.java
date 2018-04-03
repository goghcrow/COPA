package com.youzan.enable.ddd.domain;

/**
 * 领域工厂
 */
public interface DomainFactory<T extends Entity> {

	T create();

}
