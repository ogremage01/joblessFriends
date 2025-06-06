package com.joblessfriend.jobfinder.admin.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.util.SearchVo;



@Repository
public class AdminJobGroupDaoImpl implements AdminJobGroupDao {

	
	@Autowired
	private SqlSession sqlSession;
	
	private String namespace = "com.joblessfriend.jobfinder.admin.dao.AdminJobGroupDao.";
	
	

	@Override
	public List<JobGroupVo> jobGroupSelectList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "jobGroupSelectList", searchVo);
	}

	@Override
	public int jobGroupCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "jobGroupCount",searchVo);
	} 
	
	@Override
	public int insertJobGroup(String jobGroupName) {
		return sqlSession.insert(namespace + "insertJobGroup", jobGroupName);
	}
	
	@Override
	public int deleteJobGroups(List<Integer> jobGroupIdList) {
		return sqlSession.delete(namespace + "deleteJobGroups", jobGroupIdList);
	}

}
