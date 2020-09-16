package sample.ui.location;

import sample.controller.Controller;
import sample.model.Entity;
import sample.model.Location;
import sample.repo.LocationRepository;
import sample.repo.VinylRepository;

import java.util.List;

public class LocationTreeController extends Controller {
    private LocationRepository locationRepository;
    //----------------------------------------------------------------------------------
    public LocationTreeController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
    //----------------------------------------------------------------------------------
    public List<? extends Entity> getObjs() {
        return locationRepository.getObjs();
    }
    //----------------------------------------------------------------------------------
    public Entity insert(Entity object) {
        return locationRepository.insert((Location)object);
    }
    //----------------------------------------------------------------------------------
    public void delete(List<Location> lObj) {
        locationRepository.delete(lObj);
    }
}
