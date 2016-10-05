package au.edu.uq.smartass.question;
import au.edu.uq.smartass.engine.QuestionModule;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ArithmeticSeriesModule implements QuestionModule {
	/* Arithmetic Series Module
	 * Generates randomized questions with worked solutions on arithmetic series.
	 */
	
	/** Define supported TeX Sections. */
    public enum Section { QUESTION, SOLUTION, ANSWER }
    
    /** Lookup TeX string. */
    private Map<Section,String> sectionTeX = new EnumMap<>(Section.class);
    
    interface IntegerGenerator { 
    	int next(int lower, int uppper); 
    	}
	
	class Seq {
	/* Sequence class
	 * Generartes a sequence from randomized numbers
	 */
		private int[] seq;
		private int a;
		private int d;
		private int n;
		
		Seq(int a, int d, int n) {
			this.a = a;
			this.d = d;
			this.n = n;

			while (this.d >= -1 && this.d <= 1) {
				this.d = 2; //makes sure sequence is not the same
			}
			
			seq = new int[3];
            for (int i = 0; i < 3; ++i) seq[i] = this.a + this.d*i;
		}
		
		int Soln() {return a+(n-1)*d;}
		
		int[] getLst() {return seq;}
		int getFirst() {return a;}
		int getDif() {return d;}
		int getTerm() {return n;}
		int getLength() {return seq.length;}
	}
	
	class Calculator {
		/* Calculator class
		 * Generates the formatted questions and answers from a sequence
		 */
		private Seq seq;
		
		Calculator(final Seq seq) {
			this.seq = seq;
		}
		
		String formatName() {
			int[] arr = seq.getLst();
			String name = new String(String.format("%d,%d,%d", arr[0], arr[1], arr[2]));
			String term = new String();
			if (seq.getTerm() % 10 == 1) {
				term = seq.getTerm() + "st";
			}
			else if (seq.getTerm() % 10 == 2) {
				term = seq.getTerm() + "nd";
			}
			else if (seq.getTerm() % 10 == 3) {
				term = seq.getTerm() + "rd";
			}
			else {
				term = seq.getTerm() + "th";
			}
			
			String question = new String(String.format("Let $%s$ be an arithmetic sequence. Determine the %s term of the sequence", name, term));
			
			return question;
		}
		
		List<String> formatSolution() {
			List<String> solution = new ArrayList<>();
			
			solution.add(String.format("$a_n=a+(n-1)d$, where $d=%d-%d=%d$ and $a=%d$.\\", seq.getLst()[1], seq.getLst()[0], seq.getDif(), seq.getFirst()));
			solution.add(String.format("$\\therefore a_{%d}=%d+(%d-1)\\cdot %d$\\", seq.getTerm(), seq.getFirst(), seq.getTerm(), seq.getDif()));
			solution.add(String.format("$=%d+%d \\cdot%d$\\", seq.getFirst(), seq.getTerm()-1, seq.getDif()));
			solution.add(String.format("$=%d$", seq.Soln()));
			
			return solution;
		}
		
		String formatAnswer() {return Integer.toString(seq.Soln());}
	}
	
    private IntegerGenerator integers;
	Seq seq;
	Calculator soln;
	
	public ArithmeticSeriesModule() {
		this(new IntegerGenerator() {
            private java.util.Random random = new java.util.Random();
            @Override public int next(final int lower, final int upper) {
                return random.nextInt(upper + 1 - lower) + lower;
            }
        });
	}
	
	ArithmeticSeriesModule(final IntegerGenerator ints) {
		this.integers = ints;
		initialise();
		createQuestionTeX();
		createSolutionTex();
		createAnswerTex();
	}
	
	
	private void initialise() {
		seq = new Seq(integers.next(-10, 10),integers.next(-10, 10),integers.next(2, 10)*10);
		soln = new Calculator(seq);
	}
	
	private void createQuestionTeX() {
        sectionTeX.put(Section.QUESTION, soln.formatName());
	}
	
	private void createSolutionTex() {
		sectionTeX.put(Section.SOLUTION, String.join("\\", soln.formatSolution()));
	}
	
	private void createAnswerTex() {
		sectionTeX.put(Section.ANSWER, soln.formatAnswer());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArithmeticSeriesModule mod = new ArithmeticSeriesModule();
		System.out.println(mod.sectionTeX.get(Section.QUESTION));
		System.out.println(mod.sectionTeX.get(Section.ANSWER));
		System.out.println(mod.sectionTeX.get(Section.SOLUTION));
	
	}
	
	
	/**
     * Accessor for LaTeX associated with a section name.
     *
     * @param name The section name for which the LaTeX is required.
     * @return The LaTeX associated with the given section name, or NULL.
     * @throws IllegalArgumentException if the given name does not translate to a valid section.
     */
    @Override
    public String getSection(final String name) throws IllegalArgumentException {
        return sectionTeX.get(Enum.valueOf(Section.class, name.toUpperCase()));
    }
	
	

}
