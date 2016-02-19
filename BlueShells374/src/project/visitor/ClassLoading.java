package project.visitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import project.asm.ClassDeclarationVisitor;
import project.asm.ClassFieldVisitor;
import project.asm.ClassMethodVisitor;
import project.asm.Config;
import project.interfaces.IClass;
import project.interfaces.IModel;
import project.interfaces.IPhase;
import project.javaClasses.ConcreteClass;

public class ClassLoading implements IPhase {

	private List<File> DirFiles;

	@Override
	public void execute(Config config, IModel model) {
		try {
			DirFiles = new ArrayList<File>();
			File dir = new File(config.InputDir);
			this.getFilesRecursive(dir);
			ArrayList<String> classes = new ArrayList<String>();
			for (String clazz : config.classes){
				classes.add(clazz);
			}
			for (File file : DirFiles){
				classes.add(file.getPath().split("bin\\\\")[1].replace(".class","").replace("\\", "."));
			}
			for (File file : DirFiles){
				if (config.classesDiscludedFromDir.contains(file.getName().replace(".class", ""))){
					continue;
				}
				ClassReader reader;
				reader = new ClassReader(new FileInputStream(file));
				IClass currentClass = new ConcreteClass();

				ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass, classes,model);
				ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, currentClass,classes, model);
				ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass,classes, model);

				reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

				model.addClass(currentClass);
			}
			for (String className : config.classes) {
				// ASM's ClassReader does the heavy lifting of parsing the
				// compiled
				// Java class
				ClassReader reader;
				reader = new ClassReader(className);
				IClass currentClass = new ConcreteClass();

				// make class declaration visitor to get superclass and
				// interfaces
				ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass, classes,
						model);

				// DECORATE declaration visitor with field visitor
				ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, currentClass,
						classes, model);

				// DECORATE field visitor with method visitor
				ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass,
						classes, model);

				// Tell the Reader to use our (heavily decorated) ClassVisitor
				// to
				// visit the class
				reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

				// Add the class to the model
				model.addClass(currentClass);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getFilesRecursive(File pFile) {
		for (File files : pFile.listFiles()) {
			if (files.isDirectory()) {
				getFilesRecursive(files);
			} else {
				if (files.getName().endsWith(".class")){
					DirFiles.add(files);
				}
			}
		}
	}

}
