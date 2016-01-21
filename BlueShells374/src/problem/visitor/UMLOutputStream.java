package problem.visitor;

import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.objectweb.asm.Opcodes;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.javaClasses.ConcreteClass;
import problem.javaClasses.Model;

public class UMLOutputStream extends FilterOutputStream implements IStream{
	private IVisitor visitor;
	
	public UMLOutputStream(OutputStream out) {
		super(out);
		this.visitor = new Visitor();
		
		this.setupPreVisitModel();
		this.visitClass();
		this.visitField();
		this.visitMethod();
		this.postVisitClass();
		this.postVisitModel();
		
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
	
	private void visitField(){
		this.visitor.addVisit(VisitType.Visit, Field.class, (ITraverser t) -> {
			IField f = (IField) t;
			StringBuilder sb = new StringBuilder();
			
			String start = "\t\t\t";
			sb.append(start);
			
			//field string
			sb.append(f.getAccessLevel() + " ");
			if (f.getSignature().equals(""))
				sb.append(trimValue(f.getDesc(), ".") + " ");

			else {
				sb.append(trimValue(f.getDesc(), ".") + "[");
				sb.append(trimValue(f.getSignature(), ".") + "] ");
			}
			sb.append(f.getName());
			
			String end = " \\l\n";
			sb.append(end);
			
			try {
				this.write(sb.toString().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private void visitMethod(){
		this.visitor.addVisit(VisitType.Visit, Method.class, (ITraverser t) -> {
			IMethod m = (IMethod) t;
			StringBuilder sb = new StringBuilder();
			
			if (!m.getName().equals("<init>")) {
				sb.append("\t\t\t");
				
				StringBuilder sb2 = new StringBuilder();
				sb2.append(m.getAccessLevel() + " ");
				sb2.append(m.getName());
				sb2.append("(");
				for (String args : m.getArguments()) {
					sb2.append(args + ", ");
				}
				String result = sb2.toString();
				if (!m.getArguments().isEmpty()) {
					result = result.substring(0, sb2.length() - 2);
				}
				result = result + ") : ";
				result = result + trimValue(m.getReturnType(), ".");
				
				sb2.append(result);
				sb.append(sb2.toString());
				sb.append(" \\l\n");
				
				try {
					this.write(sb.toString().getBytes());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Shortens the name of strings that have a long value of extra information
	 * 
	 * @param initial
	 *            - Initial value to shorten
	 * @param delimiter
	 *            - Value to use to remove unnecessary pieces
	 * @return - Shortened string to be used containing useful information
	 */
	private String trimValue(String initial, String delimiter) {
		while (initial.indexOf(delimiter) != -1) {
			// // Used for if a type is given to a list
			// if (initial.indexOf("<") != -1){
			// if(initial.indexOf(delimiter) > initial.indexOf("<")){
			// return initial;
			// }
			// }
			initial = initial.substring(initial.indexOf(delimiter) + 1);
		}
		return initial;
	}

	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}
}
