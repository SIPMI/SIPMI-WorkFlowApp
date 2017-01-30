package models.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import models.dao.TbWorkParamDao;
import models.util.EnumUtil;
import play.db.ebean.Model;

@Entity
public class TbWork extends Model {

	public static final String TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

	@Id
	public Integer id;
	public Integer workflowId;
	public String task;
	public String status;
	public String type;
	public Integer sortNo;
	public Integer parentId;
	public Timestamp startDatetime;
	public Timestamp endDatetime;


	public String getStatusLabel(){
		return EnumUtil.getWorkStatusLabelByCode(this.status);
	}

	public String getStartDatetimeLabel(){
		if(this.startDatetime == null){
			return "-";
		}
		return new SimpleDateFormat(TIME_FORMAT).format(this.startDatetime);
	}

	public String getEndDatetimeLabel(){
		if(this.endDatetime == null){
			return "-";
		}
		return new SimpleDateFormat(TIME_FORMAT).format(this.endDatetime);
	}

	public String getImageSrc(){

		String imageStr = "";

		List<TbWorkParam> paramList = TbWorkParamDao.findWorkParamListByWorkId(this.id.longValue());
		for(TbWorkParam param : paramList){
			if(param.paramText != null  && param.paramNo == 1){
				imageStr = "data:image/jpg;base64," + param.paramText;
			}
		}

		return imageStr;
	}


	public String getParamText(){
		String paramText = "";

		List<TbWorkParam> paramList = TbWorkParamDao.findWorkParamListByWorkId(this.id.longValue());
		for(TbWorkParam param : paramList){
			if(param.paramText != null && param.paramNo == 1){
				paramText = param.paramText;
			}
		}

		return paramText;
	}

	public Integer getType(){
		return Integer.valueOf(this.type);
	}


}
