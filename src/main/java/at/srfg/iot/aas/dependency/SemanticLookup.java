package at.srfg.iot.aas.dependency;

import org.springframework.cloud.openfeign.FeignClient;

import at.srfg.iot.eclass.api.EClassService;

@FeignClient(name = "eclass-service")
public interface SemanticLookup extends EClassService {

}
