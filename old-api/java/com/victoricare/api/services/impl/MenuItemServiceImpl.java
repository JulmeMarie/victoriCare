package com.victoricare.api.services.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victoricare.api.dtos.MenuItemDTO;
import com.victoricare.api.entities.MenuItem;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ResourceAlreadyExistsException;
import com.victoricare.api.models.MenuItemModel;
import com.victoricare.api.repositories.MenuItemRepository;
import com.victoricare.api.services.IMenuItemService;

@Service
public class MenuItemServiceImpl implements IMenuItemService{
	private static final Logger logger = LoggerFactory.getLogger(MenuItemServiceImpl.class);

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Override
	public MenuItemModel create(User author, MenuItemDTO appRequest) {
		Date now = Date.from(Instant.now());
		this.menuItemRepository.findByHrefMenuItem(appRequest.getHref()).ifPresent((menuItem)-> {
			logger.error("menuItem already exists with key : {}", appRequest.getHref());
			throw new ResourceAlreadyExistsException();
		});

		MenuItem menuItem = new MenuItem();
		menuItem.setCreateAtMenuItem(now);
		menuItem.setCreateByMenuItem(author);
		menuItem.setActiveMenuItem(appRequest.isActive());
		menuItem.setGroupMenuItem(appRequest.getGroup());
		menuItem.setHrefMenuItem(appRequest.getHref());
		menuItem.setHtmlClassMenuItem(appRequest.getHtmlClass());
		menuItem.setIconMenuItem(appRequest.getIcon());
		menuItem.setOrderMenuItem(appRequest.getOrder());
		menuItem.setTargetMenuItem(appRequest.getTarget());
		menuItem.setTextMenuItem(appRequest.getText());
		menuItem.setTypeMenuItem(appRequest.getType());
		this.menuItemRepository.save(menuItem);
		return MenuItemModel.newInstance().init(menuItem);
	}

	@Override
	public MenuItemModel update(User author, MenuItemDTO appRequest) {
		MenuItem menuItem = this.menuItemRepository.findById(appRequest.getId()).orElseThrow(
				()-> {
					logger.error("menuItem does not exist with id : {}", appRequest);
					throw new ResourceAlreadyExistsException();
				});
		menuItem.setActiveMenuItem(appRequest.isActive());
		menuItem.setTextMenuItem(appRequest.getText());
		this.menuItemRepository.save(menuItem);
		return MenuItemModel.newInstance().init(menuItem);
	}

	@Override
	public void delete(User author, Integer menuItemId) {
		MenuItem menuItem = this.menuItemRepository.findById(menuItemId).orElseThrow(
			()-> {
				logger.error("menuItem already exists with id : {}", menuItemId);
				throw new ResourceAlreadyExistsException();
			});
		menuItem.setDeleteAtMenuItem(Date.from(Instant.now()));
		menuItem.setDeleteByMenuItem(author);
		this.menuItemRepository.save(menuItem);
	}

	@Override
	public MenuItemModel unique(Integer menuItemId) {
		MenuItem menuItem = this.menuItemRepository.findById(menuItemId).orElseThrow(
			()-> {
				logger.error("menuItem already exists with id : {}", menuItemId);
				throw new ResourceAlreadyExistsException();
			});
		return MenuItemModel.newInstance().init(menuItem);
	}

	@Override
	public List<MenuItemModel> list() {
		List<MenuItem> menuItemList = this.menuItemRepository.findNonDeletedAll();
		return menuItemList.stream().map(
				menuItem ->  MenuItemModel.newInstance().init(menuItem)
		).collect(Collectors.toList());
	}
}
