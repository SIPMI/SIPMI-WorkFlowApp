$(document).ready(function() {

	    var str = _onLoadXml;
	    var source = [str];
	    var file_property_bag = {
	    	// コンテンツタイプを設定
	    	type: "text/plain",
	    	// 最終更新日時を設定（単位：ミリ秒）
	    	lastModified: 0
	    };

	    var file = new File( source , "test.xml" , file_property_bag );
	    var reader = new FileReader();
	    reader.onload = function (x) {
	      var xml = x.target.result;
	      Restore(xml);
	    };
	    reader.readAsText(file, 'UTF-8');

});