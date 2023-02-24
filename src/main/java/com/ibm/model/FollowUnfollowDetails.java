package com.ibm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class FollowUnfollowDetails {
	@Id
	@GeneratedValue
	private Integer fUId;
	private Integer followingId;
	private Integer accountId;

}
