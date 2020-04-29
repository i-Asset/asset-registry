package at.srfg.iot.aas.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.basyx.vab.coder.json.metaprotocol.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import at.srfg.iot.aas.basys.RegistryProvider;

@RestController
@RequestMapping("/basyx")
public class DirectoryController implements BasyxRegistry {
	@Autowired
	private RegistryProvider provider;

	@Override
	public ResponseEntity<?> createRootValue(Object value) {
		return createValue(null, value);
	}

	@Override
	public ResponseEntity<?> createValue(String path, Object value) {
		try {
			provider.createValue(path, value);
		} catch (Exception e) {
			// report server error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<?> deleteValue(String path) {
		try {
			provider.deleteValue(path);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			// report server error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
//    @GetMapping("all/**")
//    public String allDirectories(HttpServletRequest request) {
//        return request.getRequestURI()
//            .split(request.getContextPath() + "/all/")[1];
//    }

	@Override
	public ResponseEntity<?> getValue(HttpServletRequest request, String path) {
		
		try {
			Result result = new Result(true);
			result.put(Result.ENTITY, provider.getModelPropertyValue(path));
			// return the result
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			// report server error
			Result result = new Result(e);
			return ResponseEntity.ok(result);
		}
	}
	
}
