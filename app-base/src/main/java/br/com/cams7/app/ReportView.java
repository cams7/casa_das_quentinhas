package br.com.cams7.app;

import static br.com.cams7.app.ReportView.IntervalPages.ALL_PAGES;
import static br.com.cams7.app.ReportView.IntervalPages.INFORMED_INTERVAL;

public class ReportView {

	private IntervalPages interval;

	private int firstPage;
	private int lastPage;
	private int totalPages;

	public ReportView() {
		super();
	}

	public IntervalPages getInterval() {
		return interval;
	}

	public void setInterval(IntervalPages interval) {
		this.interval = interval;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public Pagination getPagination(Integer firstPage, Short sizePage) {
		if (getInterval() != ALL_PAGES) {
			if (getInterval() == INFORMED_INTERVAL) {
				firstPage = getFirstPage();
				Short size = sizePage;

				sizePage = (short) ((getLastPage() - firstPage + 1) * size);

				firstPage--;
				firstPage *= size;
			}
		} else {
			firstPage = null;
			sizePage = null;
		}

		return new Pagination(firstPage, sizePage);
	}

	@Override
	public String toString() {
		return String.format("%s{interval:%s, firstPage:%s, lastPage:%s, totalPages:%s}",
				this.getClass().getSimpleName(), getInterval(), getFirstPage(), getLastPage(), getTotalPages());
	}

	public enum IntervalPages {
		ALL_PAGES, INFORMED_INTERVAL, CURRENT_PAGE
	}

	public class Pagination {
		private Integer firstPage;
		private Short sizePage;

		private Pagination(Integer firstPage, Short sizePage) {
			super();
			this.firstPage = firstPage;
			this.sizePage = sizePage;
		}

		@Override
		public String toString() {
			return String.format("%s{firstPage:%s, sizePage:%s}", this.getClass().getSimpleName(), getFirstPage(),
					getSizePage());
		}

		public Integer getFirstPage() {
			return firstPage;
		}

		public Short getSizePage() {
			return sizePage;
		}

		public boolean isFirstAndSizePage() {
			return getFirstPage() != null && getSizePage() != null;
		}

	}

}
