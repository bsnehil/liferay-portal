/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.taglib.util;

import com.liferay.portal.kernel.servlet.taglib.BaseBodyTagSupport;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Eduardo Lundgren
 */
public class PositionTagSupport extends BaseBodyTagSupport implements BodyTag {

	public String getPosition() {
		return getPositionValue();
	}

	public boolean isPositionAuto() {
		String position = getPosition();

		if (position.equals(_POSITION_AUTO)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isPositionInLine() {
		String position = getPosition();

		if (position.equals(_POSITION_INLINE)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setPosition(String position) {
		_position = position;
	}

	protected void cleanUp() {
		_position = null;
	}

	protected String getPositionValue() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		String position = _position;

		String fragmentId = ParamUtil.getString(request, "p_f_id");

		if (Validator.isNotNull(fragmentId)) {
			position = _POSITION_INLINE;
		}

		if (Validator.isNull(position)) {
			if (_SCRIPT_BUFFER_FILTER_ENABLED) {
				position = _POSITION_AUTO;
			}
			else {
				position = _POSITION_INLINE;
			}
		}

		return position;
	}

	private static final String _POSITION_AUTO = "auto";

	private static final String _POSITION_INLINE = "inline";

	private static final boolean _SCRIPT_BUFFER_FILTER_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(
				"com.liferay.portal.servlet.filters.script." +
					"ScriptBufferFilter"));

	private String _position;

}