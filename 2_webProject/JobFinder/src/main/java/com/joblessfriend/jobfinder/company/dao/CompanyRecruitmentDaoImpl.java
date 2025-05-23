package com.joblessfriend.jobfinder.company.dao;

import com.joblessfriend.jobfinder.recruitment.domain.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
		return sqlSession.selectList(namespace + ".companyRecruitmentList", companyId);
	}

	@Override
	public void jobPostFileDelete(List<Integer> jobPostIdList) {
		sqlSession.delete(namespace + ".jobPostFileDelete", jobPostIdList);
	}

	@Override
	public void jobPostTagDelete(List<Integer> jobPostIdList) {
		sqlSession.delete(namespace + ".jobPostTagDelete", jobPostIdList);
	}

	@Override
	public void jobPostStop(List<Integer> jobPostIdList) {
		sqlSession.update(namespace + ".jobPostStop", jobPostIdList);
	}

}
