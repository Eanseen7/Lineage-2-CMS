package studio.lineage2.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studio.lineage2.cms.model.MAccount;

@Repository public interface MAccountRepository extends JpaRepository<MAccount, Long>
{}