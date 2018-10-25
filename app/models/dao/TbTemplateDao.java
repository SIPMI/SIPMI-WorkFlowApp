package models.dao;

import models.entity.TbTemplate;
import play.db.ebean.Model.Finder;

import java.sql.Timestamp;
import java.util.List;

public class TbTemplateDao {

	public static List<TbTemplate> findTemplateList(){
		Finder<Long, TbTemplate> templateFinder = new Finder<Long, TbTemplate>(Long.class, TbTemplate.class);
        return templateFinder.all();
	}

	public static List<TbTemplate> findTemplateListOrderByRegistDate(){
		Finder<Long, TbTemplate> templateFinder = new Finder<Long, TbTemplate>(Long.class, TbTemplate.class);
		return templateFinder.orderBy("registDatetime ASC").findList();
	}

	public static TbTemplate findTemplateById(Long id){
		Finder<Long, TbTemplate> templateFinder = new Finder<Long, TbTemplate>(Long.class, TbTemplate.class);
        return templateFinder.byId(id);
	}


	public static TbTemplate findFunctionByTypeName(String name){
        Finder<Long, TbTemplate> templateFinder = new Finder<Long, TbTemplate>(Long.class, TbTemplate.class);
        List<TbTemplate> templateList = null;

        String q = "type_name = '" + name + "'";
        if(templateFinder.where(q).findList() != null){
			templateList = templateFinder.where(q).findList();
        }
        if(templateList != null && templateList.size() > 0){
        	return templateList.get(0);
        }
		return null;
	}


	public static void deleteTemplateById(Long id){

		TbTemplate func = findTemplateById(id);
		func.delete();
	}

	public static void registTemplate(TbTemplate func){

		func.registDatetime = new Timestamp(System.currentTimeMillis());
		if (func.id == null) {
			func.save();
		} else {
			func.update();
		}
	}

	public static String findTemplateXmlById(Long id){

		TbTemplate tbTemplate = findTemplateById(id);
		String xml = "";
		if(tbTemplate != null){
			xml = tbTemplate.definition;
		}
		return xml;
	}
}
