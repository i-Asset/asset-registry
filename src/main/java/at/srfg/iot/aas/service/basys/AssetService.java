package at.srfg.iot.aas.service.basys;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.srfg.iot.aas.basic.Asset;
import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.repository.basys.IdentifiableRepository;

@Service
public class AssetService {

	@Autowired
	private IdentifiableRepository<AssetAdministrationShell> aasRepo;
	@Autowired
	private IdentifiableRepository<Asset> assetRepo;
	
	public Optional<AssetAdministrationShell> getAssetAdministrationShell(Identifier identification) {
		Optional<Asset> asset = assetRepo.findByIdentification(identification);
		if ( asset.isPresent()) {
			AssetAdministrationShell theShell = asset.get().getAssetAdministrationShell();
			return Optional.of(theShell);
		}
		else 
			return Optional.empty();
		
	}
	
	public boolean deleteAsset(Identifier identification) {
		Optional<Asset> asset = assetRepo.findByIdentification(identification);
		if ( asset.isPresent()) {
			AssetAdministrationShell theShell = asset.get().getAssetAdministrationShell();
			if ( theShell != null) {
				aasRepo.delete(theShell);
				return true;
			}
		}
		return false;
	}
	
	
	

}
