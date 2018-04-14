package com.youzan.enable.ddd.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
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

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AbstractEntity other = ((AbstractEntity) obj);
		return Objects.equals(id, other.id) &&
				Objects.equals(createdAt, other.createdAt) &&
				Objects.equals(updatedAt, other.updatedAt) &&
				Objects.equals(deletedAt, other.deletedAt) &&
				Objects.equals(extValues, other.extValues);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, createdAt, updatedAt, deletedAt, extValues);
	}
}
