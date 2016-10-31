package lab4;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.PatternSyntaxException;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class ConsoleKeyEnter extends KeyAdapter{
	JTextArea jta;
	FunctionUtils fu ;
	ConsoleKeyEnter(JTextArea jta){
		this.jta = jta;
		fu = new FunctionUtils(this.jta);
	}
	@Override
	public void keyPressed(KeyEvent e){
		if(e.getKeyChar()==''){
			//如果输入的是回退
			Rectangle rec;
			try {
				rec = jta.modelToView(jta.getCaretPosition());
				int line = rec.y / rec.height + 1;
				String[] lineString = jta.getText().split("\n");
				String thisString = lineString[line-1];
				if(thisString.endsWith("\\>"))
					jta.setText(jta.getText()+">");
			}catch (BadLocationException e1) {
				System.out.println("获取光标位置错误");
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e){
		if(e.getKeyChar()=='\n'){
			//当按下回车键
			Rectangle rec;
			try {
				rec = jta.modelToView(jta.getCaretPosition());
				int line = rec.y / rec.height + 1;
				String[] lineString = jta.getText().split("\n");
				String writeNextString = lineString[line-2].split(">")[0]+">";
				String console = lineString[line-2].split(">")[1];
				String doc = writeNextString.split(">")[0];
				boolean flagCD = executeConsole(console,doc);
				if(flagCD == false){
					jta.append("\n");
					jta.append(writeNextString);
				}
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	}
	private boolean executeConsole(String console,String doc) {
		String consoleType = console.split(" ")[0];
		boolean flagCD = false;
		//try{
			switch(consoleType){
				case "md":
					String file = console.split(" ")[1].split("-")[1];
					fu.MD(file, doc);
					break;
				case "rd":
					String file1 = console.split(" ")[1].split("-")[1];
					fu.RD(file1, doc);break;
				case "cd":
					flagCD = true;
					String file2 = console.split(" ")[1].split("-")[1];
					fu.CD(file2,doc);
					break;
				case "cd.":break;
				case "cd..":
					flagCD = true;
					fu.CD2(doc);break;
				case "dir":{
					System.out.println(console);
					if(console.contains("*"))
						fu.DIRXing(doc,console.split(" ")[1]);
					else if(console.contains("?"))
						fu.DIRWen(doc,console.split(" ")[1]);
					else
						fu.DIR(doc);
					break;
				}
				case "tree":fu.TREE(doc);break;
				case "mk":
					String file4 = console.split(" ")[1].split("-")[1];
					String sizeOfByte = console.split(" ")[1].split("-")[2];
					fu.MK(file4,doc,sizeOfByte);break;
				case "del":
					String file5 = console.split(" ")[1].split("-")[1];
					fu.DEL(file5,doc);
					break;
				case "exit":
					System.exit(0);
				default: jta.append("未识别的命令。\n\n"); break;
			}
		//}catch(ArrayIndexOutOfBoundsException e){
		//	jta.append("请输入有效格式");
		//}
		return flagCD;
	}
}
