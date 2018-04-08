package com.youzan.enable.ddd.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 聚合内的实体
 * This is the parent object of all domain objects
 * @author fulan.zjf 2017年10月27日 上午10:16:10
 */
public abstract class Entity<ID>  {

	@Getter @Setter
    protected ID id;

	@Getter @Setter
	protected Date createdAt;

	@Getter @Setter
	protected Date updatedAt;

	@Getter @Setter
	protected Date deletedAt;

	/**
	 * 扩展字段
	 */
	@Getter
	@Setter
	protected Map<String, Object> extValues = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
	public<T> T getExtField(String key){
        if(extValues != null){
            return (T)extValues.get(key);
        }
        return null;
    }

    public void putExtField(String fieldName, Object value){
        this.extValues.put(fieldName, value);
    }

}
