package problem.visitor;

import java.io.FilterOutputStream;
import java.io.OutputStream;

import org.objectweb.asm.Opcodes;

import problem.javaClasses.Model;

public class UMLOutputStream extends FilterOutputStream {
	private IVisitor visitor;
	
	public UMLOutputStream(OutputStream out) {
		super(out);
		this.visitor = new Visitor();
	}
	
	private void setupPreVisitModel(){
		this.visitor.addVisit(VisitType.PreVisit, Model.class, (ITraverser t) -> {
			//code that runs goes here
			byte[] FIRST_LINE = "digraph G {  rankdir=BT; \n ".getBytes();
			try {
				this.write(FIRST_LINE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private void postVisitModel(){
		this.visitor.addVisit(VisitType.PostVisit, Model.class, (ITraverser t) -> {
			byte[] LAST_LINE = "\n}".getBytes();
			try {
				this.write(LAST_LINE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	private void VisitModel(){
		this.visitor.addVisit(VisitType.Visit, ConcreteClass.class, m);
	}
	
	private void generateClassBox(){
		String beginBrace = "[ \n";
		String endBrace = "\t]; \n";
		String box = "\t\tshape = \"record\",\n";
		String labelStart = "\t\tlabel = \n\t\t\t\"{ ";
		String labelEnd = "\t\t\t}\" \n";
		StringBuilder builder = new StringBuilder();
		String className = "\t" + obj.getClassName().replace("/", "") + " ";

		builder.append(className + beginBrace + box);
		builder.append(labelStart);
		if (obj.getAcessLevel() == Opcodes.ACC_INTERFACE) {
			builder.append("<<interface>>\n");
		}
		builder.append(obj.getClassName() + "\n\t\t\t|\n");
	}
	
	
	

}
