package problem.javaClasses;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.objectweb.asm.Opcodes;

import problem.interfaces.IArrow;
import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;

/**
 * {@link IModel} object used to create file for GraphViz tool
 * 
 * @author gateslm, daniellm, mercieal
 *
 */
public class Model implements IModel {

	private List<IClass> classes;

	public Model() {
		this.classes = new ArrayList<IClass>();
	}

	@Override
	public void addClass(IClass currentClass) {
		classes.add(currentClass);
	}

	@Override
	public List<IClass> getClasses() {
		return classes;
	}

}
