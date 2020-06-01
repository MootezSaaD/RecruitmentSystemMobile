package tn.medtech.recruitmentsystemapp.ui;

import java.util.ArrayList;
import java.util.List;

import tn.medtech.recruitmentsystemapp.api.models.JobOffer;

public class JobsRepository {

    private List<JobOffer> jobOffers = new ArrayList<>();
    private boolean loaded = false;

    private static JobsRepository repository = null;

    public static JobsRepository getInstance() {
        if (repository == null) {
            repository = new JobsRepository();
        }
        return repository;
    }

    private JobsRepository() {
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

