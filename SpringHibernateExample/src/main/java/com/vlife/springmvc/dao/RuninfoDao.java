package com.vlife.springmvc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.vlife.springmvc.model.Runinfo;

/**
 * 
 * @author Administrator
 *
 */
public interface RuninfoDao {
	/**
	 * 通过ID寻找Runinfo对象
	 * 
	 * @param id Runinfo的对象
	 * @return 返回Runinfo对象
	 */
	Runinfo findById(int id);

	/**
	 * 保存
	 * 
	 * @param runinfo runinfo对象
	 */
	void saveRuninfo(Runinfo runinfo);

	/**
	 * 查找所有Runinfo
	 * 
	 * @return 返回包含所有Runinfo的List
	 */
	List<Runinfo> findAllRuninfo();

	/**
	 * 通过组成的Map和Date来查询符合条件的Runinfo的list
	 * @param conditions	
	 * @param mytime
	 * @return
	 */
	List<Runinfo> queryData(Map<String, String> conditions, Date[] mytime);

	/**
	 * 统计当天0点到现在正在跑的case数
	 * 
	 * @return 返回Integer
	 */
	Integer countRunningCase();
	
	/**
	 * 通过UserID寻找所有的相关的Runinfo
	 * @param userID	UserID
	 * @return	与UserID相关的所有的list
	 */
	List<Runinfo> findRuninfoByUserID(Integer userID);
	/**
	 * 分页查询结果
	 * @param userID	用户ID
	 * @param offset	开始的数
	 * @param length	从开始截取的长度
	 * @return	Runinfo的List的集合
	 */
	List<Runinfo> findRuninfoByUserIDAndPage(Integer userID,int offset, int length);

}
