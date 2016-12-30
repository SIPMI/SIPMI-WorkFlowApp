var execute_task_list = function () {
  var list = TaskList;
  var promise = null;
  SIPMI.UI.task_status_is_in_progress(0);
  list.forEach(function (task, i) {
    promise = task.execute(promise).done(function () {
      SIPMI.UI.task_status_is_done(i);
      if (TaskList.length-1 != i) {
        SIPMI.UI.task_status_is_in_progress(i+1);
      }
    });
  });
  return promise;
};

$(document).on('click', '#run', function () {
  $(".result-panel-group").empty();
  $(".task-status").empty();
  execute_task_list().done(function () {
    SIPMI.UI.task_status_is_done(TaskList.length-1);
    SIPMI.UI.BuildResults(TaskList);
  });
});

var UserDataToJson = function () {
  var output_data = {};
  TaskList.forEach(function (v, i) {
    var input_of_task = v.input();
    if (input_of_task != {}) {
      output_data[v.task_name()] = input_of_task;
    }
  });
  return JSON.stringify(output_data);
};

var setDataFromJson = function (json) {
  var parsed = JSON.parse(json);

  for (var k in parsed) {
    var v = parsed[k];
    TaskList.forEach(function (t_v, t_i) {
      if (t_v.task_name() == k) {
        for (var input_key in v) {
          var input_val = v[input_key];
          TaskList[t_i].update_input(input_key, input_val);

          if (input_key == "custom_input") {
            $("#customDataInputFile").val(input_val);
          }
        }
      }
    });
  }

  $("#run").removeAttr("disabled");
};
