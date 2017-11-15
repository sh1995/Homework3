package core.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

public class TestStudent{
	
	private IStudent student;
	private IAdmin admin;
	private IInstructor inst;
	
	@Before
	public void setup() {
		this.admin = new Admin();
		this.inst = new Instructor();
		this.student = new Student();
    
		this.admin.createClass("Test1", 2017, "Instructor", 2);
		this.admin.createClass("Test2", 2017, "Instructor", 2);
		this.inst.addHomework("Instructor", "Test1", 2017, "HW1");
		this.inst.addHomework("Instructor", "Test1", 2017, "HW2");
    }
	
	//Register student
	@Test
	public void registerTest() {
		this.student.registerForClass("1", "Test1", 2017);
		
		assertTrue(this.student.isRegisteredFor("1", "Test1", 2017));
	}
	
	//Register non-valid class
	@Test
	public void registerTest2() {
		this.student.registerForClass("1", "Test1", 2018);
		
		assertFalse(this.student.isRegisteredFor("1", "Test1", 2018));
	}
	
	//Register max students
	@Test
	public void registerTest3() {
		this.student.registerForClass("1", "Test1", 2017);
		this.student.registerForClass("2", "Test1", 2017);
		
		assertTrue(this.student.isRegisteredFor("1", "Test1", 2017) && 
				this.student.isRegisteredFor("2", "Test1", 2017));
	}
	
	//Register for full class
	@Test
	public void registerTest4() {
		this.student.registerForClass("1", "Test1", 2017);
		this.student.registerForClass("2", "Test1", 2017);
		this.student.registerForClass("3", "Test1", 2017);
		
		assertFalse(this.student.isRegisteredFor("3", "Test1", 2017));
	}
	
	//Dropping a class that student is enrolled in
	@Test
	public void dropTest() {
		this.student.registerForClass("1", "Test1", 2017);
		this.student.dropClass("1", "Test1", 2017);
		
		assertFalse(this.student.isRegisteredFor("1", "Test1", 2017));
	}
	
	//Dropping a class that student is not enrolled in
	@Test
	public void dropTest2() {
		this.student.registerForClass("1", "Test1", 2017);
		this.student.registerForClass("2", "Test2", 2017);
		
		this.student.dropClass("2", "Test1", 2017);
		
		assertTrue(this.student.isRegisteredFor("2", "Test2", 2017));
	}
	
	//Dropping the same class wrong year
	@Test
	public void dropTest3() {
		this.student.registerForClass("1", "Test1", 2017);
		
		this.student.dropClass("1", "Test1", 2018);
		
		assertTrue(this.student.isRegisteredFor("1", "Test1", 2017));
	}
	
	//Enrolled student submitting HW1
	@Test
	public void submitTest() {
		this.student.registerForClass("1", "Test1", 2017);
		
		this.student.submitHomework("1", "HW1", "T", "Test1", 2017);
		
		assertTrue(this.student.hasSubmitted("1", "HW1", "Test1", 2017));
	}
	
	//Non-enrolled student submitting HW1
	@Test
	public void submitTest2() {
		this.student.registerForClass("1", "Test1", 2017);
		
		this.student.submitHomework("2", "HW1", "T", "Test1", 2017);
		
		assertFalse(this.student.hasSubmitted("2", "HW1", "Test1", 2017));
	}
	
	// One enrolled student submitting two assignments
	@Test
	public void submitTest3() {
		
		this.student.registerForClass("1", "Test1", 2017);
		
		this.student.submitHomework("1", "HW1", "T", "Test1", 2017);
		this.student.submitHomework("1", "HW2", "T", "Test1", 2017);
		
		assertTrue(this.student.hasSubmitted("1", "HW1", "Test1", 2017)
				&& this.student.hasSubmitted("1", "HW2", "Test1", 2017));
	}
	
	//Two enrolled students submitting assignment
	@Test
	public void submitTest4() {
		
		this.student.registerForClass("1", "Test1", 2017);
		this.student.registerForClass("2", "Test1", 2017);
		
		this.student.submitHomework("1", "HW1", "T", "Test1", 2017);
		this.student.submitHomework("2", "HW1", "T", "Test1", 2017);
		
		assertTrue(this.student.hasSubmitted("1", "HW1", "Test1", 2017)
				&& this.student.hasSubmitted("2", "HW1", "Test1", 2017));
	}
	
	//Submitting non-valid assignment
	@Test
	public void submitTest5() {
		this.student.registerForClass("1", "Test1", 2017);
		
		this.student.submitHomework("1", "HWX", "T", "Test1", 2017);
		
		assertFalse(this.student.hasSubmitted("1", "HWX", "Test1", 2017));
	}
}