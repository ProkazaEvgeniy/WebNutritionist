package net.www.webnutritionist.repository.storage;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.www.webnutritionist.entity.Order;
	
public interface OrderRepository extends JpaRepository<Order, Long> {

	Order findById(Long id);
	
	Page<Order> findByProfileId(Long idProfile, Pageable pageble);
	
	List<Order> findByProfileId(Long idProfile);

	int countByProfileId(Long idProfile);
}
