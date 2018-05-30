package org.andy.shop.dao;

import java.io.Serializable;
import java.util.List;

import org.andy.shop.entity.UserInfoPo;

/**
 * 创建时间：2015-1-30 上午10:11:59
 * 
 * @author andy
 * @version 2.2
 * 
 * Dao通用接口
 */

interface GenericDao<T, PK extends Serializable> {

	T getById(PK id);

	List<T> findAll();

	PK save(T entity);

}
