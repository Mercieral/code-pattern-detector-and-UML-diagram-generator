package problem.asm;

import java.util.Collection;

public interface IClass {
	public Collection<IMethod> getIMethods();
	public Collection<IField> getIField();
	public int getAcessLevel();
	public String getSignature();
	public String getClassName();
	public double getClassVersion();
	public IClass getSuperName();
	public Collection<String> getInterface();
	public String getExtension();
	public void addIMethod(IMethod method);
	public void addIField(IField field);
	public void setAccessLevel(int access);
	public void setSignature(String signature);
	public void setClassName(String name);
	public void setClassVersion(double version);
	public void setSuperName(IClass name);
	public void addInterface(String inter);
	public void setExtension(String extension);
	public String toString();
}
