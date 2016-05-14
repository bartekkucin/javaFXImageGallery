package pl.javafx.demo.ex2.fxApp2.dataprovider.data;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import pl.javafx.demo.ex2.fxApp2.dataprovider.ImageVo;

// REV: ta klasa powinna byc w pakiecie dataprovider
public class ImageViewProviderImpl implements IImageViewProvider {

	@Override
	public List<ImageVo> searchForImages(File directory) {
		ArrayList<ImageVo> resultList = new ArrayList<ImageVo>();
		Long id = 0L;

		if (directory != null) {

			File[] f = directory.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File directory, String fileName) {
					// REV: if nie jest potrzebny
					if (checkIfFileIsACorrectImage(fileName)) {
						return true;
					}
					return false;
				}
			});

			if (f != null) {
				for (File file : f) {
					resultList.add(new ImageVo(file, id++));
				}
			}
		}
		return resultList;
	}

	private boolean checkIfFileIsACorrectImage(String fileName) {
		if (fileName != null) {
			if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".png")
					|| fileName.toLowerCase().endsWith(".jpeg") || fileName.toLowerCase().endsWith(".bmp")
					|| fileName.toLowerCase().endsWith(".gif") || fileName.toLowerCase().endsWith(".bpg")
					|| fileName.toLowerCase().endsWith(".tiff") || fileName.toLowerCase().endsWith(".tif")) {
				return true;
			}
		}
		return false;
	}
}
