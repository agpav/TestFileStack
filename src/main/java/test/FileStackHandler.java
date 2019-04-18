package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.filestack.Client;
import com.filestack.Config;
import com.filestack.FileLink;
import com.filestack.transforms.AvTransform;
import com.filestack.transforms.tasks.AvTransformOptions;
import com.google.gson.JsonObject;

public class FileStackHandler {

	public static final String DATA = "./src/main/resources/data.txt";
	public static final String UPLOAD_DIR = "./src/main/upload/";
	public static final String DOWNLOAD_DIR = "./src/main/download/";
	public static final String KEY = "Aa7LmH5DQJSx4rBonGMVRz";

	private Map<String, String> map = new HashMap<>();

	private Client client;
	private Config config;

	public FileStackHandler() throws FileNotFoundException {
		// Create a client
		config = new Config(KEY);
		client = new Client(config);
		map = load();
		print();
	}

	public void print() {
		for (String handle : map.keySet()) {
			System.out.println(handle + " " + map.get(handle));
		}
	}

	public void list() throws IOException {
//		// Check a user's auth status by first trying to list contents of drive
//		CloudResponse response = client.getCloudItems(Sources.AMAZON_DRIVE, "/");
//		String authUrl = response.getAuthUrl();
//		// If auth URL isn't null, user needs to authenticate, open URL in browser
//		if (authUrl != null) {
//			CloudItem[] items = response.getItems();
//			for (CloudItem item : items) {
//				System.out.println(item.getName());
//			}
//		}

		for (String handle : map.keySet()) {
			FileLink fl = new FileLink(config, handle);
		}
	}

	public void get(String handle) throws IOException {
		FileLink fl = new FileLink(config, handle);
		fl.download(DOWNLOAD_DIR);
	}

	public void getAll() throws IOException {
		for (String handle : map.keySet()) {
			get(handle);
		}
	}

	public void delete(String handle) throws IOException {
		FileLink fl = new FileLink(config, handle);
		fl.delete();
		map.remove(handle);
	}

	public void deleteAll() throws IOException {
		for (String handle : map.keySet()) {
			delete(handle);
		}
	}

	public FileLink upload(String fileName) throws IOException {
		// Perform a synchronous, blocking upload
		FileLink file = client.upload(fileName, false);
		map.put(file.getHandle(), fileName);
		return file;
	}

	public void uploadAll(String dir) throws IOException {
		File folder = new File(dir);
		File[] files = folder.listFiles();
		for (File file : files) {
			upload(dir + file.getName());
		}
	}

	public void transform(String handle) throws IOException {
		FileLink fl = new FileLink(config, handle);
		AvTransformOptions avto = new AvTransformOptions.Builder().build();
		AvTransform avt = fl.avTransform(avto);
		JsonObject json = avt.getContentJson();
		System.out.println(json.getAsString());
	}

	public Map<String, String> load() throws FileNotFoundException {

		Map<String, String> map = new HashMap<>();

		Scanner sc = new Scanner(new File(DATA));
		while (sc.hasNextLine()) {
			String[] arr = sc.nextLine().split(" ");
			map.put(arr[0], arr[1]);
		}
		sc.close();

		return map;
	}

	public void store() throws IOException {
		store(map);
	}

	public void store(Map<String, String> map) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(DATA));
		for (String handle : map.keySet()) {
			out.write(handle + " " + map.get(handle) + "\n");
		}
		out.close();
	}
}
