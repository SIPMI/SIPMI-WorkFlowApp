// Block definition
Blockly.Blocks.PhaseField = {
  init: function() {
    this.appendDummyInput()
        .appendField("PhaseField");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(65);
  }
};

Blockly.Blocks.FlangeData = {
  init: function() {
    this.appendDummyInput()
        .appendField("FlangeData");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(100);
  }
};

Blockly.Blocks.BushingData = {
  init: function() {
    this.appendDummyInput()
        .appendField("BushingData");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(100);
  }
};

Blockly.Blocks.CustomData = {
  init: function() {
    this.appendDummyInput()
        .appendField("CustomData");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(130);
  }
};

Blockly.Blocks.Abaqus = {
  init: function() {
    this.appendDummyInput()
        .appendField("Abaqus");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(200);
  }
};


Blockly.Blocks['Visualize'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("Visualize");
    this.appendValueInput("image:")
        .setCheck(null);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(65);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};


Blockly.Blocks.InputData = {
  init: function() {
    this.appendDummyInput()
        .appendField("InputData")
    this.appendDummyInput()
        .appendField(new Blockly.FieldImage("lena.jpg", 100, 100, "*"));
    this.setOutput(true);
    //this.setPreviousStatement(false);
    //this.setNextStatement(true, "Boolean");
    this.setColour(150);
    this.setTooltip("Tooltip text.xxxxxx");
  },
  onchange: function(changeEvent) {
    if (true) {
      this.setWarningText(null);
    } else {
      this.setWarningText('Must have an input block.');
    }
  }
};

Blockly.Blocks.InputData2 = {
  init: function() {
    this.appendDummyInput()
        .appendField("InputData2")
    this.appendDummyInput()
        .appendField(new Blockly.FieldImage("mandrill.jpg", 100, 100, "*"));
    this.setOutput(true);
    //this.setPreviousStatement(false);
    //this.setNextStatement(true, "Boolean");
    this.setColour(150);
    this.setTooltip("Tooltip text.xxxxxx");
  },
  onchange: function(changeEvent) {
    if (true) {
      this.setWarningText(null);
    } else {
      this.setWarningText('Must have an input block.');
    }
  }
};

Blockly.Blocks.InputData3 = {
  init: function() {
    this.appendDummyInput()
        .appendField("InputData3");
    this.appendValueInput()
        .appendField("image1:");
    this.appendValueInput()
        .appendField("image2:");
    this.setPreviousStatement(false);
    this.setNextStatement(true, "Boolean");
    this.setColour(150);
    this.setTooltip("Tooltip text.xxxxxx");
  },
  onchange: function(changeEvent) {
    if (true) {
      this.setWarningText(null);
    } else {
      this.setWarningText('Must have an input block.');
    }
  }
};


Blockly.Blocks['InputImage'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("InputImage")
        .appendField(new Blockly.FieldImage("https://www.gstatic.com/codesite/ph/images/star_on.gif", 15, 15, "*"));
    this.setOutput(true, null);
    this.setColour(65);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};


Blockly.Blocks.InputImage2 = {
  init: function() {
    this.appendDummyInput()
        .appendField("InputImage2");
    this.appendDummyInput()
        .appendField(new Blockly.FieldImage("https://www.gstatic.com/codesite/ph/images/star_on.gif", 150, 150, "*"));
    this.setColour(150);
    this.setOutput(true);
  }
};

// Blockly.Blocks.Binarization = {
//   init: function() {
//     this.appendDummyInput()
//         .appendField("Binarization");
//     this.appendValueInput()
//         .appendField("image:");
//     this.appendValueInput()
//         .appendField("threshold:");
//     this.setOutput(true);
//     this.setPreviousStatement(false);
//     this.setNextStatement(false);
//     this.setColour(60);
//   }
// };

Blockly.Blocks['Binarization'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("Binarization");
    this.appendValueInput("img")
        .setCheck(null)
        .appendField("image:");
    this.appendValueInput("threshold")
        .setCheck(null)
        .appendField("threshold:");
    this.setOutput(true, null);
    this.setColour(65);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
 }
};

// Blockly.Blocks.ConvertGrayScale = {
//   init: function() {
//     this.appendDummyInput()
//         .appendField("ConvertGrayScale");
//     this.appendValueInput()
//         .appendField("image:");
//     this.setOutput(true);
//     this.setPreviousStatement(false);
//     this.setNextStatement(false);
//     this.setColour(60);
//   }
// };

