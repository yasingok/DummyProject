package com.example.order_adapter.service.User;

import com.example.order_adapter.dto.UserDto;
import com.example.order_adapter.exceptions.UserNotFoundException;
import com.example.order_adapter.model.Address;
import com.example.order_adapter.model.User;
import com.example.order_adapter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String USER_NOT_FOUND = "User can not be found. UserIdentity:{}";
    private final UserRepository userRepository;

    @Override
    public User getUserWithId(String identityNumber) throws UserNotFoundException {
        return isValidUser(identityNumber);
    }

    @Override
    public void createUser(UserDto userDto) {
        User user = new User();
        Address address = new Address();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setIdentityNumber(userDto.getIdentityNumber());
        user.setPhoneNumber(userDto.getPhoneNumber());
        address.setAddress1(userDto.getAddress1());
        address.setAddress2(userDto.getAddress2());
        address.setCity(userDto.getCity());
        address.setState(userDto.getState());
        address.setCountry(userDto.getCountry());
        address.setPostalCode(userDto.getPostalCode());
        user.setAddress(address);
        userRepository.save(user);
    }

    @Override
    public Page<User>  getAllUser(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    private User isValidUser(String identityNumber) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByIdentityNumber(identityNumber);
        if (!userOptional.isPresent()) {
            logger.debug(USER_NOT_FOUND, identityNumber);
            throw new UserNotFoundException(String.format("User can not be found. UserIdentity:%s ", identityNumber));
        }
        return userOptional.get();
    }
}
