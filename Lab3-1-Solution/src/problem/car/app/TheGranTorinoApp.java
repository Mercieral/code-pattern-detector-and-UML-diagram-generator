package problem.car.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import problem.car.api.ICar;
import problem.car.api.ICarPart;
import problem.car.impl.Body;
import problem.car.impl.Car;
import problem.car.impl.CarXmlOutputStream;
import problem.car.impl.Engine;
import problem.car.impl.Wheel;
import problem.car.visitor.api.ITraverser;
import problem.car.visitor.api.IVisitor;

public class TheGranTorinoApp {

	public static void main(String[] args) throws IOException {
		ICarPart body = new Body("coupe", "alumunium");
		ICarPart engine = new Engine(6, 4);
		ICarPart wheel1 = new Wheel("Good Year", 7, 5);
		ICarPart wheel2 = new Wheel("Good Year", 7, 5);
		ICarPart wheel3 = new Wheel("Good Year", 7, 5);
		ICarPart wheel4 = new Wheel("Good Year", 7, 5);
		
		ICar car1 = new Car("123456789", 
				"Ford",
				"Escort Zx2",
				Arrays.asList(body, 
						engine, 
						wheel1,
						wheel2,
						wheel3,
						wheel4));
		
		System.out.println(car1);
		
		OutputStream xmlOut = new FileOutputStream("input_output/car.xml");
		IVisitor xmlWriter = new CarXmlOutputStream(xmlOut);

		ITraverser traverser = (ITraverser)car1;
		traverser.accept(xmlWriter);
		
		ICar car2 = new Car("3214657689", 
				"Chevorelet",
				"Malibu",
				Arrays.asList(body, 
						engine, 
						wheel1,
						wheel2,
						wheel3,
						wheel4));

		System.out.println(car1);

		traverser = (ITraverser)car2;
		traverser.accept(xmlWriter);
		
		xmlOut.close();
	}
}
