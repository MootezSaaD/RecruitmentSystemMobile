package tn.medtech.recruitmentsystemapp.ui;

import java.util.ArrayList;
import java.util.List;

import tn.medtech.recruitmentsystemapp.api.models.Skill;

public class ApplicantSkillsRepository {
    private List<Skill> skills = new ArrayList<>();
    private boolean loaded = false;

    private static ApplicantSkillsRepository repository = null;

    public static ApplicantSkillsRepository getInstance() {
        if (repository == null) {
            repository = new ApplicantSkillsRepository();
        }
        return repository;
    }

    private ApplicantSkillsRepository() {};

    public void removeJob(Skill skill) {
        this.skills.remove(skill);
    }

    public List<Skill> findAll() {
        return this.skills;
    }

    public void addAll(List<Skill> offers) {
        this.skills = offers;
        this.loaded = true;
    }

    public void addJobOffer(Skill skills) {
        this.skills.add(skills);
    }

    public boolean isLoaded() {
        return this.loaded;
    }
}
