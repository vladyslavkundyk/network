package network;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
	public static void main(String[] args) {

		System.out.println("+---+---+---+---+---+---+---+\n");

		String site = "https://github.com/";

		String fileLocation = "D:\\network\\result.txt";
		File resultFile = new File(fileLocation);

		try (PrintWriter pw = new PrintWriter(resultFile)) {
			pw.print(NetworkService.getLinksFromURL(site, "UTF-8"));
			System.out.println("Готово!\n\nПроверьте файл по пути " + resultFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("\n+---+---+---+---+---+---+---+\n");

		try {
			NetworkService.getStatus(resultFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("+---+---+---+---+---+---+---+\n\nРабота программы завершена!");
	}
}
