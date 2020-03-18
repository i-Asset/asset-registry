package at.srfg.iot.aas.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.srfg.iot.aas.model.Asset;
import at.srfg.iot.aas.model.AssetAdministrationShell;
import at.srfg.iot.aas.model.Identifier;
import at.srfg.iot.aas.repository.IdentifiableRepository;

@Service
public class AssetService {

	@Autowired
	private IdentifiableRepository<Asset> assetRepo;
	
	public Optional<AssetAdministrationShell> getAssetAdministrationShell(Identifier identification) {
		Asset asset = assetRepo.findByIdentification(identification);
		if ( asset != null) {
			AssetAdministrationShell theShell = asset.getAssetAdministrationShell();
			return Optional.of(theShell);
		}
		else 
			return Optional.empty();
		
	}
	
	

}
