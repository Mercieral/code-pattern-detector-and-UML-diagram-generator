package problem.visitor;

import java.io.FilterOutputStream;
import java.io.OutputStream;

import org.objectweb.asm.Opcodes;

import problem.interfaces.IClass;
import problem.javaClasses.ConcreteClass;
import problem.javaClasses.Model;

public class UMLOutputStream extends FilterOutputStream implements IStream{
	private IVisitor visitor;
	
	public UMLOutputStream(OutputStream out) {
		super(out);
		this.visitor = new Visitor();
		
		this.setupPreVisitModel();
		this.visitClass();
		
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
	
	private void visitClass(){
		this.visitor.addVisit(VisitType.Visit, ConcreteClass.class, (ITraverser t) -> {
			IClass obj = (IClass) t;
			StringBuilder builder = new StringBuilder();

			String beginBrace = "[ \n";
			String box = "\t\tshape = \"record\",\n";
			String labelStart = "\t\tlabel = \n\t\t\t\"{ ";
			String className = "\t" + obj.getClassName().replace("/", "") + " ";

			builder.append(className + beginBrace + box);
			builder.append(labelStart);
			if (obj.getAcessLevel() == Opcodes.ACC_INTERFACE) {
				builder.append("<<interface>>\n");
			}
			builder.append(obj.getClassName() + "\n\t\t\t|\n");
			try {
				this.write(builder.toString().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private void postVisitClass(){
		this.visitor.addVisit(VisitType.PostVisit, ConcreteClass.class, (ITraverser t) -> {
			String endBrace = "\t]; \n";
			String labelEnd = "\t\t\t}\" \n";
			StringBuilder sb = new StringBuilder();
			sb.append(labelEnd);	
			sb.append(endBrace);
			try {
				this.write(sb.toString().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
