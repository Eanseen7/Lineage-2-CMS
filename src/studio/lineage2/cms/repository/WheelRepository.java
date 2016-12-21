package studio.lineage2.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studio.lineage2.cms.model.Wheel;

/**
 Eanseen
 05.11.2015
 */
@Repository public interface WheelRepository extends JpaRepository<Wheel, Long>
{}