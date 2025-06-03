package com.joblessfriend.jobfinder.util;

import com.joblessfriend.jobfinder.recruitment.domain.FilterRequestVo;

public class Pagination {
		private int totalRecordCount;     // 전체 데이터 수
		private int totalPageCount;       // 전체 페이지 수
		private int startPage;            // 첫 페이지 번호
		private int endPage;              // 끝 페이지 번호
		private int limitStart;           // LIMIT 시작 위치
		private boolean existPrevPage;    // 이전 페이지 존재 여부
		private boolean existNextPage;    // 다음 페이지 존재 여부
		private int page;				//전체 페이지 계산 후 "결과" 전달	뷰(JSP/JS)에 현재 페이지가 몇인지 전달하려면 필요함

	public Pagination(int totalRecordCount, SearchVo params) {
	        if (totalRecordCount > 0) {
	            this.totalRecordCount = totalRecordCount;
	            calculation(params);
	        }
	    }
		public Pagination(int totalRecordCount, FilterRequestVo params) {
			if (totalRecordCount > 0) {
				this.totalRecordCount = totalRecordCount;
				calculation(params.getPage(), params.getRecordSize(), 10); // 또는 params.getPageSize()가 있으면 그걸 사용
			}
		}
	//06.02 지원자 페이지네이션 //
	public Pagination(int totalRecordCount, int page, int recordSize, int pageSize) {
		if (totalRecordCount > 0) {
			this.totalRecordCount = totalRecordCount;
			calculation(page, recordSize, pageSize);
			this.page = page;
		}
	}

	private void calculation(SearchVo params) {

	        // 전체 페이지 수 계산
	        totalPageCount = ((totalRecordCount - 1) / params.getRecordSize()) + 1;

	        // 현재 페이지 번호가 전체 페이지 수보다 큰 경우, 현재 페이지 번호에 전체 페이지 수 저장
	        if (params.getPage() > totalPageCount) {
	            params.setPage(totalPageCount);
	        }

	        // 첫 페이지 번호 계산
	        startPage = ((params.getPage() - 1) / params.getPageSize()) * params.getPageSize() + 1;

	        // 끝 페이지 번호 계산
	        endPage = startPage + params.getPageSize() - 1;

	        // 끝 페이지가 전체 페이지 수보다 큰 경우, 끝 페이지 전체 페이지 수 저장
	        if (endPage > totalPageCount) {
	            endPage = totalPageCount;
	        }

	        // LIMIT 시작 위치 계산
	        limitStart = (params.getPage() - 1) * params.getRecordSize();

	        // 이전 페이지 존재 여부 확인
	        existPrevPage = startPage != 1;

	        // 다음 페이지 존재 여부 확인
	        existNextPage = (endPage * params.getRecordSize()) < totalRecordCount;
	        
	        this.page = params.getPage(); // ✅ 현재 페이지 설정
	    }
		private void calculation(int page, int recordSize, int pageSize) {
			totalPageCount = ((totalRecordCount - 1) / recordSize) + 1;

			if (page > totalPageCount) {
				page = totalPageCount;
			}

			startPage = ((page - 1) / pageSize) * pageSize + 1;
			endPage = startPage + pageSize - 1;
			if (endPage > totalPageCount) {
				endPage = totalPageCount;
			}

			limitStart = (page - 1) * recordSize;
			existPrevPage = startPage != 1;
			existNextPage = (endPage * recordSize) < totalRecordCount;
		}

	private void calculationRecruitment(SearchVo params) {
		totalPageCount = ((totalRecordCount - 1) / params.getRecordSize()) + 1;

		if (params.getPage() > totalPageCount) {
			params.setPage(totalPageCount);
		}

		this.page = params.getPage(); // ✅ 현재 페이지 설정

		startPage = ((params.getPage() - 1) / params.getPageSize()) * params.getPageSize() + 1;
		endPage = startPage + params.getPageSize() - 1;
		if (endPage > totalPageCount) {
			endPage = totalPageCount;
		}

		limitStart = (params.getPage() - 1) * params.getRecordSize();
		existPrevPage = startPage != 1;
		existNextPage = (endPage * params.getRecordSize()) < totalRecordCount;
	}

	public int getPage() {
		return page;
	}
	public int getLimitStart() {
		return limitStart;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public boolean isExistPrevPage() {
		return existPrevPage;
	}

	public boolean isExistNextPage() {
		return existNextPage;
	}

}
	
