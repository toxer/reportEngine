package it.infocamere.cont2.reportv2.commons;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobDetail;

public class SchedulerStatus {
	private String name;
	private String status;
	private Integer exectionsJob;
	private List<String>jobsName;
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getExectionsJob() {
		return exectionsJob;
	}

	public void setExectionsJob(Integer exectionsJob) {
		this.exectionsJob = exectionsJob;
	}

	public List<String> getJobsName() {
		if (jobsName==null){
			return new ArrayList<String>(0);
		}
		return jobsName;
	}
	
	public void addJobs(JobDetail job){
		if (jobsName==null){
			jobsName=new ArrayList<String>();
		}
		jobsName.add(job.getDescription());
	}

	public void setJobsName(List<String> jobsName) {
		this.jobsName = jobsName;
	}

	

}
