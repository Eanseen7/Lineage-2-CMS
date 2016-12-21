package studio.lineage2.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studio.lineage2.cms.model.Info;

/**
 Eanseen
 27.03.2016
 */
@Repository public interface InfoRepository extends JpaRepository<Info, Long>
{}