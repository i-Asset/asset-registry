package at.srfg.iot.aas.dependency;

import org.springframework.cloud.openfeign.FeignClient;

import at.srfg.indexing.AssetTypeIndexing;

@FeignClient(name = "indexing-service")
public interface AssetIndexer extends AssetTypeIndexing {
}