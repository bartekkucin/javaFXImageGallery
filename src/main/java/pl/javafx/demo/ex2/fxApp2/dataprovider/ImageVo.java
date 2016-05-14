package pl.javafx.demo.ex2.fxApp2.dataprovider;

import java.io.File;

// REV: ta klasa powinna byc w pakiecie data
public class ImageVo {

	File file;
	Long id;
	String name;

	public ImageVo(File file, Long id) {
		this.file = file;
		this.id = id;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getName() {
		return file.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
