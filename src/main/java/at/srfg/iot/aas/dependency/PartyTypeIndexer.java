package at.srfg.iot.aas.dependency;

import org.springframework.cloud.openfeign.FeignClient;

import at.srfg.indexing.PartyTypeIndexing;

@FeignClient(name = "indexing-service")
public interface PartyTypeIndexer extends PartyTypeIndexing {
}
