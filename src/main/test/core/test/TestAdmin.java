package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestAdmin {

    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    //Class from the past
    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    //Capacity of 0
    @Test
    public void testMakeClass3() {
    		this.admin.createClass("Test", 2017, "Instructor", 0);
    		assertFalse(this.admin.classExists("Test", 2017));
    }
    
    //Same instructor of 2 courses
    @Test
    public void testMakeClass4() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.admin.createClass("Test2", 2017, "Instructor", 15);
		assertTrue(this.admin.classExists("Test2", 2017)
				&& this.admin.classExists("Test", 2017));
    }
    
  //Same instructor of 3 courses
    @Test
    public void testMakeClass5() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.admin.createClass("Test2", 2017, "Instructor", 15);
		this.admin.createClass("Test3", 2017, "Instructor", 15);
		
		assertFalse(this.admin.classExists("Test3", 2017));
    }
    
    //Same Class different Year
    @Test
    public void testMakeClass6() {
    		this.admin.createClass("Test", 2017, "Instructor", 15);
    		this.admin.createClass("Test", 2018, "Instructor", 15);
    		assertTrue(this.admin.classExists("Test", 2017)
				&& this.admin.classExists("Test", 2018));
    }
    
    //Capacity of -1
    @Test
    public void testMakeClass7() {
    		this.admin.createClass("Test", 2017, "Instructor", -1);
    		assertFalse(this.admin.classExists("Test", 2017));
    }
    
    //Same instructor with two courses in one year and one course next year
    @Test
    public void testMakeClass8() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.admin.createClass("Test2", 2017, "Instructor", 15);
		this.admin.createClass("Test3", 2018, "Instructor", 15);
		
		assertTrue(this.admin.classExists("Test3", 2018));
    }
       
    //Change to a greater number
    @Test
    public void testChange() {
    		this.admin.createClass("Test", 2017, "Instructor1", 15);
    		this.admin.changeCapacity("Test", 2017, 99);
    		
    		assertTrue(this.admin.getClassCapacity
    				("Test", 2017) == 99);
    }
    
    //Change to a lesser number
    @Test
    public void testChange2() {
		this.admin.createClass("Test", 2017, "Instructor1", 15);
		this.admin.changeCapacity("Test", 2017, 1);
		
		assertFalse(this.admin.getClassCapacity
				("Test", 2017) == 1);
    }
    
    //Change to negative number
    @Test
    public void testChange3() {
		this.admin.createClass("Test", 2017, "Instructor1", 15);
		this.admin.changeCapacity("Test", 2017, -1);
		
		assertFalse(this.admin.getClassCapacity
				("Test", 2017) == -1);
    }
    
    //Change to a very large number
    @Test
    public void testChange4() {
		this.admin.createClass("Test", 2017, "Instructor1", 15);
		this.admin.changeCapacity("Test", 2017, 99999);
		
		assertTrue(this.admin.getClassCapacity
				("Test", 2017) == 99999);
    }
    
    //
}
