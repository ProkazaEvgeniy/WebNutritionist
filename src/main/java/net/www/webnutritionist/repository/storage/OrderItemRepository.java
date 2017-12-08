package net.www.webnutritionist.repository.storage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.www.webnutritionist.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	List<OrderItem> findByOrderId(Long idOrder);
}
