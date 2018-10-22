$(document).ready(function() {


    var query = getUrlQuery();
	if(query["fromWorkflow"]){
        if( window.sessionStorage ) {
            var xml = sessionStorage.getItem('tmp_xml');
			if( xml != null){
                _onLoadXml = xml;
                sessionStorage.removeItem('tmp_xml');
            }
        }
	}

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

function getUrlQuery()
{
    var vars = [], max = 0, hash = "", array = "";
    var url = window.location.search;

    //?を取り除くため、1から始める。複数のクエリ文字列に対応するため、&で区切る
    hash  = url.slice(1).split('&');
    max = hash.length;
    for (var i = 0; i < max; i++) {
        array = hash[i].split('=');    //keyと値に分割。
        vars.push(array[0]);    //末尾にクエリ文字列のkeyを挿入。
        vars[array[0]] = array[1];    //先ほど確保したkeyに、値を代入。
    }

    return vars;
}
