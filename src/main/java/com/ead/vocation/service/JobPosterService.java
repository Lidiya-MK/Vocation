package com.ead.vocation.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ead.vocation.dtos.JobRequest;
import com.ead.vocation.dtos.UpdateJobPosterRequest;
import com.ead.vocation.model.Job;
import com.ead.vocation.model.JobPoster;
import com.ead.vocation.model.User;
import com.ead.vocation.repository.JobPosterRepository;
import com.ead.vocation.repository.JobRepository;
import com.ead.vocation.repository.UserRepository;

@Service
public class JobPosterService {
	@Autowired
	private JobPosterRepository jobPosterRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public JobPoster getJobPoster(Integer id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		JobPoster jobPoster = jobPosterRepository.findByUser(user)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		return jobPoster;
	}

	public JobPoster createJobPoster(JobPoster jobPoster, Integer userID) {
		User user = userRepository.findById(userID)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		jobPoster.setUser(user);
		return jobPosterRepository.save(jobPoster);
	}

	public List<Job> getAllJobsByPosterId(Integer jobPosterID) {
		User user = userRepository.findById(jobPosterID)
				.orElseThrow(() -> new IllegalArgumentException("Job Poster not found"));

		JobPoster jobPoster = jobPosterRepository.findByUser(user)
				.orElseThrow(() -> new IllegalArgumentException("Job Poster not found"));
		if (jobPoster == null) {
			throw new IllegalArgumentException("Job Poster not found");
		}

		List<Job> jobs = jobRepository.findByJobPoster(jobPoster);
		return jobs != null ? jobs : Collections.emptyList();
	}

	public Job getJobByIdAndPosterId(Integer jobID, Integer jobPosterID) {
		User user = userRepository.findById(jobPosterID)
				.orElseThrow(() -> new IllegalArgumentException("Job Poster not found"));

		JobPoster jobPoster = jobPosterRepository.findByUser(user)
				.orElseThrow(() -> new IllegalArgumentException("Job Poster not found"));
		if (jobPoster == null) {
			throw new IllegalArgumentException("Job Poster not found");
		}

		return jobRepository.findByIdAndJobPoster(jobID, jobPoster)
				.orElseThrow(() -> new IllegalArgumentException(
						"Job not found or does not belong to the specified Job Poster"));
	}

	public Job createJob(JobRequest job, Integer jobPosterID) {
		if (job.getApplicationDeadline().isAfter(job.getStartDate())) {
			throw new IllegalArgumentException("Application deadline must be before the start date");
		}

		if (job.getStartDate().isAfter(job.getEndDate())) {
			throw new IllegalArgumentException("Start date must be before the end date");
		}

		User user = userRepository.findById(jobPosterID)
				.orElseThrow(() -> new IllegalArgumentException("Job Poster not found"));

		JobPoster jobPoster = jobPosterRepository.findByUser(user)
				.orElseThrow(() -> new IllegalArgumentException("Job Poster not found"));
		if (jobPoster == null) {
			throw new IllegalArgumentException("Job Poster not found");
		}

		Job jobEntity = job.toJobEntity();
		jobEntity.setJobPoster(jobPoster);
		return jobRepository.save(jobEntity);
	}

	public Job updateJobByIdAndPosterId(Integer jobID, JobRequest jobRequest, Integer jobPosterID) {
		User user = userRepository.findById(jobPosterID)
				.orElseThrow(() -> new IllegalArgumentException("Job Poster not found"));

		JobPoster jobPoster = jobPosterRepository.findByUser(user)
				.orElseThrow(() -> new IllegalArgumentException("Job Poster not found"));
		if (jobPoster == null) {
			throw new IllegalArgumentException("Job Poster not found");
		}

		Job job = jobRepository.findByIdAndJobPoster(jobID, jobPoster)
				.orElseThrow(() -> new IllegalArgumentException(
						"Job not found or does not belong to the specified Job Poster"));

		job.setTitle(jobRequest.getTitle());
		job.setDescription(jobRequest.getDescription());
		job.setType(jobRequest.getType());
		job.setSkillsRequired(jobRequest.getSkillsRequired());
		job.setBudget(jobRequest.getBudget());
		job.setStartDate(jobRequest.getStartDate());
		job.setEndDate(jobRequest.getEndDate());
		job.setApplicationDeadline(jobRequest.getApplicationDeadline());
		job.setStatus(jobRequest.getStatus());
		return jobRepository.save(job);
	}

	public void deleteJobByIdAndPosterId(Integer jobID, Integer jobPosterID) {
		User user = userRepository.findById(jobPosterID)
				.orElseThrow(() -> new IllegalArgumentException("Job Poster not found"));

		JobPoster jobPoster = jobPosterRepository.findByUser(user)
				.orElseThrow(() -> new IllegalArgumentException("Job Poster not found"));
		if (jobPoster == null) {
			throw new IllegalArgumentException("Job Poster not found");
		}

		Job job = jobRepository.findByIdAndJobPoster(jobID, jobPoster)
				.orElseThrow(() -> new IllegalArgumentException(
						"Job not found or does not belong to the specified Job Poster"));

		jobRepository.delete(job);
	}

	public JobPoster updateJobPoster(Integer id, UpdateJobPosterRequest newJobPosterRequest) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		JobPoster jobPoster = jobPosterRepository.findByUser(user)
				.orElseThrow(() -> new IllegalArgumentException("Job Poster not found"));

		System.out.println(newJobPosterRequest.getEmail() + "; ;" + jobPoster.getUser().getEmail());
		System.out.println(newJobPosterRequest.getEmail() != jobPoster.getUser().getEmail());
		if (userRepository.existsByEmail(newJobPosterRequest.getEmail())
				&& !newJobPosterRequest.getEmail().equals(jobPoster.getUser().getEmail())) {
			throw new IllegalArgumentException("Email already in use");
		}

		jobPoster.getUser().setName(newJobPosterRequest.getName());
		jobPoster.getUser().setEmail(newJobPosterRequest.getEmail());
		jobPoster.getUser().setPassword(passwordEncoder.encode(newJobPosterRequest.getPassword()));
		jobPoster.setIndustry(newJobPosterRequest.getIndustry());
		jobPoster.setDescription(newJobPosterRequest.getDescription());
		jobPoster.setLocation(newJobPosterRequest.getLocation());
		jobPoster.setPhoneNumber(newJobPosterRequest.getPhoneNumber());
		jobPoster.setLinks(newJobPosterRequest.getLinks());
		return jobPosterRepository.save(jobPoster);
	}
}
