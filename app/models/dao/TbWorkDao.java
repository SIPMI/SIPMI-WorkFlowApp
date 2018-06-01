package models.dao;

import java.sql.Timestamp;
import java.util.List;

import models.entity.TbWork;
import models.util.WorkStatusEnum;
import models.util.WorkTypeEnum;
import play.db.ebean.Model.Finder;

public class TbWorkDao {


    public static TbWork findWorkById(Long id) {
        Finder<Long, TbWork> workFinder = new Finder<Long, TbWork>(Long.class, TbWork.class);
        return workFinder.byId(id);
    }


    public static List<TbWork> findWorkListById(Long id) {
        Finder<Long, TbWork> workFinder = new Finder<Long, TbWork>(Long.class, TbWork.class);
        List<TbWork> workList = null;
        String q = "workflow_id = " + id;
        if (workFinder.where(q).findList() != null) {
            workList = workFinder.where(q).orderBy().asc("sort_no").findList();
        }
        return workList;
    }

    public static Integer registWork(String task, Integer sortNo, Integer parentId, Integer workflowId) {

        TbWork w = new TbWork();

        w.task = task;
        w.sortNo = sortNo;
        w.parentId = parentId;

        switch (task) {
            case "InputData":
            case "InputData2":
                w.type = WorkTypeEnum.INPUT.getCode();
                break;

            case "Visualize":
                w.type = WorkTypeEnum.OUTPUT.getCode();
                break;

            case "math_number":
            case "OutputData":
            case "Carbon":
            case "Chrome":
                w.type = WorkTypeEnum.VALUE.getCode();
                break;

            case "controls_whileUntil":
                w.type = WorkTypeEnum.LOGIC.getCode();
                break;

            case "RegistVRApp":
                w.type = WorkTypeEnum.REGIST.getCode();
                break;

            default:

                if (task.startsWith("InputData")) {
                    w.type = WorkTypeEnum.INPUT.getCode();
                } else {
                    w.type = WorkTypeEnum.FUNCTION.getCode();
                }

        }

        w.status = WorkStatusEnum.NOT_YET.getCode();
        w.workflowId = workflowId;
        w.save();

        return w.id;

    }

    public static void updateForExecute(TbWork work) {
        work.startDatetime = new Timestamp(System.currentTimeMillis());
        work.status = WorkStatusEnum.PROCESSING.getCode();
//        work.save();
        work.update(work.id);
    }

    public static void updateForSuccess(TbWork work) {
        work.endDatetime = new Timestamp(System.currentTimeMillis());
        work.status = WorkStatusEnum.DONE.getCode();
//        work.save();
        work.update(work.id);
    }

    public static void updateForError(TbWork work) {
        //work.endDatetime = new Timestamp(System.currentTimeMillis());
        work.status = WorkStatusEnum.ERROR.getCode();
//        work.save();
        work.update(work.id);
    }

    public Integer id;

}