Blockly.Blocks['ConvertGrayScale'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("ConvertGrayScale");
    this.appendValueInput("img")
        .setCheck(null)
        .appendField("image:");
    this.setOutput(true, null);
    this.setColour(65);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

// Blockly.Blocks.CalcAverageBrightness = {
//   init: function() {
//     this.appendDummyInput()
//         .appendField("CalcAverageBrightness");
//     this.appendValueInput()
//         .appendField("image:");
//     this.setOutput(true);
//     this.setPreviousStatement(false);
//     this.setNextStatement(false);
//     this.setColour(60);
//   }
// };

Blockly.Blocks['CalcAverageBrightness'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("CalcAverageBrightness");
    this.appendValueInput("img")
        .setCheck(null)
        .appendField("image:");
    this.setOutput(true, null);
    this.setColour(65);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks.SampleFunctionX = {
  init: function() {
    this.appendDummyInput()
        .appendField("SampleFunctionX");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(60);
  }
};

Blockly.Blocks.SampleFunctionY = {
  init: function() {
    this.appendDummyInput()
        .appendField("SampleFunctionY");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(60);
  }
};

Blockly.Blocks.SampleFunctionZ = {
  init: function() {
    this.appendDummyInput()
        .appendField("SampleFunctionZ");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(60);
  }
};


Blockly.Blocks['OutputData'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("OutputData");
    this.appendValueInput("image:")
        .setCheck(null);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(65);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

// Code generator definition
Blockly.JavaScript.PhaseField = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.PhaseField));\n";
  return code;
};

Blockly.JavaScript.FlangeData = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.FlangeData));\n";
  return code;
};

Blockly.JavaScript.BushingData = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.BushingData));\n";
  return code;
};

Blockly.JavaScript.CustomData = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.CustomData));\n";
  return code;
};

Blockly.JavaScript.Abaqus = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.Abaqus));\n";
  return code;
};

Blockly.JavaScript.Visualize = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.Visualize));\n";
  return code;
};

Blockly.JavaScript.InputData = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.InputData));\n";
  return code;
};

Blockly.JavaScript.InputData2 = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.InputData2));\n";
  return code;
};

Blockly.JavaScript.InputData3 = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.InputData3));\n";
  return code;
};

Blockly.JavaScript.InputImage = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.InputImage));\n";
  return code;
};

Blockly.JavaScript.InputImage2 = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.InputImage2));\n";
  return code;
};

Blockly.JavaScript.Binarization = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.Binarization));\n";
  return code;
};

Blockly.JavaScript.ConvertGrayScale = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.ConvertGrayScale));\n";
  return code;
};

Blockly.JavaScript.CalcAverageBrightness = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.CalcAverageBrightness));\n";
  return code;
};


Blockly.JavaScript.SampleFunctionX = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.SampleFunctionX));\n";
  return code;
};

Blockly.JavaScript.SampleFunctionY = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.SampleFunctionY));\n";
  return code;
};

Blockly.JavaScript.SampleFunctionZ = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.SampleFunctionZ));\n";
  return code;
};

Blockly.JavaScript.OutputData = function (block) {
  var code = "TaskList.push(new SIPMI.Task(SIPMI.TaskType.OutputData));\n";
  return code;
};


var workspace = Blockly.inject('blocklyDiv',
  {
    media: './media/',
    toolbox: document.querySelector('#toolbox'),
    trashcan: true
  });
Blockly.Xml.domToWorkspace(workspace, document.querySelector('#startBlocks'));

function showCode() {
  // Generate JavaScript code and display it.
  Blockly.JavaScript.INFINITE_LOOP_TRAP = null;
  var code = Blockly.JavaScript.workspaceToCode(workspace);
  alert(code);
}

// jshint ignore: start
function runCode() {
  // Generate JavaScript code and run it.
  window.LoopTrap = 1000;
  Blockly.JavaScript.INFINITE_LOOP_TRAP =
  'if (--window.LoopTrap == 0) throw "Infinite loop.";\n';
  var code = Blockly.JavaScript.workspaceToCode(workspace);
  Blockly.JavaScript.INFINITE_LOOP_TRAP = null;

  window.TaskList = [];
  try {
    eval(code);
  } catch (e) {
    alert(e);
  }
  SIPMI.UI.BuildTaskList();
}

function ToXML() {
  var dom = Blockly.Xml.workspaceToDom(workspace);
  return Blockly.Xml.domToPrettyText(dom);
}

function Restore(xml) {
  var dom = Blockly.Xml.textToDom(xml)
  workspace.clear();
  Blockly.Xml.domToWorkspace(workspace, dom);
}

function SaveToFile(fname, value) {
  var file = new File([value], fname);
  var url = window.URL.createObjectURL(file);
  var a = document.createElement('a');
  a.href = url;
  a.setAttribute('download', file.name);
  document.body.appendChild(a);
  a.click();
}

$(document).on('click', '#task-list-gen', function () {
  $('#upload-json-btn').removeAttr('disabled');
  runCode();
});
