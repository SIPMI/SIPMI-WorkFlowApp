package controllers;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;//
import java.awt.FlowLayout;//
import java.io.FileWriter;//
import java.io.IOException;//

import com.fasterxml.jackson.databind.node.ObjectNode;

import models.dao.TbFunctionDao;
import models.dao.TbWorkDao;
import models.dao.TbWorkParamDao;
import models.dao.TbWorkflowDao;
import models.entity.TbFunction;
import models.entity.TbTask;
import models.entity.TbWork;
import models.entity.TbWorkParam;
import models.entity.TbWorkflow;
import models.util.ImageUtil;
import play.Logger;
import play.libs.F.Promise;
import play.libs.Json;
import play.libs.WS;

public class TaskRunner {
	// バッチ処理用にシングルトンにしておく
	private static TaskRunner taskRunner = new TaskRunner();

    private TaskRunner(){
        System.out.println("create instance");
    }

    public static TaskRunner getInstance(){
        return taskRunner;
    }

    public void execute(TbTask tbTask){

    	Long workflowId = tbTask.workflowId.longValue();

    	Logger.info("task workflow_id :" + tbTask.workflowId);

    	TbWorkflow workflow = TbWorkflowDao.findWorkflowById(workflowId);
    	List<TbWork> workList = TbWorkDao.findWorkListById(workflowId);


    	if(workflow != null && workList != null && workList.size() != 0){

    		Logger.info("task start");
    		TbWorkflowDao.updateForExecute(workflow);

    		try{

	    		if(isDummy(workList)){
	    			// ダミー処理実行
	    			executeDummyLogic(workList);
	    		}else{
	    			// 通常処理実行
	    			for(TbWork work: workList){

		    			TbWorkDao.updateForExecute(work);

		    			try{

		    				executeWork(work);
		    				TbWorkDao.updateForSuccess(work);

		    			}catch(Exception e1){
							e1.printStackTrace();
							try{
								TbWorkDao.updateForError(work);
							}catch (Exception ex){
								ex.printStackTrace();
							}
		    				throw e1;
		    			}

		    		}
	    		}

	    		TbWorkflowDao.updateForSuccess(workflow);
	        	Logger.info("task done");

    		}catch(Exception e2){
				e2.printStackTrace();
				try{
					TbWorkflowDao.updateForError(workflow);
				}catch (Exception e3){
					e3.printStackTrace();
				}

    			Logger.info("task error");
    		}
    	}else{
    		Logger.info("workflow not found");
    	}

		tbTask.delete();

    }

