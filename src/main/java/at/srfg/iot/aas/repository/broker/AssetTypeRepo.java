package at.srfg.iot.aas.repository.broker;

import at.srfg.iot.aas.entity.broker.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface AssetTypeRepo extends JpaRepository<AssetType, Long> {
    AssetType findOneByName(String name);
    Set<AssetType> findByName(String name);
    List<AssetType> findAllByOrderByIdAsc();
}