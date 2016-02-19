package problem.visitor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import problem.asm.Config;
import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IPattern;
import problem.interfaces.IPhase;
import problem.interfaces.IRelation;
import problem.javaClasses.ConcreteClass;
import problem.javaClasses.ExtensionRelation;
import problem.javaClasses.Field;
import problem.javaClasses.HasRelation;
import problem.javaClasses.InterfaceRelation;
import problem.javaClasses.Method;
import problem.javaClasses.Model;
import problem.javaClasses.UsesRelation;

public class UMLGenerator implements IPhase {
	private IVisitor visitor;
	private Map<String, List<String>> hasRelations;
	private Map<String, List<String>> useRelations;
	private FileOutputStream out;
	private String dotPath;
	private String outPath;

	
	public UMLGenerator() {
		this.visitor = new Visitor();
		this.hasRelations = new HashMap<String, List<String>>();
		this.useRelations = new HashMap<String, List<String>>();

		this.setupPreVisitModel();
		this.setupPreVisitClass();
		this.visitClass();
		this.visitField();
		this.visitMethod();
		this.postVisitClass();
		this.postVisitModel();
		this.visitHasRelation();
		this.visitUsesRelation();
		this.visitInterfaceRelation();
		this.visitExtensionRelation();

	}

	private void visitHasRelation() {
		this.visitor.addVisit(VisitType.Visit, HasRelation.class,
				(ITraverser t) -> {
					IRelation r = (IRelation) t;
					//String pointerClass = parsePointerClass(r.getToObject());
					if (this.hasRelations.get(r.getFromObject()) == null || !this.hasRelations.get(r.getFromObject()).contains(r.getToObject())){
						if (this.hasRelations.get(r.getFromObject()) == null) {
							this.hasRelations.put(r.getFromObject(), new ArrayList<String>());
							this.hasRelations.get(r.getFromObject()).add(r.getToObject());
						}
						else if (!this.hasRelations.get(r.getFromObject()).contains(r.getToObject())){
							this.hasRelations.get(r.getFromObject()).add(r.getToObject());
						}
						try {
							out.write(r.drawRelation().getBytes());
							if (useRelations.get(r.getFromObject()) != null && useRelations.get(r.getFromObject()).contains(r.getToObject())) {
								useRelations.remove(r.getFromObject());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void visitUsesRelation() {
		this.visitor.addVisit(VisitType.Visit, UsesRelation.class,
				(ITraverser t) -> {
					IRelation r = (IRelation) t;
					if ((useRelations.get(r.getFromObject()) == null || !useRelations.get(r.getFromObject()).contains(r.getToObject())) 
							&& ((hasRelations.get(r.getFromObject()) == null) || !hasRelations.get(r.getFromObject()).contains(r.getToObject()))) {
						if (useRelations.get(r.getFromObject()) == null){
							useRelations.put(r.getFromObject(), new ArrayList<String>());
							useRelations.get(r.getFromObject()).add(r.getToObject());
						}
						else if (!useRelations.get(r.getFromObject()).contains(r.getToObject())){
							useRelations.get(r.getFromObject()).add(r.getToObject());
						}
						try {
							out.write(r.drawRelation().getBytes());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				});
	}

	private void visitInterfaceRelation() {
		this.visitor.addVisit(VisitType.Visit, InterfaceRelation.class,
				(ITraverser t) -> {
					IRelation r = (IRelation) t;
					try {
						out.write(r.drawRelation().getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
	}

	private void visitExtensionRelation() {
		this.visitor.addVisit(VisitType.Visit, ExtensionRelation.class,
				(ITraverser t) -> {
					IRelation r = (IRelation) t;
					try {
						out.write(r.drawRelation().getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}

				});

	}

	private void setupPreVisitModel() {
		this.visitor.addVisit(VisitType.PreVisit, Model.class,
				(ITraverser t) -> {
					// code that runs goes here
					byte[] FIRST_LINE = "digraph G {  rankdir=BT; \n splines=\"ortho\"; \n "
							.getBytes();
					try {
						out.write(FIRST_LINE);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
	}

	private void postVisitModel() {
		this.visitor.addVisit(VisitType.PostVisit, Model.class,
				(ITraverser t) -> {
					byte[] LAST_LINE = "\n}".getBytes();
					try {
						out.write(LAST_LINE);
						Runtime rt = Runtime.getRuntime();
						Process graphviz = rt.exec("\"" + dotPath +  "\" "
								+ "-Tpng input_output\\graph.gv -o " + this.outPath + "\\graph.png");
						while(graphviz.isAlive()){}
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
	}

	private void setupPreVisitClass() {
		this.visitor.addVisit(VisitType.PreVisit, ConcreteClass.class,
				(ITraverser t) -> {
					IClass obj = (IClass) t;
					StringBuilder builder = new StringBuilder();

					String beginBrace = "[ \n";
					String box = "\t\tshape = \"record\",\n\t\t";

					// pattern code here;
					StringBuilder sb = new StringBuilder();
					StringBuilder sb2 = new StringBuilder();
					for (IPattern pattern : obj.getPatterns()) {
						sb.append(pattern.getProperty() + "\n\t\t");
						sb2.append(pattern.getLabel() + "\\n\n\t\t\t");
					}

					String labelStart = "label = \n\t\t\t\"{ ";
					String className = "\t"
							+ obj.getClassName().replace("/", "") + " ";

					builder.append(
							className + beginBrace + box + sb.toString());
					builder.append(labelStart);
					if (obj.getAccessLevel() == 1537) {
						builder.append("\\<\\<interface\\>\\>\\n\n\t\t\t");
					}
					builder.append(obj.getClassName() + "\n\t\t\t\\n\n\t\t\t"
							+ sb2.toString() + "|\n");
					try {
						out.write(builder.toString().getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
	}

	private void visitClass() {
		this.visitor.addVisit(VisitType.Visit, ConcreteClass.class,
				(ITraverser t) -> {
					try {
						out.write("\t\t\t| \n ".getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
	}

	private void postVisitClass() {
		this.visitor.addVisit(VisitType.PostVisit, ConcreteClass.class,
				(ITraverser t) -> {
					String endBrace = "\t]; \n";
					String labelEnd = "\t\t\t}\" \n";
					StringBuilder sb = new StringBuilder();
					sb.append(labelEnd);
					sb.append(endBrace);
					try {
						out.write(sb.toString().getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
	}

	private void visitField() {
		this.visitor.addVisit(VisitType.Visit, Field.class, (ITraverser t) -> {
			IField f = (IField) t;
			StringBuilder sb = new StringBuilder();

			String start = "\t\t\t";
			sb.append(start);

			// field string
			sb.append(f.getAccessLevel() + " ");
			if (f.getSignature().equals(""))
				sb.append(trimValue(f.getDesc(), ".") + " ");

			else {
				sb.append(trimValue(f.getDesc(), ".") + "[");
				sb.append(trimValue(trimValue(f.getSignature(), "<"), ".") + "] ");
			}
			sb.append(f.getName());

			String end = " \\l\n";
			sb.append(end);

			try {
				out.write(sb.toString().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private void visitMethod() {
		this.visitor.addVisit(VisitType.Visit, Method.class, (ITraverser t) -> {
			IMethod m = (IMethod) t;
			StringBuilder sb = new StringBuilder();

			if (!m.getName().equals("<init>") && !m.getName().equals("<clinit>")) {
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

				sb.append(result);
				sb.append(" \\l\n");

				try {
					out.write(sb.toString().getBytes());
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
	public void execute(Config config, IModel model) {
		try {
			this.dotPath = config.dotPath;
			this.outPath = config.outDir;
			this.out = new FileOutputStream("input_output/graph.gv");
			ITraverser traverser = (ITraverser) model;
			traverser.accept(this.visitor);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
