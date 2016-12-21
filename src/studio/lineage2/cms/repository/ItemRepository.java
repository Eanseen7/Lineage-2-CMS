package studio.lineage2.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studio.lineage2.cms.model.Item;

/**
 Eanseen
 04.11.2015
 */
@Repository public interface ItemRepository extends JpaRepository<Item, Long>
{}