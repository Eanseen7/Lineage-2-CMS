package studio.lineage2.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studio.lineage2.cms.model.Server;

/**
 Eanseen
 07.06.2016
 */
@Repository public interface ServerRepository extends JpaRepository<Server, Long>
{}