    private void executeWork(TbWork work){

    	Logger.info("execute " + work.task);

    	// 自分のワークのパラメータを取得
		//TbWorkParam selfParam = null;
		//TbWorkParam selfParam2 = null;
		List<TbWorkParam> paramList = TbWorkParamDao.findWorkParamListByWorkId(work.id.longValue());
		//if(paramList != null && paramList.size() != 0){
		//	selfParam = paramList.get(0);
		//	if(paramList.size() > 1){
		//		selfParam2 = paramList.get(1);
		//	}
		//	Logger.info("get param :" + selfParam.paramStr);
		//}else{
		//	Logger.info("get param : null");
		//}s


    	switch(work.task){
    		case "math_number":
	    		// 親のワークのパラメータに保存
    			TbWorkParamDao.registWorkParam(work.parentId, 2, "InputVal", paramList.get(0).paramText);
    			break;

	    	case "InputData":
	    	case "InputData2":
	    		// 親のワークのパラメータに保存
	    		TbWorkParamDao.registWorkParam(work.parentId, 1, "InputData", paramList.get(0).paramText);
	    		break;

			case "Carbon":
			case "Chrome":
				// 親のワークのパラメータに保存
				//TbWorkParamDao.registWorkParam(work.parentId, 1, "InputData", paramList.get(0).paramText);
				break;

	    	case "ConvertGrayScale":
	    		// 親のワークのパラメータに保存
	    		TbWorkParamDao.registWorkParam(
	    				work.parentId,
	    				1,
	    				"ConvertGrayScaleData for Visualize",
	    				ImageUtil.convertGrayScale(paramList.get(0).paramText)
	    				);
	    		break;

	    	case "Binarization":
	    		// 親のワークのパラメータに保存
	    		TbWorkParamDao.registWorkParam(
	    				work.parentId,
	    				1,
	    				"Binarization for Visualize",
	    				ImageUtil.binarize((paramList.get(0).paramText), Double.parseDouble(paramList.get(1).paramText))
	    				);
	    		break;

			case "OutputVRObjectFromThermocalc":
				// 親のワークのパラメータに保存
//				TbWorkParamDao.registWorkParam(
//						work.parentId,
//						1,
//						"OutputVRObjectFromThermocalc for RegistVRApp",
//						ImageUtil.convertGrayScale(paramList.get(0).paramText)
//				);
				break;

				case "OutputARObjectFromThermocalc"://ARBlockly
					// 親のワークのパラメータに保存
					TbWorkParamDao.registWorkParam(
							work.parentId,
							1,
							"OutputARObjectFromThermocalc for RegistVRApp",
							//ImageUtil.convertGrayScale(paramList.get(0).paramText)
							//なぜか6数値全部ｙばかり、テキスト出力はOK
	ImageUtil.printmake((Double.parseDouble(paramList.get(0).paramText)),(Double.parseDouble(paramList.get(1).paramText)),(Double.parseDouble(paramList.get(2).paramText)),(Double.parseDouble(paramList.get(3).paramText)),(Double.parseDouble(paramList.get(4).paramText)),Double.parseDouble(paramList.get(5).paramText))
					);

//フローチャート

//#1変数とした引数を１行にまとめ
//#2１行をテキストとしてデータベースかサーバにファイル生成
//#3リザルトにダウンロードボタン作る
//#4ボタンを押すとテキストダウンロードできるようにする

//#2test,OK(/tmp/test.txt)

//start #2

//printmake
/*public class printing{
    public static void main(String[] args) {
        try {
            FileWriter fw = new FileWriter("test.txt");
            fw.write("テスト");//本番では変数(printwriter?)pwformatで#1ごといけるかも？
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
			}
    }//データベースにファイル生成、書き込み
*/
		//end #2

		//start #3,4

/*
		// レイアウトを設定
     frame.setLayout(new FlowLayout());//frameはnewされてない、ここはResultの中にという意味
		// ボタン1を作成
	 JButton button1 = new JButton("ボタン1");

	 // ボタンを押した時の処理を設定
	 button1.addActionListener(e -> {
		 System.out.println("ボタン1が押された");//本番はここにダウンロード処理（#4)
		 //ダウンロード処理開始

		 // ダウンロード対象ファイルの読み込み用オブジェクト
FileInputStream fis = null;
InputStreamReader isr = null;

// ダウンロードファイルの書き出し用オブジェクト
OutputStream os = null;
OutputStreamWriter osw = null;

try {
	// ダウンロード対象ファイルのFileオブジェクトを生成
	File file = new File("ダウンロード対象ファイルのフルパス");

	if (!file.exists() || !file.isFile()) {
		// ファイルが存在しない場合のエラー処理
	}

	// レスポンスオブジェクトのヘッダー情報を設定
	res.setContentType("application/octet-stream");
	res.setHeader("Content-Disposition", "attachment; filename=" +
		new String("ダイアログに表示するファイル名".getBytes("Windows-31J"), "ISO-8859-1"));

	// ダウンロード対象ファイルの読み込み用オブジェクトを生成
	fis = new FileInputStream(file);
	isr = new InputStreamReader(fis, "ISO-8859-1");

	// ダウンロードファイルの書き出し用オブジェクトを生成
	os = res.getOutputStream();
	osw = new OutputStreamWriter(os, "ISO-8859-1");

	// IOストリームを用いてファイルダウンロードを行う
	int i;
	while ((i = isr.read()) != -1) {
		osw.write(i);
	}
} catch (FileNotFoundException e) {
	// 例外発生時処理
} catch (UnsupportedEncodingException e) {
	// 例外発生時処理
} catch (IOException e) {
	// 例外発生時処理
} finally {
	try {
		// 各オブジェクトを忘れずにクローズ
		if (osw != null) {
			osw.close();
		}
		if (os != null) {
			os.close();
		}
		if (isr != null) {
			isr.close();
		}
		if (fis != null) {
			fis.close();
		}
	} catch (IOException e) {
		// 例外発生時処理
	}
}

 //ダウンロード処理終了
	 });
	 // ボタンをウィンドウに追加する
    frame.add(button1);

*/
//#end#3,4
//	);
					break;


						    	case "Visualize":
						    	case "RegistVRApp":
						    		// 結果が自分のワークのパラメータに保存されているので
						    		// ここでは何もしない
						    		break;

						    	default:

						    		if(work.task.startsWith("InputData")){
						    			// 親のワークのパラメータに保存
							    		TbWorkParamDao.registWorkParam(work.parentId, 1, "InputData", paramList.get(0).paramText);

						    		}else{

							    		//ファンクションの取得
							    		TbFunction func = TbFunctionDao.findFunctionByTypeName(work.task);
							    		if(func == null){
							    			Logger.info("function not found !!");
							    			break;
							    		}

							    		//リモートのファンクション呼び出し
							    		//String url = "http://localhost:9000/convertGrayScale";
							    		String url = func.url;
							    		String req = paramList.get(0).paramText;
							    		ObjectNode node = Json.newObject();
							    		node.put("img", paramList.get(0).paramText);
							    		if(paramList.size() > 1){
							    			node.put("threshold", paramList.get(1).paramText);
							    		}

							    		Promise<WS.Response> response = WS.url(url)
							    											//.setQueryParameter("img", req)
							    											//.post(Json.newObject().put("img", req));
							    											.post(node);

							        	// 親のワークのパラメータに保存
							    		TbWorkParamDao.registWorkParam(work.parentId, 1, "Remote Function for Visualize", response.get().asJson().findValue("img").asText());

						    		}
						    	}

					    }

