package pl.javafx.demo.ex2.fxApp2.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import pl.javafx.demo.ex2.fxApp2.dataprovider.ImageVo;



public class ImageSearchModel {

	private final StringProperty name = new SimpleStringProperty();
	private final ListProperty<ImageVo> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public final String getName() {
		return name.get();
	}
	
	public final void setName(String value) {
		name.set(value);
	}

	public final List<ImageVo> getResult() {
		return result.get();
	}

	public final void setResult(List<ImageVo> arrayList) {
		result.setAll(arrayList);
	}

	public ListProperty<ImageVo> resultProperty() {
		return result;
	}

}
