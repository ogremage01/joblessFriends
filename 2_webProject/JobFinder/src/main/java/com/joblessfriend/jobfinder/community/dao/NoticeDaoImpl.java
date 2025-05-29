package com.joblessfriend.jobfinder.community.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.community.domain.NoticeVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Repository
public class NoticeDaoImpl implements NoticeDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	String namespace = "com.joblessfriend.jobfinder.community.notice.";

	@Override
	public List<NoticeVo> noticeSelectList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+"noticeSelectList", searchVo);
	}
	
	@Override
	public int getNoticeTotalCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace+"getNoticeTotalCount", searchVo);
	}

	@Override
	public int noticeSeqNum() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace+"noticeSeqNum");
	}

	@Override
	public void noticeInsertOne(NoticeVo noticeVo) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace+"noticeInsertOne", noticeVo);
	}

	@Override
	public NoticeVo noticeDetail(int no) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace+"noticeDetail", no);
	}

	@Override
	public void noticeViewCount(NoticeVo noticeVo) {
		// TODO Auto-generated method stub
		sqlSession.update(namespace+"noticeViewCount", noticeVo);
	}

	@Override
	public void noticeUpdate(NoticeVo noticeVo) {
		// TODO Auto-generated method stub
		sqlSession.update(namespace+"noticeUpdate", noticeVo);
	}

	@Override
	public void noticeDelete(List<Integer> noticeIdList) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace+"noticeDelete", noticeIdList);
	}
	

}