					    // ダミー判断
					    private Boolean isDummy(List<TbWork> workList){

					    	Boolean isDummy = false;

					    	for(TbWork work : workList){
					    		// controls_whileUntilが含まれていたらダミーと判断
					    		if(work.task.equals("controls_whileUntil")){
					    			isDummy = true;
					    			break;
					        	}
					    	}

					    	return isDummy;

					    }

					    private void executeDummyLogic(List<TbWork> workList){

					    	Logger.info("execute DummyLogic");

					    	// 一旦初期値をセット
					    	Double i = 100.0;
					    	Double iPlus = 1.0;
					    	Double average = 200.0;
					    	Double averageUntil = 255.0;
					    	String inputData = "";
					    	String tmp_img = "";

					    	for(TbWork work : workList){
					    		if(work.task.equals("InputData")){
					    			List<TbWorkParam> paramList = TbWorkParamDao.findWorkParamListByWorkId(work.id.longValue());
					    			inputData = paramList.get(0).paramText;
					    		}else if(work.task.equals("controls_whileUntil")){
					    			List<TbWorkParam> paramList = TbWorkParamDao.findWorkParamListByWorkId(work.id.longValue());
					    			i = Double.valueOf(paramList.get(0).paramText);
					    			average = Double.valueOf(paramList.get(1).paramText);
					    			averageUntil = Double.valueOf(paramList.get(2).paramText);
					    			iPlus = Double.valueOf(paramList.get(3).paramText);
					    		}
					    	}

					    	Integer workflowId = 0;
					    	// 一旦削除して以下で入れ直す
					    	for(TbWork work : workList){
					    		if(work.sortNo != 1){
					    			work.delete();
					    		}else{
					    			workflowId = work.workflowId;
					    		}
					    	}

					    	Integer sortNo = 2;
					    	List<Long> workIdList;

					    	while(! (average < averageUntil)){
					    		Logger.info("i: " + i);
					    		workIdList = new ArrayList<Long>();

					    		workIdList.add(TbWorkDao.registWork("InputData", sortNo++, null, workflowId).longValue());
					    		workIdList.add(TbWorkDao.registWork("Binarization", sortNo++, null, workflowId).longValue());
					    		workIdList.add(TbWorkDao.registWork("Visualize", sortNo++, null, workflowId).longValue());
					    		workIdList.add(TbWorkDao.registWork("CalcAverageBrightness", sortNo++, null, workflowId).longValue());
					    		workIdList.add(TbWorkDao.registWork("OutputData", sortNo++, null, workflowId).longValue());

					    		// inputData

					    		TbWorkDao.updateForExecute(TbWorkDao.findWorkById(workIdList.get(0)));
					    		TbWorkParamDao.registWorkParam(workIdList.get(0).intValue(), 1, "InputData", inputData);
					    		TbWorkDao.updateForSuccess(TbWorkDao.findWorkById(workIdList.get(0)));

					    		// binarize
					    		TbWorkDao.updateForExecute(TbWorkDao.findWorkById(workIdList.get(1)));
					    		tmp_img = ImageUtil.binarize(inputData, i);
					    		TbWorkDao.updateForSuccess(TbWorkDao.findWorkById(workIdList.get(1)));

					    		// Visualize にセット
					    		TbWorkDao.updateForExecute(TbWorkDao.findWorkById(workIdList.get(2)));
					    		TbWorkParamDao.registWorkParam(workIdList.get(2).intValue(), 1, "Visualize", tmp_img);
					    		TbWorkDao.updateForSuccess(TbWorkDao.findWorkById(workIdList.get(2)));

					    		// calcAverageBrightness
					    		TbWorkDao.updateForExecute(TbWorkDao.findWorkById(workIdList.get(3)));
					    		average = Double.valueOf(ImageUtil.calcAverageBrightness(tmp_img));
					    		Logger.info("average: " + average);
					    		TbWorkDao.updateForSuccess(TbWorkDao.findWorkById(workIdList.get(3)));

					    		// OutputData にセット
					    		TbWorkDao.updateForExecute(TbWorkDao.findWorkById(workIdList.get(4)));
					    		TbWorkParamDao.registWorkParam(workIdList.get(4).intValue(), 1, "OutputData", average.toString());
					    		TbWorkDao.updateForSuccess(TbWorkDao.findWorkById(workIdList.get(4)));



					    		i = i + iPlus;
					    		tmp_img = null;
					    		workIdList = null;
					    	}



					    }


					}
