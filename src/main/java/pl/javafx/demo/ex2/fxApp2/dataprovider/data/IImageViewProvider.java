package pl.javafx.demo.ex2.fxApp2.dataprovider.data;

import java.io.File;
import java.util.List;

import pl.javafx.demo.ex2.fxApp2.dataprovider.ImageVo;

// REV: ta klasa powinna byc w pakiecie dataprovider
public interface IImageViewProvider {

	IImageViewProvider INSTANCE = new ImageViewProviderImpl();

	List<ImageVo> searchForImages(File directory);

}
