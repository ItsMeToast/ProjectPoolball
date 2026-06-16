package net.poolballgms.poolballweb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerDataRepository extends JpaRepository<PlayerData, PlayerDataID> {
    List<PlayerData> findBySeason(int season);
}
