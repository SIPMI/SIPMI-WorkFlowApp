package models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import java.sql.Timestamp;

import play.db.ebean.Model;

@Entity
public class TbWorkParam extends Model {

	@Id
	public Integer id;
	public Integer workId;
	public Integer paramNo;

	// 必要に応じて使い分ける
	public String paramStr;
	@Column(name="param_text", columnDefinition="LONGTEXT")
	public String paramText;
//	@Lob
//	public byte[] paramBlob;


	public TbWorkParam(){
	}


}
