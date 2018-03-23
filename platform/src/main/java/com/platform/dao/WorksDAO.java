package com.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.platform.model.Works;
import com.platform.rmodel.work.WorkInfo;

public interface WorksDAO {
	/**
	 * 根据用户的id来获取用户上传的总作品数量
	 * @param stuid
	 * @return
	 */
	@Select("select count(id) from works where author_id=#{stuid}")
	public Integer getMyWorksNum(@Param("stuid") String stuid);
	
	/**
	 * 根据用户的stuid来获取用户近期五篇上传的作品
	 * @return
	 */
	@Select("select id, title,description,upload_time,author_id from works where author_id=#{stuid} order by id desc limit 5")
	public List<Works> getMyRecentWorks(@Param("stuid") String stuid);
	
	/**
	 * 根据用户的stuid来获取用户所有上传的作品
	 * @return
	 */
	@Select("select id, title, works_url from works where author_id=#{stuid} order by id desc ")
	public List<WorkInfo> getMyAllWorks(@Param("stuid") String stuid);
	/**
	 * 根据作品的id来删除作品
	 * @param work_id
	 * @return
	 */
	public void deleteMultiWorks(@Param("list") List<Integer> list);
	
	
	
	/**
	 * 插入作品
	 * @param notice
	 * @return
	 */
	@Insert("insert into works(title,description,works_url,upload_time) values(#{title},#{description},#{works_url},#{upload_time};")
	@SelectKey(statement = "SELECT LAST_INSERT_ID() ", keyProperty = "id", before = false, resultType = int.class)
	public Integer insertWorks(Works works);
	
	/**
	 * 获取最近4篇作品用于主页(上传时间）
	 * @param is_good
	 * @return
	 */
	@Select("select id ,title ,description,upload_time from works order by id desc limit 4")
	public List<WorkInfo> getRecentFourWorks();
	/**
	 * 获取总的作品数量
	 * @return
	 */
	@Select("select count(id) from works ")
	public Integer getWorksTotalNum();
	/**
	 * 获取所有作品
	 * @return
	 */
	@Select("select id,title,works_url,author_id,upload_time from works  order by id desc")
	public List<Works> getAllWorks();
	
	/**以下是管理员进行的作品操作***/
	/**
	 * 管理员首页获取最近五个学生作品
	 * @return
	 */
	@Select("select id,title,description,label,format,works_url,author_id,is_good,praise,upload_time from works  order by id desc limit 5")
	public List<Works> getRecentWorks();
	/**
	 * 将作品设置为优秀作品
	 * @param is_good
	 * @param works_id
	 * @return
	 */
	@Update("update works set is_good=#{is_good} where id=#{works_id}")
	public Integer setGoodWorks(@Param("is_good") Integer is_good,@Param("works_id") Integer works_id);
	
	
	
	
	
	
	

}
