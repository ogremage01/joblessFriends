package com.joblessfriend.jobfinder.jobGroup.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;



@Repository
public class JobGroupDaoImpl implements JobGroupDao {

	
	@Autowired
	private SqlSession sqlSession;
	
	private String namespace = "com.joblessfriend.jobfinder.jobGroup.dao.JobGroupDao.";
	
	
	
	@Override
	public List<JobGroupVo> jobGroupSelectList(int page) {
		// TODO Auto-generated method stub
		
		return sqlSession.selectList(namespace + "jobGroupSelectList", page);
	}

	@Override
	public List<JobGroupVo> jobGroupSelectList(int page, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int jobGroupCount(String keyword) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int jobGroupCount() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "jobGroupCount");
	}

	@Override
	public List<JobGroupVo> selectAllJobGroupsForAjax() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "selectAllJobGroupsForAjax");
	}

	@Override
	public String getJobGroupNameById(int jobGroupId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "getJobGroupNameById", jobGroupId);
	} 
	
	
	


}
