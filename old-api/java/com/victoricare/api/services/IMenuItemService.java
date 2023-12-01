package com.victoricare.api.services;

import java.util.List;

import com.victoricare.api.dtos.MenuItemDTO;
import com.victoricare.api.entities.User;
import com.victoricare.api.models.MenuItemModel;

public interface IMenuItemService {

	public MenuItemModel create(User author, MenuItemDTO menuItemReq);

	public MenuItemModel update(User author, MenuItemDTO menuItemReq);

	public void delete(User author, Integer menuItemId);

	public MenuItemModel unique(Integer menuItemId);

	public List<MenuItemModel> list();

}
