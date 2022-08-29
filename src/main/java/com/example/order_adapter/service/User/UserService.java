package com.example.order_adapter.service.User;

import com.example.order_adapter.dto.UserDto;
import com.example.order_adapter.exceptions.UserNotFoundException;
import com.example.order_adapter.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User getUserWithId(String identityNumber) throws UserNotFoundException;

    void createUser(UserDto userDto);

    Page<User> getAllUser(Pageable pageable);
}
