package com.example.order_adapter.repository;

import com.example.order_adapter.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Transactional
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    @Query(value = "Select o.* FROM Orders o ,Users u WHERE u.id = o.user_id And u.identity_number = ?1 And o.Status = ?2", nativeQuery = true)
    Page<Order> findByIdentityNumberAndStatus(final String identityNumber,
                                              final String status, Pageable pageable);

    @Query(value = "Select o.* FROM Orders o ,Users u WHERE u.id = o.user_id And o.order_id =:order_id  And u.identity_number = :identity_number And o.Status = :Status", nativeQuery = true)
    Optional<Order> findByIdentityNumberAndOrderIdAndStatus(@Param("identity_number") final String identityNumber,
                                                            @Param("Status") final String status, @Param("order_id") int orderId);

    @Modifying
    @Query(value = "Update Orders o set o.status =:status where o.order_id=:order_id", nativeQuery = true)
    void softDeleteByOrderId( @Param("status") final String status, @Param("order_id") int orderId);

    boolean existsByOrderId(int orderId);

    Optional<Order> findByOrderId(int orderId);
}
