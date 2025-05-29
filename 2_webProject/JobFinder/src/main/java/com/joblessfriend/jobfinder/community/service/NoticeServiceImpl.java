package com.joblessfriend.jobfinder.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.community.dao.CommunityDao;
import com.joblessfriend.jobfinder.community.dao.NoticeDao;
import com.joblessfriend.jobfinder.community.domain.NoticeVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private NoticeDao noticeDao;
	
	@Override
	public int getNoticeTotalCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return noticeDao.getNoticeTotalCount(searchVo);
	}

	@Override
	public List<NoticeVo> noticeSelectList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return noticeDao.noticeSelectList(searchVo);
	}

	@Override
	public int noticeSeqNum() {
		// TODO Auto-generated method stub
		return noticeDao.noticeSeqNum();
	}

	@Override
	public void noticeInsertOne(NoticeVo noticeVo) {
		// TODO Auto-generated method stub
		noticeDao.noticeInsertOne(noticeVo);
	}

	@Override
	public NoticeVo noticeDetail(int no) {
		// TODO Auto-generated method stub
		return noticeDao.noticeDetail(no);
	}

	@Override
	public void noticeViewCount(NoticeVo noticeVo) {
		// TODO Auto-generated method stub
		noticeDao.noticeViewCount(noticeVo);
	}

	@Override
	public void noticeUpdate(NoticeVo noticeVo) {
		// TODO Auto-generated method stub
		noticeDao.noticeUpdate(noticeVo);
	}

	@Override
	public void noticeDelete(List<Integer> noticeIdList) {
		// TODO Auto-generated method stub
		noticeDao.noticeDelete(noticeIdList);
	}


}
