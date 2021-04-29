package at.srfg.iot.aas.dependency;

import org.springframework.cloud.openfeign.FeignClient;

import at.srfg.iot.common.datamodel.indexing.SubmodelTypeIndexing;

@FeignClient(name = "indexing-service")
public interface SubmodelIndexer extends SubmodelTypeIndexing {
}
