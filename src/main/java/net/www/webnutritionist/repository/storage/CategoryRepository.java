package net.www.webnutritionist.repository.storage;

import org.springframework.data.jpa.repository.JpaRepository;

import net.www.webnutritionist.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
}
