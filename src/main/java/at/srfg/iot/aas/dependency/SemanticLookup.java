package at.srfg.iot.aas.dependency;

import org.springframework.cloud.openfeign.FeignClient;

import at.srfg.iot.eclass.api.SemanticLookupService;

@FeignClient(name = "semantic-lookup-service")
public interface SemanticLookup extends SemanticLookupService {
}
