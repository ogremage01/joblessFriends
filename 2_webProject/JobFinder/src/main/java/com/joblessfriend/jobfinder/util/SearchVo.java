package com.joblessfriend.jobfinder.util;

public class SearchVo {
	private int page;             // 현재 페이지 번호
	private int recordSize;       // 페이지당 출력할 데이터 개수
	private int pageSize;         // 화면 하단에 출력할 페이지 사이즈
	private String keyword;       // 검색 키워드
	private String searchType; // 검색 유형
	private int startRow;
	private int endRow;
	private int companyId;
	private int jobPostId;
	// Getter/Setter 추가
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public SearchVo() {
		this.page = 1;
		this.recordSize = 8;
		this.pageSize = 10;
	}

	//GETTER SETTER
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}


    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(int jobPostId) {
        this.jobPostId = jobPostId;
    }
}
