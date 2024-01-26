package com.victoricare.api.services.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.victoricare.api.dtos.inputs.AddressInputDTO;
import com.victoricare.api.entities.Address;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.repositories.IAddressRepository;
import com.victoricare.api.services.IAddressService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AddressService implements IAddressService {

    @Autowired
    private IAddressRepository repository;

    private void saveAddress(Address address, AddressInputDTO dto) {
        address.setStreet(dto.getStreet());
        address.setTown(dto.getTown());
        address.setZipCode(dto.getZipCode());
        address.setCountry(dto.getCountry());
        address.setAddtionnal(dto.getAddtionnal());
        address.setCountry(dto.getCountry());
        this.repository.save(address);
    }

    @Override
    public Address doCreate(Integer userId, AddressInputDTO dto) {
        Address address = Address.builder()
                .creationDate(new Date())
                .creationAuthorId(userId)
                .build();

        this.saveAddress(address, dto);
        return address;
    }

    @Override
    public Address doUpdate(User onlineUser, AddressInputDTO dto, Integer addressId) {
        Address exisitingAddress = this.doGet(onlineUser.getId(), addressId);

        if (exisitingAddress.getDeletionDate() != null) {
            throw new ResourceNotFoundException(EMessage.ADDRESS_HAS_BEEN_CANCELED);
        }

        if (!exisitingAddress.getCreationAuthorId().equals(onlineUser.getId())) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_AUTHOR);
        }

        exisitingAddress.setUpdateAuthorId(onlineUser.getId());
        exisitingAddress.setUpdateDate(new Date());
        this.saveAddress(exisitingAddress, dto);
        return exisitingAddress;
    }

    @Override
    public void doCancel(User user, Integer id) {
        Address exisitingAddress = this.doGet(user.getId(), id);

        if (exisitingAddress.getDeletionDate() != null) {
            throw new ResourceNotFoundException(EMessage.ADDRESS_HAS_BEEN_CANCELED);
        }

        if (!exisitingAddress.getCreationAuthorId().equals(user.getId())) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_AUTHOR);
        }
        exisitingAddress.setDeletionDate(new Date());
        exisitingAddress.setDeletionAuthorId(user.getId());
        this.repository.save(exisitingAddress);
    }

    @Override
    public void doDelete(User onlineUser, Integer id) {
        Address exisitingAddress = this.doGet(onlineUser.getId(), id);
        this.repository.delete(exisitingAddress);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + Address.class + " with id : [" + id + "]");
    }

    @Override
    public Address doGet(Integer onlineUserId, Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.ADDRESS_NOT_FOUND));
    }

    @Override
    public Page<Address> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public List<Address> doListByUser(User onlineUser, Integer userId) {
        return this.repository.findAllByUserId(userId);
    }
}