$(document).ready(function() {
    var activeSystemClass = $('.list-group-item.active');

    //something is entered in search form
    $('#system-search').keyup( function() {
       var that = this;
        // affect all table rows on in systems table
        var tableBody = $('.table-list-search tbody');
        var tableRowsClass = $('.table-list-search tbody tr');
        $('.search-sf').remove();
        tableRowsClass.each( function(i, val) {

            //Lower text for case insensitive
            var rowText = $(val).text().toLowerCase();
            var inputText = $(that).val().toLowerCase();
            if(inputText != '')
            {
                $('.search-query-sf').remove();
                tableBody.prepend('<tr class="search-query-sf"><td colspan="6"><strong>Searching for: "'
                    + $(that).val()
                    + '"</strong></td></tr>');
            }
            else
            {
                $('.search-query-sf').remove();
            }

            if( rowText.indexOf( inputText ) == -1 )
            {
                //hide rows
                tableRowsClass.eq(i).hide();

            }
            else
            {
                $('.search-sf').remove();
                tableRowsClass.eq(i).show();
            }
        });
        //all tr elements are hidden
        if(tableRowsClass.children(':visible').length == 0)
        {
            tableBody.append('<tr class="search-sf"><td class="text-muted" colspan="6">No entries found.</td></tr>');
        }
    });
});

function viewResult(){
  var waitTime = 3000;

    var btn = '<a href="result_sample_20160101235999.csv"><span class="glyphicon glyphicon-download-alt"></span></a>';
    var img = '<img src="image.png"></img>';
    var loadinggif = '<img src="./media/gif-load.gif"></img>';

    printAsync(1,"#st2",setNowDatetime(), 0)
    // .then(() => printAsync(2,"#l-gif1", loadinggif, 0))
    // .then(() => printAsync(1,"#s1",'Done', 3000))
    // .then(() => printAsync(1,"#et1", setNowDatetime(), 0))
    // .then(() => printAsync(2,"#btn1", btn, 0))
    // .then(() => printAsync(1,"#st2", setNowDatetime(), 0))
    // .then(() => printAsync(2,"#l-gif1", "", 0))
    .then(() => printAsync(2,"#l-gif2", loadinggif, 0))
    .then(() => printAsync(1,"#s2",'Done', 3000))
    .then(() => printAsync(1,"#et2", setNowDatetime(), 0))
    //.then(() => printAsync(2,"#btn2", btn, 0))
    //.then(() => printAsync(1,"#st3", setNowDatetime(), 0))
    .then(() => printAsync(2,"#l-gif2", "", 0))
    //.then(() => printAsync(2,"#l-gif3", loadingjpg, 0));
    // .then(() => printAsync(1,"#s3",'Done', 3000))
    // .then(() => printAsync(1,"#et3", setNowDatetime(), 0))
    // .then(() => printAsync(2,"#btn3", btn, 0))
    // .then(() => printAsync(2,"#l-gif3", "", 0))
    // .then(() => printAsync(2,"#img", img, 3000));
    .then(() => printAsync(3,"#result-view-image", "", 0));

}


function printAsync(type, id, text, delay) {
    const p = new Promise((resolve, reject) => {
        setTimeout(() => {
            switch(type){
              case 1:
              $(id).text(text);
              break;

              case 2:
              $(id).html(text);
              break;

              case 3:
              $(id).css('display','block');
              break;

              default:
              break;
            }

            resolve();
        }, delay)
    });

    return p;
}



function setNowDatetime(){
  // 現在日時
  var nowDate = new Date();
  var year = nowDate.getFullYear();
  var month = nowDate.getMonth()+1;
  var day = nowDate.getDate();
  var hour = nowDate.getHours();
  var minute = nowDate.getMinutes();
  var second = nowDate.getSeconds();

  return day + "-" + month + "-" + year  + " " + hour + ":" + minute;
}
