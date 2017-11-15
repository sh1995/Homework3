package core.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;


public class TestInstructor {

	private IAdmin admin;
    private IInstructor inst;
    private IStudent student;

    @Before
    public void setup() {
    		this.admin = new Admin();
        this.inst = new Instructor();
        this.student = new Student();
        
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("S", "Test", 2017);
    }
    
    //Add Homework to class
    @Test
    public void TestAdd() {
    		this.inst.addHomework("Instructor", "Test", 2017, "HW1");
    		
    		assertTrue(this.inst.homeworkExists("Test",2017, "HW1"));
    }
    
    //Non-assigned Instructor adding HW1
    @Test
    public void TestAdd2() {
    		this.inst.addHomework("Instructor2", "Test", 2017, "HW1");
		assertFalse(this.inst.homeworkExists("Test",2017, "HW1"));
    }
    
    //More than one assignment in a class
    @Test
    public void TestAdd3() {
    		this.inst.addHomework("Instructor", "Test", 2017, "HW1");
    		this.inst.addHomework("Instructor", "Test", 2017, "HW2");
		
		assertTrue(this.inst.homeworkExists("Test",2017, "HW1") 
				&& this.inst.homeworkExists("Test",2017, "HW2"));
    }
    
    //Same course different year
    @Test
    public void TestAdd4() {
    		this.inst.addHomework("Instructor", "Test", 2018, "HW1");
		
		assertFalse(this.inst.homeworkExists("Test", 2017, "HW1")
				&& this.inst.homeworkExists("Test", 2018, "HW1"));
    }
    
    //Adding to wrong course
    @Test
    public void TestAdd5() {
    		this.inst.addHomework("Instructor", "Test1", 2017, "HW1");
		
		assertFalse(this.inst.homeworkExists("Test", 2017, "HW1")
				&& this.inst.homeworkExists("Test1", 2017, "HW1"));
    }
    
    //Assign Grade to valid hw
    @Test
    public void assignTest() {
    		this.inst.addHomework("Instructor", "Test", 2017, "HW1");
    		
    		this.student.submitHomework("S", "HW1", "T", "Test", 2017);
    		
    		this.inst.assignGrade("Instructor", "Test", 2017, "HW1", "S", 100);
		
		assertTrue(this.inst.getGrade("Test", 2017, "HW1", "S") == 100);
    }
    
    //Assign Grade to non-valid HW
    @Test
    public void assignTest2() {
    		this.inst.addHomework("Instructor", "Test", 2017, "HW1");
    		
    		this.student.submitHomework("S", "HW1", "T", "Test", 2017);
    		
    		this.inst.assignGrade("Instructor", "Test", 2017, "HW2", "S", 100);
		
		assertNull(this.inst.getGrade("Test", 2017, "HW2", "S"));
    }
    
    //Wrong Instructor assigning grade
    @Test
    public void assignTest3() {
    		this.inst.addHomework("Instructor", "Test", 2017, "HW1");
    		
    		this.student.submitHomework("S", "HW1", "T", "Test", 2017);
    		
    		this.inst.assignGrade("InstructorX", "Test", 2017, "HW1", "S", 100);
		
		assertNull(this.inst.getGrade("Test", 2017, "HW1", "S"));
    }
    
    //Assigning grade to non-enrolled student
    @Test
    public void assignTest4() {
    		this.inst.addHomework("Instructor", "Test", 2017, "HW1");
    		
    		this.student.submitHomework("S", "HW1", "T", "Test", 2017);
    		
    		this.inst.assignGrade("Instructor", "Test", 2017, "HW1", "X", 100);
		
		assertNull(this.inst.getGrade("Test", 2017, "HW1", "X"));
    }
    
    //Assign grade to wrong course year
    @Test
    public void assignTest5() {
    		this.inst.addHomework("Instructor", "Test", 2017, "HW1");
    		
    		this.student.submitHomework("S", "HW1", "T", "Test", 2017);
    		
    		this.inst.assignGrade("Instructor", "Test", 2018, "HW1", "S", 100);
		
		assertNull(this.inst.getGrade("Test", 2018, "HW1", "S"));
    }
    
    //Assign negative grade
    @Test
    public void assignTest6() {
    		this.inst.addHomework("Instructor", "Test", 2017, "HW1");
    		
    		this.student.submitHomework("S", "HW1", "T", "Test", 2017);
    		
    		this.inst.assignGrade("Instructor", "Test", 2017, "HW1", "S", -100);
		
		assertNull(this.inst.getGrade("Test", 2017, "HW1", "S"));
    }
    
    //Assign grade of 0
    @Test
    public void assignTest7() {
    		this.inst.addHomework("Instructor", "Test", 2017, "HW1");
    		
    		this.student.submitHomework("S", "HW1", "T", "Test", 2017);
    		
    		this.inst.assignGrade("Instructor", "Test", 2017, "HW1", "S", 00);
		
		assertTrue(this.inst.getGrade("Test", 2017, "HW1", "S") == 0);
    }
    
  //Assign grade > 100
    @Test
    public void assignTest8() {
    		this.inst.addHomework("Instructor", "Test", 2017, "HW1");
    		
    		this.student.submitHomework("S", "HW1", "T", "Test", 2017);
    		
    		this.inst.assignGrade("Instructor", "Test", 2017, "HW1", "S", 200);
		
		assertTrue(this.inst.getGrade("Test", 2017, "HW1", "S") == 200);
    }
}
