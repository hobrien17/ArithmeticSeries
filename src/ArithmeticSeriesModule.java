
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ArithmeticSeriesModule {
	
	/** Define supported TeX Sections. */
    public enum Section { QUESTION, SOLUTION, ANSWER }
    
    /** Lookup TeX string. */
    private Map<Section,String> sectionTeX = new EnumMap<>(Section.class);
	
	class Seq {
		private int[] seq;
		private int a;
		private int d;
		private int n;
		
		Seq() {
			a = randint(-10,10); //-10 to 10
			d = randint(-10,10); //-10 to 10
			n = randint(2,10)*10; //2 to 10 *10
			
			while (d >= -1 && d <= 1) {
				d = 2; //makes sure sequence is not the same
			}
			
			seq = new int[3];
            for (int i = 0; i < 3; ++i) seq[i] = a + d*i;
		}
		
		int Soln() {return a+(n-1)*d;}
		
		int[] Lst() {return seq;}
		int First() {return a;}
		int Dif() {return d;}
		int Term() {return n;}
	}
	
	class Calculator {
		private Seq seq;
		
		Calculator(final Seq seq) {
			this.seq = seq;
		}
		
		String formatName() {
			int[] arr = seq.Lst();
			String name = new String(String.format("%d,%d,%d", arr[0], arr[1], arr[2]));
			String term = new String();
			if (seq.Term() % 10 == 1) {
				term = seq.Term() + "st";
			}
			else if (seq.Term() % 10 == 2) {
				term = seq.Term() + "nd";
			}
			else if (seq.Term() % 10 == 3) {
				term = seq.Term() + "rd";
			}
			else {
				term = seq.Term() + "th";
			}
			
			String question = new String(String.format("Let $%s$ be an arithmetic sequence. Determine the %s term of the sequence", name, term));
			
			return question;
		}
		
		List<String> formatSolution() {
			List<String> solution = new ArrayList<>();
			
			solution.add(String.format("$a_n=a+(n-1)d$, where $d=%d-%d=%d$ and $a=%d$.\\", seq.Lst()[1], seq.Lst()[0], seq.Dif(), seq.First()));
			solution.add(String.format("$\\therefore a_{%d}=%d+(%d-1)\\cdot %d$\\", seq.Term(), seq.First(), seq.Term(), seq.Dif()));
			solution.add(String.format("$=%d+%d \\cdot%d$\\", seq.First(), seq.Term()-1, seq.Dif()));
			solution.add(String.format("$=%d$", seq.Soln()));
			
			return solution;
		}
		
		String formatAnswer() {return Integer.toString(seq.Soln());}
	}
	
	ArithmeticSeriesModule() {
		initialise();
		createQuestionTeX();
		createSolutionTex();
		createAnswerTex();
	}
	
	Seq seq;
	Calculator soln;
	
	private int randint(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	private void initialise() {
		seq = new Seq();
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

}
