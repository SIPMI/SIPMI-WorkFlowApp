$(function() {

  // $('#pressure').slider().on('slide', function(e) {
  //   $("#pressure-val").text(e.value);
  // }).slider('setValue', 0.01);


  $(".save-data").click(function (e) {
    e.preventDefault();
    var pressure_val = $("#pressure").slider("getValue");
    console.log(pressure_val);

    TaskList.forEach(function (v, i) {
      if (v.task_type() === SIPMI.TaskType.FlangeData || v.task_type() === SIPMI.TaskType.BushingData) {
        v.update_input("pressure", pressure_val);
        return false;
      }
    });

    $("#run").removeAttr("disabled");
  });

  $(".save-custom-data").click(function (e) {
    e.preventDefault();
    var custom_input = $("#customDataInputFile").val();

    TaskList.forEach(function (v, i) {
      if (v.task_type() === SIPMI.TaskType.CustomData) {
        v.update_input("custom_input", custom_input);
        return false;
      }
    });

    $("#run").removeAttr("disabled");
  });


  $('#execute-workflow').click(function (e) {
    e.preventDefault();
    var xml = ToXML();
    //alert(xml);
    var taskList = new Array();
    var varList = new Array();


    if (xml.match(/controls_whileUntil/)) {
    	// 特別なパターン
    	getVarFromXml(xml, varList);

    	var varListTag = "";
	    varList.forEach(function(value) {
	    	varListTag += "<input type=hidden name=varList[] value=" + value + " >";
	    	});
	    $('#varListTag').html(varListTag);

    }else{
    	// 通常のｘｍｌ解析処理
	    parseXmlToTask(xml, taskList, varList);
	    var taskListTag = "";
	    taskList.forEach(function(value) {
	    	taskListTag += "<input type=hidden name=taskList[] value=" + value + " >";
	    	});
	    $('#taskListTag').html(taskListTag);

	    var varListTag = "";
	    varList.forEach(function(value) {
	    	varListTag += "<input type=hidden name=varList[] value=" + value + " >";
	    	});
	    $('#varListTag').html(varListTag);

    }

    $('#workflowXml').val(xml.replace(/[\n\r]/g,""));
    $('#workForm').submit();


  });

  var parseXmlToTask = function (xml, taskList, varList) {

    var parser = new DOMParser();
    var dom = parser.parseFromString(xml, 'text/xml');
    var dom_1 = dom.getElementsByTagName('block').length;
    var node = dom.documentElement;

    analyzeXmlTask(node.childNodes, taskList, varList, null);

  }


  var analyzeXmlTask = function (nodes, taskList, varList, tmp_var) {

    if(tmp_var == null){
      tmp_var = "";
    }

    for(var nodeIdx in nodes){
      if(nodes[nodeIdx].localName == "value"){
          analyzeXmlTask(nodes[nodeIdx].childNodes, taskList, varList, tmp_var);
      } else
      if(nodes[nodeIdx].localName == "block"){

        var hasValue = false;
        for(var tmpNodeIdx in nodes[nodeIdx].childNodes){
          if(nodes[nodeIdx].childNodes[tmpNodeIdx].localName == "value" ){
            hasValue = true;
            break;
          }
        }
        if(hasValue){
            analyzeXmlTask(nodes[nodeIdx].childNodes, taskList, varList, tmp_var);
        }
        var attr = nodes[nodeIdx].attributes;
        if(attr != ""){
          taskList.push(attr[0].nodeValue);

          if(attr[0].nodeValue == "math_number"){
            var varNode = nodes[nodeIdx].childNodes;
            varList.push(varNode[1].textContent);
          }else{
        	  var execFunc = "func" + attr[0].nodeValue + "(varList);";
              eval(execFunc);
          }
        }

      } else
      if(nodes[nodeIdx].localName == "next"){
        analyzeXmlTask(nodes[nodeIdx].childNodes, taskList, varList, tmp_var);
      }
    }

  }


  var viewTaskList = function (taskList, varList) {

    var viewTaskTag = "";

    for (var i = 0; i < taskList.length; i++) {
      var no = i + 1;

      switch (taskList[i]) {
        case "InputData":
          viewTaskTag += '<tr><td >' + no + '</td><td >InputData <span id="l-gif1"></span></td><td colspan="4"><img src="lena.jpg" class="result-image"></td></tr>';
          break;
        case "InputData2":
          viewTaskTag += '<tr><td >' + no + '</td><td >InputData <span id="l-gif1"></span></td><td colspan="4"><img src="mandrill.jpg" class="result-image"></td></tr>';
          break;
        case "Binarization":
          viewTaskTag += '<tr><td>' + no + '</td><td>Binarization <span id="l-gif_Binarization"></span></td><td><span id="s_Binarization">Not Yet</span></td><td><span id="st_Binarization">-</span></td><td><span id="et_Binarization">-</span></td></tr>';
          break;
        case "ConvertGrayScale":
          viewTaskTag += '<tr><td>' + no + '</td><td>ConvertGrayScale <span id="l-gif_ConvertGrayScale"></span></td><td><span id="s_ConvertGrayScale">Not Yet</span></td><td><span id="st_ConvertGrayScale">-</span></td><td><span id="et_ConvertGrayScale">-</span></td></tr>';
          break;
        case "CalcAverageBrightness":
            viewTaskTag += '<tr><td>' + no + '</td><td>CalcAverageBrightness <span id="l-gif_CalcAverageBrightness"></span></td><td><span id="s_CalcAverageBrightness">Not Yet</span></td><td><span id="st_CalcAverageBrightness">-</span></td><td><span id="et_CalcAverageBrightness">-</span></td></tr>';
            break;
        case "OutputData":
          viewTaskTag += '<tr><td rowspan="2">' + no + '</td><td rowspan="2">OutputData <span id="l-gif_OutputData"></span></td><td colspan="4"><span id="result-view-image" style="display:none">$$outputFile$$</span></td></tr>';
          break;
        case "Visualize":
          viewTaskTag += '<tr><td rowspan="2">' + no + '</td><td rowspan="2">Visualize <span id="l-gif_Visualize"></span></td><td colspan="4"><span id="result-view-image" style="display:none"><canvas id="c1" class="result-image"></span></td></tr>';
            break;
        case "math_number":
          viewTaskTag += '<tr><td >' + no + '</td><td >math_number <span id="l-gif1"></span></td><td colspan="4">' + varList[0] +'</td></tr>';
          break;
        case "math_arithmetic":
          viewTaskTag += '<tr><td >' + no + '</td><td >math_arithmetic <span id="l-gif1"></span></td><td colspan="4">' + varList[0] +'</td></tr>';
          break;
        case "variables_set":
          viewTaskTag += '<tr><td >' + no + '</td><td >variables_set <span id="l-gif1"></span></td><td colspan="4">' + varList[0] +'</td></tr>';
          break;
        case "variables_get":
          viewTaskTag += '<tr><td >' + no + '</td><td >variables_get <span id="l-gif1"></span></td><td colspan="4">' + varList[0] +'</td></tr>';
          break;

        default:

      }
    }

    document.getElementById("task_tr").innerHTML = viewTaskTag;

  }

  var execTaskList = function (taskList, varList) {

    var tmp_input = new Array();

    for (var i = 0; i < taskList.length; i++) {

      switch (taskList[i]) {
        case "InputData":
            funcInputData(tmp_input);
          break;
        case "InputData2":
            funcInputData2(tmp_input);
          break;
        case "Binarization":
            funcBinarization(varList,tmp_input);
            break;
        case "ConvertGrayScale":
            funcConvertGrayScale(varList,tmp_input);
            break;
        case "CalcAverageBrightness":
            funcCalcAverageBrightness(varList,tmp_input);
            break;
        case "OutputData":
            funcOutputData(tmp_input);
            break;
        case "Visualize":
            funcVisualize(tmp_input);
            break;
        case "math_number":
            //funcMathNumber(tmp_input);
            break;

        default:

      }
    }


  }

  var funcInputData = function (varList) {
	  	varList.push("/public/images/upload/lena.jpg");
	  }

  var funcInputData2 = function (varList) {
	  	varList.push("/public/images/upload/mandrill.jpg");
	  }

  var funcBinarization = function (varList) {
  }
  var funcConvertGrayScale = function (varList) {
  }
  var funcVisualize = function (varList) {
  }


  var getVarFromXml = function (xml, varList) {

	  var parser = new DOMParser();
	  var dom = parser.parseFromString(xml, 'text/xml');
	  var dom_1 = dom.getElementsByTagName('block').length;
	  var node = dom.documentElement;

	  var target = "InputData";

	  if(xml.match(/InputData_\d+/)){
		  target = xml.match(/InputData_\d+/);
      }else if(xml.match(/InputData2/)){
		  target = "InputData2";
	  }else if(xml.match(/InputData/)){
		  target = "InputData";
	  }

	  var execFunc = "func" + target + "(varList);";
      eval(execFunc);

      if(xml.match(/"NUM">[0-9\.]+</)){
    	 var numList = xml.match(/"NUM">[0-9\.]+</g);
    	 for (var i = 0; i < numList.length; i++) {
    		 var varNum = numList[i].replace(/"NUM">([0-9\.]+)</, "$1");
    		 if(! varNum.match(/\./)){
    			 varNum = varNum + ".0";
    		 }
    		 varList.push(varNum);
    	 }

      }


  }

//  var funcInputData = function (tmp_input) {
//    tmp_input.push("lena.jpg");
//  }
//
//  var funcInputData2 = function (tmp_input) {
//    tmp_input.push("mandrill.jpg");
//  }

//  var funcBinarization = function (varList,tmp_input) {
//    const DEFAULT_THRESHOLD = 70;
//
//    var threshold = DEFAULT_THRESHOLD;
//
//    if(varList[0] != null && varList[0] != ""){
//      threshold = varList[0];
//    }
//
//    $("#st_Binarization").text(setNowDatetime());
//    var canvas = document.getElementById("c1");
//    var ctx = canvas.getContext("2d");
//    var img = new Image();
//    img.src = tmp_input[0];
//    img.onload = function() {
//        canvas.width = img.width;
//        canvas.height = img.height;
//        ctx.drawImage(img, 0, 0);
//        var src = ctx.getImageData(0, 0, canvas.width, canvas.height);
//        var dst = ctx.createImageData(canvas.width, canvas.height);
//
//        // for (var i = 0; i < src.data.length; i=i+4) {
//        //     var y = ~~(0.299 * src.data[i] + 0.587 * src.data[i + 1] + 0.114 * src.data[i + 2]);
//        //     var ret = (y > threshold) ? 255 : 0;
//        //     dst.data[i] = dst.data[i+1] = dst.data[i+2] = ret;
//        //     dst.data[i+3] = src.data[i+3];
//        // }
//
//        dst.data = calcBinarization(dst.data, src.data, threshold);
//
//        ctx.putImageData(dst, 0, 0);
//    };
//
//    $("#et_Binarization").text(setNowDatetime());
//    $("#s_Binarization").text("Done");
//
//  }

//  var calcBinarization  = function(arrBaseData, arrInputData, threshold){
//    var arrOutputData = Array();
//
//    // 各フィールドから値を取得してJSONデータを作成
//    var data = {
//        //arrBaseData: arrBaseData,
//        //arrInputData: arrInputData,
//        threshold: threshold,
//    };
//
//
//    //通信実行
//    $.ajax({
//        type:"post",                // method = "POST"
//        //url:"https://api-test-kota0913.c9users.io/binarization.php",
//        url:"http://sipmi.dev-bloom.com/binarization.php",        // POST送信先のURL
//        data:JSON.stringify(data),  // JSONデータ本体
//        contentType: 'application/json', // リクエストの Content-Type
//        async: false, // 非推奨だが今回はモックのため使用
//        dataType: "json",           // レスポンスをJSONとしてパースする
//        success: function(json_data) {   // 200 OK時
//            // JSON Arrayの先頭が成功フラグ、失敗の場合2番目がエラーメッセージ
//            // if (!json_data[0]) {    // サーバが失敗を返した場合
//            //     alert("Transaction error. " + json_data[1]);
//            //     return;
//            // }
//            // 成功時処理
//            alert("OK");
//
//        },
//        error: function() {         // HTTPエラー時
//            alert("Server Error. Pleasy try again later.");
//        },
//        complete: function() {      // 成功・失敗に関わらず通信が終了した際の処理
//
//        }
//    });
//
//
//    return arrBaseData;
//
//    for (var i = 0; i < arrInputData.length; i=i+4) {
//        var y = ~~(0.299 * arrInputData[i] + 0.587 * arrInputData[i + 1] + 0.114 * arrInputData[i + 2]);
//        var ret = (y > threshold) ? 255 : 0;
//        arrBaseData[i] = arrBaseData[i+1] = arrBaseData[i+2] = ret;
//        arrBaseData[i+3] = arrInputData[i+3];
//    }
//
//    return arrBaseData;
//  }
//
//  var funcConvertGrayScale = function (varList,tmp_input) {
//    //const THRESHOLD = varList[0];
//
//    $("#st_ConvertGrayScale").text(setNowDatetime());
//
//    var canvas = document.getElementById("c1");
//    var ctx = canvas.getContext("2d");
//    var img = new Image();
//    img.src = tmp_input[0];
//    img.onload = function() {
//        canvas.width = img.width;
//        canvas.height = img.height;
//        ctx.drawImage(img, 0, 0);
//        var src = ctx.getImageData(0, 0, canvas.width, canvas.height);
//        var dst = ctx.createImageData(canvas.width, canvas.height);
//
//        for (var i = 0; i < src.data.length; i=i+4) {
//            var pixel = (src.data[i] + src.data[i+1] + src.data[i+2]) / 3;
//            dst.data[i] = dst.data[i+1] = dst.data[i+2] = pixel;
//            dst.data[i+3] = src.data[i+3];
//        }
//
//        ctx.putImageData(dst, 0, 0);
//    };
//
//    $("#et_ConvertGrayScale").text(setNowDatetime());
//    $("#s_ConvertGrayScale").text("Done");
//
//  }
//
//  var funcCalcAverageBrightness = function (varList, tmp_input) {
//
//    $("#st_CalcAverageBrightness").text(setNowDatetime());
//
//    var canvas = document.getElementById("c1");
//    var ctx = canvas.getContext("2d");
//    var img = new Image();
//    img.src = tmp_input[0];
//    //img.onload = function() {
//        ctx.drawImage(img, 0, 0);
//        var imagedata = ctx.getImageData(0, 0, canvas.width, canvas.height);
//        var res = getYArray(imagedata.data);
//        varList.push(res);
//    //};
//
//    $("#et_CalcAverageBrightness").text(setNowDatetime());
//    $("#s_CalcAverageBrightness").text("Done");
//
//  }
//
//  function getYArray(data) {
//    var ys = new Array();
//
//    for (var i = 0; i < data.length; i= i + 4) {
//        // 輝度(YUVのY)の計算を行う
//        var y = ~~(0.299 * data[i] + 0.587 * data[i + 1] + 0.114 * data[i + 2]);
//        // 輝度の値をセット
//        ys.push(y);
//    }
//
//    return average(ys);
//    }
//
//  var sum = function(arr, fn){
//    if (fn) {
//        return sum(arr.map(fn));
//    }
//    else {
//        return arr.reduce(function(prev, current, i, arr) {
//                return prev+current;
//        });
//    }
//  };
//
//  var average = function(arr, fn) {
//    return sum(arr, fn)/arr.length;
//  };
//
//  var funcVisualize = function (tmp_input) {
//    $("#result-view-image").css('display','block');
//  }



  $('#save-to-file').click(function (e) {
    e.preventDefault();
    var xml = ToXML();
    var d = formatDate(new Date(), "YYYYMMDD-hhmmss");
    SaveToFile("workflow_" + d + ".xml", xml);
    var json = UserDataToJson();
    SaveToFile("userdata_" + d + ".json", json);
  });

  var formatDate = function (date, format) {
    if (!format) format = 'YYYY-MM-DD hh:mm:ss.SSS';
    format = format.replace(/YYYY/g, date.getFullYear());
    format = format.replace(/MM/g, ('0' + (date.getMonth() + 1)).slice(-2));
    format = format.replace(/DD/g, ('0' + date.getDate()).slice(-2));
    format = format.replace(/hh/g, ('0' + date.getHours()).slice(-2));
    format = format.replace(/mm/g, ('0' + date.getMinutes()).slice(-2));
    format = format.replace(/ss/g, ('0' + date.getSeconds()).slice(-2));
    if (format.match(/S/g)) {
      var milliSeconds = ('00' + date.getMilliseconds()).slice(-3);
      var length = format.match(/S/g).length;
      for (var i = 0; i < length; i++) format = format.replace(/S/, milliSeconds.substring(i, i + 1));
    }
    return format;
  };

  $('#upload-xml').change(function (e) {
    var file = e.target.files[0];
    var reader = new FileReader();
    reader.onload = function (x) {
      var xml = x.target.result;
      Restore(xml);
    };
    reader.readAsText(file, 'UTF-8');
  });

  $('#upload-json').change(function (e) {
    var file = e.target.files[0];
    var reader = new FileReader();
    reader.onload = function (x) {
      var json = x.target.result;
      setDataFromJson(json);
    };
    reader.readAsText(file, 'UTF-8');
  });

  $('#upload-xml-btn').click(function (e) {
    e.preventDefault();
    $('#upload-xml').click();
  });

  $('#upload-json-btn').click(function (e) {
    e.preventDefault();
    $('#upload-json').click();
  });



});
