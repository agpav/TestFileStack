package test;

import java.io.IOException;

public class TestFileStack {

	public static final String UPLOAD_DIR = "./src/main/upload/";
	public static final String DOWNLOAD_DIR = "./src/main/download/";

	public static void main(String[] args) throws IOException {
//
//		// Set options and metadata for upload
//		StorageOptions options = new StorageOptions.Builder().mimeType("text/plain").filename("hello.txt").build();

		FileStackHandler fth = new FileStackHandler();

		// fth.list();
		// FileLink fl = fth.upload(UPLOAD_DIR + "audio-example.mp3");
		fth.store();
		fth.transform("XCS3SwQMTFqbnIpdwv9U");
		// fth.uploadAll(UPLOAD_DIR);

		// fth.getAll();

		// fth.deleteAll();

		fth.print();
	}

}
