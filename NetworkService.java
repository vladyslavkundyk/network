package network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class NetworkService {

	public static String getStringFromURL(String spec, String code) throws IOException {
		URL url = new URL(spec);
		URLConnection connection = url.openConnection();
		String text = "";
		try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), code))) {
			for (;;) {
				String temp = br.readLine();
				if (temp == null) {
					break;
				}
				text += temp + System.lineSeparator();
			}
			return text;
		}
	}

	public static void downloadFileFromURL(String spec, File folder) throws IOException {
		URL url = new URL(spec);
		URLConnection connection = url.openConnection();
		int n = spec.lastIndexOf("/");
		String fileName = spec.substring(n + 1);
		File file = new File(folder, fileName);
		try (InputStream is = connection.getInputStream(); OutputStream os = new FileOutputStream(file)) {
			is.transferTo(os);
		}
	}

	public static String getLinksFromURL(String spec, String code) throws IOException {

		String html = getStringFromURL(spec, code);
		String links = "";
		int start = 0;
		int end = 0;
		String startWith = "<a href=\"https";
		int skip = startWith.length() - "https".length();

		for (;;) {
			String temp = "";
			start = html.indexOf(startWith, end);
			if (start == -1) {
				break;
			}
			start += skip;
			end = html.indexOf("\"", start);
			temp += html.substring(start, end);
			links += temp + System.lineSeparator();
		}
		return links;
	}

	public static void getStatus(File file) throws IOException {

		try (Scanner sc = new Scanner(file)) {
			for (; sc.hasNextLine();) {
				String str = sc.nextLine();
				try {
					URL url = new URL(str);
					HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
					if (urlc.getResponseCode() == 200) {
						System.out.println("Адрес " + str + " доступен!\n");
					} else if (urlc.getResponseCode() == 300) {
						System.out.println("Адрес " + str + " перенаправляемый!\n");
					} else {
						System.out.println("Адрес " + str + " не доступен!\n");
					}
				} catch (IOException e) {
					System.out.println("Адреса " + str + " не существует или он не доступен!\n");
				}
			}
		}
	}
}
