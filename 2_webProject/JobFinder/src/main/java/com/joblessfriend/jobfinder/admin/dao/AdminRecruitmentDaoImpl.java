package com.joblessfriend.jobfinder.admin.dao;

import com.joblessfriend.jobfinder.recruitment.domain.*;
import com.joblessfriend.jobfinder.util.SearchVo;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AdminRecruitmentDaoImpl implements AdminRecruitmentDao {

    @Autowired
    private SqlSession sqlSession;

    String namespace = "com.joblessfriend.jobfinder.admin.dao.AdminRecruitmentDao";

  
    @Override
	public void jobPostDelete(List<Integer> jobPostIdList) {
		sqlSession.delete(namespace+".jobPostDelete", jobPostIdList);
	}


    


    @Override
	public List<RecruitmentVo> adminRecruitmentList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.joblessfriend.jobfinder.admin.dao.AdminRecruitmentDao.adminRecruitmentList",searchVo);
    }




    @Override
	public void jobPostFileDelete(List<Integer> jobPostIdList) {
		// TODO Auto-generated method stub
		sqlSession.delete("com.joblessfriend.jobfinder.admin.dao.AdminRecruitmentDao.jobPostFileDelete", jobPostIdList);
		
	}

	@Override
	public void jobPostTagDelete(List<Integer> jobPostIdList) {
		// TODO Auto-generated method stub
		sqlSession.delete("com.joblessfriend.jobfinder.admin.dao.AdminRecruitmentDao.jobPostTagDelete", jobPostIdList);
		
	}

	 @Override
	    public int getRecruitmentTotalCount(SearchVo searchVo) {
	        return sqlSession.selectOne("com.joblessfriend.jobfinder.admin.dao.AdminRecruitmentDao.getRecruitmentTotalCount", searchVo);
	    }
	

}
