package com.victoricare.api.validators.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.InvalidInputException;
import com.victoricare.api.validators.ValidPage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageValidator implements ConstraintValidator<ValidPage, PageInputDTO> {

    private static final int MIN_PAGE = 0;
    private static final int MAX_PAGE = 5000;
    private static final int MIN_SIZE = 5;
    private static final int MAX_SIZE = 2000;

    @Override
    public boolean isValid(PageInputDTO dto, ConstraintValidatorContext context) {

        log.info("Checking for signUpDTO.");

        if (dto.getPage() == null || dto.getPage() < MIN_PAGE || dto.getPage() > MAX_PAGE) {
            log.info("PageDTO.#page not valid.");
            return false;
        }

        if (dto.getSize() == null || dto.getSize() < MIN_SIZE || dto.getSize() > MAX_SIZE) {
            log.info("PageDTO.#size not valid.");
            return false;
        }

        String[] sorts = dto.getSort();
        if (sorts != null) {
            for (String sort : sorts) {
                String[] tabSort = sort.split(",");
                if (tabSort.length != 2) {
                    return false;
                }
                if (!List.of(Direction.ASC.name(), Direction.DESC.name()).contains(tabSort[1])) {
                    return false;
                }
            }
        }

        return true;
    }

    public static <T> Pageable getPageable(Class<T> class1, PageInputDTO dto) throws InvalidInputException {
        List<Order> orderList = new ArrayList<Order>();

        if (dto.getSort() != null) {
            String[] sorts = dto.getSort();

            for (String sort : sorts) {
                String[] tabSort = sort.split(",");

                String property = tabSort[0];
                String direction = tabSort[1];

                boolean propertyExist = Stream.of(class1.getDeclaredFields())
                        .anyMatch(field -> {
                            System.out.println(field.getName());
                            return field.getName().equalsIgnoreCase(property);
                        });

                if (!propertyExist) {
                    throw new InvalidInputException(EMessage.INVALID_SORT_PROPERTY);
                }
                orderList.add(new Order(Sort.Direction.fromString(direction), property));
            }
        }
        return PageRequest.of(dto.getPage(), dto.getSize(), Sort.by(orderList));
    }
}
