package studio.lineage2.cms.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 Eanseen
 05.11.2015
 */
@Entity @Table(name = "wheel") public class Wheel
{
	@Id @GeneratedValue(generator = "increment") @GenericGenerator(name = "increment", strategy = "increment") @Column(name = "id") private @Getter long id;
	@Column(name = "item_id") private @Getter @Setter int itemId;
	@Column(name = "item_count") private @Getter @Setter long itemCount;
	@Column(name = "chance") private @Getter @Setter int chance;
	@Column(name = "image", length = 100000) private @Getter @Setter byte[] image;
	@Transient private @Getter @Setter String itemName;
}