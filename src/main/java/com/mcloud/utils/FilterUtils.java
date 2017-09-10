package com.mcloud.utils;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class FilterUtils {

	public static void applyPageAndPerPageFilter(Integer page, Integer perpage, Criteria criteria) {
		if (page != null && perpage != null) {
			criteria.setFirstResult(page * perpage);
			criteria.setMaxResults(perpage);
		}
	}

	public static void applyMonthYearFilter(String year, String month, Criteria criteria) {

		// String sql = "MONTH(START_TIME) = "+month+" AND YEAR(START_TIME) =
		// "+year+"";

		String sql = "";

		if (month != null && month != "") {
			sql += "MONTH(START_TIME) = " + month;
		}

		if (year != null && year != "") {
			if (!sql.equalsIgnoreCase("")) {
				sql += " AND YEAR(START_TIME) = " + year;
			} else {
				sql += "YEAR(START_TIME) = " + year;
			}
		}

		if (!sql.equalsIgnoreCase("")) {
			criteria.add(Restrictions.sqlRestriction(sql));
		}
	}
}
