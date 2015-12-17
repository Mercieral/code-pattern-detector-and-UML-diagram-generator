package problem.car.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import problem.car.api.ICar;
import problem.car.api.ICarPart;
import problem.car.visitor.api.ITraverser;
import problem.car.visitor.api.IVisitor;

public class Car implements ICar, ITraverser {
	private String vin;
	private String make;
	private String model;
	private Collection<ICarPart> parts;
	

	public Car(String vin, String make, String model, Collection<ICarPart> parts) {
		this.vin = vin;
		this.make = make;
		this.model = model;
		this.parts = Collections.unmodifiableCollection(new ArrayList<>(parts));
	}

	@Override
	public String getVIN() {
		return this.vin;
	}

	@Override
	public String getMake() {
		return this.make;
	}

	@Override
	public String getModel() {
		return this.model;
	}

	@Override
	public Collection<ICarPart> getParts() {
		return this.parts;
	}

	@Override
	public String toString() {
		return "Car [vin=" + vin + ", make=" + make + ", model=" + model + ", parts=" + parts + "]";
	}

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		for(ICarPart p: this.parts) {
			ITraverser t = (ITraverser)p;
			t.accept(v);
		}
		v.postVisit(this);
	}
}
