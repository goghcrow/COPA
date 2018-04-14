package com.youzan.enable.ddd.domain;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 领域实体基类
 * @author chuxiaofeng
 */
public abstract class AbstractEntity<ID> implements Entity {

	@Getter
	@Setter
    protected ID id;

	@Getter
	@Setter
	protected Date createdAt;

	@Getter
	@Setter
	protected Date updatedAt;

	@Getter
	@Setter
	protected Date deletedAt;

	/**
	 * 扩展字段
	 */
	@Getter
	@Setter
	protected Map<String, Object> extValues = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
	public<T> T getExtField(@NotNull String key){
        if(extValues != null){
            return (T)extValues.get(key);
        }
        return null;
    }

    public void putExtField(@NotNull String fieldName, Object value){
        this.extValues.put(fieldName, value);
    }

}
