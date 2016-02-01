package com.explorersguild.model;

import com.explorersguild.entities.map.fieldtypeendpoint.model.FieldType;

import android.graphics.Bitmap;

public class DrawableFieldType {

	private FieldType fieldType;
	private Bitmap bitmap;
	
	public DrawableFieldType(FieldType fieldType, Bitmap bitmap) {
		this.fieldType = fieldType;
		this.bitmap = bitmap;
	}

	public FieldType getFieldType() {
		return fieldType;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

}
