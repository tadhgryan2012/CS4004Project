package main;

import java.io.File;
import java.util.ArrayList;

public class Database {
	File file;
	ArrayList<String[]> data;

	public Database(File file) {
		this.file = file;
	}
	
	public void writeToDatabase(String data) {

	}
	public String[] getRow(String column, String row) {
		return null;
	}
	public String[] getColumn(String column, String row) {
		return null;
	}
	public String[] getTable(String table) {
		return null;
	}
}
