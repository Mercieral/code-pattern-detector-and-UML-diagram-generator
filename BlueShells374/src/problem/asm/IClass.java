package problem.asm;

import java.util.Collection;

public interface IClass {
	public Collection<IMethod> getIMethods();
	public Collection<IField> getIField();
	public EClassAccess getAcessLevel();
	public String getSignature();
	public String getClassName();
	public double getClassVersion();
	public IClass getSuperName();
	public Collection<Interface> getInterface();
	public void addIMethods(IMethod method);
	public void addIField(IField field);
	public void setAccessLevel(EClassAccess access);
	public void setSignature(String signature);
	public void setClassName(String name);
	public void setClassVersion(double version);
	public void setSuperName(IClass name);
	public void addInterface(Interface inter);
	public String toString();
}
