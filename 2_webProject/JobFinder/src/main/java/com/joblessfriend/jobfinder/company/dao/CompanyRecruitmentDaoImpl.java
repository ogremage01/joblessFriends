package com.joblessfriend.jobfinder.company.dao;

import com.joblessfriend.jobfinder.recruitment.domain.*;
import com.joblessfriend.jobfinder.util.SearchVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CompanyRecruitmentDaoImpl implements CompanyRecruitmentDao {

	@Autowired
	private SqlSession sqlSession;

	String namespace = "com.joblessfriend.jobfinder.company.dao.CompanyRecruitmentDao";

	@Override
	public void jobPostDelete(List<Integer> jobPostIdList) {
		sqlSession.delete(namespace + ".jobPostDelete", jobPostIdList);
	}

	@Override
	public List<CompanyRecruitmentVo> companyRecruitmentSelectList(int companyId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(
				"com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.companyRecruitmentList", companyId);
	}

	@Override
	public void jobPostFileDelete(List<Integer> jobPostIdList) {
		// TODO Auto-generated method stub
		sqlSession.delete("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.jobPostFileDelete",
				jobPostIdList);

	}

	@Override
	public void jobPostTagDelete(List<Integer> jobPostIdList) {
		// TODO Auto-generated method stub
		sqlSession.delete("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.jobPostTagDelete", jobPostIdList);

	}

	@Override
	public void jobPostStop(List<Integer> jobPostIdList) {
		// TODO Auto-generated method stub
		sqlSession.update(namespace + ".jobPostStop", jobPostIdList);

	}

}
