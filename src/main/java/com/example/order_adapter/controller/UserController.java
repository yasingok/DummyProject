package com.example.order_adapter.controller;

import com.example.order_adapter.common.constant.AdapterConstant;
import com.example.order_adapter.controller.converter.UserConverter;
import com.example.order_adapter.exceptions.UserNotFoundException;
import com.example.order_adapter.resources.UserResource;
import com.example.order_adapter.resources.UserRequestPayload;
import com.example.order_adapter.resources.UserResponsePayload;
import com.example.order_adapter.service.User.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(AdapterConstant.USER_CONTROLLER_PATH)
@Api(value = "User Api documentation")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserServiceImpl userService;

    @GetMapping("/receive/{userIdentity}")
    @ApiOperation(value = "Receive user with identity")
    ResponseEntity<UserResource> getUser(@Valid @NotBlank @PathVariable("userIdentity") String userIdentity) throws UserNotFoundException {
        logger.trace("Receive user with identity request has been taken with :{}",userIdentity);
     UserResource userResource = UserConverter.fromUser(userService.getUserWithId(userIdentity));
        return ResponseEntity.ok(userResource);
    }

    @GetMapping("/receive")
    @ApiOperation(value = "Receive all user")
    ResponseEntity<UserResponsePayload> getAllUser(@PageableDefault(value = 100) final Pageable pageable) {
        logger.trace("Receive all user request has been taken");
        Page<UserResource> userResource = userService.getAllUser(pageable).map(UserConverter::fromUser);
        UserResponsePayload userResponsePayload = new UserResponsePayload();
        userResponsePayload.setUserResource(userResource.getContent());
        return ResponseEntity.ok(userResponsePayload);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create new user")
    ResponseEntity<UserRequestPayload> createUser(@Valid @NotNull @RequestBody final UserRequestPayload userRequestPayload){
        logger.trace("Create new User request has been taken with :{}", userRequestPayload);
        UserResource userResource = userRequestPayload.getUserResource();
        userService.createUser(UserConverter.fromUserResource(userResource));

        return ResponseEntity.ok(userRequestPayload);
    }
}
