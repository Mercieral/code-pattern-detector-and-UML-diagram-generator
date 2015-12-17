package problem.car.impl;
import java.io.IOException;
import java.io.OutputStream;

import problem.car.api.IBody;
import problem.car.api.ICar;
import problem.car.api.IEngine;
import problem.car.api.IWheel;
import problem.car.visitor.api.VisitorAdapter;

public class CarXmlOutputStream extends VisitorAdapter {
	private final OutputStream out;
	
	public CarXmlOutputStream(OutputStream out) throws IOException {
		this.out = out;
	}
	
	private void write(String m) {
		try {
			this.out.write(m.getBytes());
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	
	@Override
	public void preVisit(ICar c) {
		String line = String.format("<car vin=\"%s\" make=\"%s\" model=\"%s\">%n", 
				c.getVIN(),
				c.getMake(),
				c.getModel());
		
		this.write(line);
	}

	@Override
	public void postVisit(ICar c) {
		this.write("</car>\n");
	}

	
	@Override
	public void visit(IBody b) {
		String line = String.format("<body type=\"%s\" material=\"%s\" />%n",
				b.getType(),
				b.getMaterial());
		this.write(line);
	}


	@Override
	public void visit(IEngine e) {
		String line = String.format("<engine cylinder=%d capacity=%.2f />%n",
				e.getCylinder(),
				e.getCapacity());
		this.write(line);
	}

	
	@Override
	public void visit(IWheel w) {
		String line = String.format("<wheel vendor=\"%s\" radius=%.2f width=%.2f />%n",
				w.getVendor(),
				w.getRadius(),
				w.getWidth());
		this.write(line);
	}
}
