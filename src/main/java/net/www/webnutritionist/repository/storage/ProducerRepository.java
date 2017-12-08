package net.www.webnutritionist.repository.storage;

import org.springframework.data.jpa.repository.JpaRepository;

import net.www.webnutritionist.entity.Producer;

public interface ProducerRepository extends JpaRepository<Producer, Integer> {
}
