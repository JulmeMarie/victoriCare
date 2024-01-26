package com.victoricare.api.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.dtos.inputs.AddressInputDTO;
import com.victoricare.api.entities.Address;
import com.victoricare.api.entities.User;

public interface IAddressService {

    Address doCreate(Integer userId, AddressInputDTO dto);

    Address doUpdate(User onlineUser, AddressInputDTO dto, Integer addressId);

    void doCancel(User onlineUser, Integer id);

    void doDelete(User onlineUser, Integer id);

    Address doGet(Integer onlineUserId, Integer id);

    Page<Address> doPage(Pageable pageable);

    List<Address> doListByUser(User onlineUser, Integer userId);
}
