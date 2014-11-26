import java.math.BigInteger;

import org.jfree.ui.RefineryUtilities;

public class Main {
	
	static String PATH_JSFILE = "./calc.js";
	static String EXTENSION = "js";
	static CalcPoisson poisson;
	static CalcBinomial binomial;
	
	static public BigInteger binomialCoefficient(int n, int k) {
		if (k == n || k == 0)
			return (BigInteger.ONE);
		if (k < 0 || k > n)
			return BigInteger.ZERO;
		if (k == 1)
			return (BigInteger.valueOf(k));

		k = Math.min( k, n - k );

	    long highnumber[] = new long[k];
	    for (int i = 0; i < k; i++)
	        highnumber[i] = n - i;
	    int dividers[] = new int[k - 1];
	    for (int i = 0; i < k - 1; i++)
	        dividers[i] = k - i;

	    for (int divider: dividers) 
	       for (int i = 0; i < k; i++)
	          if (highnumber[i] % divider == 0) {
	             highnumber[i] /= divider;
	             break;
	          }

	    BigInteger result = BigInteger.ONE;
	    for (long high : highnumber)
	       result = result.multiply(BigInteger.valueOf(high));
	    return result;
	}
	
	public static void main(String[] args) {

		if (args.length == 2) {
			int k = Integer.valueOf(args[0]);
			int n = Integer.valueOf(args[1]);

			if (k <= 0 || n <= 0) {
				System.out.println("Value must be greater than zero");
				return ;				
			}
			BigInteger combi = binomialCoefficient(n, k);
			System.out.println("combinaison de " + k + " parmi " + n + " : " + combi.toString());
		}
		else if (args.length == 1) {
			int sec = 0;
			
			sec = Integer.valueOf(args[0]);
			if (sec <= 0) {
				System.out.println("Value must be greater than zero");
				return ;
			}
			poisson = new CalcPoisson(sec, PATH_JSFILE, EXTENSION);
			binomial = new CalcBinomial(sec, PATH_JSFILE, EXTENSION);

			System.out.println("loi binomiale :");
			System.out.println("                temps de calcul : " + String.format("%.2f", binomial.getElapsedTime()) + " ms");
			System.out.println("                probabilite d’encombrement : " + String.format("%.1f", binomial.getEncombrementProba() * 100.0) + " %");
			
			System.out.println("loi de Poisson :");
			System.out.println("                temps de calcul : " + String.format("%.2f", poisson.getElapsedTime()) + " ms");
			System.out.println("                probabilite d’encombrement : " + String.format("%.1f", poisson.getEncombrementProba() * 100.0) + " %");
			
			if (poisson.getPoissonProba() != null && binomial.getBinoProba() != null) {
				Display dis = new Display("203hotline", poisson, binomial);
				dis.pack();
				RefineryUtilities.centerFrameOnScreen(dis);
				dis.setVisible(true);				
			}
		}
		else {
			System.out.println("Usage : 203hotline a [b]");
			return ;
		}
	}
}
