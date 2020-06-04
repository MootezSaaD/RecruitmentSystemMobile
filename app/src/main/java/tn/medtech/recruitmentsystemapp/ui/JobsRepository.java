package tn.medtech.recruitmentsystemapp.ui;

import java.util.ArrayList;
import java.util.List;

import tn.medtech.recruitmentsystemapp.api.models.JobOffer;

public class JobsRepository {

    private static JobsRepository repository = null;
    private List<JobOffer> jobOffers = new ArrayList<>();
    private boolean loaded = false;

    private JobsRepository() {
    }

    public static JobsRepository getInstance() {
        if (repository == null) {
            repository = new JobsRepository();
        }
        return repository;
    }

    ;

    public void removeJob(JobOffer jobOffer) {
        this.jobOffers.remove(jobOffer);
    }

    public List<JobOffer> findAll() {
        return this.jobOffers;
    }

    public void addAll(List<JobOffer> offers) {
        this.jobOffers = offers;
        this.loaded = true;
    }

    public void addJobOffer(JobOffer jobOffer) {
        this.jobOffers.add(jobOffer);
    }

    public boolean isLoaded() {
        return this.loaded;
    }

}

