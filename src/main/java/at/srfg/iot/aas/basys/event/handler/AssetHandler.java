package at.srfg.iot.aas.basys.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basys.event.GetAssetEvent;
import at.srfg.iot.aas.basys.event.SetAssetEvent;

@Component
public class AssetHandler {

	@EventListener
	public void onAssetSet(SetAssetEvent  event) {
		System.out.println("Asset handling");
	}

	@EventListener
	public void onAssetGet(GetAssetEvent event) {
		System.out.println("Asset handling");
	
		
		
	}
}
