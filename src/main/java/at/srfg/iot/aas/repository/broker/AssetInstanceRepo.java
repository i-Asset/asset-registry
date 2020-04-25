package at.srfg.iot.aas.repository.broker;

import at.srfg.iot.aas.entity.broker.AssetInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface AssetInstanceRepo extends JpaRepository<AssetInstance, Long> {
    AssetInstance findOneByName(String name);
    Set<AssetInstance> findByName(String name);
    List<AssetInstance> findAllByOrderByIdAsc();
}