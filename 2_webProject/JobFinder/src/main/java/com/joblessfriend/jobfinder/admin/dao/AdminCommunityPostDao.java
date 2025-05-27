package com.joblessfriend.jobfinder.admin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface AdminCommunityPostDao {

	void communityPostDelete(List<Integer> communityIdList);

}
