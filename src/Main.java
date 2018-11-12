
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

	public static void main(String[] args) throws IOException {
		FileWriter fileWriter = new FileWriter(new File("./DependencyData.csv"));
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print("Instability, Abstractness\n");
		List<Double> ins = new ArrayList<>();
		List<Double> abs = new ArrayList<>();

		try {

			AtomicInteger numFile = new AtomicInteger(0);
			Files.walk(Paths.get("C:\\Users\\PC\\Desktop\\apex-core-master")).forEach(file -> {
				File dir = new File(file.toString());
				if (dir.isDirectory()) {
					ReadFile fr = new ReadFile();
					for (String myFile : dir.list()) {
						if (myFile.contains(".java")) {
							fr.readInFile(dir.toString(), myFile);
							numFile.incrementAndGet();
						}
					}
					if (fr.getNa() != 0 && fr.getNc() != 0 && fr.getCa() != 0 && fr.getCe() != 0) {
						Formula pack = new Formula(fr.getNa(), fr.getNc(), fr.getCa(), fr.getCe());
						ins.add(pack.getInstability());
						abs.add(pack.getAbstractness());
						if(!Double.isNaN(pack.getInstability())&&!Double.isNaN(pack.getAbstractness())) {
							printWriter.printf("%.6f, %.6f \n", pack.getInstability(), pack.getAbstractness());							
						}

					}
				}

			});
			System.out.println(numFile.get());
			for (int i = 0; i < ins.size() - 1; i++) {
				System.out.printf("%.6f, %.6f \n", ins.get(i), abs.get(i));
			}
			printWriter.close();

		} catch (Exception e) {
			return;
		}
	}

}