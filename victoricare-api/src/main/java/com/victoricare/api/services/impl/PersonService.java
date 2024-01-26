package com.victoricare.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.victoricare.api.dtos.inputs.AddressInputDTO;
import com.victoricare.api.dtos.inputs.PersonInputDTO;
import com.victoricare.api.entities.Address;
import com.victoricare.api.entities.Person;
import com.victoricare.api.entities.User;
import com.victoricare.api.services.IAddressService;
import com.victoricare.api.services.ICodeService;
import com.victoricare.api.services.IPersonService;

import lombok.Getter;

@Getter
@Service
public class PersonService implements IPersonService {

    @Autowired
    protected ICodeService codeService;

    @Autowired
    private IAddressService addressService;

    private void setAddress(User onlineUser, Person person, AddressInputDTO dto) {
        if (dto != null) {
            Address address = null;
            if (dto.getId() == null) {
                address = this.addressService.doCreate(onlineUser.getId(), dto);
            } else {
                address = this.addressService.doUpdate(onlineUser, dto, dto.getId());
            }
            person.setAddress(address);
        }
    }

    protected Person initPerson(User onlineUser, Person person, PersonInputDTO dto) {
        person.setBirthday(dto.getBirthday());
        person.setFirstname(dto.getFirstname());
        person.setLastname(dto.getLastname());
        person.setCountry(dto.getCountry());
        person.setGender(dto.getGender());
        person.setImage(dto.getImage());
        person.setPhone(dto.getPhone());
        this.setAddress(onlineUser, person, dto.getAddress());
        return person;
    }
}
