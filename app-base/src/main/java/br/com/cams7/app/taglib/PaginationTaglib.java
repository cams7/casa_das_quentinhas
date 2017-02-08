/**
 * 
 */
package br.com.cams7.app.taglib;

import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @author César Magalhães
 *
 */
public class PaginationTaglib extends SimpleTagSupport {
	private String uri;
	private int offset;
	private int count;
	private int max = 10;
	private int steps = 10;

	private final String PREVIOUS = "&laquo;";
	private final String NEXT = "&raquo;";

	private Writer getWriter() {
		JspWriter out = getJspContext().getOut();
		return out;
	}

	@Override
	public void doTag() throws JspException {
		final int TOTAL_PAGES = getTotalPages();
		final int CURRENT_PAGE = getPage(offset);

		Writer out = getWriter();

		try {
			out.write("<nav>");
			out.write("<ul class=\"pagination\">");

			if (offset < steps)
				out.write(constructLink(1, PREVIOUS, "disabled", true));
			else
				out.write(constructLink(offset - steps, PREVIOUS, null, false));

			int i = 0;
			while (i < count) {
				if (offset == i)
					out.write(
							constructLink((i / steps + 1) - 1 * steps, String.valueOf(i / steps + 1), "active", true));
				else
					out.write(constructLink(i / steps * steps, String.valueOf(i / steps + 1), null, false));

				final int NEXT = nextOffset(TOTAL_PAGES, CURRENT_PAGE, i);
				if (i != NEXT) {
					out.write(constructLink(0, "<span>...</span>", "disabled", true));
					i = NEXT;
				} else
					i += steps;
			}

			if (offset + steps >= count)
				out.write(constructLink(offset + steps, NEXT, "disabled", true));
			else
				out.write(constructLink(offset + steps, NEXT, null, false));

			out.write("</ul>");
			out.write("</nav>");
		} catch (java.io.IOException ex) {
			throw new JspException("Error in Paginator tag", ex);
		}
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	private String constructLink(int page, String text, String className, boolean disabled) {
		StringBuilder link = new StringBuilder("<li");
		if (className != null) {
			link.append(" class=\"");
			link.append(className);
			link.append("\"");
		}
		if (disabled)
			link.append(">").append("<a href=\"#\">" + text + "</a></li>");
		else
			link.append(">").append("<a href=\"" + uri + "?offset=" + page + "\">" + text + "</a></li>");
		return link.toString();
	}

	private int getTotalPages() {
		int pages = count / steps;
		if (count % steps > 0)
			pages++;
		return pages;
	}

	private int getPage(final int offset) {
		return offset / steps + 1;
	}

	private int nextOffset(final int totalPages, final int currentPage, final int offset) {
		if (totalPages > max) {
			final int PAGE = getPage(offset);

			final int SKIP_AFTER_PAGE = 2;
			final int SKIP_BEFORE_PAGE = totalPages - 1;

			if (currentPage < max - 3) {
				if (PAGE == max - 2)
					return SKIP_BEFORE_PAGE * steps - steps;
			} else if (currentPage > totalPages - max + 4) {
				if (PAGE == SKIP_AFTER_PAGE)
					return (totalPages - max + 3) * steps - steps;
			} else {
				final int TOTAL_PAGES = max - 5;
				final int BEFORE_PAGE = TOTAL_PAGES / 2;
				final int AFTER_PAGE = BEFORE_PAGE + (TOTAL_PAGES % 2 > 0 ? 1 : 0);

				if (PAGE == SKIP_AFTER_PAGE)
					return (currentPage - BEFORE_PAGE) * steps - steps;
				if (PAGE == currentPage + AFTER_PAGE)
					return SKIP_BEFORE_PAGE * steps - steps;

			}
		}

		return offset;
	}

}
