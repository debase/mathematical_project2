import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class CalcPoisson {

	static int NB_CALL = 3500;
	static double HOUR = 8.0;
	
	double sec = 0;
	double moyenneCall = 0;
	double encombrementProba = 0;

	Invocable invocableEngine = null;
	List<Double> probaBino = null;
	double elapsedTime = 0;
	
	CalcPoisson(double second, String javaScriptPath, String extension) {

		if (new File(javaScriptPath).exists() == false) {
			System.out.println("\"" + javaScriptPath + "\"" + " doesn't exist");
			return ;
		}
		sec = second;
		moyenneCall = getMoyenneCall(sec);
		invocableEngine = createEngine(javaScriptPath, extension);
		elapsedTime = System.currentTimeMillis();
		probaBino = generateProbaBino();
		encombrementProba = generateEncombrement();
		elapsedTime = System.currentTimeMillis() - elapsedTime;
	}

	public double getMoyenneCall(double sec) {
		double p = sec / (8.0 * 3600.0);
		double m = 3500 * p;
		return (m);
	}
	
	protected List<Double> generateProbaBino() {
		List<Double>	probaList = new LinkedList<Double>();
		
		for (int i = 0 ; i <= 50 ; i++) {
			try {
				Object object = invocableEngine.invokeFunction("probPoisson", new Object[]{moyenneCall, i});
				probaList.add((double)object);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return probaList;
	}
	
	protected double generateEncombrement() {
		double proba = 1.0;
		
		for (int i = 0 ; i <= 25 ; i++) {
			proba -= probaBino.get(i);
		}
		return proba;
	}
	
	protected Invocable createEngine(String javaScriptPath, String extension) {
		try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine javascriptEngine = manager.getEngineByExtension("js");
             
            // Get script from JS File
            @SuppressWarnings("resource")
			FileInputStream fileInputStream = new FileInputStream(javaScriptPath);
            if (fileInputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
         
                javascriptEngine.eval(reader);
                Invocable invocableEngine = (Invocable)javascriptEngine;
                return invocableEngine;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
		return null;
	}
	
	public double getEncombrementProba() {
		return encombrementProba;
	}

	public List<Double> getPoissonProba() {
		return probaBino;
	}

	public double getElapsedTime() {
		return elapsedTime;
	}
}