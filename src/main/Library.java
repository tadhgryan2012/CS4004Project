package main;

import java.util.ArrayList;

public class Library {
	private ArrayList<Department> deps;
	private ArrayList<Subscription> subs;
	private ArrayList<Library> libraries;

	public Library() {
		deps = new ArrayList<>();
		subs = new ArrayList<>();
		libraries = new ArrayList<>();
	}

	public ArrayList<Library> getLibraries() {
		return libraries;
	}

	public void setLibraries(ArrayList<Library> libraries) {
		this.libraries = libraries;
	}
	public void addLibrary(Library lib) {
		libraries.add(lib);
	}

	public ArrayList<Subscription> getSubs() {
		return subs;
	}

	public void setSubs(ArrayList<Subscription> subs) {
		this.subs = subs;
	}

	public void addSub(Subscription sub) {
		subs.add(sub);
	}

	public ArrayList<Department> getDeps() {
		return deps;
	}

	public void setDeps(ArrayList<Department> deps) {
		this.deps = deps;
	}

	public void shareSubscription(Library lib) {

	}
}