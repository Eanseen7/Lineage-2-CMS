package studio.lineage2.cms.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 Eanseen
 04.11.2015
 */
@Entity @Table(name = "vote_mmotop") public class VoteMMOTOP
{
	@Id @GeneratedValue(generator = "increment") @GenericGenerator(name = "increment", strategy = "increment") @Column(name = "id") private @Getter @Setter long id;
	@Column(name = "vote_id") private @Getter @Setter long voteId;
	@Column(name = "name") private @Getter @Setter String name;
	@Column(name = "take") private @Getter @Setter boolean take;
}