package au.edu.uq.smartass.question;
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArithmeticSeriesModuleTest {

	@Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }
	
    /**
     * Check for 'public' default constructor.
     * 
     * @throws Exception
     */
	@Test
	public void testModule() {
		try {
            Constructor<ArithmeticSeriesModule> constructor
                    = ArithmeticSeriesModule.class.getConstructor();
            assertTrue(true);

        } catch (NoSuchMethodException ex) {
            fail("No default constructor");
        }
	}
	
	@Test
    public void testGetSectionPackage() throws Exception {
		ArithmeticSeriesModule asm = new ArithmeticSeriesModule(new ArithmeticSeriesModule.IntegerGenerator() {
            private int[] ints = {3, 2, 5};
            private int idx = 0;
            @Override public int next(int lower, int uppper) {
                   return ints[idx++];
                }
            });
				
		assertEquals(asm.seq.getLength(),3);
		assertEquals(asm.seq.getLst()[0],3);
		assertEquals(asm.seq.getLst()[1],5);
		assertEquals(asm.seq.getLst()[2],7);
		assertEquals(asm.seq.getDif(),2);
		assertEquals(asm.seq.getTerm(),50);
						
		assertEquals(asm.soln.formatName(),"Let $3,5,7$ be an arithmetic sequence. "
				+ "Determine the 50th term of the sequence");
		assertEquals(asm.soln.formatSolution().get(0), "$a_n=a+(n-1)d$, where $d=5-3=2$ and $a=3$.\\");
		assertEquals(asm.soln.formatSolution().get(1), "$\\therefore a_{50}=3+(50-1)\\cdot 2$\\");
		assertEquals(asm.soln.formatSolution().get(2), "$=3+49 \\cdot2$\\");
		assertEquals(asm.soln.formatSolution().get(3), "$=101$");

	}
	
	@Test
    public void testGetSection() throws Exception {
        ArithmeticSeriesModule asm = new ArithmeticSeriesModule(new ArithmeticSeriesModule.IntegerGenerator() {
            private int[] ints = {0, 5, 10};
            private int idx = 0;
            @Override public int next(int lower, int uppper) {
                return ints[idx++];
            }
        });
        assertEquals(asm.getSection("question"), asm.getSection("QUESTION"));
        assertEquals(asm.getSection("question"), "Let $0,5,10$ be an arithmetic sequence. Determine the 100th term of the sequence" );
        
        assertEquals(asm.getSection("Answer"), asm.getSection("AnSwEr"));
        assertEquals(asm.getSection("answer"), "495");
        
        assertEquals(asm.getSection("soluTION"), asm.getSection("SOLUtion"));
        assertEquals(asm.getSection("solution"), "$a_n=a+(n-1)d$, where $d=5-0=5$ and $a=0$.\\\\"
        		+ "$\\therefore a_{100}=0+(100-1)\\cdot 5$\\\\"
        		+ "$=0+99 \\cdot5$\\\\"
        		+ "$=495$");
        
	}
	
	@Test
    public void testGetSectionFail() throws Exception {
        ArithmeticSeriesModule asm = new ArithmeticSeriesModule();
        try {
            asm.getSection("NonExistantSectionName");
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

}
