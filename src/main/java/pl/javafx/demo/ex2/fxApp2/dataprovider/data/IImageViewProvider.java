package pl.javafx.demo.ex2.fxApp2.dataprovider.data;

import java.io.File;
import java.util.List;

import pl.javafx.demo.ex2.fxApp2.dataprovider.ImageVo;

public interface IImageViewProvider {

	IImageViewProvider INSTANCE = new ImageViewProviderImpl();

	List<ImageVo> searchForImages(File directory);

}
