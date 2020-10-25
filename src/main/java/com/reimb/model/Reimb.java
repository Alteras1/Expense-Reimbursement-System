package com.reimb.model;

import java.sql.Date;

public class Reimb {
	private int reimbId;
	private double amount;
	private Date submitted;
	private Date resolved;
	private String description;
	private User author;
	private User resolver;
	private ReimbStatus status;
	private ReimbType type;
	
	public Reimb(double amount, String description, User author, ReimbStatus status, ReimbType type) {
		super();
		this.reimbId = 0;
		this.amount = amount;
		this.description = description;
		this.author = author;
		this.status = status;
		this.type = type;
	}
	public Reimb(int reimbId, double amount, String description, User author, ReimbStatus status, ReimbType type) {
		super();
		this.reimbId = reimbId;
		this.amount = amount;
		this.description = description;
		this.author = author;
		this.status = status;
		this.type = type;
	}
	public Reimb(int reimbId, double amount, Date submitted, Date resolved, String description, User author,
			User resolver, ReimbStatus status, ReimbType type) {
		super();
		this.reimbId = reimbId;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}
	public int getReimbId() {
		return reimbId;
	}
	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getSubmitted() {
		return submitted;
	}
	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}
	public Date getResolved() {
		return resolved;
	}
	public void setResolved(Date resolved) {
		this.resolved = resolved;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public User getResolver() {
		return resolver;
	}
	public void setResolver(User resolver) {
		this.resolver = resolver;
	}
	public ReimbStatus getStatus() {
		return status;
	}
	public void setStatus(ReimbStatus status) {
		this.status = status;
	}
	public ReimbType getType() {
		return type;
	}
	public void setType(ReimbType type) {
		this.type = type;
	}
	
	
}
