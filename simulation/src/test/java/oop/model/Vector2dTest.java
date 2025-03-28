package oop.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {

    @Test
    public void equalsVector2dTest() {
        // given 1
        Vector2d obj1 = new Vector2d(2, 9);
        Vector2d obj2 = new Vector2d(2, 9);
        // then 1 (expected: equals)
        assertTrue(obj1.equals(obj2));


        // given 2
        Vector2d obj3 = new Vector2d(2, 9);
        Vector2d obj4 = new Vector2d(0, 5);
        // then 2 (expected: does not equal)
        assertFalse(obj3.equals(obj4));


        // given 3
        Vector2d obj5 = new Vector2d(2, 9);
        Vector2d obj6 = new Vector2d(2, 5);
        // then 3 (expected: does not equal, because only x are equal)
        assertFalse(obj5.equals(obj6));


        // given 4
        Vector2d obj7 = new Vector2d(2, 9);
        Vector2d obj8 = new Vector2d(0, 9);

        // then 4 (expected: does not equal, because only y are equal)
        assertFalse(obj7.equals(obj8));

        // given 4
        Vector2d obj9 = new Vector2d(2, 9);
        Vector2d obj10 = new Vector2d(-2, -9);

        // then 4 (expected: does not equal, because have different signs)
        assertFalse(obj9.equals(obj10));
    }

    @Test
    public void toStringVector2dTest(){
        // given 1
        Vector2d obj1 = new Vector2d(2, 9);
        // then 1 (expected: equals)
        assertEquals("(2, 9)", obj1.toString());


        // given 2
        Vector2d obj2 = new Vector2d(-2, -9);
        // then 2 (expected: equals)
        assertEquals("(-2, -9)", obj2.toString());
    }

    @Test
    public void precedesVector2dTest(){
        // given 1
        Vector2d obj1 = new Vector2d(1, 2);
        Vector2d obj2 = new Vector2d(2, 3);
        // then 1 (expected: precedes, because first coordinates less than second)
        assertTrue(obj1.precedes(obj2));

        // given 2
        Vector2d obj3 = new Vector2d(2, 3);
        Vector2d obj4 = new Vector2d(1, 2);
        // then 2 (expected: does not precede, because first coordinates more than second)
        assertFalse(obj3.precedes(obj4));

        // given 3
        Vector2d obj5 = new Vector2d(2, 3);
        Vector2d obj6 = new Vector2d(2, 3);
        // then 3 (expected: precedes, because first coordinates equal to second)
        assertTrue(obj5.precedes(obj6));

        // given 4
        Vector2d obj7 = new Vector2d(1, 2);
        Vector2d obj8 = new Vector2d(1, 3);
        // then 4 (expected: precedes, because first coordinates less or equal than second)
        assertTrue(obj7.precedes(obj8));

        // given 5
        Vector2d obj9 = new Vector2d(2, 3);
        Vector2d obj10 = new Vector2d(2, 1);
        // then 5 (expected: does not precede, because first coordinates more or equal than second)
        assertFalse(obj9.precedes(obj10));

        // given 6
        Vector2d obj11 = new Vector2d(2, 3);
        // then 6 (expected: precedes, because first coordinates equal to second)
        assertTrue(obj11.precedes(obj11));
    }

    @Test
    public void followsVector2dTest(){
        // given 1
        Vector2d obj1 = new Vector2d(2, 3);
        Vector2d obj2 = new Vector2d(1, 2);
        // then 1 (expected: follows, because first coordinates more than second)
        assertTrue(obj1.follows(obj2));

        // given 2
        Vector2d obj3 = new Vector2d(1, 2);
        Vector2d obj4 = new Vector2d(2, 3);
        // then 2 (expected: does not follow, because first coordinates less than second)
        assertFalse(obj3.follows(obj4));


        // given 3
        Vector2d obj5 = new Vector2d(2, 3);
        Vector2d obj6 = new Vector2d(2, 3);
        // then 3 (expected: follows, because first coordinates equal to second)
        assertTrue(obj5.follows(obj6));

        // given 4
        Vector2d obj7 = new Vector2d(1, 2);
        Vector2d obj8 = new Vector2d(1, 3);
        // then 4 (expected: does not follow, because first coordinates less or equal than second)
        assertFalse(obj7.follows(obj8));

        // given 5
        Vector2d obj9 = new Vector2d(2, 3);
        Vector2d obj10 = new Vector2d(2, 1);
        // then 5 (expected: follows, because first coordinates more or equal than second)
        assertTrue(obj9.follows(obj10));

        // given 6
        Vector2d obj11 = new Vector2d(2, 3);
        // then 6 (expected: follows, because first coordinates equal to second)
        assertTrue(obj11.follows(obj11));
    }

    @Test
    public void upperRightVector2dTest(){
        // given 1
        Vector2d obj1 = new Vector2d(2, 3);
        Vector2d obj2 = new Vector2d(1, 2);
        // then 1 (first follows second, expected: (2,3) as maximum for each coordinate)
        assertEquals("(2, 3)", obj1.upperRight(obj2).toString());


        // given 2
        Vector2d obj3 = new Vector2d(1, 3);
        Vector2d obj4 = new Vector2d(2, 2);
        // then 2 (first does not follow or precede second, expected: (2,3) as maximum for each coordinate)
        assertEquals("(2, 3)", obj3.upperRight(obj4).toString());


        // given 3
        Vector2d obj5 = new Vector2d(-1, -3);
        Vector2d obj6 = new Vector2d(-2, -4);
        // then 3 (negative coordinates, expected: (-1,-3) as maximum for each coordinate)
        assertEquals("(-1, -3)", obj5.upperRight(obj6).toString());

        // given 4
        Vector2d obj7 = new Vector2d(1, 1);
        Vector2d obj8 = new Vector2d(2, 1);
        // then 4 (y are equal, expected: (2,1) as maximum for each coordinate)
        assertEquals("(2, 1)", obj7.upperRight(obj8).toString());

        // given 5
        Vector2d obj9 = new Vector2d(1, 2);
        Vector2d obj10 = new Vector2d(1, 1);
        // then 5 (x are equal, expected: (1,2) as maximum for each coordinate)
        assertEquals("(1, 2)", obj9.upperRight(obj10).toString());

        // given 6
        Vector2d obj11 = new Vector2d(1, 2);
        // then 6 (x and y are equal, expected: (1,2) as maximum for each coordinate)
        assertEquals("(1, 2)", obj11.upperRight(obj11).toString());
    }

    @Test
    public void lowerLeftVector2dTest(){
        // given 1
        Vector2d obj1 = new Vector2d(2, 3);
        Vector2d obj2 = new Vector2d(1, 2);
        // then 1 (first follows second, expected: (1,2) as minimum for each coordinate)
        assertEquals("(1, 2)", obj1.lowerLeft(obj2).toString());


        // given 2
        Vector2d obj3 = new Vector2d(1, 3);
        Vector2d obj4 = new Vector2d(2, 2);
        // then 2 (first does not follow or precede second, expected: (1,2) as minimum for each coordinate)
        assertEquals("(1, 2)", obj3.lowerLeft(obj4).toString());


        // given 3
        Vector2d obj5 = new Vector2d(-1, -3);
        Vector2d obj6 = new Vector2d(-2, -4);
        // then 3 (negative coordinates, expected: (-2,-4) as minimum for each coordinate)
        assertEquals("(-2, -4)", obj5.lowerLeft(obj6).toString());

        // given 4
        Vector2d obj7 = new Vector2d(1, 1);
        Vector2d obj8 = new Vector2d(2, 1);
        // then 4 (y are equal, expected: (1,1) as minimum for each coordinate)
        assertEquals("(1, 1)", obj7.lowerLeft(obj8).toString());

        // given 5
        Vector2d obj9 = new Vector2d(1, 2);
        Vector2d obj10 = new Vector2d(1, 1);
        // then 5 (x are equal, expected: (1,1) as minimum for each coordinate)
        assertEquals("(1, 1)", obj9.lowerLeft(obj10).toString());

        // given 6
        Vector2d obj11 = new Vector2d(1, 2);
        // then 6 (x and y are equal, expected: (1,2) as minimum for each coordinate)
        assertEquals("(1, 2)", obj11.lowerLeft(obj11).toString());
    }

    @Test
    public void addVector2dTest(){
        // given 1
        Vector2d obj1 = new Vector2d(2, 3);
        Vector2d obj2 = new Vector2d(1, 2);
        // when 1
        obj1 = obj1.add(obj2);
        // then 1 (positive values, expected: (3,5) as sum for each coordinate)
        assertEquals("(3, 5)", obj1.toString());


        // given 2
        Vector2d obj3 = new Vector2d(-2, -3);
        Vector2d obj4 = new Vector2d(-1, -2);
        // when 2
        obj3 = obj3.add(obj4);
        // then 2 (negative values, expected: (-3,-5) as sum for each coordinate)
        assertEquals("(-3, -5)", obj3.toString());


        // given 3
        Vector2d obj5 = new Vector2d(2, 3);
        Vector2d obj6 = new Vector2d(-1, -2);
        // when 3
        obj5 = obj5.add(obj6);
        // then 3 (positive and negative values, expected: (1,1) as sum for each coordinate)
        assertEquals("(1, 1)", obj5.toString());


        // given 4
        Vector2d obj7 = new Vector2d(2, 3);
        Vector2d obj8 = new Vector2d(0, 0);
        // when 4
        obj7 = obj7.add(obj8);
        // then 4 (positive and zero values, expected: (2,3) as sum for each coordinate)
        assertEquals("(2, 3)", obj7.toString());
    }

    @Test
    public void subtractVector2dTest(){
        // given 1
        Vector2d obj1 = new Vector2d(2, 3);
        Vector2d obj2 = new Vector2d(1, 2);
        // when 1
        obj1 = obj1.subtract(obj2);
        // then 1 (positive values, expected: (1,1) as difference for each coordinate)
        assertEquals("(1, 1)", obj1.toString());


        // given 2
        Vector2d obj3 = new Vector2d(-2, -3);
        Vector2d obj4 = new Vector2d(-1, -2);
        // when 2
        obj3 = obj3.subtract(obj4);
        // then 2 (negative values, expected: (-1,-1) as difference for each coordinate)
        assertEquals("(-1, -1)", obj3.toString());


        // given 3
        Vector2d obj5 = new Vector2d(2, 3);
        Vector2d obj6 = new Vector2d(-1, -2);
        // when 3
        obj5 = obj5.subtract(obj6);
        // then 3 (positive and negative values, expected: (3,5) as difference for each coordinate)
        assertEquals("(3, 5)", obj5.toString());


        // given 4
        Vector2d obj7 = new Vector2d(2, 3);
        Vector2d obj8 = new Vector2d(0, 0);
        // when 4
        obj7 = obj7.subtract(obj8);
        // then 4 (positive and zero values, expected: (2,3) as difference for each coordinate)
        assertEquals("(2, 3)", obj7.toString());
    }
}
