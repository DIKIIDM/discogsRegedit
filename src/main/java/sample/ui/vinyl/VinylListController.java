package sample.ui.vinyl;

import sample.model.Entity;
import sample.repo.VinylRepository;
import sample.controller.Controller;

import java.util.List;

public class VinylListController extends Controller {
    private VinylRepository vinylRepository;

    public VinylListController(VinylRepository vinylRepository) {
        this.vinylRepository = vinylRepository;
    }

    public List<? extends Entity> getObjs() {
        return vinylRepository.getObjs();
    }
}
