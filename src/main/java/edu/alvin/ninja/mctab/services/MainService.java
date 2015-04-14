package edu.alvin.ninja.mctab.services;

import edu.alvin.ninja.mctab.models.Main;
import edu.alvin.ninja.mctab.models.Main.Status;
import edu.alvin.ninja.mctab.repositories.MainRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class MainService {
    private MainRepository mainRepository;

    @Inject
    public MainService(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public List<Main> findAll() {
        return mainRepository.findByStatus(Status.VALID);
    }

    public Optional<Main> findById(Integer id) {
        return mainRepository.find(id);
    }

    public void save(Main main) {
        mainRepository.save(main);
    }

    public void saveOrUpdate(Main main) {
        mainRepository.saveOrUpdate(main);
    }

    public void delete(Main main) {
        main.setStatus(Status.INVALID);
        mainRepository.saveOrUpdate(main);
    }
}
