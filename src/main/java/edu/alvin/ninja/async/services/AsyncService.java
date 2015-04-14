package edu.alvin.ninja.async.services;

import com.google.inject.persist.Transactional;
import edu.alvin.ninja.async.models.Async;
import edu.alvin.ninja.async.repositories.AsyncRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;
import java.util.Optional;

@Singleton
public class AsyncService {
    private AsyncRepository asyncRepository;

    @Inject
    public AsyncService(AsyncRepository asyncRepository) {
        this.asyncRepository = asyncRepository;
    }

    public Optional<Async> findNewAsync() {
        return asyncRepository.findNewAsync();
    }

    public Optional<Async> findById(Integer id) {
        return asyncRepository.find(id);
    }

    @Transactional
    public void save(Async async) {
        asyncRepository.save(async);
    }

    @Transactional
    public boolean process(Integer id) {
        Optional<Async> asyncOptional = findById(id);
        if (!asyncOptional.isPresent()) {
            return false;
        }
        Async async = asyncOptional.get();
        if (Objects.equals(async.getStep(), async.getMaximum())) {
            return false;
        }
        int speed = Math.min(async.getMaximum() - async.getStep(), async.getSpeed());
        async.setStep(async.getStep() + speed);
        asyncRepository.saveOrUpdate(async);
        return true;
    }
}
