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
 07.06.2016
 */
@Entity @Table(name = "game_account") public class GAccount
{
	@Id @GeneratedValue(generator = "increment") @GenericGenerator(name = "increment", strategy = "increment") @Column(name = "id") private @Getter long id;
	@Column(name = "m_account_id") private @Getter @Setter long mAccountId;
	@Column(name = "server_id") private @Getter @Setter long serverId;
	@Column(name = "name") private @Getter @Setter String name;

	public GAccount()
	{
	}

	public GAccount(long mAccountId, long serverId, String name)
	{
		this.mAccountId = mAccountId;
		this.serverId = serverId;
		this.name = name;
	}
}