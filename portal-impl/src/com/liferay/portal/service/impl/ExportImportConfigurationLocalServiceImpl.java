/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.ExportImportConfigurationLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 */
public class ExportImportConfigurationLocalServiceImpl
	extends ExportImportConfigurationLocalServiceBaseImpl {

	@Override
	public ExportImportConfiguration addExportImportConfiguration(
			long userId, long groupId, String name, String description,
			int type, Map<String, Serializable> settingsMap,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long exportImportConfigurationId = counterLocalService.increment();

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationPersistence.create(
				exportImportConfigurationId);

		exportImportConfiguration.setGroupId(groupId);
		exportImportConfiguration.setCompanyId(user.getCompanyId());
		exportImportConfiguration.setUserId(userId);
		exportImportConfiguration.setUserName(user.getFullName());
		exportImportConfiguration.setCreateDate(
			serviceContext.getCreateDate(now));
		exportImportConfiguration.setModifiedDate(
			serviceContext.getModifiedDate(now));
		exportImportConfiguration.setName(name);
		exportImportConfiguration.setDescription(description);
		exportImportConfiguration.setType(type);

		if (settingsMap != null) {
			String settings = JSONFactoryUtil.serialize(settingsMap);

			exportImportConfiguration.setSettings(settings);
		}

		return exportImportConfigurationPersistence.update(
			exportImportConfiguration);
	}

	@Override
	public void deleteExportImportConfigurations(long groupId)
		throws SystemException {

		List<ExportImportConfiguration> exportImportConfigurations =
			exportImportConfigurationPersistence.findByGroupId(groupId);

		for (ExportImportConfiguration configuration :
				exportImportConfigurations) {

			exportImportConfigurationPersistence.remove(configuration);
		}
	}

	@Override
	public List<ExportImportConfiguration> getExportImportConfigurations(
			long groupId, int type)
		throws SystemException {

		return exportImportConfigurationPersistence.findByG_T(groupId, type);
	}

	@Override
	public List<ExportImportConfiguration> getExportImportConfigurations(
			long groupId, int type, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return exportImportConfigurationPersistence.findByG_T(
			groupId, type, start, end, orderByComparator);
	}

	@Override
	public int getExportImportConfigurationsCount(long groupId, int type)
		throws SystemException {

		return exportImportConfigurationPersistence.countByG_T(groupId, type);
	}

	@Override
	public ExportImportConfiguration updateExportImportConfiguration(
			long configurationId, String name, String description,
			Map<String, Serializable> settingsMap,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationPersistence.findByPrimaryKey(
				configurationId);

		exportImportConfiguration.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		exportImportConfiguration.setName(name);
		exportImportConfiguration.setDescription(description);

		if (settingsMap != null) {
			String settings = JSONFactoryUtil.serialize(settingsMap);

			exportImportConfiguration.setSettings(settings);
		}

		return exportImportConfigurationPersistence.update(
			exportImportConfiguration);
	}

}