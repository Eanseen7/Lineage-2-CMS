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
@Entity @Table(name = "server") public class Server
{
	@Id @GeneratedValue(generator = "increment") @GenericGenerator(name = "increment", strategy = "increment") @Column(name = "id") private @Getter @Setter long id;
	@Column(name = "type") private @Getter @Setter ServerType type;
	@Column(name = "loginId") private @Getter @Setter long loginId;
	@Column(name = "name") private @Getter @Setter String name;
	@Column(name = "ip") private @Getter @Setter String ip;
	@Column(name = "port") private @Getter @Setter String port;
	@Column(name = "login") private @Getter @Setter String xmlRpcL;
	@Column(name = "password") private @Getter @Setter String xmlRpcP;
	@Column(name = "enable") private @Getter @Setter boolean enable;
}