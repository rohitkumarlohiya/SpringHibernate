package com.mcloud.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.mcloud.entities.AbstractEntity;

public class AppBeanUtils {

	public static void copyProperties(Object source, Object target, String... ignoreProperties) {

		if (target instanceof AbstractEntity && source instanceof AbstractEntity) {

			AbstractEntity aeTarget = (AbstractEntity) target;
			AbstractEntity aeSource = (AbstractEntity) source;

			BeanUtils.copyProperties(source, target, ignoreProperties);

			List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties)
					: new ArrayList<String>();

			/*
			 * ignoreList.add("CD"); ignoreList.add("CB");
			 */

			if (!ignoreList.contains("id")) {
				aeTarget.setId(aeSource.getId());
			}
			/*
			 * if (!ignoreList.contains("CD")) {
			 * aeTarget.setCD(aeSource.getCD()); } if
			 * (!ignoreList.contains("CB")) { aeTarget.setCB(aeSource.getCB());
			 * } if (!ignoreList.contains("UD")) {
			 * aeTarget.setUD(aeSource.getUD()); } if
			 * (!ignoreList.contains("UB")) { aeTarget.setUB(aeSource.getUB());
			 * }
			 */
			if (!ignoreList.contains("enabled")) {
				aeTarget.setEnabled(aeSource.getEnabled());
			}
		}
	}

}
