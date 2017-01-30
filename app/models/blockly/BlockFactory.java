package models.blockly;

public class BlockFactory {

	public static String createJavascriptBlock(String typeName, String name){

		StringBuilder blockStr = new StringBuilder();

		blockStr.append("Blockly.Blocks." + typeName + " = {\n");
		blockStr.append("init: function() {\n");
		blockStr.append("this.appendDummyInput().appendField('" + typeName + "')\n");
		blockStr.append("this.appendDummyInput().appendField(new Blockly.FieldImage('/assets/images/upload/" + name + "', 100, 100, '*'))\n");
		blockStr.append("this.setOutput(true);\n");
		blockStr.append("this.setColour(150);\n");
		blockStr.append("}\n");
		blockStr.append("};\n");
		blockStr.append("Blockly.JavaScript." + typeName + " = function (block) {\n");
		blockStr.append("var code = 'TaskList.push(new SIPMI.Task(SIPMI.TaskType." + typeName + "));\\n';\n");
		blockStr.append("return code;\n");
		blockStr.append("};\n");
		blockStr.append("var func" + typeName + " = function (varList) {\n");
		blockStr.append("varList.push('/public/images/upload/" + name + "');\n");
		blockStr.append("}\n");

		return blockStr.toString();
	}

}
