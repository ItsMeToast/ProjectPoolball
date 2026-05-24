package net.poolballgms.poolballweb;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<PlayerRecordJPA, Long> {
}
