package com.inzynierkanew.model;

import android.graphics.Bitmap;

import com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType;

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
