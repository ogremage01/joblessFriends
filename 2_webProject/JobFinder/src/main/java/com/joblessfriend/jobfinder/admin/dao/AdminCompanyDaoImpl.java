package com.joblessfriend.jobfinder.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;

@Repository
public class AdminCompanyDaoImpl implements AdminCompanyDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	private String namespace = "com.joblessfriend.jobfinder.admin.dao.AdminCompanyDao.";
	

	@Override
	public List<CompanyVo> companySelectList(int page) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "companySelectList",page);
	}


	@Override
	public int companyCount() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "companyCount");
	}


	@Override
	public CompanyVo companySelectOne(int companyId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "companySelectOne",companyId);
	}


	@Override
	public int companyUpdateOne(CompanyVo existCompanyVo) {
		// TODO Auto-generated method stub
		return sqlSession.update(namespace + "companyUpdateOne",existCompanyVo);
		
	}



	@Override
	public int companyDeleteOne(int companyId) {
		// TODO Auto-generated method stub
		return sqlSession.delete(namespace + "companyDeleteOne", companyId);
	}


	@Override
	public int companyDeleteList(List<Integer> companyIdList) {
		// TODO Auto-generated method stub
		return sqlSession.delete(namespace + "companyDeleteList", companyIdList);
	}
	

	//검색기업 목록
	@Override
	public List<CompanyVo> companySelectList(int page, String keyword) {
		// TODO Auto-generated method stub
				
		Map<String, Object> paramMap = new HashMap<>();
	    paramMap.put("page", page);
	    paramMap.put("keyword", keyword);
		
		return sqlSession.selectList(namespace + "companySelectListByKeyword",paramMap);
	}

	//검색 기업 숫자
	@Override
	public int companyCount(String keyword) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "companyCountByKeyword",keyword);
	}


}
