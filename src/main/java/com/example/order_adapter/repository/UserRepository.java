package com.example.order_adapter.repository;

import com.example.order_adapter.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByIdentityNumber(String identifier);

    boolean existsByIdentityNumber(String identityNumber);
}
