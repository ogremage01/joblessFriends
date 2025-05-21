package com.joblessfriend.jobfinder.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.member.dao.MemberBookmarkDao;

@Service
public class MemberBookmarkServiceimpl implements MemberBookmarkService {

	@Autowired
	MemberBookmarkDao bookmarkDao;

	@Override
	public void deleteOne(int memberId, int jobPostId) {
		// TODO Auto-generated method stub

		bookmarkDao.deleteOne(memberId, jobPostId);
	}

}
